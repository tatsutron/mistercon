#define _FILE_OFFSET_BITS 64

#include <dirent.h>
#include <err.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include "sha1.h"

void hash(char* path, int headerSize) {
    FILE* file = fopen(path, "rb");
    if (file == 0) {
        perror(path);
        err(1, "Failed to open file");
    }

    fseek(file, 0L, SEEK_END);
    long fileSize = ftell(file);
    rewind(file);

    char* buffer = calloc(1, fileSize + 1);
    if (!buffer) {
        fclose(file);
        err(1, "Failed to allocate memory");
    }
    if (fread(buffer, fileSize, 1, file) != 1) {
        fclose(file);
        free(buffer);
        err(1, "Failed to read file");
    }
    fclose(file);

    char result[21];
    SHA1(result, buffer + headerSize, fileSize - headerSize);
    free(buffer);

    char hexresult[41];
    for (size_t offset = 0; offset < 20; offset += 1) {
        sprintf(hexresult + (offset * 2), "%02x", result[offset] & 0xff);
    }

    printf("%s\n", hexresult);
}
 
int main(int argc, char** argv) {
    char* filePath = argv[1];
    int headerSize = atoi(argv[2]);

    hash(filePath, headerSize);

    return 0;
}

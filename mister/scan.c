#define _FILE_OFFSET_BITS 64

#include <dirent.h>
#include <err.h>
#include <regex.h>
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

    printf("%s\t%s\n", path, hexresult);
}
 
void scan(char* dirPath, regex_t* regex, int headerSize) {
    int dirPathLength = strlen(dirPath);
    if (dirPathLength >= FILENAME_MAX - 1) {
        err(1, "Max path length exceeded");
    }
 
    char filePath[FILENAME_MAX];
    strcpy(filePath, dirPath);
    filePath[dirPathLength] = '/';
    dirPathLength += 1;
 
    DIR* dir;
    if (!(dir = opendir(dirPath))) {
        err(1, "IO error");
    }
 
    struct dirent* entry;
    while (entry = readdir(dir)) {
        if (entry->d_name[0] == '.') {
            continue;
        }
        if (!strcmp(entry->d_name, ".") || !strcmp(entry->d_name, "..")) {
            continue;
        }
        int offset = dirPathLength;
        strncpy(filePath + offset, entry->d_name, FILENAME_MAX - offset);
        struct stat stat;
        if (lstat(filePath, &stat) == -1) {
            err(1, "IO error");
        }
        if (S_ISLNK(stat.st_mode)) {
            continue;
        }
        if (S_ISDIR(stat.st_mode)) {
            scan(filePath, regex, headerSize);
            continue;
        }
        if (!regexec(regex, filePath, 0, 0, 0)) {
            hash(filePath, headerSize);
        }
    }
 
    if (dir) {
        closedir(dir);
    }
}
 
int main(int argc, char** argv) {
    char* root = argv[1];
    char* pattern = argv[2];
    int headerSize = atoi(argv[3]);

    regex_t regex;
    if (regcomp(&regex, pattern, REG_EXTENDED | REG_NOSUB) != 0) {
        err(1, "Bad regex pattern");
    }

    scan(root, &regex, headerSize);
    regfree(&regex);

    return 0;
}

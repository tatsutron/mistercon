#define _FILE_OFFSET_BITS 64

#include <dirent.h>
#include <err.h>
#include <regex.h>
#include <stdio.h>
#include <string.h>
#include <sys/stat.h>
 
void list(char* dirPath, regex_t* regex) {
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
            list(filePath, regex);
            continue;
        }
        if (!regexec(regex, filePath, 0, 0, 0)) {
            printf("%s\n", filePath);
        }
    }
 
    if (dir) {
        closedir(dir);
    }
}
 
int main(int argc, char** argv) {
    char* root = argv[1];
    char* pattern = argv[2];

    regex_t regex;
    if (regcomp(&regex, pattern, REG_EXTENDED | REG_NOSUB) != 0) {
        err(1, "Bad regex pattern");
    }

    list(root, &regex);
    regfree(&regex);

    return 0;
}

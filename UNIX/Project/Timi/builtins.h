#ifndef BUILTINS_H_
#define BUILTINS_H_

#include <sys/wait.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <err.h>
#include <ctype.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <fcntl.h>

struct bi // BuiltIn
{
    int (*function)(char **args);
    const char *name;
};

int shCD(char **args);
int shHELP(char **args);
int shEXIT(char **args);
int number_of_builtins();

extern struct bi builtins[];


#endif

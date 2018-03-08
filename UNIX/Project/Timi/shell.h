#ifndef SHELL_H_
#define SHELL_H_

#include "builtins.h"

void redirect(int i, char **args);
int createProcess(int in, int out, int i, char **args, int pipe[]);
int runNewProcess(char **args);
int shExecuteCommand(char **args);
char *shReadLine();
char **shParseLine(char *line);
void shStart();

#endif

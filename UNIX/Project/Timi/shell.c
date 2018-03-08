

#include "shell.h"


#define BUFSIZE 1024

/** Takes the command arguments as input
  * Checks for the last parameters
  * If a redirection command was used, performs the appropiate action
  * Commands implemented: >, <, >>
*/
void redirect(int i, char **args)
{
  // fprintf(stderr, "%d\n", i);
  if (i > 2)
  {
    if (!strcmp(args[i - 2], ">"))
    {
      int out;
      unlink(args[i - 1]);
      if ((out = open(args[i - 1], O_WRONLY | O_CREAT, 0666)) == -1)
      {
        err(1, "Error opening output file");
      }
      if (close(1) == - 1)
      {
        err(1, "Error closing file");
      }
      if (dup(out) == - 1)
      {
        err(1, "Error duplicating file descriptor ");
      }
      if (close(out) == -1)
      {
        err(1, "Error closing file descriptor");
      }
      args[i - 2] = NULL;
    }
    else if (!strcmp(args[i - 2], "<"))
    {
      int in;
      if ((in = open(args[i - 1], O_RDONLY)) == -1)
      {
        err(1, "Error opening file");
      }
      if (close(0) == - 1)
      {
        err(1, "Error closing file");
      }
      if (dup(in) == - 1)
      {
        err(1, "Error duplicating file descriptor ");
      }
      if (close(in) == -1)
      {
        err(1, "Error closing file descriptor");
      }
      args[i - 2] = NULL;
    }
    else if (!strcmp(args[i - 2], ">>"))
    {
      int out;
      if ((out = open(args[i - 1], O_WRONLY | O_APPEND | O_CREAT, 0666)) == -1)
      {
        err(1, "Error opening file");
      }
      if (close(1) == - 1)
      {
        err(1, "Error closing file");
      }
      if (dup(out) == - 1)
      {
        err(1, "Error duplicating file descriptor ");
      }
      if (close(out) == -1)
      {
        err(1, "Error closing file descriptor");
      }
      args[i - 2] = NULL;
    }
  }
}



/**
  * Creates a new process and executes the given command
  * Process is executed via execvp
  * If in or out parameters don't get default values(0, 1)
  * Then the respective file descriptors are redirected to the pipe ends
  * The function returns the process id of the new process
  *
*/
int createProcess(int in, int out, int i, char **args, int pipe[])
{
  int pid;
  switch(pid = fork())
  {
    case -1:
      err(1, "Error with fork");
    case 0:
      redirect(i, args);
      if (out != 1)
      {
        if (close(1) == -1)
          err(1, "close error");
        if (dup(pipe[1]) == -1)
          err(1, "dup error");

        if (close(pipe[0]) == -1)
          err(1, "close error");
        // fprintf(stderr, "%s");
        if (close(pipe[1]) == -1)
          err(1, "close error");
      }
      if (in != 0)
      {
        if (close(0) == -1)
          err(1, "close error");
        if (dup(pipe[0]) == -1)
          err(1, "dup error");
        if (close(pipe[0]) == -1)
          err(1, "close error");
        if (close(pipe[1]) == -1)
          err(1, "close error");
      }
      execvp(args[0], args);
      err(1, "Error with exec call");
      return 1;
  }
  return pid;
}



/**
  * Runs a new process via the execvp command, with the help of the createProcess function
  * Checks for the presence of a pipe. If a pipe is present
  * The command is split into two separate commands
  * Currently only one pipe is supported
  * Returns 1 for continous execution
  *
*/
int runNewProcess(char **args)
{
  pid_t pid;
  int status;

  //check for pipe
  int i = 0, j = 0, t;
  int isPipe = 0;
  char **cmd1 = args;
  char **cmd2 = malloc(BUFSIZE * sizeof(char*));
  while (args[i])
  {
    if (!strcmp(args[i], "|"))
    {
      isPipe = 1;
      cmd2 = args + i + 1;
      j = t = i;
      cmd1[i] = NULL;
      i++;
      while(args[i] && strcmp(args[i], "|"))
      {
        i++;
      }
      if (args[i])
      {
        fprintf(stderr, "Multiple pipes are not supported!\n");
        return 1;
      }
      break;
    }
    i++;
  }
  if (isPipe)
  {
    j = i - j - 1;
    i = t;
  }

  if (!isPipe)
  {
        pid = createProcess(0, 1, i, args, NULL);
        do
        {
          waitpid(pid, &status, WUNTRACED);
        } while (!WIFEXITED(status) && !WIFSIGNALED(status));

  }
  else
  {
    int pd[2], pid1, pid2;
    if (pipe(pd) == - 1)
    {
      err(1, "Error creating pipe");
    }
    pid1 = createProcess(0, 2, i, cmd1, pd);


    pid2 = createProcess(2, 1, j, cmd2, pd);
    if (close(pd[0]) == -1)
      err(1, "Error closing file descriptor");
    if (close(pd[1]) == -1)
      err(1, "Error closing file descriptor");
    do
    {
      waitpid(pid1, &status, WUNTRACED);
    } while (!WIFEXITED(status) && !WIFSIGNALED(status));
    do
    {
      waitpid(pid2, &status, WUNTRACED);
    } while (!WIFEXITED(status) && !WIFSIGNALED(status));
  }

  return 1;
}



/**
  * Firstly it checks if the command input matches one of the builtin functions
  * If it does, it executes the given function
  * If it does not, it creates a new process for the input command with the help of the above functions.
  *
*/
int shExecuteCommand(char **args)
{
  int i;

  if (args[0] == NULL)
  {
    return 1;
  }
  for (i = 0; i < number_of_builtins(); i++)
  {
    if (!strcmp(args[0], builtins[i].name))
    {
      return (*builtins[i].function)(args);
    }
  }
  return runNewProcess(args);
}



/**
  * Reads a line character by characer from user input
  * Returns said line
  *
*/
char *shReadLine()
{
  int bufsize = BUFSIZE;
  int position = 0;
  char *buffer = malloc(sizeof(char) * bufsize);
  int c;

  if (!buffer)
  {
    err(1, "Error allocating buffer");
  }

  while (1)
  {
    c = getchar();

    if (c == '\n' || c == EOF)
    {
      buffer[position] = '\0';
      return buffer;
    }
    else
    {
      buffer[position] = c;
    }
    position++;

    if (position >= bufsize)
    {
      bufsize += BUFSIZE;
      buffer = realloc(buffer, bufsize);
      if (!buffer)
      {
        err(1, "Error allocating buffer");
      }
    }
  }
}



/**
  * Parses a line into a string array
  * Returns said array
  *
*/
char **shParseLine(char *line)
{
  char **args = malloc(BUFSIZE * sizeof(char*));
  int i = 0;
  int pos = 0;
  while(i < strlen(line))
  {
    char *tmp = malloc(BUFSIZE * sizeof(char*));
    int k = 0;
    if (line[i] == '"')
    {
      i++;
      while(line[i] != '"' && i < strlen(line))
      {
        tmp[k] = line[i];
        i++;
        k++;
      }
    }
    else
    {
      while(!isspace(line[i]) && i < strlen(line))
      {
        tmp[k] = line[i];
        i++;
        k++;
      }
    }
    while(isspace(line[i]) && i < strlen(line))
      i++;
    args[pos] = tmp;
    pos++;

  }
  args[pos] = NULL;
  return args;
}



/**
  * Driving function of the shell program
  * Prints out the prompt with user and directory information
  * Waits for user input
  * Currently only one line of input is supported
  * Parses the input line into a string array
  * Executes the command
  *
*/
void shStart()
{
  char *line;
  char **args;
  int status;
  char *cwd = (char*)malloc(BUFSIZE * sizeof(char*));
  char *usr = (char*)malloc(BUFSIZE * sizeof(char*));
  char *hm = (char*)malloc(BUFSIZE * sizeof(char*));
  do {
    cwd = getcwd(cwd, BUFSIZE);
    usr = getenv("USER");
    hm = getenv("HOME");
    cwd += strlen(hm) - 1;
    cwd[0] = '~';
    printf("%s@%s> ", usr, cwd);

    line = shReadLine();
    args = shParseLine(line);
    status = shExecuteCommand(args);

    free(line);
    free(args);
  } while (status);
}

#include "builtins.h"

#define BUFSIZE 1024

#define EXTMSG "Exiting custom shell..."



int shCD(char **args);
int shHELP(char **args);
int shEXIT(char **args);

struct bi builtins[] = 
{
    { shCD, "cd"},
    { shHELP, "help"},
    { shEXIT, "exit"}
};

int number_of_builtins()
{
  return sizeof(builtins) / sizeof(struct bi);
}


int shCD(char **args)
{
  if (args[1] == NULL)
  {
    fprintf(stderr, "Usage: cd <directory>\n");
  }
  else
  {
    if (chdir(args[1]))
    {
      err(1, "Error changing directory");
    }
  }
  return 1;
}

int shHELP(char **args)
{
  int i;
  printf("Udvarhelyi Timea Basic Shell\n");
  printf("Basic shell implementation.");
  printf("To run commands, type in the command name and arguments and press ENTER.\n");
  printf("Functionality includes: running builtin functions and other programs.\n");
  printf("Piping is possible only with one pipe.\n");
  printf("The following are built in:\n");

  for (i = 0; i < number_of_builtins(); i++) {
    printf(" *%s\n", builtins[i].name);
  }

  printf("For usage of other commands, the man page can be used for help.\n");
  return 1;
}

int shEXIT(char **args)
{
  printf("%s\n", EXTMSG);
  return 0;
}

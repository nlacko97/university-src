
/*
    *
    * Udvarhelyi Timea
    * Code Project for NSWI015
    * Basic shell code implementation
    * Possibility of running builtin commands and executing other programs
    * Implemented usage of pipes and shell redirection
    *
*/


#include "builtins.h"
#include "shell.h"



int main(int argc, char **argv)
{

  printf("----------------Interactive shell----------------\n");
  printf("by Udvarhelyi Timea\n");
  printf("For usage and other information type in the help command\n\n");

  shStart();

  return 0;
}

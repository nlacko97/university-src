#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <err.h>

/*
  *
  * FORK()
  * parent prints its PID
  * child prints its PPID
*/

int main(int argc, char *argv[]) {
  pid_t pid;
  switch(pid = fork()) {
    case -1: {
      err(1, "Error forking");
    }
    case 0: {
      printf("I'm child, my ppid is: %d\n", getppid());
    }break;
    default: {
      printf("I'm parent, my pid is: %d\n", getpid());
    }
  }
  return 0;
}

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <err.h>

/*
 * Use fork() to create 16 processes(including a.out)
 * Every process will print its PID and exit
*/

int main(int argc, char *argv[]) {

  pid_t pid = getpid();

  fork();
  fork();
  fork();
  fork();


  printf("pid: %d\n", getpid());
  if (getpid() == pid)
    sleep(1);

  return 0;
}

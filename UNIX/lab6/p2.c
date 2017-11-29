#include <stdlib.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <err.h>
#include <unistd.h>
#include <stdio.h>
#include <time.h>

/*
 * ./a.out <n>
 * fork n times
 * every child execs sleep with random nr. of seconds
 * parent waits for all children, prints out PIDs as the children exit
 * -after- all children exit, the parent exits
 */

 int getRandomNumber(int start, int end) {

   return rand() % end + start;

 }

 int main(int argc, char const *argv[]) {

   if (argc != 2)
    err(1, "Usage: %s <n>", argv[0]);

   int n = atoi(argv[1]);
   srand(time(NULL));
   int seconds;

   for(int i = 0; i < n; i++)
   {
     switch(fork())
     {
       case -1:
        err(1, "Error with fork");
       case 0:
         seconds = getRandomNumber(0, 5);
         char *sec = (char*)malloc(2);
         sprintf(sec, "%d", seconds);
         printf("%d. child sleeping for %s seconds...\n", i, sec);
         execl("/bin/sleep", "sleep", sec, NULL);
         err(1, "Exec error");
         break;
     }
   }
   int pid;
   for(int i = 0; i < n; ++i)
   {
    //  (void)wait(NULL);
    pid = wait(NULL);
    if (pid != -1)
      printf("Child exiting, PID:%d\n", pid);
    else
      err(1, "Error with child");
   }
 }













//

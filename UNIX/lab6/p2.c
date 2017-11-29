#include <stdlib.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <semaphore.h>
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
         seconds = rand() % 4;
         char *sec = (char*)malloc(2);
         sprintf(sec, "%d", seconds);
         printf("%s\n", sec);
         execl("/bin/sleep ", "sleep", sec, NULL);
         err(1, "Exec error");
         break;
     }
   }
   for(int i = 0; i < n; ++i)
   {
     (void)wait(NULL);
   }
 }

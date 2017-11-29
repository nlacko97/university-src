

/* init semaphor to 0
 * fork()
 * in parent sleep(5), sem_post()
 * in child sem_wait()
 * print debug messages
 */
#include <stdlib.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <semaphore.h>
#include <err.h>
#include <unistd.h>
#include <stdio.h>

int main(int argc, char* argv[])
{
  sem_unlink("/semaphore");
  sem_t *sem = sem_open("/semaphore", O_CREAT | O_EXCL, S_IRWXU, 0);
  if (sem == SEM_FAILED)
    err(1, "Semaphore already created");

  switch(fork())
  {
    case -1:
      err(1, "Fork error");
    case 0:
      sem_wait(sem);
      //write(1, "I'm the child\n", 15);
      printf("I'm the child\n");
      break;
    default:
      //write(1, "Parent sleeping...\n", 19);
      printf("Parent sleeping\n");
      sleep(3);
      sem_post(sem);
      //write(1, "Parent posting...\n", 18);
      printf("Parent posting\n");
      sem_close(sem);
  }
}

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <err.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>

#define BUFSIZE 256

/*
 * Use mkfifo() to create a named pipe
 * In parent, open pipe and write something to it
 * In the child, open pipe, read what the parent wrote and write it to STOUDT
*/

int main(int argc, char const *argv[]) {

  const char* fname= "myfifo";

  if (mkfifo(fname, 0666) == -1) {
    err(1, "Error making fifo file");
  }

  switch(fork()) {
    case -1: {
      err(1, "Fork errror");
    }
    case 0: {
      int fd1 = open(fname, O_RDONLY);
      if (fd1 == -1)  {
        err(1, "Error opening file");
      }
      char *response = (char*)malloc(BUFSIZE*sizeof(char*));
      if (read(fd1, response, BUFSIZE) == -1) {
          err(1, "Error reading file");
      }
      printf("Received: %s\n", response);
      close(fd1);
    }break;
    default: {
      int fd = open(fname, O_WRONLY);
      if (fd == -1)  {
        err(1, "Error opening file");
      }
      const char* msg = "Hello World!";
      if (write(fd, msg, strlen(msg)) == -1) {
        err(1, "Error writing to file");
      }
      close(fd);
      unlink(fname);
    }
  }

  return 0;
}

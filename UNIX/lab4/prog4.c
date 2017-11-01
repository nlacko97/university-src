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
 * pipe /etc/passwd through
 * parent writes /etc/passwd to fifo -> child saves to argv[1] file
*/

int main(int argc, char const *argv[]) {

  if (argc != 2) {
    err(1, "Usage: %s <filename>", argv[0]);
  }

  const char* fname= "myfifo";

  if (mkfifo(fname, 0666) == -1) {
    err(1, "Error making fifo file");
  }

  switch(fork()) {
    case -1:
      err(1, "Error with fork");
    case 0:{
      int fd2 = open(fname, O_RDONLY);
      if (fd2 == -1)  {
        err(1, "Error opening file");
      }
      int fd3 = open(argv[1], O_WRONLY | O_CREAT, 0666);
      if (fd3 == -1)  {
        err(1, "Error opening file");
      }
      char *buf1 = (char*)malloc(BUFSIZE*sizeof(char*));
      while(read(fd2, buf1, BUFSIZE)) {
        write(fd3, buf1, BUFSIZE);
      }
      close(fd2);
      close(fd3);
    }break;
    default:{
      int fd = open(fname, O_WRONLY);
      if (fd == -1)  {
        err(1, "Error opening file");
      }
      int fd1 = open("/etc/passwd", O_RDONLY);
      if (fd1 == -1)  {
        err(1, "Error opening file");
      }
      char *buf = (char*)malloc(BUFSIZE*sizeof(char*));
      while(read(fd1, buf, BUFSIZE)) {
        write(fd, buf, BUFSIZE);
      }
      close(fd);
      close(fd1);
      unlink(fname);
    }
  }

  return 0;
}

#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <err.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <libgen.h>
#include <string.h>

/*
 * Exam Assingment ==========
   * ./a.out <path_to_cmd>
   * read the current directory
   * ignore non-regular files
   * for every regular file:
   *      - open it
   *      - pipe all the data from the file to the command in the argument
   * test: ./a.out /bin/wc
 */

#define BUFSIZE 256
#define OUT 1
#define IN 0

int
main(int argc, char *argv[])
{
  if(argc != 2)
  {
    fprintf(stderr, "Usage: %s <path_to_cmd>\n", argv[0]);
    exit(1);
  }

  DIR *d;
  struct dirent *dir;
  struct stat st;
  int pd[2];
  // Creating pipe
  if (pipe(pd) == -1)
    err(1, "Error creating pipe!");

  switch(fork())
  {
    case -1:
      err(1, "Error with fork!");
    case 0:
      // Redirecting stdout to pipe, closing all other fildescriptors
      if (close(OUT) == -1)
        err(1, "Error closing file descriptor");
      if (dup(pd[OUT]) == -1)
        err(1, "Error duplicating file descriptor");
      if (close(pd[IN]) == -1)
        err(1, "Error closing file descriptor");
      if (close(pd[OUT]) == -1)
        err(1, "Error closing file descriptor");

      fprintf(stderr, "Child with PID: %d created. Parent PID: %d\n", getpid(), getppid());

      // Opening directory
      if(!(d = opendir(".")))
      {
        err(1, "Could not open current directory!");
      }
      else
      {
        // Iterating on directory entries
        while((dir = readdir(d)) != NULL)
        {
          // Obtaining file information about the directory entry
          int ls = lstat(dir->d_name, &st);
          if (ls == -1)
            err(1, "Error getting file information for %s!", dir->d_name);

          // The mode field needs to be masked in order to obtain the file type
          switch(st.st_mode & S_IFMT)
          {
            case S_IFREG:
              fprintf(stderr, "Opening file %s\n", dir->d_name);
              int fd;
              // File is opened for reading
              fd = open(dir->d_name, O_RDONLY);
              if (fd == -1)
              {
                warn("Error opening %s", dir->d_name);
                continue;
              }

              char *buf = (char*)malloc(BUFSIZE*sizeof(char*));
              int bsr; // bytes read

              // The contents of the file are written to stdout
              while(bsr = read(fd, buf, BUFSIZE))
              {
                if (bsr == -1)
                  err(1, "Error while reading from file");
                if (write(OUT, buf, bsr) == -1)
                  err(1, "Error while writing");
              }

            break;
            default:
              fprintf(stderr, "Ignoring non-regular file: %s\n", dir->d_name);
          }
        }
        closedir(d);
      }
      break;
    default:
      // The stdin is redirected to the pipe, unwanted file descriptor are closed
      if (close(IN) == -1)
        err(1, "Error closing file descriptor");
      if (dup(pd[IN]) == -1)
        err(1, "Error duplicating file descriptor");
      if (close(pd[IN]) == -1)
        err(1, "Error closing file descriptor");
      if (close(pd[OUT]) == -1)
        err(1, "Error closing file descriptor");
      fprintf(stderr, "Parent with PID: %d started.\n", getpid());
      char *cmd;
      cmd = basename(argv[1]);
      execl(argv[1], cmd, (char *)NULL);
      err(1, "Error with exec call");
  }

}

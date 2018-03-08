/**
Udvarhelyi Timea
useage: fill /tmp/fifo with command echo -ne "<n>\n<data with n bytes>\n<n>\n<data>...." > /tmp/fifo
eg.:echo -ne "3\nabc\n15\n123456789012345" > /tmp/fifo

*/
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <string.h>
#include <fcntl.h>
#include <ctype.h>
#include <err.h>

#define NL "\n"
#define BUFSIZE 1024

int
main(int argc, char** argv)
{
  //arg checking
  if (argc != 2)
    errx(1, "Usage: %s <fifoname>", argv[0]);

  //deletinf existing file with name of argument (if it is the case)
  unlink(argv[1]);
  //creating new fifo
  if (mkfifo(argv[1], 0666) == -1)
  {
      err(1, "Error creating named pipe.");
  }
  int fd1, fd2;
  fd1 = open(argv[1], O_RDWR);
  if (fd1 == -1)
  {
    err(1, "Error opening named pipe");
  }
  int ch;
  lseek(fd1, 5, SEEK_CUR);
  while(1)
  {
    read(fd1, &ch, sizeof(char));
    write(1, &ch, sizeof(char));

  }
}
/*
  fd2 = open("/tmp/fifo", O_RDONLY);
  if (fd2 == -1)
  {
      err(1, "Error opening input file");
  }

  int ch, b;
  char c;

  while(1)
  {
    //reading n (nr of bytes)
    int n = 0;
    b = read(fd2, &ch, sizeof(char));
    if (!b)
      break;
    c = ch;
    while(!isspace(c) && ch != EOF)
    {
      if (isdigit(c))
      {//converting to number
        n = n * 10 + (c - '0');
      }
      else
      {
        errx(1, "Input error");
      }

      b = read(fd2, &ch, sizeof(char));
      if(b==-1)
      {
        errx(1, "read error");
      }
      c = ch;
    }

    //reading data
    int fd3;
    unlink("tmp");
    fd3 = open("tmp", O_RDWR | O_CREAT, 0666);
    if (fd3 == -1)
      err(1, "Error opening temporary file");
    for(int i = 0; i < n; i++)
    {
      //if data is not of n bytes or there is an error
      if (read(fd2, &ch, sizeof(char)) < 1)
        {
          errx(1, "Input error");
        }
      c = ch;
      if (isspace(c) || ch == EOF)
        {
          errx(1, "Input error");
        }
      if(write(fd3, &ch, sizeof(char))==-1)
      {
        errx(1,"Write error");
      }
    }

    for(int i = 0; i < n; i++)
    {
      if(lseek(fd3, -i - 1, SEEK_END)==-1)
      {
        errx(1,"Lseek error");
      }
      if(read(fd3, &ch, sizeof(char))==-1)
      {
        errx(1,"Read error");
      }
      if(write(fd1, &ch, sizeof(char))==-1)
      {
        err(1,"Write erro");
      }
    }
    close(fd3);
    //separating data structures
    if(write(fd1, "\n", sizeof(char))==-1)
    {
      errx(1,"Write error");
    }
  }
  close(fd2);
  char *buf = malloc(BUFSIZE*sizeof(char*));
  while((b = read(fd1, buf, BUFSIZE)) != -1)
    if(write(1, buf, b)==-1)
    {
      errx(1,"Write error");
    }
  close(fd1);
  unlink(argv[1]);

}*/

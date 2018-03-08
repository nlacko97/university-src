#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>
#include <unistd.h>
#include <err.h>
#include <ctype.h>
#include <errno.h>

#define BUFSIZE 256

/*
   *
   *-i <infile>    -> read input from infile
   *-x a           -> convert to lowercase
   *-x A           -> convert to uppercase
   *[-o <outfile>] -> write output to file
   *
*/

char* transformString(char* s, int option) // 1 - tolower, 2 - toupper
{
  switch(option)
  {
    case 1: {
      for(int i = 0; i < BUFSIZE; i++) {
        s[i] = tolower(s[i]);
      }
    }break;
    case 2: {
      for(int i = 0; i < BUFSIZE; i++) {
        s[i] = toupper(s[i]);
      }
    }break;
    default: {
      err(1, "Invalid option.");
    }
  }
  return s;
}

int main(int argc, char* argv[]) {
  int opt;
  struct f {
    int   x; // -x option
    char *i; // input file name
    char *o; // output file name
  }flags;
  flags.o = "";
  while((opt = getopt(argc, argv, "i:o:x:")) != -1) {
    switch(opt) {
      case 'x': {
        if (!strcmp(optarg, "a"))
          flags.x = 1;
        else if (!strcmp(optarg, "A"))
          flags.x = 2;
        else
          err(1, "Invaild option - %s", optarg);
      }break;
      case 'i': {
        flags.i = strdup(optarg);
      }break;
      case 'o': {
        flags.o = strdup(optarg);
        printf("%s\n", flags.o);
      }
    }
  }

  int fd = open(flags.i, O_RDONLY), fd2;
  if (fd == -1) {
    err(errno, "Couldn't open file %s", flags.i);
  }

  char *buf = (char*)malloc(BUFSIZE*(sizeof(char*)));

  if (strcmp(flags.o, "")) {
    fd2 = open(flags.o, O_WRONLY | O_CREAT, 0666);
    if (fd2 == -1) {
      err(errno, "Couldn't open file %s", flags.o);
    }
  }

  while(read(fd, buf, BUFSIZE)) {

    buf = transformString(buf, flags.x);

    switch(strcmp(flags.o, ""))
    {
      case 0: {
        printf("%s", buf);
      }break;

      default: {
        write(fd2, buf, BUFSIZE);
      }
    }
  }

  close(fd);
  close(fd2);

  exit(0);
}

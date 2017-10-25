#include <stdio.h>
#include <stdlib.h>
#include <err.h>
#include <fcntl.h>
#include <unistd.h>
/*
 * open() a file
 */

int main(int argc, char* argv[])
{
  while(1) {
    int f = open(argv[1], O_RDWR);
    if (f != -1) {
      printf("File opened: %s\n", argv[1]);
      // close(f);
    }
    else {
      err(1, "%s", argv[1]);
    }
  }
  exit(0);
}

#include <stdio.h>
#include <stdlib.h>
#include <err.h>
#include <unistd.h>
#include <fcntl.h>
#include <time.h>
#include <string.h>

#define RAND_STR_SIZE 10

/*
  * ./a.out "hello"
  * main:           h
                    <random file1>
  * <random file1:  e
                    <random file2>
          .
          .
          .

  * TODO write the testing shell script in C
*/

void testing()
{
  char *s = (char*)malloc(RAND_STR_SIZE*sizeof(char*));
  s = "main";
  char *k = (char*)malloc(RAND_STR_SIZE*sizeof(char*));
  char *c = (char*)malloc(2*sizeof(char*));
  while(1)
  {
    while(1)
    {
      int fd = open(s, O_RDWR);
      if (fd == -1)
        err(1, "Error opening file");
      read(fd, c, 2*sizeof(char));
      read(fd, k, RAND_STR_SIZE);
      printf("%c + %d\n", c[0], strlen(k));
      if (strlen(k) == 1)
        exit(0);
      s = k;
    }
  }
}

int main(int argc, char* argv[])
{
  if (argc != 2) {
    err(1, "Usage: ./a.out <string>");
  }

  srand(time(NULL));
  char *newLine = "\n";
  int len = strlen(argv[1]);
  char *s = (char*)malloc(RAND_STR_SIZE*sizeof(char*));

  int fd = open("main", O_WRONLY | O_CREAT, 0666);
  if (fd == -1)
    err(1, "Couldn't open file.");
  write(fd, argv[1], sizeof(char));
  write(fd, newLine, sizeof(char));
  for(int i = 0; i < RAND_STR_SIZE; i++) {
    s[i] = rand() % 26 + 'a';
  }
  write(fd, s, RAND_STR_SIZE);
  write(fd, newLine, sizeof(char));
  close(fd);
  for(int i = 1; i < len; i++)
  {
    fd = open(s, O_WRONLY | O_CREAT, 0666);
    write(fd, argv[1] + i, sizeof(char));
    write(fd, newLine, sizeof(char));
    for(int i = 0; i < RAND_STR_SIZE; i++) {
      s[i] = rand() % 26 + 'a';
    }
    if (i < len - 1)
    {
      write(fd, s, RAND_STR_SIZE);
      write(fd, newLine, sizeof(char));
    }
    close(fd);
  }

  testing();

}

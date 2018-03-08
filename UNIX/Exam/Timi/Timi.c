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

/**
  *
  * Udvarhelyi Timea
  * Usage: ./a.out <fifoname>
  *        from another terminal: echo -ne "4\n1234" > <fifoname>
  * Stop a.out with ^C
*/

int
createTemporaryFile()
{
  int tmp;
  char tmpFileName[] = "/tmp/fifoXXXXXX";
  if ((tmp = mkstemp(tmpFileName)) == -1)
    err(1, "Error creating temporary file");
  return tmp;
}

int
readN (int pipe)
{
  int n = 0, ch, done = 0;
  do
  {
    if (read(pipe, &ch, sizeof(char)) == -1)
      err(1, "Reading error");
    if (strchr(" \n\t\r\a", ch))
      done = 1;
    else
    {
      if (isdigit((char)ch))
        n = n * 10 + ((char)ch - '0');
      else
        errx(1, "Input error");
    }
  } while (!done);

  if (!n)
    errx(1, "Input error: n needs to be > 0");

  return n;
}


void
readAndReverseData(int pipe, int tmp, int n)
{
  int ch;
  for(int i = 0; i < n; i++)
  {
    if (read(pipe, &ch, sizeof(char)) == -1)
      err(1, "Reading error");
    if (write(tmp, &ch, sizeof(char)) == -1)
      err(1, "Error writing to temporary file");
  }

  for(int i = 0; i < n; i++)
  {
    if (lseek(tmp, -i - 1, SEEK_END) == -1)
      err(1, "Seek error");
    if (read(tmp, &ch, sizeof(char)) == -1)
      err(1, "Reading error");
    if (write(1, &ch, sizeof(char)) == -1)
      err(1, "Writing error");
  }
}

int
main (int argc, char **argv)
{
  if (argc != 2)
    errx(1, "Usage: %s <fifoname>", argv[0]);
  unlink(argv[1]);
  if (mkfifo(argv[1], 0666) == -1)
    err(1, "Error creating named pipe");

  int pipe, tmp;
  if ((pipe = open(argv[1], O_RDWR)) == -1)
    err(1, "Error opening named pipe");


  while (1)
  {

    int tmp = createTemporaryFile();

    int n = readN(pipe);

    readAndReverseData(pipe, tmp, n);

    write(1, "\n", sizeof(char));
  }

}

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <string.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <err.h>
int
main(int arc, char **argv)
{

	if (mkfifo(argv[1], 0666) == -1)
    err(1, "fifo");
	int n;
	char *buf = malloc(1024*sizeof(char*));


  switch(fork())
  {
    case -1: err(1, "fork");
    case 0:
      {
        int fd1 = open("/tmp/fifo", O_RDWR);
      	read(fd1, buf, sizeof(int));
      	n = atoi(buf);
      	int size = lseek(fd1, 0, SEEK_END);
      	char *addr, *p1, *p2;
      	p1 = addr = mmap(0, size, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd1, 0);
      	p2 = p1 + size - 1;
      	p1 += 2;
      	while(p1 < p2) {char c = *p1; *p1++ = *p2; *p2-- = c;}
      	addr += 2;
      	addr[n] = '\0';
        int fdfif = open(argv[1], O_WRONLY);
        if(fdfif == -1) err(1, "open");
        write(fdfif, addr, sizeof(addr));
        close(fdfif);
      }
    default:
      {
        int ff = open(argv[1], O_RDONLY);
        read(ff, buf, 100);
        write(1, buf, sizeof(buf));
        close(ff);
        unlink(argv[1]);
      }


  }


}

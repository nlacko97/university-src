#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <err.h>

// pstree, lsof
// pipe standard input through n children, back to std outptu

#define BUFSIZE 256
#define READ 0
#define WRITE 1

int main(int argc, char* argv[])
{
	// if (argc != 2)
	// 	err(1, "Usage: %s <N>", argv[0]);
	int in[2], out[2];
	pipe(in);
	pipe(out);
	// int n = atoi(argv[1]);
	char *buf = (char*)malloc(BUFSIZE*sizeof(char*));


	if (fork() == 0)
	{
		close(in[READ]);
		close(in[WRITE]);
		dup2(out[READ], READ);
		close(out[READ]);
		dup2(out[WRITE], WRITE);
		close(out[WRITE]);
		// if (read(READ, buf, BUFSIZE) == -1)
		// {
		// 	err(1, "Error reading");
		// }
		// printf("Read: %s\n", buf);
		read(READ, buf, BUFSIZE);
		write(WRITE, buf, BUFSIZE);
	}
	else
	{
		// sleep(2);
		// duplicate stdin to input pipe
		close(out[READ]);
		close(out[WRITE]);
		if ((dup2(0, in[READ])) == -1)
		{
			err(1, "Error dupping");
		}
		printf("Waiting ...\n");
		read()
		printf("Read: %s\n", buf);
	}

	// for(int i = 0; i < n; i++)
	// {
	// 	switch(fork())
	// 	{
	// 		case -1:
	// 			err(1, "Fork error!");
	// 		case 0:
	// 			close(in[WRITE]);
	// 			close(out[READ]);
	// 			dup2(in[READ], 0);
	// 			dup2(out[WRITE], 1);
	//
	// 			break;
	// 	}
	// }
}

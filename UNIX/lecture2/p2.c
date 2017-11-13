#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char* argv[])
{
	int pd[2], pd2[2];
	pipe(pd);
	pipe(pd2);
	switch(fork())
	{
	case 0: 
		write(pd[1], "x", 1); 
		char k;
		//read(pd2[0], &k, 1);
		//printf("Read: %c\n", k);
		break;
	default: {
		char c;
		read(pd[0], &c, 1);
		printf("Read character: %c\n", c);
		//write(pd2[1], c, 1);
		}
	}
	exit(0);
}

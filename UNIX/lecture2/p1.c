#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

int main() {
	fork();
	printf("PID: %d\nPPID: %d\nPGID: %d\nSID: %d\n\n", getpid(), getppid(), getpgid(getpid()), getsid(getpid()));
	exit(0);
}

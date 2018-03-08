#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <err.h>
#include <sys/types.h>
#include <strings.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>

#define	BUF_LEN	100

int
main(int argc, char **argv)
{
	int fd, n;
	char buf[BUF_LEN];
	struct sockaddr_in in;

	if (argc < 2)
		errx(1, "usage: %s <signal nr> <?port?>", argv[0]);

	bzero(&in, sizeof (in));
	in.sin_family = AF_LOCAL;
	in.sin_port = htons(atoi(argv[2]));

	/*
	 * Convert a dotted format to 4 bytes suitable for use in
	 * sockaddr_in.
	 */
	// if (inet_pton(AF_INET, argv[1], &(in.sin_addr)) != 1)
	// 	errx(1, "inet_pton failed for '%s'", argv[1]);

	if ((fd = socket(AF_LOCAL, SOCK_STREAM, 0)) == -1)
		err(1, "socket");

	if (connect(fd, (struct sockaddr *) &in, sizeof (in)) == -1)
		err(1, "connect");

	/* Now, read from the standard input and send it to the other side. */
  int pid = getpid();
  char *tmp = malloc(1024*sizeof(char*));
  char *tmp2 = malloc(1024*sizeof(char*));
  sprintf(tmp, "%d", pid);
  write(fd, tmp, sizeof(tmp));
  int sig = atoi(argv[1]);
  sprintf(tmp2, "%d", sig);
  write(fd, &sig, sizeof(int));

	if (n != 1) {
		fprintf(stderr, "server closed the connection\n");
		close(fd);
	}

	return (0);
}

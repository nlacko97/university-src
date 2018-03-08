#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <strings.h>
#include <err.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

#define	BUF_LEN	100

int
main(int argc, char **argv)
{
	char buf[BUF_LEN];
	struct sockaddr_in in = { 0 };
	int fd, newsock, n, optval = 1;
  char* port = "212";
	in.sin_family = AF_LOCAL;
	in.sin_port = htons(atoi(port));
	in.sin_addr.s_addr = htonl(INADDR_ANY);

	if ((fd = socket(AF_LOCAL, SOCK_STREAM, 0)) == -1)
		err(1, "socket");

	/* So that we can use the port immediately again after restart. */
	if (setsockopt(fd, SOL_SOCKET, SO_REUSEADDR,
	    &optval, sizeof (optval)) == -1) {
		err(1, "setsockopt");
	}

	if (bind(fd, (struct sockaddr *)&in, sizeof (in)) == -1)
		err(1, "bind");

	if (listen(fd, SOMAXCONN) == -1)
		err(1, "listen");

	/* We will serve one connection at a time. */
	for (;;) {
		if ((newsock = accept(fd, NULL, 0)) == -1)
			err(1, "accept");

		fprintf(stderr, "-- connection accepted --\n");
    int pid, sig;
    read(newsock, &pid, sizeof(int));
    write(1, &pid, sizeof(int));
    read(newsock, &sig, sizeof(int));
    printf("\n");
    write(1, &sig, sizeof(int));

		/*
		 * The close() is important cause otherwise you will waste
		 * a few bytes of memory in the kernel with every connection
		 * and eventually run out of file descriptors.
		 */
		(void) close(newsock);
		fprintf(stderr, "-- connection closed --\n");
	}

	return (0);
}

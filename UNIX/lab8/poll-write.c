
// 1.
// use poll(2) instead of select for write-select.
// behavior should be the same


/*
 * 2.
 * put the accept() socket to poll or select
 * fork() on new accept
 *
 */

/*
 * 3.
 * tcp-sink.c
 * connect the browser to it
 * parse the GET line
 * GET <>
 */


#define	_XOPEN_SOURCE	700

#include <assert.h>
#include <err.h>
#include <fcntl.h>
#include <libgen.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/select.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>
#include <poll.h>

#define	BUF_LEN	1024

int
main(int argc, char **argv)
{
	fd_set wrfds;
	char buf[BUF_LEN];
	struct sockaddr_in sa;
	struct pollfd fds[1];
	nfds_t nfds = 1;
	int s, newsock, n, total = 0, optval = 1;

	if (argc != 2)
		errx(1, "usage: %s <port>", basename(argv[0]));

	if ((s = socket(AF_INET, SOCK_STREAM, 0)) == -1)
		err(1, "socket");

	sa.sin_family = AF_INET;
	sa.sin_port = htons((short) atoi(argv[1]));
	sa.sin_addr.s_addr = htonl(INADDR_ANY);

	memset(buf, '.', sizeof (buf));

	if (setsockopt(s, SOL_SOCKET, SO_REUSEADDR, &optval,
	    sizeof (optval)) == -1) {
		err(1, "setsockopt");
	}

	if (bind(s, (struct sockaddr *)&sa, sizeof (sa)) == -1)
		err(1, "bind");

	if (listen(s, SOMAXCONN) == -1)
		err(1, "listen");

	// if ((newsock = accept(s, NULL, 0)) == -1)
	// 	err(1, "accept");

	/*
	 * Just write data to the remote side.  If the remote side does not read
	 * the data we should be finally put to sleep in select().
	 *
	 * Note that in this case, as we only have one descriptor we are
	 * interested in, the select(2) is an overkill, a simple write() without
	 * it would suffice.  However, this is for a demonstration.
	 */
	// memset(fds, 0 , sizeof(fds));
	// fprintf(stderr, "something\n");
	// fds[0].fd = newsock;


	while(1)
	{
		FD_ZERO(&wrfds);
		FD_SET(s, &wrfds);
		if (select(s + 1, NULL, &wrfds, NULL, NULL) != -1)
		{
			if (newsock = accept(s, NULL, 0) != -1)
			{
				switch(fork())
				{
					case -1:
						err(1, "fork");
					default:
						fprintf(stderr, "New process created");
				}
			}
		}
	}

	for (;;) {
		/* Must do this each time before calling select(). */
		// FD_ZERO(&wrfds);
		// FD_SET(newsock, &wrfds);

		if (poll(fds, nfds, 0) == -1)
			err(1, "poll");

		// if (select(newsock + 1, NULL, &wrfds, NULL, NULL) == -1)
		// 	err(1, "select");

		/*
		 * See above.  We only have one descriptor and we do not expect
		 * an interrupted select().
		 */
		// assert(FD_ISSET(newsock, &wrfds));

		if ((n = write(newsock, buf, sizeof (buf))) == -1)
			err(1, "write");
		total = total + n;
		fprintf(stderr, "[ %d bytes written (total %d) ]\n",
		    n, total);
	}

	/* Not reached. */
	close(newsock);
	close(s);

	return (0);
}

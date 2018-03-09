// Nagy László, 523, nlim1659
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/select.h>
#include <ctype.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <err.h>
#include <signal.h>

#define BUFSIZE 256

int socketfd;

void exitHandler(int e)
{
  close(socketfd);
  printf("Server shutting down...\n");
  exit(0);
}

int
main(int argc, char **argv)
{

  struct sockaddr_storage ca;
  int clientfd;
  struct addrinfo *it;
  struct addrinfo *addresses;
  struct addrinfo hi;
  int option = 1;

  signal(SIGINT, exitHandler);

  memset(&hi, 0, sizeof (hi));
  hi.ai_family = AF_UNSPEC;
  hi.ai_socktype = SOCK_STREAM;
  hi.ai_flags = AI_PASSIVE;

  if (getaddrinfo(NULL, "22000", &hi, &addresses))
  {
    err(1, "Error getting address info");
  }

  for (it = addresses; it != NULL; it = it->ai_next)
  {
    socketfd = socket(it->ai_family, it->ai_socktype, it->ai_protocol);
    if (!bind(socketfd, it->ai_addr, it->ai_addrlen))
    {
      break;
    }
  }

  freeaddrinfo(addresses);
  if (it == NULL)
  {
    close(socketfd);
    err(1, "None of the addresses could bind to socket");
  }

  if (setsockopt(socketfd, SOL_SOCKET, SO_REUSEADDR, &option, sizeof(option)) == -1)
  {
    err(1, "Error setting socket options");
  }

  if (listen(socketfd, SOMAXCONN) == -1)
  {
    err(1, "Error setting listener");
  }

  unsigned int sz = sizeof(ca);
  clientfd = accept(socketfd, (struct sockaddr *)&ca, &sz);
  if (clientfd == -1)
  {
    err(1, "Error accepting client");
  }
  printf("Client accepted\n");

  char *buf = (char*)malloc(BUFSIZE*sizeof(char*));

  while (1)
  {
    if (read(clientfd, buf, BUFSIZE) == -1)
    {
      err(1, "Error reading from client");
    }
    if (!strcmp(buf, "exit"))
    {
      break;
    }
    for(int i = 0; i < strlen(buf); i++)
    {
      buf[i] = toupper(buf[i]);
    }
    if (write(clientfd, buf, strlen(buf) + 1) == -1)
    {
      err(1, "Error writing to client");
    }
  }
  close(clientfd);
  close(socketfd);
}

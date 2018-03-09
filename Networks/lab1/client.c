// Nagy László, 523, nlim1659
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <signal.h>
#include <err.h>

#define PORT "22000"
#define HOST "localhost"
#define BUFSIZE 256
int sockfd;

void
exitHandler(int e)
{
  write(sockfd, ".exit", 6);
  close(sockfd);
  exit(0);
}

void
signalExit()
{
  if (kill(getpid(), SIGINT) == -1)
  {
    printf("Error sending exit signal");
  }
}

int
main(int argc, char **argv)
{

  if (signal(SIGINT, exitHandler) == SIG_ERR)
  {
    err(1, "Error setting signal handler");
  }

  // int fd;
  struct addrinfo *it;
  struct addrinfo *addresses;
  struct addrinfo hi;

  memset(&hi, 0, sizeof (hi));
  hi.ai_family = AF_UNSPEC;
  hi.ai_socktype = SOCK_STREAM;

  if (getaddrinfo(HOST, PORT, &hi, &addresses))
  {
    perror("Error getting address info");
    signalExit();
  }

  for (it = addresses; it != NULL; it = it->ai_next)
  {
    sockfd = socket(it->ai_family, it->ai_socktype, it->ai_protocol);
    if (connect(sockfd, (struct sockaddr *)it->ai_addr, it->ai_addrlen) == 0)
    {
      break;
    }
  }

  freeaddrinfo(addresses);
  if (it == NULL)
  {
    if (close(sockfd) == -1)
    {
      warn("Error closing socketfd");
    }
    err(1, "None of the addresses could connect to socket");
  }

  int b;
  char *sendline = (char*)malloc(BUFSIZE * sizeof(char*));
  char *recvline = (char*)malloc(BUFSIZE * sizeof(char*));
  while(1)
  {
    bzero(sendline, BUFSIZE);
    bzero(recvline, BUFSIZE);
    if (read(0, sendline, BUFSIZE) == -1)
    {
      signalExit();
    }
    sendline[strlen(sendline) - 1] = '\0';
    if (write(sockfd, sendline, strlen(sendline) + 1) == -1)
    {
      perror("Write error");
      signalExit();
    }
    if (read(sockfd, recvline, BUFSIZE) == -1)
    {
      perror("Read error");
      signalExit();
    }
    printf("%s\n", recvline);
    if (!strcmp(sendline, "exit"))
      break;


  }
  close(sockfd);

}

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <err.h>

// check options <-a|-o> env1 env2 ....
// -a check all env variables are set
// -o check if at least one env variable is set

extern int optind;
int main(int argc, char *argv[]) {

  int opt, flagA = 0, flagO = 0;
  while ((opt = getopt(argc, argv, "ao")) != -1) {
    switch(opt) {
      case 'a': {
        flagA = 1;
        printf("option a\n");
      }break;
      case 'o': {
        flagO = 1;
        printf("opt b\n");
      }break;
      default: {
        printf("Bad option\n");
      }
    }
  }

  argc -= optind;
  argv += optind;
  if ( argc <= 0 ) {
    err(1, "No arguments!");
  }

  if (flagA && flagO) {
    err(1, "Too many options set!");
  }
  else if (flagA) {
    for(int i = 0; i < argc && isSet; i++) {
      if (!getenv(argv[i])) {
        return 0;
      }
    }
    return 1;
  }
  else if (flagO) {
    for(int i = 0; i < argc; i++) {
      if (getenv(argv[i])) {
        return 1;
      }
    }
    return 0;
  }
  exit(0);
}

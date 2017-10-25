#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char* argv[])
{
  srand(getpid());
  int x = 10, y = 30;
  int mountain[x][y];
  for(int i = 0; i < x; i++)
    for(int j = 0 ; j < y; j++)
      mountain[i][j] = 0;

  int i = x - 1, j = 0;
  do {
    int dir = rand() % 3;
    // printf("dir: %d\n", dir);
    switch(dir)
    {
      case 0: {
        i--; j++;
        mountain[i][j] = 1;
      }break;
      case 1: {
        j++;
        mountain[i][j] = 2;
      }break;
      case 2: {
        if (i == x - 1)
          continue;
        i++; j++;
        mountain[i][j] = 3;
      }break;
    }
  } while(i < x && i >= 0 && j >= 0 && j < y - 1);

  for(int i = 0; i < x; i++) {
    for(int j = 0; j < y; j++) {
      switch(mountain[i][j])
      {
        case 0: printf("  "); break;
        case 1: printf("/ "); break;
        case 2: printf("- "); break;
        case 3: printf("\\ ");
      }
    }
    printf("\n");
  }
  exit(0);
}

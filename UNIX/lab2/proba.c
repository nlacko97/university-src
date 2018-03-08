#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main()
{
  char *str;
  str = (char*)malloc(sizeof(char*));
  str = "s";
  char * tmp = (char*)malloc((strlen(str) + 1)*sizeof(char*));
  // strcpy(tmp, str);
  tmp = strdup(str);
  strcat(tmp, "t");
  str = tmp;
  printf("%s - %d\n", str, (int)strlen(str));
}

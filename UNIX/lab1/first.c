#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

void printTypeOfString(char *s)
{
	int foundDigit = 0;
	int foundChar = 0;
	int argLength = strlen(s);
	for(int j = 0; j < argLength; j++)
		if (!isdigit(s[j]))
			foundChar = 1;
		else
			foundDigit = 1;
	if (foundDigit && foundChar)
		printf("\t\tERROR: %s\n", s);
	else if (foundDigit)
		printf("\t\tNUMBER: %d\n", atoi(s));
	else
		printf("\t\tSTRING: %s\n", s);
}

void printLineWithChar(int width, int charPos, char c)
{
	printf("\t\t");
	for(int i = 0; i < width; i++) {
		if (charPos == 0 || charPos == width - 1)
			printf("* ");
		else if (i == 0 || i == width - 1)
			printf("* ");
		else if (i == charPos)
			printf("%c ", c);
		else
			printf("  ");
	}
	printf("\n");
}

int main(int argc, char* argv[])
{
	printf("/**\n\n\tArguments:\n");

	// print all arguments
	for(int i = 1; i < argc; i++)
		printf("\t\t%d.: %s\n", i, argv[i]);

	// print arguments in reverse order
	printf("\n\tArguments in reverse order:\n");
	for(int i = argc - 1; i > 0; i--)
		printf("\t\t%d.: %s\n", i, argv[i]);
	printf("\n");

	// print sum of arguments, assuming they are all integers
	int sum = 0;
	for(int i = 1; i < argc; i++)
		sum += atoi(argv[i]);
	printf("\tSum of all arguments: %d\n", sum);

	// decide if arguments are numbver or not
	printf("\n\tType of arguments:\n");
	for(int i = 1; i < argc; i++) {
		printTypeOfString(argv[i]);

	}

	// a.out <n> <char>
	// print <n> sized box with <character> as diagonal
	printf("\n\n");
	int n = atoi(argv[1]);
	for(int i = 0 ; i < n; i++) {
		printLineWithChar(n, i, argv[2][0]);
	}

	printf("\n**/\n");
	exit(0);
}

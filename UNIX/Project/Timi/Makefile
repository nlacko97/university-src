
# Makefile for the simple shell program

CC = gcc
CFLAGS = -Wall
DEPS = builtins.h shell.h
OBJ = main.o shell.o builtins.o

%.o: %.c $(DEPS)
	$(CC) $(CFLAGS) -c -o $@ $<

shell: $(OBJ)
	gcc $(CFLAGS) -o $@ $^

.PHONY: clean
clean:
	rm *.o shell

#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <err.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

/**
  * print files in a directory
  * emulate ls -F => dt_type, stat(2)

  * TODO do it recursively with indenting => chdir(2)
 */

 int main(int argc, char* argv[])
 {
   if (argc != 2) {
     err(1, "Usage: ./a.out <dir-name>");
   }
   DIR *d;
   struct dirent *dir;
   struct stat st;
   if (d = opendir(argv[1]))
   {
     printf("\nContents of <%s>:\n", argv[1]);

     while ((dir = readdir(d)) != NULL) {
       lstat(dir->d_name, &st);

       switch(st.st_mode & S_IFMT)
       {
         case S_IFREG:
         {
           printf("\t%s", dir->d_name);
           if (st.st_mode & (S_IXUSR | S_IXGRP | S_IXOTH))
            printf("*");
           printf("\n");
         }break;

         case S_IFDIR:
          printf("\t%s/\n", dir->d_name);break;

         case S_IFIFO:
          printf("\t%s|\n", dir->d_name);break;

         case S_IFLNK:
          printf("\t%s@\n", dir->d_name);break;
         default:
          printf("\t%s - other type\n", dir->d_name);break;
       }
     }
     closedir(d);
   }
   printf("\n");
   exit(0);
 }

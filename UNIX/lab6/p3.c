/*
 * Talking heads
 * ./a.out <n> <init>
 * <init> processes talk at the same time for random nr. of seconds
 * wait() talk() post() sleep(1)
 * catch SIGINT so that processes can clean up => sem_close
 * parent waits for children to exit
 * --check GitHub repo for solution and comments
 */

 

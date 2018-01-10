#define	_XOPEN_SOURCE	700

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <time.h>

/*
 * implement barrier
 * N threads simulate work with sleep
 * after reaching the barrier, they start again
 *
 */
#define MAX_WAIT 3
#define N 5
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

int on_barrier = 0;
int woken_up = 0;

void *simulate_work(void *x)
{
  while(1)
  {
    int seconds = rand() % MAX_WAIT;
    sleep(seconds);
    // fprintf(stderr, "%d ", *(int *)x);
    fprintf(stderr, "%d ", seconds);
    pthread_mutex_lock(&mutex);
    on_barrier++;
    if(on_barrier % N == 0)
    {
      fprintf(stderr, "\n");
      pthread_cond_broadcast(&cond);
    }
    else
    {
      while(on_barrier % N != 0)
        pthread_cond_wait(&cond, &mutex);
    }
    while(woken_up % N != 0)
      pthread_cond_wait(&cond, &mutex);
    woken_up++;
    pthread_mutex_unlock(&mutex);
  }

}

int main()
{
  srand(time(NULL));
  pthread_t threads[N];

  for(int i = 0; i < N; i++)
  {
    // int *p; p = &i;
    pthread_create(&threads[i], NULL, simulate_work, NULL);
  }

  for(int i = 0; i < N; i++)
  {
    pthread_join(threads[i], NULL);
  }
}

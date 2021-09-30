#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>

void report_and_die(const char * msg) {
  perror(msg);
  exit(-1);
}

char * setup(key_t key, int byte_count, int flags){
  /* Get the shared memory and its identifier */
  int mem_id = shmget(key,
                      byte_count,
                      flags);
  if (mem_id < 0) report_and_die("failed  to shmget");
  /* attach memory to putter's address space */
  char * mem_ptr = shmat(mem_id,
                         0,
                         0);
  if (mem_ptr == (void*) -1 ) report_and_die("failed on shmat");
  return mem_ptr;
}

int main() {
  const char * greeting = "Hello, world!";
  int len = strlen(greeting) + 1;
  key_t key = 9876;
  
  char * mem_ptr = setup(key, len, IPC_CREAT | 0666);
  
  memcpy(mem_ptr, greeting, len); /* copy the msg to shared memroy */
  while ( *mem_ptr == 'H')
    sleep(1);
  printf("%s is new msg; putter is exiting...\n", mem_ptr);
  return 0;
}
  

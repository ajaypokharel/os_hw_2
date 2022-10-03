#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<pthread.h>
#define BUFFER_SIZE 10

int in = 0;
int out = 0;
int buffer[BUFFER_SIZE];
int counter = 0;

void *producer();
void *consumer();

int main(){
    pthread_t producer_thread_id, consumer_thread_id;
    pthread_create(&producer_thread_id, NULL, producer, NULL);
    pthread_create(&consumer_thread_id, NULL, consumer, NULL);
    pthread_join(producer_thread_id, NULL);
    pthread_join(consumer_thread_id, NULL);
    pthread_exit(0);
    return 0;
}
void* producer(){
    int produced = 1;
    while(1){
        printf("Job %d started\n", produced);
        produced++;
        
        while(counter == BUFFER_SIZE); 

        buffer[in] = produced;
        in = (in + 1) % BUFFER_SIZE;
        counter++;
    }
}

void* consumer(){
    int consumed = -1;
    while(1){

        while(counter == 0);

        consumed = buffer[out];
        out = (out + 1) % BUFFER_SIZE;
        counter--;
        printf("Job %d finished\n", consumed);
    }
}

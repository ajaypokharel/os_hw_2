from multiprocessing.managers import SharedMemoryManager
from random import random
import threading
from time import sleep

def producer(sl, size):
	
    produced = 1
    for i in range(9):
        sleep(random())
        print(f"Job {produced} started \n")
        produced += 1
        
        while (sl[-1] == size):
            print(".", end="")
            sleep(0.01)

        sl[sl[-3]] = produced
        sl[-3] = (sl[-3] + 1) % size

        sl[-1] += 1


def consumer(sl, size):

    consumed = -1

    for i in range(9):
        sleep(random())

        while (sl[-1] == 0): 
            print(",", end="")
            sleep(0.01)

        consumed = sl[sl[-2]]
        sl[-2] = (sl[-2] + 1) % size
        sl[-1] -= 1
        print(f'Job {consumed} finished')

if __name__ =="__main__":
    size = 10
    with SharedMemoryManager() as smm:
        sl = smm.ShareableList([0]*(size+3))
        # creating thread
        t1 = threading.Thread(target=consumer, args=(sl, size))
        t2 = threading.Thread(target=producer, args=(sl, size))

        # starting thread 1
        t1.start()
        # starting thread 2
        t2.start()

        # wait until thread 1 is completely executed
        t1.join()
        # wait until thread 2 is completely executed
        t2.join()


import socket
import threading

s = socket.socket( socket.AF_INET, socket.SOCK_DGRAM)

ip_client = "127.0.0.1"
port_client = 6013


s.bind(( ip_client, port_client ))

def receive():
    global ip_client, port_client
    while True:
        response = s.recvfrom(2048)     
        ip_client = response[1][0]           # IP of client
        port_client = response[1][1]
        mssg_client = response[0].decode()   # Message from client in "string format"
        if mssg_client == 'FINISH': 
            print("Client closed the connection")
            break
        print("Client says: ", mssg_client)

    s.close()

def send():
    while True:
        data = input()
        if data == "FINISH": 
            print("Connection Closed")
            s.sendto( data.encode(), ( ip_client, port_client ))
            break
        s.sendto( data.encode(), ( ip_client, port_client ))

    s.close()


# applying multithreading
thread1= threading.Thread( target= receive )
thread2= threading.Thread( target= send )
thread1.start()
thread2.start()

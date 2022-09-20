import socket
import threading


s= socket.socket( socket.AF_INET, socket.SOCK_DGRAM)


ip_server= "127.0.0.1"
port_server= 6013

s.connect(( ip_server, port_server ))


def receive():
    while True:
        response = s.recvfrom(2048)     
        mssg_server= response[0].decode()   # Message from server in "string format"
        if mssg_server == 'FINISH':
            print("Server closed the connection")
            break
        print("Server says: " + mssg_server )
    s.close()


def send():
    while True:
        data = input()
        if data == 'FINISH': 
            print("Connection closed")
            s.sendto( data.encode(), ( ip_server, port_server ))
            break
        s.sendto( data.encode(), ( ip_server, port_server ))
    s.close()

# applying multithreading
thread1= threading.Thread( target= receive )
thread2= threading.Thread( target= send )
thread1.start()
thread2.start()
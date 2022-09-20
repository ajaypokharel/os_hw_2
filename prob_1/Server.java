import java.net.*;
import java.io.*;

public class Server {
	
	ServerSocket sock	;
	Socket socket; // To store the client socket object
	BufferedReader br;// To read data
	PrintWriter out;// To write data
	
	public Server() {
		try {
			sock=new ServerSocket(6013);

			socket = sock.accept(); // we have a connection

			// to read data coming from the client
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// to read input data
			out = new PrintWriter(socket.getOutputStream());
			Read();
			Write();
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void Read() {
		//thread to continuously read data coming from the client
		Runnable readOne=()->{
			
			try {
				while(true) {
					
						String msg=br.readLine();// the message from the client
						if(msg.equals("FINISH")) {
							System.out.println("Client has terminated the chat");
							socket.close();
							break;
						}
						System.out.println("Client says: " + msg);
					
				}
			System.out.println("Connection Down");
			}catch (IOException ioe) {
				ioe.printStackTrace();
			}
		};
		
		new Thread(readOne).start();// to start the thread
	}
	
	public void Write() {
		//thread to take user input and send it to the client
		Runnable writeOne=()->{
			
			try {
				while(!socket.isClosed()) {
					
						BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));// To take input from the console
						String content=br1.readLine();
						
						out.println(content);// sent to client
						out.flush();
						if(content.equals("FINISH")) {
							socket.close();
							break;
						}
						
				
				}
			
			}catch (IOException ioe) {
				ioe.printStackTrace();
			}
		};
		
		new Thread(writeOne).start();
	}
	
	public static void main(String[] args) {
		System.out.println("SERVER");
		new Server();
	}
}

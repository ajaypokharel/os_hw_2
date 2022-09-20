import java.net.*;
import java.io.*;

public class Client {
	
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	
	public Client() {
		try {
			socket=new Socket("127.0.0.1", 6013);
			System.out.println("Connected to the server");

			// server message input
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
			// output to the server
			out = new PrintWriter(socket.getOutputStream());


			Read();
			Write();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Read() {
		// thread to continuously read data coming from the client
		Runnable readOne = ()->{
			try {
				while(true) {
	
						String msg = br.readLine();// the message from the client
						if(msg.equals("FINISH")) {
							System.out.println("Server terminated with FINISH");
							socket.close();
							break;
						}
						System.out.println("Server says: " + msg);
					
					
				}
				System.out.println("Connection Closed");
			} catch(Exception e) {
				e.printStackTrace();
			}
		};
		
		new Thread(readOne).start();// to start the thread
	}
	
	public void Write() {
		// thread to take user input and send it to the client
		Runnable writeOne = () -> {
			try {
				while(!socket.isClosed()) {
					
						BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
						String content = br1.readLine();
						out.println(content); // sent to client
						out.flush();
						if(content.equals("FINISH")) {
							socket.close();
							break;
						}
						
					
				}
				System.out.println("Connection Down");
			} catch(Exception e) {
				e.printStackTrace();
			}
		};
		
		new Thread(writeOne).start();
	}
	

	public static void main(String[] args) {
		new Client();
	}
}

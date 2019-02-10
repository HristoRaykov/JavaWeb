import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server {
	
	private int port;
	
	private ServerSocket tcpListener;
	
	public Server(int port) {
		this.port = port;
	}
	
	public void run() throws IOException {
		
		this.tcpListener = new ServerSocket(this.port);
		
		System.out.println("Listening on http://localhost:" + this.port);
		
		while (true) {
			Socket clientConnection = this.tcpListener.accept();
			System.out.println("Client connected!");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
			
			String requestContent = "";
			String line;
			
			while (true) {
				line = reader.readLine();
				if (line == null || line.equals("")) {
					break;
				}
				requestContent += line + System.lineSeparator();
			}
			if (requestContent.equals("")){
				continue;
			}
			
			System.out.println(requestContent);
			
			/**
			 * through Postman sent request http://localhost:8000/../test.txt
			 * and print the content of the file in the body of the response
			 */
			String requestUrl = requestContent.split(System.lineSeparator())[0].split("\\s+")[1].substring(1);
			String content = String.join(System.lineSeparator(),Files.readAllLines(Paths.get(requestUrl)));
			
			OutputStreamWriter writer = new OutputStreamWriter(clientConnection.getOutputStream());
			
			writer.write(this.getResponse() + "<p>"+ content+"</p>");
			writer.flush();
			
			reader.close();
			writer.close();
			
		}
		
		
	}
	
	private String getResponse() {
		
		return "HTTP/1.1 200 OK" + System.lineSeparator() +
				"Host: SoftUni Server 2019" + System.lineSeparator() +
				"Content-Type: text/html" + System.lineSeparator() +
				System.lineSeparator() +
				"<center><h1>Hello, world!</h1></center>";
	}
	
}

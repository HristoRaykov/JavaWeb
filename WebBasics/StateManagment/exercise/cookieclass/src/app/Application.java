package app;

import app.models.HttpRequest;
import app.models.HttpRequestImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {

    public static void main(String[] args) throws IOException {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    	
        
        StringBuilder requestText = new StringBuilder();
        String line;
        while (!"".equals(line=reader.readLine())){
        	requestText.append(line).append(System.lineSeparator());
        }
        
        requestText.append(System.lineSeparator());
        requestText.append(reader.readLine());
	
	    HttpRequest httpRequest = new HttpRequestImpl(requestText.toString());
	    
		httpRequest.getCookies()
				.forEach(httpCookie -> System.out.println(String.format("%s <-> %s",
						httpCookie.getKey(),httpCookie.getValue())));
	    
	}
}

package app;

import app.models.HttpRequest;
import app.models.HttpRequestImpl;
import app.models.HttpResponse;
import app.models.HttpResponseImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    
        List<String> validUrls = Arrays.asList(reader.readLine().trim().split("\\s+"));
        
        StringBuilder requestText = new StringBuilder();
        String line;
        while (!"".equals(line=reader.readLine())){
        	requestText.append(line).append(System.lineSeparator());
        }
        
        requestText.append(System.lineSeparator());
        requestText.append(reader.readLine());
	
	    HttpRequest httpRequest = new HttpRequestImpl(requestText.toString());
	    HttpResponse httpResponse = new HttpResponseImpl(httpRequest,validUrls);
	
	    System.out.println(httpResponse);
	}
}

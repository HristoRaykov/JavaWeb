package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Application {
	
	private static final String[] validResponseHeaderParams = new String[]{"Date", "Host", "Content-Type"};
	
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	
	public static void main(String[] args) throws IOException {
		
		List<String> validURLs = parseUrls();
		
		List<String> requestLineParams = parseRequestLine();
		
		Map<String, String> headers = parseHeaders();
		
		Map<String, String> requestBodyParams = parseRequestBodyParams();
		
		
		Map<String, String> responseHeaders = getResponseHeaders(headers);
		
		String responseHeadersString = responseHeaders.entrySet().stream()
				.map(e -> String.format("%s: %s", e.getKey(), e.getValue()))
				.collect(Collectors.joining(System.lineSeparator()));
		
		String authorizationString = headers.get("Authorization");
		
		if (!validURLs.contains(requestLineParams.get(1))) {
			System.out.println(String.format(Constants.RESPONSE_TEMPLATE, requestLineParams.get(2),
					"404 Not Found", responseHeadersString, Constants.NOT_FOUND_404));
			return;
		}
		
		if (authorizationString == null) {
			System.out.println(String.format(Constants.RESPONSE_TEMPLATE, requestLineParams.get(2),
					"401 Unauthorized", responseHeadersString, Constants.UNAUTHORIZED_401));
			return;
		}
		
		String username = parseUserName(authorizationString);
		
		if (requestBodyParams.size() == 0) {
			if (requestLineParams.get(0).equals("POST")) {
				System.out.println(String.format(Constants.RESPONSE_TEMPLATE, requestLineParams.get(2),
						"400 Bad Request", responseHeadersString, Constants.UNAUTHORIZED_400));
			} else {
				System.out.println(String.format(Constants.RESPONSE_TEMPLATE, requestLineParams.get(2),
						"400 Bad Request", responseHeadersString, String.format(Constants.GREETINGS, username)));
			}
			return;
		}
		
		String[] paramKeys = requestBodyParams.keySet().toArray(String[]::new);
		String[] paramValues = requestBodyParams.values().toArray(String[]::new);
		
		String responseBody = String.format(Constants.RESPONSE_BODY, username, paramValues[0],
				paramKeys[1], paramValues[1],
				paramKeys[2], paramValues[2]
		);
		
		System.out.println(String.format(Constants.RESPONSE_TEMPLATE, requestLineParams.get(2),
				"200 OK", responseHeadersString, responseBody));
		
	}
	
	private static Map<String, String> parseRequestBodyParams() throws IOException {
		Map<String, String> requestBodyParams = new LinkedHashMap<>();
		String[] entrySets = reader.readLine().split("&");
		if (entrySets.length!=3){
			return requestBodyParams;
		}
		for (String entrySet : entrySets) {
			String[] params = entrySet.split("=");
			requestBodyParams.putIfAbsent(params[0], params[1]);
		}
		return requestBodyParams;
	}
	
	private static Map<String, String> getResponseHeaders(Map<String, String> headers) {
		Map<String, String> responseHeaders = new LinkedHashMap<>();
		
		for (String validResponseHeaderParam : validResponseHeaderParams) {
			if (headers.containsKey(validResponseHeaderParam)) {
				responseHeaders.put(validResponseHeaderParam, headers.get(validResponseHeaderParam));
			}
		}
		
		return responseHeaders;
	}
	
	private static String parseUserName(String authorizationString) {
		String encodedName = authorizationString.split("\\s+")[1];
		return new String(Base64.getDecoder().decode(encodedName));
		
	}
	
	private static Map<String, String> parseHeaders() throws IOException {
		String line;
		Map<String, String> headers = new LinkedHashMap<>();
		while (!"".equals(line = reader.readLine())) {
			String[] params = line.split(": ");
			headers.putIfAbsent(params[0], params[1]);
		}
		return headers;
	}
	
	private static List<String> parseRequestLine() throws IOException {
		return Arrays.asList(reader.readLine().trim().split("\\s+"));
	}
	
	private static List<String> parseUrls() throws IOException {
		return Arrays.asList(reader.readLine().trim().split("\\s+"));
	}
	
	
}

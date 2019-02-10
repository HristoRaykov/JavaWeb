package app.models;

import java.util.*;

public class HttpRequestImpl implements HttpRequest {
	
	public static final String AUTHORIZATION_HEADER_PROPERTY_NAME = "Authorization";
	
	private String requestText;
	
	private String method;
	
	private String requestUrl;
	
	private String httpVersion;
	
	private Map<String, String> headers;
	
	private List<HttpCookie> cookies;
	
	private Map<String, String> bodyParameters;
	
	private String username;
	
	public HttpRequestImpl(String requestText) {
		this.requestText = requestText;
		List<String> requestLines = Arrays.asList(requestText.split(System.lineSeparator(), -1));
		List<String> requestLineParams = Arrays.asList(requestLines.get(0).split("\\s+"));
		this.method = requestLineParams.get(0);
		this.requestUrl = requestLineParams.get(1);
		this.httpVersion = requestLineParams.get(2);
		this.headers = this.parseHeaders(requestLines);
		this.cookies = this.parseCookies();
		this.bodyParameters = this.parseBodyParameters(requestLines);
		this.username = this.parseUsername();
	}
	

	@Override
	public Map<String, String> getHeaders() {
		return this.headers;
	}
	
	@Override
	public Map<String, String> getBodyParameters() {
		return this.bodyParameters;
	}
	
	@Override
	public String getMethod() {
		return this.method;
	}
	
	@Override
	public void setMethod(String method) {
		this.method = method;
	}
	
	@Override
	public String getRequestUrl() {
		return this.requestUrl;
	}
	
	@Override
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	@Override
	public void addHeader(String header, String value) {
		this.headers.putIfAbsent(header, value);
	}
	
	@Override
	public void addBodyParameter(String parameter, String value) {
		this.bodyParameters.putIfAbsent(parameter, value);
	}
	
	@Override
	public boolean isResource() {
		return this.getRequestUrl().contains(".");
	}
	
	@Override
	public String getHttpVersion() {
		return this.httpVersion;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public List<HttpCookie> getCookies() {
		return this.cookies;
	}
	
	
	private Map<String, String> parseHeaders(List<String> requestLines) {
		Map<String, String> headers = new LinkedHashMap<>();
		String line;
		String[] params;
		for (int i = 1; i < requestLines.size(); i++) {
			line = requestLines.get(i);
			if (line.equals("")) {
				break;
			}
			params = line.split(": ");
			headers.putIfAbsent(params[0], params[1]);
		}
		return headers;
	}
	
	private Map<String, String> parseBodyParameters(List<String> requestLines) {
		Map<String, String> bodyParameters = new LinkedHashMap<>();
		String bodyParametersLine = requestLines.get(requestLines.size() - 1);
		String[] entrySets = bodyParametersLine.split("&");
		if (entrySets.length != 3) {
			return bodyParameters;
		}
		for (String entrySet : entrySets) {
			String[] params = entrySet.split("=");
			bodyParameters.putIfAbsent(params[0], params[1]);
		}
		return bodyParameters;
	}
	
	private String parseUsername() {
		
		String authorizationPropertyValue = null;
		authorizationPropertyValue = this.getHeaders().get(AUTHORIZATION_HEADER_PROPERTY_NAME);
		if (authorizationPropertyValue == null){
			return null;
		}
		String encodedUsername = authorizationPropertyValue.split("\\s+")[1];
		return new String(Base64.getDecoder().decode(encodedUsername));
	}
	
	private List<HttpCookie> parseCookies() {
		List<HttpCookie> cookies = new ArrayList<>();
		
		String cookieString = this.headers.get("Cookie");
		
		if (cookieString.isEmpty()) {
			return cookies;
		}
		
		Arrays.stream(cookieString.split("; "))
				.forEach(cookiePair -> {
					String[] cookieSplitted = cookiePair.split("=");
					HttpCookie cookie = new HttpCookieImpl(cookieSplitted[0],cookieSplitted[1]);
					cookies.add(cookie);
				});
		
		return cookies;
	}
	
	
	
}

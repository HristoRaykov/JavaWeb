package app.models;

import app.Constants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponseImpl implements HttpResponse {
	
	private static final String[] validResponseHeaderProperties = new String[]{"Date", "Host", "Content-Type"};
	
	private HttpRequest httpRequest;
	
	private List<String> validUrls;
	
	private int statusCode;
	
	private String responseLine;
	
	private Map<String, String> headers;
	
	private String responseBody;
	
	private byte[] content;
	
	public HttpResponseImpl(HttpRequest httpRequest, List<String> validUrls) {
		this.httpRequest = httpRequest;
		this.validUrls = validUrls;
		this.headers = this.parseResponseHeaders();
		this.setResponseBody();
	}
	
	
	@Override
	public Map<String, String> getHeaders() {
		return this.headers;
	}
	
	@Override
	public int getStatusCode() {
		return this.statusCode;
	}
	
	@Override
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	@Override
	public byte[] getContent() {
		return this.responseBody.getBytes();
	}
	
	@Override
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	@Override
	public byte[] getBytes() {
		return this.toString().getBytes();
	}
	
	@Override
	public void addHeader(String header, String value) {
		this.getHeaders().putIfAbsent(header, value);
	}
	
	private void setResponseBody() {
		if (!this.validUrls.contains(this.httpRequest.getRequestUrl())) {
			this.responseBody = Constants.NOT_FOUND_404;
			this.setStatusCode(404);
			this.responseLine = String.format(Constants.RESPONSE_LINE_TEMPLATE,
					this.httpRequest.getHttpVersion(), this.getStatusCode(), "Not Found");
			return;
		}
		if (this.httpRequest.getUsername() == null) {
			this.responseBody = Constants.UNAUTHORIZED_401;
			this.setStatusCode(401);
			this.responseLine = String.format(Constants.RESPONSE_LINE_TEMPLATE,
					this.httpRequest.getHttpVersion(), this.getStatusCode(), "Unauthorized");
			return;
		}
		if (this.httpRequest.getBodyParameters().size() == 0) {
			if ("POST".equals(this.httpRequest.getMethod())) {
				this.responseBody = Constants.UNAUTHORIZED_400;
				this.setStatusCode(400);
				this.responseLine = String.format(Constants.RESPONSE_LINE_TEMPLATE,
						this.httpRequest.getHttpVersion(), this.getStatusCode(), "Bad Request");
			} else {
				this.responseBody = String.format(Constants.GREETINGS, this.httpRequest.getUsername());
				this.setStatusCode(200);
				this.responseLine = String.format(Constants.RESPONSE_LINE_TEMPLATE,
						this.httpRequest.getHttpVersion(), this.getStatusCode(), "OK");
			}
			return;
		}
		String[] paramKeys = this.httpRequest.getBodyParameters().keySet().toArray(String[]::new);
		String[] paramValues = this.httpRequest.getBodyParameters().values().toArray(String[]::new);
		this.responseBody = String.format(Constants.RESPONSE_BODY,
				this.httpRequest.getUsername(), paramValues[0],
				paramKeys[1], paramValues[1],
				paramKeys[2], paramValues[2]);
		this.setStatusCode(200);
		this.responseLine = String.format(Constants.RESPONSE_LINE_TEMPLATE,
				this.httpRequest.getHttpVersion(), this.getStatusCode(), "OK");
	}
	
	private String getHeadersString() {
		return this.headers.entrySet().stream()
				.map(e -> String.format("%s: %s", e.getKey(), e.getValue()))
				.collect(Collectors.joining(System.lineSeparator()));
	}
	
	private Map<String, String> parseResponseHeaders() {
		Map<String, String> headers = new LinkedHashMap<>();
		
		for (String validResponseHeader : validResponseHeaderProperties) {
			if (httpRequest.getHeaders().containsKey(validResponseHeader)) {
				headers.put(validResponseHeader, httpRequest.getHeaders().get(validResponseHeader));
			}
		}
		return headers;
	}
	
	@Override
	public String toString() {
		return String.format(Constants.RESPONSE_TEMPLATE,this.responseLine,this.getHeadersString(),this.responseBody);
	}
}

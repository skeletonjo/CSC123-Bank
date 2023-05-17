package com.usman.csudh.bank.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class HooksHTTP {
	protected InputStream getInputStream() throws IOException, InterruptedException
	{
		HttpRequest.Builder builder = HttpRequest.newBuilder();
		builder.uri(URI.create("http://www.usman.cloud/banking/exchange-rate.csv"));
		builder.method("GET", HttpRequest.BodyPublishers.noBody());
		HttpRequest req=builder.build();
		HttpClient client=HttpClient.newHttpClient();
		HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
		byte[] bytes = response.body().getBytes();
		return new ByteArrayInputStream(bytes);
	}
}
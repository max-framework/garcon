package model.HTTP;

import exceptions.BadRequestException;
import model.Request;

import java.util.LinkedHashMap;
import java.util.Map;

public class HTTPRequest implements Request {

    private String uri;
    private String method;
    private String protocol;
    private String entityBody;
    private final Map<String, String> headers;
    private final String requestText;

    public HTTPRequest(String requestText) {
        this.headers = new LinkedHashMap<>();
        this.requestText = requestText;
        parseRequest(requestText);
    }

    public void parseRequest(String requestText) {
        String[] clfrSplit = requestText.split("\r\n", 2);
        if (clfrSplit.length != 2)
            throw new BadRequestException(requestText, "Missing CRLF");
        this.entityBody = clfrSplit[1];

        String[] methodUriProtocol = clfrSplit[0].split(" ", 3);
        if (methodUriProtocol.length != 3)
            throw new BadRequestException(requestText, "Missing one of the following: Method, URI or protocol");
        this.method = methodUriProtocol[0];
        this.uri = methodUriProtocol[1];
        this.protocol = methodUriProtocol[2];
        if (!HTTPMethod.contains(this.method))
            throw new BadRequestException(requestText, "Unknown model.HTTP method " + this.method);

        String[] headers = clfrSplit[0].split("\n");
        for (int i = 1; i < headers.length; i++) {
            String[] headerSplit = headers[i].split(":", 2);
            if (headerSplit.length != 2)
                throw new BadRequestException(requestText, "Bad headers");
            this.headers.put(headerSplit[0], headerSplit[1]);
        }
    }

    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getEntityBody() {
        return entityBody;
    }

    public String getRequestText() {
        return requestText;
    }
}

package model.HTTP;

import model.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HTTPResponse implements Response {

    private String protocol;
    private String code;
    private Map<String, String> headers;
    private String body;

    public HTTPResponse(String protocol, String code, Map<String, String> headers, String body) {
        this.protocol = protocol;
        this.code = code;
        this.headers = headers == null ? new HashMap<>() : headers;
        this.body = body;
    }

    @Override
    public String getText() {
        StringBuilder builder = new StringBuilder();
        String headersText = headers.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining("\n"));

        builder.append(protocol)
                .append(" ")
                .append(code)
                .append("\n")
                .append(headersText)
                .append("\r\n")
                .append(body);

        return builder.toString();
    }

    public byte[] getBytes() {
        return getText().getBytes();
    }

    public String getProtocol() {
        return protocol;
    }

    public String getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }
}

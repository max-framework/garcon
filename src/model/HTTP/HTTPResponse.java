package model.HTTP;

import model.Protocol;
import model.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HTTPResponse implements Response {

    private final Protocol protocol;
    private final String code;
    private final Map<String, String> headers;
    private final String body;

    public HTTPResponse(Protocol protocol, String code, Map<String, String> headers, String body) {
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

        builder.append(protocol.name())
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

    public Protocol getProtocol() {
        return protocol;
    }

    public String getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }
}

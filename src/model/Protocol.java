package model;

public enum Protocol {
    HTTP11("HTTP/1.0"),
    HTTP10("HTTP/1.0");

    private final String value;

    Protocol(String value) {
        this.value = value;
    }

    public static Protocol getProtocol(String valueOf) {
        for (Protocol p: Protocol.values()) {
            if (p.value.equals(valueOf))
                return p;
        }
        return null;
    }
}

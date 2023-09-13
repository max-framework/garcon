package model.HTTP;

public enum HTTPMethod {
    POST,
    PUT,
    GET,
    DELETE,
    HEAD,
    CONNECT,
    PATCH,
    OPTIONS,
    TRACE;

    public static boolean contains(String test) {
        for (HTTPMethod r : HTTPMethod.values()) {
            if (r.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}

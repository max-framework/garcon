package model.HTTP;

public enum HTTPCode {
    NOT_FOUND("404 Not found"),
    INTERNAL_ERROR("500 Internal Server Error"),
    BAD_REQUEST("400 Bad Request"),
    OK("200 OK");

    final String code;

    HTTPCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

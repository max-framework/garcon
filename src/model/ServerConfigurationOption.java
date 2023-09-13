package model;

public enum ServerConfigurationOption {
    PORT,
    WEBROOT,
    PROTOCOL;

    public static int DEFAULT_PORT = 6969;
    public static String DEFAULT_WEBROOT = "webroot";
    public static String DEFAULT_PROTOCOL = "HTTP/1.1";
}

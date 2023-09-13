package model;

public enum ServerConfigurationOption {
    PORT,
    WEBROOT,
    PROTOCOL;

    public static final int DEFAULT_PORT = 6969;
    public static final String DEFAULT_WEBROOT = "webroot";
    public static final Protocol DEFAULT_PROTOCOL = Protocol.HTTP10;
}

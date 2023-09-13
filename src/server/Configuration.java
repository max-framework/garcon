package server;

import exceptions.ResourceNotFoundException;
import model.Protocol;
import model.ServerConfigurationOption;

import java.io.File;
import java.util.Map;

public class Configuration {

    public static final String ROOT = System.getProperty("user.dir") + File.separator;
    public static final int REQUEST_SIZE = Integer.MAX_VALUE / 4;

    private static Configuration instance = null;

    private int port;
    private String webroot;
    private Protocol protocol;

    private Configuration() {
        Map<String, String> configs = null;

        try {
            configs = ResourceResolver.getConfigs();
        } catch (ResourceNotFoundException e) {
            this.port = ServerConfigurationOption.DEFAULT_PORT;
            this.webroot = ServerConfigurationOption.DEFAULT_WEBROOT;
            this.protocol = ServerConfigurationOption.DEFAULT_PROTOCOL;
            System.out.println("No configuration file found. \n" +
                    "Starting on port " + ServerConfigurationOption.DEFAULT_PORT + "\n" +
                    "With web root folder " + ServerConfigurationOption.WEBROOT);
        }

        try {
            if (configs != null)
                this.port = Integer.parseInt(configs.get(ServerConfigurationOption.PORT.name()));
        } catch (NumberFormatException e) {
            this.port = ServerConfigurationOption.DEFAULT_PORT;
            System.out.println("Error while reading the port, using the default one: " + this.port);
        }

        if (configs != null) {
            this.webroot = configs.get(ServerConfigurationOption.WEBROOT.name());
            if (this.webroot == null) {
                this.webroot = ServerConfigurationOption.DEFAULT_WEBROOT;
                System.out.println("Missing webroot, using the default one: " + this.webroot);
            } else {
                this.webroot = ROOT + this.webroot;
            }

            this.protocol = Protocol.getProtocol(configs.get(ServerConfigurationOption.PROTOCOL.name()));
            if (this.protocol == null) {
                this.protocol = ServerConfigurationOption.DEFAULT_PROTOCOL;
                System.out.println("Missing protocol, using the default one: " + this.protocol);
            }
        }
    }

    public static Configuration getInstance() {
        if (instance == null)
            instance = new Configuration();
        return instance;
    }

    public int getPort() {
        return this.port;
    }

    public String getWebroot() {
        return this.webroot;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }
}

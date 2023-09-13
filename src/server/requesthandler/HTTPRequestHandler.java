package server.requesthandler;

import exceptions.BadRequestException;
import exceptions.ResourceNotFoundException;
import model.HTTP.HTTPCode;
import model.HTTP.HTTPRequest;
import model.HTTP.HTTPResponse;
import server.Configuration;
import server.ResourceResolver;

public class HTTPRequestHandler implements RequestHandler {

    @Override
    public String handleRequest(String requestText) {
        Configuration config = Configuration.getInstance();
        HTTPRequest request;

        try {
            request = new HTTPRequest(requestText);
        } catch (BadRequestException e) {
            return new HTTPResponse(
                    config.getProtocol(),
                    HTTPCode.BAD_REQUEST.getCode(),
                    null,
                    "Bad request"
            ).getText();
        }

        try {
            String fileContent = ResourceResolver.getFileContentString(request.getUri());

            return new HTTPResponse(
                    config.getProtocol(),
                    HTTPCode.OK.getCode(),
                    null,
                    fileContent
            ).getText();
        } catch (ResourceNotFoundException e) {
            return new HTTPResponse(
                    config.getProtocol(),
                    HTTPCode.NOT_FOUND.getCode(),
                    null,
                    "File " + request.getUri() + " not found"
            ).getText();
        }
    }


}

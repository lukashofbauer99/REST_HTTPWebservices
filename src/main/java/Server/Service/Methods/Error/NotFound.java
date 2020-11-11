package Server.Service.Methods.Error;

import Server.Service.Methods.IHTTPMethod;
import Server.Service.Request.IRequestContext;
import Server.Service.Response.IResponseContext;
import Server.Service.Response.ResponseContext;

public class NotFound implements IHTTPMethod {

    @Override
    public Boolean analyse(IRequestContext data) {
        return true;
    }

    @Override
    public IResponseContext exec(IRequestContext data) {
        ResponseContext responseContext = new ResponseContext();
        responseContext.setPayload("\r\n");
        responseContext.setHttpStatusCode("HTTP/1.1 404");
        responseContext.getHeaders().put("Connection", "close");

        return responseContext;
    }
}

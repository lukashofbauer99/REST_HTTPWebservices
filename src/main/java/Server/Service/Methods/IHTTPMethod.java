package Server.Service.Methods;

import Server.Service.Request.IRequestContext;
import Server.Service.Response.IResponseContext;

import java.io.IOException;

public interface IHTTPMethod {

    Boolean analyse(IRequestContext data);

    IResponseContext exec(IRequestContext data) throws IOException;
}

package Server.Service.Methods;

import Server.Service.Request.RequestContext;
import Server.Service.Response.ResponseContext;

public interface IHTTPMethod {

    Boolean analyse(RequestContext data);

    ResponseContext exec(RequestContext data);
}

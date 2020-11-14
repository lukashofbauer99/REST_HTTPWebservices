package Server.Service.Methods.DELETE;

import Server.Domain.IRepository;
import Server.Domain.MessageRepository;
import Server.Model.Message;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Request.IRequestContext;
import Server.Service.Response.IResponseContext;
import Server.Service.Response.ResponseContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import static java.lang.Integer.parseInt;

@AllArgsConstructor
@NoArgsConstructor
public class DELETE_messages_Id implements IHTTPMethod {

    IRepository<Message> repository = new MessageRepository();

    @Override
    public Boolean analyse(IRequestContext data) {
        return data.getHttpVerb_Res().startsWith("DELETE /messages/");
    }

    @Override
    public IResponseContext exec(IRequestContext data) {
        ResponseContext responseContext = new ResponseContext();
        Message messageToRemove=null;

        if(repository.deleteEntity( parseInt(data.getHttpVerb_Res().substring("DELETE /messages/".length(),data.getHttpVerb_Res().indexOf(" HTTP/")))))
            responseContext.setHttpStatusCode("HTTP/1.1 200");
        else
            responseContext.setHttpStatusCode("HTTP/1.1 400");

        responseContext.getHeaders().put("Connection", "close");
        responseContext.getHeaders().put("Content-Length", "0");
        responseContext.getHeaders().put("Content-Type", "text/plain");

        return responseContext;
    }
}

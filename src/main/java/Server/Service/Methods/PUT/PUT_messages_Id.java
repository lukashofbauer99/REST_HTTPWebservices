package Server.Service.Methods.PUT;

import Server.Domain.MessageSave;
import Server.Model.Message;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Request.IRequestContext;
import Server.Service.Response.IResponseContext;
import Server.Service.Response.ResponseContext;

import static java.lang.Integer.parseInt;

public class PUT_messages_Id implements IHTTPMethod {

    @Override
    public Boolean analyse(IRequestContext data) {
        return data.getHttpVerb_Res().startsWith("PUT /messages/");
    }

    @Override
    public IResponseContext exec(IRequestContext data) {
        ResponseContext responseContext = new ResponseContext();

        responseContext.setHttpStatusCode("HTTP/1.1 400");

        for (Message message : MessageSave.messages) {
            if(message.getId()==  parseInt(data.getHttpVerb_Res().substring("PUT /messages/".length(),data.getHttpVerb_Res().indexOf(" HTTP/"))))
            {
                message.setContent(data.getPayload());
                responseContext.setHttpStatusCode("HTTP/1.1 200");
            }
        }

        responseContext.getHeaders().put("Connection", "close");
        return responseContext;
    }
}
package Server.Service.Methods.PUT;

import Server.Domain.MessageSave;
import Server.Model.Message;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Request.RequestContext;
import Server.Service.Response.ResponseContext;

import static java.lang.Integer.parseInt;

public class PUT_messages_Id implements IHTTPMethod {

    @Override
    public Boolean analyse(RequestContext data) {
        return data.getHttpVerb_Res().startsWith("PUT /messages/");
    }

    @Override
    public ResponseContext exec(RequestContext data) {
        ResponseContext responseContext = new ResponseContext();
        responseContext.setHTTPStatusCode("HTTP/1.1 201 \r\n");
        responseContext.getHeaders().put("Connection", "close\r\n");
        responseContext.getHeaders().put("Content-Type", "text/plain\r\n");

        for (Message message : MessageSave.messages) {
            if(message.getId()==  parseInt(data.getHttpVerb_Res().substring("PUT /messages/".length(),data.getHttpVerb_Res().indexOf(" HTTP/"))))
            {
                message.setContent(data.getPayload());
            }
        }
        responseContext.setPayload("\r\n");
        return responseContext;
    }
}
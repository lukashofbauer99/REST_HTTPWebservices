package Server.Service.Methods.POST;

import Server.Domain.MessageSave;
import Server.Model.Message;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Request.RequestContext;
import Server.Service.Response.ResponseContext;

public class POST_messages implements IHTTPMethod {

    @Override
    public Boolean analyse(RequestContext data) {
        return data.getHttpVerb_Res().startsWith("POST /messages");
    }

    @Override
    public ResponseContext exec(RequestContext data) {
        ResponseContext responseContext = new ResponseContext();
        responseContext.setHTTPStatusCode("HTTP/1.1 201 \r\n");
        responseContext.getHeaders().put("Connection", "close\r\n");
        responseContext.getHeaders().put("Content-Type", "text/plain\r\n");

        int messageID=MessageSave.getNextID();
        Message message = new Message(messageID,data.getPayload());
        MessageSave.messages.add(message);
        responseContext.setPayload("\r\n"+messageID);
        return responseContext;
    }
}
package Server.Service.Methods.POST;

import Server.Domain.MessageSave;
import Server.Domain.PersistanceHandler;
import Server.Model.Message;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Request.IRequestContext;
import Server.Service.Response.IResponseContext;
import Server.Service.Response.ResponseContext;

import java.io.IOException;

public class POST_messages implements IHTTPMethod {

    @Override
    public Boolean analyse(IRequestContext data) {
        return data.getHttpVerb_Res().startsWith("POST /messages ");
    }

    @Override
    public IResponseContext exec(IRequestContext data) throws IOException {
        ResponseContext responseContext = new ResponseContext();

        int messageID=MessageSave.getNextID();
        Message message = new Message(messageID,data.getPayload());
        MessageSave.messages.add(message);
        PersistanceHandler.persistMessage(message);
        responseContext.setPayload(String.valueOf(messageID));

        responseContext.setHttpStatusCode("HTTP/1.1 201");
        responseContext.getHeaders().put("Connection", "close");
        responseContext.getHeaders().put("Content-Lenght", String.valueOf(String.valueOf(messageID).length()));
        responseContext.getHeaders().put("Content-Type", "text/plain");
        return responseContext;
    }
}
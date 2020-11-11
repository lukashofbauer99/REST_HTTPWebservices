package Server.Service.Methods.GET;

import Server.Domain.MessageSave;
import Server.Model.Message;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Request.IRequestContext;
import Server.Service.Response.IResponseContext;
import Server.Service.Response.ResponseContext;

public class GET_messages implements IHTTPMethod {

    @Override
    public Boolean analyse(IRequestContext data) {
        return data.getHttpVerb_Res().startsWith("GET /messages ");
    }

    @Override
    public IResponseContext exec(IRequestContext data) {
        ResponseContext responseContext = new ResponseContext();

        String messagesString="";
        for (Message message : MessageSave.messages) {//TODO: Change so that messages get read from file
            messagesString+=message.getId()+":\n"+message.getContent()+"\n";
        }
        responseContext.setPayload(messagesString);
        responseContext.setHttpStatusCode("HTTP/1.1 200");
        responseContext.getHeaders().put("Content-Lenght",  String.valueOf(messagesString.length()));
        responseContext.getHeaders().put("Content-Type", "text/plain");
        responseContext.getHeaders().put("Connection", "close");


        return responseContext;
    }
}

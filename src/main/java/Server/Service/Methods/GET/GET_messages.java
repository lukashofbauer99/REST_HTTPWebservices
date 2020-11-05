package Server.Service.Methods.GET;

import Server.Domain.MessageSave;
import Server.Model.Message;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Request.RequestContext;
import Server.Service.Response.ResponseContext;

public class GET_messages implements IHTTPMethod {

    @Override
    public Boolean analyse(RequestContext data) {
        return data.getHttpVerb_Res().startsWith("GET /messages ");
    }

    @Override
    public ResponseContext exec(RequestContext data) {
        ResponseContext responseContext = new ResponseContext();
        responseContext.setHTTPStatusCode("HTTP/1.1 200 \r\n");
        responseContext.getHeaders().put("Connection", "close\r\n");
        responseContext.getHeaders().put("Content-Type", "text/plain\r\n");
        String messagesString="";
        for (Message message : MessageSave.messages) {
            messagesString+=message.getId()+":\n"+message.getContent()+"\n";
        }
        responseContext.setPayload("\r\n"+messagesString);
        return responseContext;
    }
}

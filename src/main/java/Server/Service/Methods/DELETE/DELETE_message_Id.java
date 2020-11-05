package Server.Service.Methods.DELETE;

import Server.Domain.MessageSave;
import Server.Model.Message;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Request.RequestContext;
import Server.Service.Response.ResponseContext;

import static java.lang.Integer.parseInt;

public class DELETE_message_Id implements IHTTPMethod {

    @Override
    public Boolean analyse(RequestContext data) {
        return data.getHttpVerb_Res().startsWith("DELETE /messages/");
    }

    @Override
    public ResponseContext exec(RequestContext data) {
        ResponseContext responseContext = new ResponseContext();
        responseContext.setHTTPStatusCode("HTTP/1.1 200 \r\n");
        responseContext.getHeaders().put("Connection", "close\r\n");
        responseContext.getHeaders().put("Content-Type", "text/plain\r\n");
        Message messageToRemove=null;
        for (Message message : MessageSave.messages) {
           if(message.getId()==  parseInt(data.getHttpVerb_Res().substring("DELETE /messages/".length(),data.getHttpVerb_Res().indexOf(" HTTP/"))))
           {
               messageToRemove =message;
               break;
           }
        }
        if(messageToRemove != null)
            MessageSave.messages.remove(messageToRemove);
        responseContext.setPayload("\r\n");
        return responseContext;
    }
}

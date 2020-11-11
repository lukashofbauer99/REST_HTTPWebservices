package Server.Service.Methods.DELETE;

import Server.Domain.MessageSave;
import Server.Model.Message;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Request.IRequestContext;
import Server.Service.Response.IResponseContext;
import Server.Service.Response.ResponseContext;

import static java.lang.Integer.parseInt;

public class DELETE_message_Id implements IHTTPMethod {

    @Override
    public Boolean analyse(IRequestContext data) {
        return data.getHttpVerb_Res().startsWith("DELETE /messages/");
    }

    @Override
    public IResponseContext exec(IRequestContext data) {
        ResponseContext responseContext = new ResponseContext();
        Message messageToRemove=null;

        responseContext.setHttpStatusCode("HTTP/1.1 400");

        for (Message message : MessageSave.messages) {
           if(message.getId()==  parseInt(data.getHttpVerb_Res().substring("DELETE /messages/".length(),data.getHttpVerb_Res().indexOf(" HTTP/"))))
           {
               MessageSave.messages.remove(message);
               responseContext.setHttpStatusCode("HTTP/1.1 200");
               break;
           }
        }
        responseContext.getHeaders().put("Connection", "close");


        return responseContext;
    }
}

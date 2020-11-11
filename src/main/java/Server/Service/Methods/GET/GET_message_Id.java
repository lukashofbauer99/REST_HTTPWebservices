package Server.Service.Methods.GET;

import Server.Domain.MessageSave;
import Server.Model.Message;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Request.IRequestContext;
import Server.Service.Response.IResponseContext;
import Server.Service.Response.ResponseContext;

import static java.lang.Integer.parseInt;

public class GET_message_Id implements IHTTPMethod {

    @Override
    public Boolean analyse(IRequestContext data) {
        return data.getHttpVerb_Res().startsWith("GET /messages/");
    }

    @Override
    public IResponseContext exec(IRequestContext data) {
        ResponseContext responseContext = new ResponseContext();

        String returnMessage= "";

        responseContext.setHttpStatusCode("HTTP/1.1 400"); //TODO: Change so that message gets read from file
        for (Message message : MessageSave.messages) {
           if(message.getId()==  parseInt(data.getHttpVerb_Res().substring("GET /messages/".length(),data.getHttpVerb_Res().indexOf(" HTTP/"))))
           {
               returnMessage=message.getId()+":\n"+message.getContent()+"\n";
               responseContext.setHttpStatusCode("HTTP/1.1 200");
           }
        }
        responseContext.setPayload(returnMessage);

        responseContext.getHeaders().put("Connection", "close");
        responseContext.getHeaders().put("Content-Lenght",  String.valueOf(returnMessage.length()));
        responseContext.getHeaders().put("Content-Type", "text/plain");
        return responseContext;
    }
}

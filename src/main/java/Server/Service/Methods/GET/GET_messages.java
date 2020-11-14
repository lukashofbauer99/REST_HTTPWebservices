package Server.Service.Methods.GET;

import Server.Domain.IRepository;
import Server.Domain.MessageRepository;
import Server.Model.Message;
import Server.Service.Methods.IHTTPMethod;
import Server.Service.Request.IRequestContext;
import Server.Service.Response.IResponseContext;
import Server.Service.Response.ResponseContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
public class GET_messages implements IHTTPMethod {

    ObjectMapper mapper = new ObjectMapper();
    IRepository<Message> repository = new MessageRepository();

    public GET_messages(IRepository<Message> repository) {
        this.repository=repository;
    }

    @Override
    public Boolean analyse(IRequestContext data) {
        return data.getHttpVerb_Res().startsWith("GET /messages ");
    }

    @Override
    public IResponseContext exec(IRequestContext data) {
        ResponseContext responseContext = new ResponseContext();


        String messagesString="";
        for (Message message : repository.getAllEntities()) {
            try {
                messagesString+= mapper.writerWithDefaultPrettyPrinter().writeValueAsString(message);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        responseContext.setPayload(messagesString);
        responseContext.setHttpStatusCode("HTTP/1.1 200");
        responseContext.getHeaders().put("Content-Length",  String.valueOf(messagesString.length()));
        responseContext.getHeaders().put("Content-Type", "text/plain");
        responseContext.getHeaders().put("Connection", "close");


        return responseContext;
    }
}

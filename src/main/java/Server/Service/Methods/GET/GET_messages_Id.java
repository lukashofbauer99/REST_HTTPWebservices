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

import static java.lang.Integer.parseInt;

@NoArgsConstructor
public class GET_messages_Id implements IHTTPMethod {

    ObjectMapper mapper = new ObjectMapper();
    IRepository<Message> repository = new MessageRepository();

    public GET_messages_Id(IRepository<Message> repository) {
        this.repository = repository;
    }

    @Override
    public Boolean analyse(IRequestContext data) {
        return data.getHttpVerb_Res().startsWith("GET /messages/");
    }

    @Override
    public IResponseContext exec(IRequestContext data) {
        ResponseContext responseContext = new ResponseContext();

        String returnMessageString= "";
        Message returnMessage;

        responseContext.setHttpStatusCode("HTTP/1.1 400");
        returnMessage=  repository.findEntity(parseInt(data.getHttpVerb_Res().substring("GET /messages/".length(),data.getHttpVerb_Res().indexOf(" HTTP/"))));
        if(returnMessage!=null)
        {
            try {
                responseContext.setHttpStatusCode("HTTP/1.1 200");
                returnMessageString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(returnMessage);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        responseContext.setPayload(returnMessageString);

        responseContext.getHeaders().put("Connection", "close");
        responseContext.getHeaders().put("Content-Length",  String.valueOf(returnMessageString.length()));
        responseContext.getHeaders().put("Content-Type", "text/plain");
        return responseContext;
    }
}

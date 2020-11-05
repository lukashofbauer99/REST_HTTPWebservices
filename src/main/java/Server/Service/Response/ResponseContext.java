package Server.Service.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class ResponseContext {

    String HTTPStatusCode;


    Map<String,String> headers= new HashMap<>();

    String payload;

    public List<String> toList()
    {
        List<String> list = new ArrayList<>();
        list.add(HTTPStatusCode);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            list.add(entry.getKey()+": "+entry.getValue());
        }
        list.add(payload);
        return list;
    }
}

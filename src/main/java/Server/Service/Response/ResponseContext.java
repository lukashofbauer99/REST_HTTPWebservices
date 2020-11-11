package Server.Service.Response;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ResponseContext implements IResponseContext {

    String httpStatusCode;

    Map<String,String> headers= new HashMap<>();

    String payload;

    private List<String> toList()
    {
        List<String> list = new ArrayList<>();
        list.add(httpStatusCode);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            list.add(entry.getKey()+": "+entry.getValue());
        }
        list.add(payload);
        return list;
    }

    public void SendResponse(BufferedWriter writer) throws IOException {
        System.out.println(httpStatusCode);
        writer.write(httpStatusCode);
        writer.newLine();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            System.out.println(entry.getKey()+": "+entry.getValue());
            writer.write(entry.getKey()+": "+entry.getValue());
            writer.newLine();
        }
        writer.write("\r\n");
        System.out.println(payload);
        writer.write(payload);
        writer.newLine();
        writer.flush();
    }
}

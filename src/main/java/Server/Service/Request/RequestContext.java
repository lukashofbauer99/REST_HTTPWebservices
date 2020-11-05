package Server.Service.Request;

import lombok.Getter;

import java.util.*;

@Getter
public class RequestContext {

    public RequestContext(List<String> Req) {
        boolean header=true;
        httpVerb_Res=Req.stream().findFirst().orElse(null);
        Req.remove(httpVerb_Res);

        for (String x : Req)
        {
            if(!x.contains(":")) {
                header = false;
                boundary=x;
            }
            else {
                if(header)
                    headers.put(x.substring(0, x.indexOf(":")), x.substring(x.indexOf(":") + 2));
                else
                    payload+=x;
            }
        }


    }

    String boundary;

    String httpVerb_Res;

    Map<String,String> headers= new HashMap<>();

    String payload="";

}

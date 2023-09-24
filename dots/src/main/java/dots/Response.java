package dots;

import java.util.Map;

public class Response {
    private ResponseType responseType;
    private Map<String, String> data;

    public Response(ResponseType response, Map<String, String> data) {
        this.responseType = response;
        this.data = data;
    }
}
package dots;

import java.util.Map;

public class Request {
    private RequestType request;

    private Map<String, String> data;

    public Request(RequestType request, Map<String, String> data) {
        this.request = request;
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }

    public RequestType getRequest() {
        return request;
    }
}
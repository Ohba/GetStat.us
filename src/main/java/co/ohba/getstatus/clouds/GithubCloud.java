package co.ohba.getstatus.clouds;

import co.ohba.getstatus.enums.ResponseType;
import co.ohba.getstatus.enums.Status;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class GithubCloud implements Cloud<JsonNode>{

    @Getter
    private Status status;
    @Getter
    private DateTime timestamp;
    @Getter
    private String name = "Github";
    @Getter
    private ResponseType responseType = ResponseType.JSON;

    @Override
    public URL getUrl() throws MalformedURLException {
        return new URL("https://status.github.com/api/status.json");
    }

    @Override
    public void parse(HttpResponse<JsonNode> response, DateTime timestamp) {
        this.timestamp = timestamp;
        String prod = null;
        try {
            prod = response.getBody().getObject().getString("status");
        } catch (JSONException e) {
            prod = "bad";
        }
        if(prod.equals("good")){
            status =  Status.OK;
        } else {
            status = Status.ERROR;
        }
    }
}

package co.ohba.getstatus.clouds;

import co.ohba.getstatus.enums.ResponseType;
import co.ohba.getstatus.enums.Status;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.Getter;
import org.joda.time.DateTime;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

public class HerokuCloud implements Cloud<JsonNode>{

    @Getter
    private Status status;
    @Getter
    private DateTime timestamp;
    @Getter
    private String name = "Heroku";
    @Getter
    private ResponseType responseType = ResponseType.JSON;

    @Override
    public URL getUrl() throws MalformedURLException {
        return new URL("https://status.heroku.com/api/v3/current-status");
    }

    @Override
    public void parse(HttpResponse<JsonNode> response, DateTime timestamp) {
        this.timestamp = timestamp;
        String prod = null;
        try {
            prod = response.getBody().getObject().getJSONObject("status").getString("Production");
        } catch (JSONException e) {
            prod = "red";
        }
        if(prod.equals("green")){
            status =  Status.OK;
        } else {
            status = Status.ERROR;
        }
    }
}

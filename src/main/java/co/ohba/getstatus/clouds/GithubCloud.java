package co.ohba.getstatus.clouds;

import co.ohba.getstatus.enums.Status;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: andre
 * Date: 5/16/13
 * Time: 10:57 PM
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class GithubCloud implements Cloud<JsonNode>{

    private Status status;

    @Override
    public String getName() {
        return "Github";
    }

    @Override
    public URL getUrl() throws MalformedURLException {
        return new URL("https://status.github.com/api/status.json");
    }

    @Override
    public void setStatus(HttpResponse<JsonNode> response) {
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

    @Override
    public Status getStatus() {
        return status;
    }
}

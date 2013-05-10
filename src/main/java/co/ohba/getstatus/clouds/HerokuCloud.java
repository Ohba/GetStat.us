package co.ohba.getstatus.clouds;

import co.ohba.getstatus.enums.Status;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONException;


import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: andre
 * Date: 5/9/13
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class HerokuCloud implements Cloud<JsonNode>{
    @Override
    public String getName() {
        return "Heroku";
    }

    @Override
    public URL getUrl() throws MalformedURLException {
        return new URL("https://status.heroku.com/api/v3/current-status");
    }

    @Override
    public Status getStatus(HttpResponse<JsonNode> response) {
        String prod = null;
        try {
            prod = response.getBody().getObject().getJSONObject("status").getString("Production");
        } catch (JSONException e) {
            prod = "red";
        }
        if(prod.equals("green")){
            return Status.OK;
        } else {
            return Status.ERROR;
        }
    }
}

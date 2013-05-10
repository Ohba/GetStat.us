package co.ohba.getstatus.services;

import co.ohba.getstatus.clouds.HerokuCloud;
import co.ohba.getstatus.enums.Status;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: andre
 * Date: 5/9/13
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class CloudService {

    public Status getCloudStatus() {
        HerokuCloud hc = new HerokuCloud();
        HttpResponse<JsonNode> response;
        try {
            response = Unirest.get(hc.getUrl().toString()).asJson();
        } catch (MalformedURLException e) {
            return Status.ERROR;
        }

        return hc.getStatus(response);
    }
}

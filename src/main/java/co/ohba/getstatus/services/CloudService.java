package co.ohba.getstatus.services;

import co.ohba.getstatus.clouds.Cloud;
import co.ohba.getstatus.clouds.HerokuCloud;
import co.ohba.getstatus.enums.Status;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        hc.setStatus(response);
        return hc.getStatus();
    }

    public List<Cloud> getClouds() throws Exception{
        Reflections reflections = new Reflections(Cloud.class.getPackage().getName());
        Set<Class<? extends Cloud>> cloudTypes = reflections.getSubTypesOf(Cloud.class);
        List<Class<? extends Cloud>> clouds = new ArrayList<>(cloudTypes);
        List<Cloud> c = new ArrayList<>();
        for(Class<? extends Cloud> clazz : clouds){
            Constructor<?> con = clazz.getConstructor();
            Cloud o = (Cloud) con.newInstance();
            HttpResponse<JsonNode> response = Unirest.get(o.getUrl().toString()).asJson();
            o.setStatus(response);
            c.add(o);
        }
        return c;
    }
}

package co.ohba.getstatus.services;

import co.ohba.getstatus.clouds.Cloud;
import co.ohba.getstatus.providers.CloudInstanceProvider;
import co.ohba.getstatus.providers.HttpResponseProvider;
import com.mashape.unirest.http.JsonNode;
import org.reflections.Reflections;

import javax.inject.Inject;
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

    @Inject CloudInstanceProvider cloudProvider;
    @Inject HttpResponseProvider httpProvider;

    public Cloud getCloudStatus(String cloudName) throws MalformedURLException {

        Cloud cloud = cloudProvider.get(cloudName.toLowerCase());

        HttpResponseProvider.TimedResponse<JsonNode> response =  httpProvider.getJson(cloud.getUrl());

        cloud.parse(response.getResponse(), response.getDatetime());

        return cloud;
    }

    public List<Cloud> getClouds() throws ReflectiveOperationException {

        Reflections reflections = new Reflections(Cloud.class.getPackage().getName());
        Set<Class<? extends Cloud>> cloudTypes = reflections.getSubTypesOf(Cloud.class);
        List<Class<? extends Cloud>> clouds = new ArrayList<>(cloudTypes);

        List<Cloud> c = new ArrayList<>();

        for(Class<? extends Cloud> clazz : clouds){
            Constructor<?> con = clazz.getConstructor();
            Cloud o = (Cloud) con.newInstance();
            c.add(o);
            cloudProvider.put(o.getName().toLowerCase(), o);
        }

        return c;
    }
}

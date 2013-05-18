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

public class CloudService {

    @Inject CloudInstanceProvider cloudProvider;
    @Inject HttpResponseProvider httpProvider;

    public Cloud getCloudStatus(String cloudName) throws MalformedURLException {

        Cloud cloud = cloudProvider.get(cloudName.toLowerCase());

        switch (cloud.getResponseType()){
            case JSON:
                HttpResponseProvider.TimedResponse<JsonNode> jsonResponse =  httpProvider.getJson(cloud.getUrl());
                cloud.parse(jsonResponse.getResponse(), jsonResponse.getDatetime());
                break;
            case TEXT:
                HttpResponseProvider.TimedResponse<String> stringResponse =  httpProvider.getString(cloud.getUrl());
                cloud.parse(stringResponse.getResponse(), stringResponse.getDatetime());
                break;
        }

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

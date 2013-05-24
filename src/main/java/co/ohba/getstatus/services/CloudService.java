package co.ohba.getstatus.services;

import co.ohba.getstatus.clouds.Cloud;
import co.ohba.getstatus.providers.CloudInstanceProvider;
import co.ohba.getstatus.providers.HttpResponseProvider;
import com.mashape.unirest.http.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
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
            if(Modifier.isAbstract(clazz.getModifiers())){
                log.info("will not attempt to instantiate abstract class {}", clazz.getSimpleName());
            } else {
                Constructor<?> con = clazz.getConstructor();
                Cloud o = (Cloud) con.newInstance();
                c.add(o);
                cloudProvider.put(o.getName().toLowerCase(), o);
                log.info("instantiated cloud {}", clazz.getSimpleName());
            }
        }

        return c;
    }
}

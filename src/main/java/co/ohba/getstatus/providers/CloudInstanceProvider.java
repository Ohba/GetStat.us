package co.ohba.getstatus.providers;

import co.ohba.getstatus.clouds.Cloud;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class CloudInstanceProvider {

    private Map<String,Cloud> instanceCache = new ConcurrentHashMap <>();


    public Cloud get(String s) {
        return instanceCache.get(s);
    }

    public void put(String s, Cloud o) {
        instanceCache.put(s,o);
    }
}

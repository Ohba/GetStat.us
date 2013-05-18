package co.ohba.getstatus.providers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import javax.inject.Singleton;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Singleton
public class HttpResponseProvider  {

    private static final Integer THRESHHOLD = 5;

    private Map<URL,TimedResponse<JsonNode>> jsonCache = new ConcurrentHashMap<>();
    private Map<URL,TimedResponse<String>> stringCache = new ConcurrentHashMap<>();

    public TimedResponse<JsonNode> getJson(URL url) {

        DateTime timestamp = jsonCache.get(url) == null ? null : jsonCache.get(url).getDatetime();

        DateTime someMinAgo = DateTime.now().minusMinutes(THRESHHOLD);

        if(timestamp == null || timestamp.isBefore(someMinAgo)){
            log.info("cache is old lets GET again");
            updateJsonCache(url);
        } else {
            log.info("i am able to read from cache");
        }

        return jsonCache.get(url);
    }

    public TimedResponse<String> getString(URL url){

        DateTime timestamp = stringCache.get(url) == null ? null : stringCache.get(url).getDatetime();

        DateTime someMinAgo = DateTime.now().minusMinutes(THRESHHOLD);

        if(timestamp == null || timestamp.isBefore(someMinAgo)){
            log.info("cache is old lets GET again");
            updateStringCache(url);
        } else {
            log.info("i am able to read from cache");
        }

        return stringCache.get(url);
    }


    private void updateJsonCache(URL url) {
        HttpResponse<JsonNode> res = Unirest.get(url.toString()).asJson();
        jsonCache.put(url,new TimedResponse(res, DateTime.now() ));
    }

    private void updateStringCache(URL url) {
        HttpResponse<String> res = Unirest.get(url.toString()).asString();
        stringCache.put(url,new TimedResponse(res, DateTime.now() ));
    }

    @Data
    public static class TimedResponse<T> {

        private HttpResponse<T> response;
        private DateTime datetime;

        public TimedResponse(HttpResponse<T> response, DateTime datetime) {
            this.response = response;
            this.datetime = datetime;
        }
    }

}

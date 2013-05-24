package co.ohba.getstatus.clouds;

import co.ohba.getstatus.enums.ResponseType;
import co.ohba.getstatus.enums.Status;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class MongoLabDB implements Cloud<String>{


    private String apiKey = "4f023946e4b0d227da1b51d8";

    @Getter
    private Status status;
    @Getter
    private DateTime timestamp;
    @Getter
    private String name = "MongoLab-DB";
    @Getter
    private ResponseType responseType = ResponseType.TEXT;

    @Override
    public URL getUrl() throws MalformedURLException {
        return new URL("https://api.mongolab.com/api/1/clusters/rs-dbh97/databases/mongolab-status/collections/public-status-events?q={ '$and': [  {'timeResolved': {'$exists': false}},{'timeStarted': {'$lt': {'$date': '" + DateTime.now().withZone(DateTimeZone.UTC) + "'}}},{'published': true},{'serviceType': 'DB'}  ] }&fo=true&s={'timeStarted': -1}&apiKey=" + apiKey);
    }

    @Override
    public void parse(HttpResponse<String> response, DateTime timestamp) {
        this.timestamp = timestamp;
        if(response.getBody().toString().trim().equalsIgnoreCase("null")){
            status = Status.OK;
        }else{
            status = Status.ERROR;
        }
    }
}

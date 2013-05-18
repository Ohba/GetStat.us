package co.ohba.getstatus.clouds;

import co.ohba.getstatus.enums.ResponseType;
import co.ohba.getstatus.enums.Status;
import com.mashape.unirest.http.HttpResponse;
import org.joda.time.DateTime;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A Cloud is an interface used to define a service who's status will be requested.
 * Each implementation of Cloud needs to be able to parse the appropriate response
 * from hitting the service's provided url.
 */
public interface Cloud<T>{

    /**
    * Return the name of the service you are requesting the status of
    * @return String
    */
    String getName();

    /**
    * Return the url of the service's API to get the status
    * @return URL
    */
    URL getUrl() throws MalformedURLException;

    /**
    * Returns the actual status of the service
    * @return Status
    */
    Status getStatus();

    /**
    * Return the time the status was updated from the service
    * @return DateTime
    */
    DateTime getTimestamp();

    /**
     * Return the type of response that is expected from calling the
     * service's url
     * @return ResponseType
     */
    ResponseType getResponseType();

    /**
    * parses through the returned response of the url to get the status.
    * Also stores the date it got the status for caching purposes
    * @param response the response from calling the service's url
    * @param timestamp the time that the response was received
    */
    void parse(HttpResponse<T> response, DateTime timestamp);
}

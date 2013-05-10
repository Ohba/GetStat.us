package co.ohba.getstatus.clouds;

import co.ohba.getstatus.enums.Status;
import com.mashape.unirest.http.HttpResponse;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: andre
 * Date: 5/9/13
 * Time: 10:09 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Cloud<T>{

    String getName();
    URL getUrl() throws MalformedURLException;
    Status getStatus(HttpResponse<T> response);
}

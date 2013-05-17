package co.ohba.getstatus.resources;

import co.ohba.getstatus.clouds.Cloud;
import co.ohba.getstatus.services.CloudService;
import com.google.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: andre
 * Date: 5/16/13
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/clouds")
@Produces(MediaType.APPLICATION_JSON)
public class CloudResource {

    @Inject
    CloudService service;

    @GET
    public Response getClouds() throws Exception {
        List<Cloud> clouds = service.getClouds();
        return Response.ok().entity(clouds).build();
    }

    @GET
    @Path("/{cloudName}")
    public Cloud getCloud(@PathParam("cloudName") String cloudName) throws MalformedURLException {
        return service.getCloudStatus(cloudName);
    }

}

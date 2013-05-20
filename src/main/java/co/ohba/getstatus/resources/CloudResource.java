package co.ohba.getstatus.resources;

import co.ohba.getstatus.clouds.Cloud;
import co.ohba.getstatus.services.CloudService;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Path("/clouds")
@Produces(MediaType.APPLICATION_JSON)
public class CloudResource {

    @Inject
    CloudService service;

    private void subjectLog() {
        Subject s  = SecurityUtils.getSubject();
        log.info("subject: {}", s);
        log.info("hasRole? admin:{} guest:{} geek:{}", s.hasRole("admin"),s.hasRole("guest"),s.hasRole("geek"));
        log.info("session: {}", s.getSession());
    }

    @GET
    public Response getClouds() throws Exception {
        subjectLog();
        List<Cloud> clouds = service.getClouds();
        return Response.ok().entity(clouds).build();
    }

    @GET
    @Path("/{cloudName}")
    public Cloud getCloud(@PathParam("cloudName") String cloudName) throws MalformedURLException {
        subjectLog();
        return service.getCloudStatus(cloudName);
    }

}

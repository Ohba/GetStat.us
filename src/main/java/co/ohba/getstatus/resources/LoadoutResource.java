package co.ohba.getstatus.resources;

import co.ohba.getstatus.entities.Loadout;
import co.ohba.getstatus.services.LoadoutService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/geek/{geekId}/loadout")
public class LoadoutResource {

    @Inject
    LoadoutService service;

    @GET
    public List<Loadout> get(@PathParam("geekId") Long geekId){
        //TODO: security
        return service.get(geekId);
    }

    @GET
    @Path("/{id}")
    public Loadout get(@PathParam("geekId") Long geekId, @PathParam("id") Long id){
        //TODO: security
        return service.get(geekId, id);
    }

    @POST
    public Loadout create(@PathParam("geekId") Long geekId, Loadout loadout){
        //TODO: security
        return service.save(loadout);
    }

    @PUT
    @Path("/{id}")
    public Loadout update(@PathParam("geekId") Long geekId, @PathParam("id") Long id, Loadout loadout){
        //TODO: security
        return service.update(loadout);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("geekId") Long geekId, @PathParam("id") Long id){
        //TODO: security
        service.delete(id);
        return Response.status(204).build();
    }
}

package co.ohba.getstatus.resources;

import co.ohba.getstatus.entities.Geek;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: andre
 * Date: 5/9/13
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Path("/api/geeks")
@Produces(MediaType.APPLICATION_JSON)
public class GeekResource {

    @Inject
    private EntityManager em;

    @Inject
    SecureRandomNumberGenerator srng;

    @GET
    public List<Geek> get() {
        log.error("here 1");
        return em.createQuery("SELECT g from Geek g").getResultList();
    }

    @GET
    @Path("me")
    public Geek getMe() {
        Subject s  = SecurityUtils.getSubject();
        Long id = (Long) s.getPrincipal();
        log.info("subject={} id={}", s, id);
        Geek gk = em.find(Geek.class, id);
        return gk;
    }

    @POST
    @Path("me")
    public Geek postMe() {
        return getMe();
    }

    @GET
    @Path("create/{username}/{password}")
    public Geek createTempUser(@PathParam("username") String username, @PathParam("password") String password) {

        log.error("here 2");

        ByteSource bsource = srng.nextBytes();
        String hashedPw = new Sha512Hash(password, bsource, 1024).toBase64();

        Geek gk = new Geek();
        gk.setUsername(username);
        gk.setHashedPw(hashedPw);
        gk.setPwSalt(bsource.getBytes());

        em.getTransaction().begin();
        em.persist(gk);
        em.getTransaction().commit();

        log.error("tried to save user {} with pw {}", username, password);

        return gk;
    }
}

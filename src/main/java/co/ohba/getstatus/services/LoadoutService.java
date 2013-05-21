package co.ohba.getstatus.services;

import co.ohba.getstatus.entities.Loadout;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
public class LoadoutService {

    @Inject
    EntityManager em;

    public List<Loadout> get(Long geekId){
        List<Loadout> loadouts = em.createQuery("SELECT l FROM Loadout l WHERE l.geekId = :geekId", Loadout.class)
                .setParameter("geekId", geekId)
                .getResultList();
        return loadouts;
    }

    public Loadout get(Long geekId, Long id){
        List<Loadout> loadouts = em.createQuery("SELECT l FROM Loadout l WHERE l.id = :id AND l.geekId = :geekId", Loadout.class)
                .setParameter("id", id)
                .setParameter("geekId", geekId)
                .getResultList();
        if(loadouts.size() > 0){
            return loadouts.get(0);
        }
        return null;
    }

    public Loadout save(Loadout loudout){
        em.getTransaction().begin();
        em.persist(loudout);
        em.getTransaction().commit();
        em.close();
        return loudout;
    }

    public Loadout update(Loadout loudout){
        em.getTransaction().begin();
        em.merge(loudout);
        em.getTransaction().commit();
        em.close();
        return loudout;
    }

    public void delete(Long id){
        Loadout loadout = em.find(Loadout.class, id);
        em.getTransaction().begin();
        em.remove(loadout);
        em.getTransaction().commit();
        em.close();
    }
}

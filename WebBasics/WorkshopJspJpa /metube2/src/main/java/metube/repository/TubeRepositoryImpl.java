package metube.repository;

import metube.domain.entities.Tube;
import metube.domain.entities.enums.TubeStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class TubeRepositoryImpl implements TubeRepository {
	
	@PersistenceContext
    private final EntityManager entityManager;

    @Inject
    public TubeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Tube save(Tube entity) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(entity);
	    this.entityManager.flush();
        this.entityManager.getTransaction().commit();

        return entity;
    }

    @Override
    public List<Tube> findAll() {
        this.entityManager.getTransaction().begin();
        List<Tube> allTubes = this.entityManager
                .createQuery("SELECT t FROM Tube t", Tube.class)
                .getResultList();
        this.entityManager.getTransaction().commit();

        return allTubes;
    }

    @Override
    public Tube findById(String id) {
        this.entityManager.getTransaction().begin();
        Tube tube = this.entityManager
                .createQuery("SELECT t FROM Tube t WHERE t.id = :id", Tube.class)
                .setParameter("id", id)
                .getSingleResult();
        this.entityManager.getTransaction().commit();

        return tube;
    }

    @Override
    public Long size() {
        this.entityManager.getTransaction().begin();
        long size = this.entityManager
                .createQuery("SELECT count(t) FROM Tube t", Long.class)
                .getSingleResult();
        this.entityManager.getTransaction().commit();

        return size;
    }

    @Override
    public Tube update(Tube tube) {
	    this.entityManager.getTransaction().begin();
        this.entityManager.merge(tube);
	    this.entityManager.flush();
	    this.entityManager.getTransaction().commit();
        return tube;
    }
    
    @Override
    public List<Tube> findAllPendingTubes(){
	    this.entityManager.getTransaction().begin();
	    List<Tube> allPendingTubes = this.entityManager
			    .createQuery("SELECT t FROM Tube t where t.tubeStatus=:tubeStatus ", Tube.class)
			    .setParameter("tubeStatus", TubeStatus.PENDING)
			    .getResultList();
	    this.entityManager.getTransaction().commit();
	
	    return allPendingTubes;
    	
    }
}

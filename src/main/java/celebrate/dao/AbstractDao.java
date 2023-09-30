package celebrate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao {
	
	@PersistenceContext
	@Autowired
	protected EntityManager entityManager;
}

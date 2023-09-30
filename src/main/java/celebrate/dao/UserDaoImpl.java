package celebrate.dao;

import java.math.BigInteger;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository("userDao")
@SuppressWarnings("unchecked")
public class UserDaoImpl extends AbstractDao implements UserDao {

	@Override
	public long getUserId(String email) throws Exception {
		String queryStr = "SELECT u.USERID FROM USER u WHERE u.EMAIL = ?";
		Query query = entityManager.createNativeQuery(queryStr);
		query.setParameter(1, email);
		return ((BigInteger) query.getSingleResult()).longValue();
	}

}

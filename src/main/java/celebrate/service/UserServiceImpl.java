package celebrate.service;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import celebrate.dao.QuestionDao;
import celebrate.dao.UserDao;
import celebrate.model.SignUpRequest;
import celebrate.model.User;
import celebrate.util.CelebrateUtil;

@Service("userService")
@Transactional(propagation=Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
	
	Map<String, String> userTokens = new HashMap<String, String>();
	
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	
	@Override
	public User getUser(long userId) throws Exception {
		User user = new User();
		//user.setCreateDate(new Date());
		//user.setUpdatedDate(new Date());
		user.setEmail("jayaram@gmail.com");
		user.setFirstname("javasavvy");
		user.setLastname("exmp");
		return user;
	}

	@Override
	public boolean authenticateUser(String email, String password) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void signup(SignUpRequest signUpRequest) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean authenticateGuest(String guest) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getAuthenticationToken(String email) throws Exception {
		String token = userTokens.get(email);
		if(token == null) {
			token = "TokenID" + CelebrateUtil.generateNewToken();
			userTokens.put(email, token);
		} 
		return token;
	}

	@Override
	public long getUserId(String token) throws Exception {
		String email = null;
		for(Map.Entry<String, String> userToken : userTokens.entrySet()) {
			if(userToken.getValue().equals(token)) {
				email = userToken.getKey();
				break;
			}
		}
		if(email == null) {
			throw new Exception("User not found");
		}
		return userDao.getUserId(email);
	}
	
	

}

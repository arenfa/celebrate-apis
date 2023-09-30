package celebrate.service;


import celebrate.model.SignUpRequest;
import celebrate.model.User;

public interface UserService {

	User getUser(long userId) throws Exception;
	boolean authenticateUser(String email, String password) throws Exception;
	void signup(SignUpRequest signUpRequest) throws Exception;
	boolean authenticateGuest(String guest) throws Exception;
	String getAuthenticationToken(String email) throws Exception;
	long getUserId(String token) throws Exception;
}

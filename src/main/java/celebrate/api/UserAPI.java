package celebrate.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import celebrate.model.SignUpRequest;
import celebrate.model.SignUpResponse;
import celebrate.service.UserService;

/**
 * @author arenfakhourian
 *
 */
@Component
@Path("/user")
public class UserAPI {

	
	@Autowired(required=true)
	@Qualifier("userService")
	private UserService userService;
	
	@POST
	@Path("/signup")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public SignUpResponse register(SignUpRequest signUpRequest) throws WebApplicationException {
		SignUpResponse signUpResponse = new SignUpResponse();
		try {
			userService.signup(signUpRequest);
			
			signUpResponse.setEmail(signUpRequest.getEmail());
			signUpResponse.setGuestId(signUpRequest.getGuestId());
			signUpResponse.setStatus("success");
		} catch(Exception e) {
			//log.error("API error", e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
		return signUpResponse;
	}

	// Forgot password
	
	
}

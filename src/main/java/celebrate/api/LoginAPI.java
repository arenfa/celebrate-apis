package celebrate.api;

 
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import celebrate.model.LoginRequest;
import celebrate.model.LoginResponse;
import celebrate.model.SignUpRequest;
import celebrate.model.SignUpResponse;
import celebrate.security.SessionContext;
import celebrate.service.UserService;
import celebrate.util.CelebrateUtil;


 
/**
 * @author arenfakhourian
 *
 */
@Path("/login")
@Component
public class LoginAPI {
	
	// TODO loger
	
	@Autowired(required=true)
	@Qualifier("userService")
	private UserService userService;
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response login(LoginRequest loginRequest, 
			@QueryParam("Guest") @DefaultValue("false") boolean guest) throws WebApplicationException {
		LoginResponse loginResponse = new LoginResponse();
		try {
			// Guest
			if(guest) {
				String guestId = loginRequest.getGuestId(); 
				// authenticate guest id
				boolean authenticated = userService.authenticateGuest(guestId);
				if(!authenticated) {
					// TODO guest not authenticated
					throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
				}
				// generate auth token
				String authToken = "TokenID" + CelebrateUtil.generateNewToken();
				NewCookie cookie1 = new NewCookie("CelebrateAuthToken", authToken);
			    Response.ResponseBuilder rb = Response.ok(loginResponse);
			    Response response = rb.cookie(cookie1).build();
				loginResponse.setGuestId(guestId);
				loginResponse.setStatus("success");
				loginResponse.setAuthTokenValidUntil("2019-01-16T09:24:12.134Z");
				return response;
			} else {
//				boolean authenticated = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
//				if(!authenticated) {
//					// TODO user not authenticated
//					throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
//				}
				// generate auth token
				String authToken = userService.getAuthenticationToken(loginRequest.getEmail());
				NewCookie cookie1 = new NewCookie("CelebrateAuthToken", authToken);
			    Response.ResponseBuilder rb = Response.ok(loginResponse);
			    Response response = rb.cookie(cookie1).build();
				loginResponse.setEmail(loginRequest.getEmail());
				loginResponse.setStatus("success");
				loginResponse.setAuthTokenValidUntil("2019-01-16T09:24:12.134Z");
				return response;
			}
		} catch(Exception e) {
			//log.error("API error", e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GET
	@Path("/guest")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public LoginResponse generateGuestId() throws WebApplicationException {
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setGuestId(CelebrateUtil.generateNewGuestId());
		return loginResponse;
	}



}

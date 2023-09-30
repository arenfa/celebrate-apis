package celebrate.api;

 
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import celebrate.model.BooleanRequest;
import celebrate.model.Comment;
import celebrate.model.Comments;
import celebrate.model.Question;
import celebrate.model.QuestionResponse;
import celebrate.model.Questions;
import celebrate.security.SessionContext;
import celebrate.security.SessionContextImpl;
import celebrate.service.QuestionService;
import celebrate.service.UserService;

 
/**
 * @author arenfakhourian
 *
 */
@Path("/questions")
@Component
public class QuestionAPI {
	
	Logger log = Logger.getLogger(QuestionAPI.class);
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
//	@Autowired
//	private SessionContext sessionContext;
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	//@RolesAllowed("ROLE_USER")
	public Questions getUserQuestions(@QueryParam("includeComments") boolean includeComments, @Context HttpHeaders headers) throws WebApplicationException {
		Questions questions = new Questions();
		long userId = getUserId(headers);		
		try {
			List<Question> questionList = questionService.getQuestions(userId, includeComments);
			questions.getQuestion().addAll(questionList);
		} catch (Exception e) {
			log.error("API error", e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
		
		return questions;
	}
	
	@GET
	@Path("/{questionId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	//@RolesAllowed("ROLE_USER")
	public Question getQuestion(@PathParam("questionId") long questionId, @Context HttpHeaders headers) throws WebApplicationException {
		long userId = getUserId(headers);		
		try {
			Question question = questionService.getQuestion(questionId);
			return question;
		} catch (Exception e) {
			log.error("API error", e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GET
	@Path("/{questionId}/comments")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Comments getQuestionComments(@PathParam("questionId") long questionId, 
			@QueryParam("sortBy") String sortBy, @Context HttpHeaders headers) throws WebApplicationException {
		Comments comments = new Comments();
		long userId = getUserId(headers);
		try {
			List<Comment> commentList = questionService.getQuestionComments(questionId, sortBy);
			if(commentList != null) {
				comments.getComment().addAll(commentList);
			}
		} catch (Exception e) {
			log.error("API error", e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
		
		return comments;
	}
		
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Question createQuestion(Question question, @Context HttpHeaders headers) throws WebApplicationException {
		long userId = getUserId(headers);
		try {
			question = questionService.createQuestion(userId, question);
		} catch (Exception e) {
			log.error("API error", e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
		return question;
	}
	
	@POST
	@Path("/{questionId}/respond")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public QuestionResponse respondQuestion(@PathParam("questionId") long questionId, QuestionResponse response, @Context HttpHeaders headers) throws WebApplicationException {
		long userId = getUserId(headers);
		try {
			response.setQuestionId(questionId);
			response.setUserId(userId);
			response = questionService.respondQuestion(response);
		} catch (Exception e) {
			log.error("API error", e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@POST
	@Path("/{questionId}/comments")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Comment addComment(@PathParam("questionId") long questionId, Comment comment, @Context HttpHeaders headers) throws WebApplicationException {
		long userId = getUserId(headers);
		try {
			comment = questionService.addComment(questionId, comment);
		} catch (Exception e) {
			log.error("API error", e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
		return comment;
	}


	@POST
	@Path("/{questionId}/comments/{commentId}/like")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public BooleanRequest likeComment(@PathParam("questionId") long questionId, @PathParam("commentId") long commentId, BooleanRequest request, @Context HttpHeaders headers) throws WebApplicationException {
		long userId = getUserId(headers);
		try {
			questionService.likeComment(commentId, request.isValue());
		} catch (Exception e) {
			log.error("API error", e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
		return request;
	}
			
	@POST
	@Path("/{questionId}/comments/{commentId}/flag")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public BooleanRequest flagComment(@PathParam("questionId") long questionId, @PathParam("commentId") long commentId, BooleanRequest request, @Context HttpHeaders headers) throws WebApplicationException {
		long userId = getUserId(headers);
		try {
			 questionService.flagComment(commentId, request.isValue());
		} catch (Exception e) {
			log.error("API error", e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
		return request;
	}


	@GET
	@Path("/suggest")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	//@RolesAllowed("ROLE_USER")
	public Questions getUserSuggestedQuestions(@Context HttpHeaders headers) throws WebApplicationException {
		Questions questions = new Questions();
		
		try {
			long userId = getUserId(headers);
			List<Question> questionList = questionService.getSuggestedQuestions(userId);
			questions.getQuestion().addAll(questionList);
		} catch (Exception e) {
			log.error("API error", e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
		
		return questions;
	}

	@GET
	@Path("/search")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	//@RolesAllowed("ROLE_USER")
	public Questions searchQuestions(@Context HttpHeaders headers) throws WebApplicationException {
		Questions questions = new Questions();
		
		try {
			List<Question> questionList = questionService.searchQuestions();
			questions.getQuestion().addAll(questionList);
		} catch (Exception e) {
			log.error("API error", e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
		
		return questions;
	}
	
	private long getUserId(HttpHeaders headers) throws WebApplicationException {
		String token = null;
        for(Map.Entry<String, Cookie> cookie : headers.getCookies().entrySet()) {
        		if("CelebrateAuthToken".equals(cookie.getKey())) {
        			token = cookie.getValue().getValue();
        			break;
        		}
        }
        if(token == null) {
        		throw new WebApplicationException(Status.UNAUTHORIZED);
        }
        
        try {
        		long userId = userService.getUserId(token);
        		//((SessionContextImpl) sessionContext).setUserId(userId);
        		return userId;
        } catch(Exception e) {
			log.error("API error", e);
        		throw new WebApplicationException(e, Status.UNAUTHORIZED);
        }
	}

}

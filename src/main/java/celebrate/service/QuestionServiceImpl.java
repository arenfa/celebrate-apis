package celebrate.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import celebrate.dao.QuestionDao;
import celebrate.entity.QuestionAvailableResponse;
import celebrate.entity.UserQuestion;
import celebrate.model.AvailableResponse;
import celebrate.model.Comment;
import celebrate.model.Question;
import celebrate.model.QuestionResponse;
import celebrate.security.SessionContext;

@Service("questionService")
@Transactional(propagation = Propagation.REQUIRED)
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	@Qualifier("questionDao")
	private QuestionDao questionDao;
	
//	@Autowired
//	private SessionContext sessionContext;

	@Override
	public List<Question> getQuestions(long userId, boolean includeComments) throws Exception {
		List<Question> questions = new ArrayList<Question>();
		List<celebrate.entity.Question> dbQuestions = questionDao.getQuestions(userId, includeComments);
		for(celebrate.entity.Question dbQuestion : dbQuestions) {
			Question question = new Question();
			question.setId(dbQuestion.getQuestionId());
			question.setFrequency(dbQuestion.getFrequency());
			question.setTitle(dbQuestion.getTitle());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dbQuestion.getCreatedDate());
			question.setDateTime(calendar);
			if(includeComments) {
				for(celebrate.entity.Comment dbComment : dbQuestion.getComments()) {
					Comment comment = new Comment();
					comment.setId(dbComment.getCommentId());
					comment.setCommentText(dbComment.getValue());
					Calendar calendar2 = Calendar.getInstance();
					calendar2.setTime(dbComment.getCreatedDate());
					comment.setDateTime(calendar2);
					comment.setLikes((int) dbComment.getLike());
					question.getComments().add(comment);
				}
				
			}
			for(QuestionAvailableResponse dbAvailableResponse : dbQuestion.getAvailableResponses()) {
				AvailableResponse availableResponse = new AvailableResponse();
				availableResponse.setId(dbAvailableResponse.getResponseId());
				availableResponse.setResponse(dbAvailableResponse.getResponse());
				question.getAvailableResponses().add(availableResponse);
			}
			questions.add(question);
		}
		return questions;
	}
	
	@Override
	public Question getQuestion(long questionId) throws Exception {
		celebrate.entity.Question dbQuestion = questionDao.getQuestion(questionId);
		Question question = new Question();
		question.setId(dbQuestion.getQuestionId());
		question.setFrequency(dbQuestion.getFrequency());
		question.setTitle(dbQuestion.getTitle());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dbQuestion.getCreatedDate());
		question.setDateTime(calendar);
		return question;
	}

	@Override
	public List<Comment> getQuestionComments(long questionId, String sortBy) throws Exception {
		List<Comment> comments = new ArrayList<Comment>();
		List<celebrate.entity.Comment> dbComments = questionDao.getQuestionComments(questionId, sortBy);
		for(celebrate.entity.Comment dbComment : dbComments) {
			Comment comment = new Comment();
			comment.setId(dbComment.getCommentId());
			comment.setCommentText(dbComment.getValue());
			comment.setLikes((int) dbComment.getLike());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dbComment.getCreatedDate());
			comment.setDateTime(calendar);
			comments.add(comment);
		}
		return comments;
	}

	@Override
	public Question createQuestion(long userId, Question question) throws Exception {
		celebrate.entity.Question dbQuestion = new celebrate.entity.Question();
		dbQuestion.setTitle(question.getTitle());
		dbQuestion.setFrequency(question.getFrequency());
		dbQuestion.setCreatedDate(new Date());
		dbQuestion = questionDao.createQuestion(dbQuestion);
		
		question.setId(dbQuestion.getQuestionId());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dbQuestion.getCreatedDate());
		question.setDateTime(calendar);
		
		UserQuestion userQuestion = new UserQuestion();
		userQuestion.setUserId(userId);
		userQuestion.setQuestionId(dbQuestion.getQuestionId());
		userQuestion = questionDao.addUserQuestion(userQuestion);
		
		return question;
	}

	@Override
	public QuestionResponse respondQuestion(QuestionResponse response) throws Exception {
		celebrate.entity.QuestionResponse dbResponse = new celebrate.entity.QuestionResponse();
		dbResponse.setQuestionId(response.getQuestionId());
		dbResponse.setUserId(response.getUserId());
		dbResponse.setResponse(response.getValue());
		dbResponse.setCreateDate(new Date());
		dbResponse = questionDao.respondQuestion(dbResponse);
		
		if(response.getCommentText() != null && !response.getCommentText().isEmpty()) {
			celebrate.entity.Comment dbComment = new celebrate.entity.Comment();
			dbComment.setQuestionId(response.getQuestionId());
			dbComment.setLike(1);
			dbComment.setValue(response.getCommentText());
			dbComment.setCreatedDate(new Date());
			dbComment = questionDao.addComment(dbComment);
		}
		return response;
	}
	
	@Override
	public Comment addComment(long questionId, Comment comment) throws Exception {
		if(comment.getCommentText() != null && !comment.getCommentText().isEmpty()) {
			celebrate.entity.Comment dbComment = new celebrate.entity.Comment();
			dbComment.setQuestionId(questionId);
			dbComment.setLike(0);
			dbComment.setValue(comment.getCommentText());
			dbComment = questionDao.addComment(dbComment);
			
			comment.setId(dbComment.getCommentId());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dbComment.getCreatedDate());
			comment.setDateTime(calendar);
		}
		
		return comment;
	}

	@Override
	public void likeComment(long commentId, boolean like) throws Exception {
		questionDao.likeComment(commentId, like);
	}

	@Override
	public void flagComment(long commentId, boolean flag) throws Exception {
		questionDao.flagComment(commentId, flag);
	}

	@Override
	public List<Question> getSuggestedQuestions(long userId) throws Exception {
		return null;
		//return questionDao.getSuggestedQuestions(userId);
	}

	@Override
	public List<Question> searchQuestions() throws Exception {
		return null;
		//return questionDao.searchQuestions();
	}
}

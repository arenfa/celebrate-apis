package celebrate.service;

import java.util.List;

import celebrate.model.BooleanRequest;
import celebrate.model.Comment;
import celebrate.model.Question;
import celebrate.model.QuestionResponse;

public interface QuestionService {
	
	List<Question> getQuestions(long userId, boolean includeComments) throws Exception;
	public Question getQuestion(long questionId) throws Exception;
	List<Comment> getQuestionComments(long questionId, String sortBy) throws Exception;
	Question createQuestion(long userId, Question question) throws Exception;
	QuestionResponse respondQuestion(QuestionResponse response) throws Exception;
	Comment addComment(long questionId, Comment comment) throws Exception;
	void likeComment(long commentId, boolean like) throws Exception;
	void flagComment(long commentId, boolean flag) throws Exception;
	List<Question> getSuggestedQuestions(long userId) throws Exception;
	List<Question> searchQuestions() throws Exception;

}

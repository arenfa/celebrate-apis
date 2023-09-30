package celebrate.dao;

import java.util.List;

import celebrate.entity.Comment;
import celebrate.entity.Question;
import celebrate.entity.QuestionAvailableResponse;
import celebrate.entity.QuestionResponse;
import celebrate.entity.UserQuestion;

public interface QuestionDao {
	
	List<Question> getQuestions(long userId, boolean includeComments) throws Exception;
	Question getQuestion(long questionId) throws Exception;
	List<Comment> getQuestionComments(long questionId, String sortBy) throws Exception;
	List<QuestionAvailableResponse> getQuestionAvailableResponses(long questionId) throws Exception;
	Question createQuestion(Question question) throws Exception;
	Comment addComment(Comment comment) throws Exception;
	QuestionResponse respondQuestion(QuestionResponse response) throws Exception;
	UserQuestion addUserQuestion(UserQuestion userQuestion) throws Exception;
	void likeComment(long commentId, boolean like) throws Exception;
	void flagComment(long commentId, boolean flag) throws Exception;
	List<Question> getSuggestedQuestions(long userId) throws Exception;
	List<Question> searchQuestions() throws Exception;

}

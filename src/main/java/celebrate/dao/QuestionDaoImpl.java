package celebrate.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import celebrate.model.BooleanRequest;
import celebrate.entity.Comment;
import celebrate.entity.Question;
import celebrate.entity.QuestionAvailableResponse;
import celebrate.entity.QuestionResponse;
import celebrate.entity.UserQuestion;

@Repository("questionDao")
@SuppressWarnings("unchecked")
public class QuestionDaoImpl extends AbstractDao implements QuestionDao {

	
	@Override
	public List<Question> getQuestions(long userId, boolean includeComments) throws Exception {
		String queryStr = "SELECT q.QUESTIONID, q.TITLE, q.FREQUENCY, q.RESPONSE, q.CREATED_AT FROM QUESTION q where q.QUESTIONID in (SELECT q.QUESTIONID FROM USER_QUESTION uq WHERE uq.USERID =:userId)";
		Query query = entityManager.createNativeQuery(queryStr, Question.class);
		query.setParameter("userId", userId);
		
		List<Question> questions = query.getResultList();
		if(questions != null) {
			for(Question question : questions) {
				question.setAvailableResponses(getQuestionAvailableResponses(question .getQuestionId()));
				if(includeComments) {
					question.setComments(getQuestionComments(question.getQuestionId(), null));
				}
			}
		}
		
		return questions;
	}
	
	@Override
	public Question getQuestion(long questionId) throws Exception {
		String queryStr = "SELECT q.QUESTIONID, q.TITLE, q.FREQUENCY, q.RESPONSE, q.CREATED_AT FROM QUESTION q where q.QUESTIONID =: questionId";
		Query query = entityManager.createNativeQuery(queryStr, Question.class);
		query.setParameter("questionId", questionId);
		return (Question) query.getSingleResult();	
	}

	@Override
	public List<Comment> getQuestionComments(long questionId, String sortBy) throws Exception {
		String queryStr = "SELECT c.COMMENTID, c.CTEXT, c.CLIKE, c.CREATED_AT, c.QUESTIONID FROM COMMENT c where c.QUESTIONID =:questionId";
		Query query = entityManager.createNativeQuery(queryStr, Comment.class);
		query.setParameter("questionId", questionId);
		return query.getResultList();
	}
	
	@Override
	public List<QuestionAvailableResponse> getQuestionAvailableResponses(long questionId) throws Exception {
		String queryStr = "SELECT qar.QAR_ID, qar.QUESTIONID, qar.RESPONSEID, r.RESPONSE FROM QUESTION_AVAIL_RESPONSE qar INNER JOIN RESPONSE r on qar.RESPONSEID=r.RESPONSEID AND qar.QUESTIONID =:questionId";
		Query query = entityManager.createNativeQuery(queryStr, QuestionAvailableResponse.class);
		query.setParameter("questionId", questionId);
		return query.getResultList();
	}

	@Override
	public Question createQuestion(Question question) throws Exception { 
		entityManager.persist(question);
		return question;
	}
	
	@Override
	public Comment addComment(Comment comment) throws Exception {
		entityManager.persist(comment);
		return comment;
	}

	@Override
	public QuestionResponse respondQuestion(QuestionResponse response) throws Exception {
		entityManager.persist(response);
		return response;
	}
	
	@Override
	public UserQuestion addUserQuestion(UserQuestion userQuestion) throws Exception {
		entityManager.persist(userQuestion);
		return userQuestion;
	}

	@Override
	public void likeComment(long commentId, boolean like) throws Exception {
		String queryStr = "SELECT c.CLIKE FROM COMMENT c where c.COMMENTID =:commentId";
		Query query = entityManager.createNativeQuery(queryStr);
		query.setParameter("commentId", commentId);
		int likes = (int) query.getSingleResult();
		
		String queryUpdateStr = "UPDATE COMMENT c set c.CLIKE =: clike where c.COMMENTID =:commentId";
		Query updateQuery = entityManager.createNativeQuery(queryUpdateStr);
		query.setParameter("clike", likes + 1);
		query.setParameter("commentId", commentId);
		updateQuery.executeUpdate();
	}

	@Override
	public void flagComment(long commentId, boolean flag) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Question> getSuggestedQuestions(long userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> searchQuestions() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

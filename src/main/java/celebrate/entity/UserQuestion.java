package celebrate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="USER_QUESTION")
public class UserQuestion implements Serializable {
	
	private static final long serialVersionUID = -4477578942364171442L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="UQ_ID")
	private long userQuestionId;
	
	@Column(name="USERID")
	private long userId;
	
	@Column(name="QUESTIONID")
	private long questionId;

	/**
	 * @return the questionId
	 */
	public long getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the userQuestionId
	 */
	public long getUserQuestionId() {
		return userQuestionId;
	}

	/**
	 * @param userQuestionId the userQuestionId to set
	 */
	public void setUserQuestionId(long userQuestionId) {
		this.userQuestionId = userQuestionId;
	}

}

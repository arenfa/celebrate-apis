package celebrate.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="QUESTION_RESPONSE")
public class QuestionResponse implements Serializable {

	private static final long serialVersionUID = -5295414854968459157L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="QR_ID")
	private long questionResponseId;
	
	@Column(name="USERID")
	private long userId;
	
	@Column(name="QUESTIONID")
	private long questionId;
	
	
	@Column(name="RESPONSE")
	private long response;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createDate;


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
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the questionResponseId
	 */
	public long getQuestionResponseId() {
		return questionResponseId;
	}

	/**
	 * @param questionResponseId the questionResponseId to set
	 */
	public void setQuestionResponseId(long questionResponseId) {
		this.questionResponseId = questionResponseId;
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
	 * @return the response
	 */
	public long getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(long response) {
		this.response = response;
	}
	
}

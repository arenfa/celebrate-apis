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
@Table(name="QUESTION_AVAIL_RESPONSE")
public class QuestionAvailableResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 898572696464621832L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="QAR_ID")
	private long questionAvailResponseId;
	
	@Column(name="QUESTIONID")
	private long questionId;
	
	
	@Column(name="RESPONSEID")
	private long responseId;

	
	@Column(name="RESPONSE")
	private String response;

	/**
	 * @return the questionAvailResponseId
	 */
	public long getQuestionAvailResponseId() {
		return questionAvailResponseId;
	}


	/**
	 * @param questionAvailResponseId the questionAvailResponseId to set
	 */
	public void setQuestionAvailResponseId(long questionAvailResponseId) {
		this.questionAvailResponseId = questionAvailResponseId;
	}


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
	 * @return the responseId
	 */
	public long getResponseId() {
		return responseId;
	}


	/**
	 * @param responseId the responseId to set
	 */
	public void setResponseId(long responseId) {
		this.responseId = responseId;
	}


	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}


	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	

	
}

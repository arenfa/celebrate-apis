package celebrate.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name="QUESTION")
public class Question implements Serializable {
	
	private static final long serialVersionUID = -4171229113974944059L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="QUESTIONID")
	private long questionId;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="FREQUENCY")
	private String frequency;
	
	@Column(name="RESPONSE", nullable=true)
	private String response;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name="CREATED_AT", nullable=false, columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	private Date createdDate;
	
	@Transient
	private List<Comment> comments;
	
	@Transient
	private List<QuestionAvailableResponse> availableResponses;

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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
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

	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		if(comments == null) {
			comments = new ArrayList<Comment>();
		}
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the availableResponses
	 */
	public List<QuestionAvailableResponse> getAvailableResponses() {
		return availableResponses;
	}

	/**
	 * @param availableResponses the availableResponses to set
	 */
	public void setAvailableResponses(List<QuestionAvailableResponse> availableResponses) {
		this.availableResponses = availableResponses;
	}
	
}

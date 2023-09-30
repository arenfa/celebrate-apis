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

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name="COMMENT")
public class Comment implements Serializable {

	private static final long serialVersionUID = 4537099352419726735L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="COMMENTID")
	private long commentId;
	
	@Column(name="CTEXT")
	private String value;
	
	@Column(name="CLIKE")
	private long like;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name="CREATED_AT", nullable=false, columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	private Date createdDate;
	
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
	 * @return the commentId
	 */
	public long getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the like
	 */
	public long getLike() {
		return like;
	}

	/**
	 * @param like the like to set
	 */
	public void setLike(long like) {
		this.like = like;
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

	
}

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
@Table(name="USER")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1020230L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="USERID")
	private long userId;
	
	@Column(name="EMAIL")
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_AT")
	private Date updatedDate;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}

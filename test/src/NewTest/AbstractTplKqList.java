package NewTest;

import java.util.Date;

/**
 * AbstractTplKqList entity provides the base persistence definition of the
 * TplKqList entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTplKqList implements java.io.Serializable {

	// Fields

	private Long id;
	private String storeCode;
	private Date createDate;
	private String laneNo;

	// Constructors

	/** default constructor */
	public AbstractTplKqList() {
	}

	/** minimal constructor */
	public AbstractTplKqList(Long id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractTplKqList(Long id, String storeCode, Date createDate,
			String laneNo) {
		this.id = id;
		this.storeCode = storeCode;
		this.createDate = createDate;
		this.laneNo = laneNo;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStoreCode() {
		return this.storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLaneNo() {
		return this.laneNo;
	}

	public void setLaneNo(String laneNo) {
		this.laneNo = laneNo;
	}

}
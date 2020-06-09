package NewTest;

import java.util.Date;

/**
 * AbstractTplDepotException entity provides the base persistence definition of
 * the TplDepotException entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTplDepotException implements java.io.Serializable {

	// Fields

	private Long id;
	private String checkType;
	private String deviceType;
	private String stockCode;
	private String devNo;
	private String devName;
	private Date planTimeBegin;
	private Date planTimeEnd;
	private String remark;
	private Date createDate;

	// Constructors

	/** default constructor */
	public AbstractTplDepotException() {
	}

	/** minimal constructor */
	public AbstractTplDepotException(Long id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractTplDepotException(Long id, String checkType,
			String deviceType, String stockCode, String devNo, String devName,
			Date planTimeBegin, Date planTimeEnd, String remark, Date createDate) {
		this.id = id;
		this.checkType = checkType;
		this.deviceType = deviceType;
		this.stockCode = stockCode;
		this.devNo = devNo;
		this.devName = devName;
		this.planTimeBegin = planTimeBegin;
		this.planTimeEnd = planTimeEnd;
		this.remark = remark;
		this.createDate = createDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCheckType() {
		return this.checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getStockCode() {
		return this.stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getDevNo() {
		return this.devNo;
	}

	public void setDevNo(String devNo) {
		this.devNo = devNo;
	}

	public String getDevName() {
		return this.devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public Date getPlanTimeBegin() {
		return this.planTimeBegin;
	}

	public void setPlanTimeBegin(Date planTimeBegin) {
		this.planTimeBegin = planTimeBegin;
	}

	public Date getPlanTimeEnd() {
		return this.planTimeEnd;
	}

	public void setPlanTimeEnd(Date planTimeEnd) {
		this.planTimeEnd = planTimeEnd;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
package NewTest;

import java.util.Date;

/**
 * AbstractTplZgWarehouse entity provides the base persistence definition of the
 * TplZgWarehouse entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTplZgWarehouse implements java.io.Serializable {

	// Fields

	private Long id;
	private String landingSpotId;
	private String landingSpotName;
	private String productType;
	private String paramOne;
	private String paramTwo;
	private String createId;
	private Date createDate;
	private String modifyId;
	private Date modifyDate;
	private String status;

	// Constructors

	/** default constructor */
	public AbstractTplZgWarehouse() {
	}

	/** minimal constructor */
	public AbstractTplZgWarehouse(Long id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractTplZgWarehouse(Long id, String landingSpotId,
			String landingSpotName, String productType, String paramOne,
			String paramTwo, String createId, Date createDate, String modifyId,
			Date modifyDate, String status) {
		this.id = id;
		this.landingSpotId = landingSpotId;
		this.landingSpotName = landingSpotName;
		this.productType = productType;
		this.paramOne = paramOne;
		this.paramTwo = paramTwo;
		this.createId = createId;
		this.createDate = createDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
		this.status = status;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLandingSpotId() {
		return this.landingSpotId;
	}

	public void setLandingSpotId(String landingSpotId) {
		this.landingSpotId = landingSpotId;
	}

	public String getLandingSpotName() {
		return this.landingSpotName;
	}

	public void setLandingSpotName(String landingSpotName) {
		this.landingSpotName = landingSpotName;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getParamOne() {
		return this.paramOne;
	}

	public void setParamOne(String paramOne) {
		this.paramOne = paramOne;
	}

	public String getParamTwo() {
		return this.paramTwo;
	}

	public void setParamTwo(String paramTwo) {
		this.paramTwo = paramTwo;
	}

	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifyId() {
		return this.modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
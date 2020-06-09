package NewTest;

import java.util.Date;

/**
 * AbstractTplMExtractionTrainItem entity provides the base persistence
 * definition of the TplMExtractionTrainItem entity. @author MyEclipse
 * Persistence Tools
 */

public abstract class AbstractTplMExtractionTrainItem implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String reqPlanNo;
	private String reqPlanId;
	private String packNo;
	private String pickNo;
	private String storeCode;
	private Date createDate;

	// Constructors

	/** default constructor */
	public AbstractTplMExtractionTrainItem() {
	}

	/** minimal constructor */
	public AbstractTplMExtractionTrainItem(Long id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractTplMExtractionTrainItem(Long id, String reqPlanNo,
			String reqPlanId, String packNo, String pickNo, String storeCode,
			Date createDate) {
		this.id = id;
		this.reqPlanNo = reqPlanNo;
		this.reqPlanId = reqPlanId;
		this.packNo = packNo;
		this.pickNo = pickNo;
		this.storeCode = storeCode;
		this.createDate = createDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReqPlanNo() {
		return this.reqPlanNo;
	}

	public void setReqPlanNo(String reqPlanNo) {
		this.reqPlanNo = reqPlanNo;
	}

	public String getReqPlanId() {
		return this.reqPlanId;
	}

	public void setReqPlanId(String reqPlanId) {
		this.reqPlanId = reqPlanId;
	}

	public String getPackNo() {
		return this.packNo;
	}

	public void setPackNo(String packNo) {
		this.packNo = packNo;
	}

	public String getPickNo() {
		return this.pickNo;
	}

	public void setPickNo(String pickNo) {
		this.pickNo = pickNo;
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

}
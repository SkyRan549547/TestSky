package NewTest;

import java.util.Date;

/**
 * AbstractTplMExtractionTrainPlan entity provides the base persistence
 * definition of the TplMExtractionTrainPlan entity. @author MyEclipse
 * Persistence Tools
 */

public abstract class AbstractTplMExtractionTrainPlan implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String procType;
	private String reqPlanNo;
	private String reqLotNo;
	private Integer reqNum;
	private Date startReqTime;
	private Date endReqTime;
	private String preLotNo;
	private Double loadingAbility;
	private Date createDate;

	// Constructors

	/** default constructor */
	public AbstractTplMExtractionTrainPlan() {
	}

	/** minimal constructor */
	public AbstractTplMExtractionTrainPlan(Long id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractTplMExtractionTrainPlan(Long id, String procType,
			String reqPlanNo, String reqLotNo, Integer reqNum,
			Date startReqTime, Date endReqTime, String preLotNo,
			Double loadingAbility, Date createDate) {
		this.id = id;
		this.procType = procType;
		this.reqPlanNo = reqPlanNo;
		this.reqLotNo = reqLotNo;
		this.reqNum = reqNum;
		this.startReqTime = startReqTime;
		this.endReqTime = endReqTime;
		this.preLotNo = preLotNo;
		this.loadingAbility = loadingAbility;
		this.createDate = createDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcType() {
		return this.procType;
	}

	public void setProcType(String procType) {
		this.procType = procType;
	}

	public String getReqPlanNo() {
		return this.reqPlanNo;
	}

	public void setReqPlanNo(String reqPlanNo) {
		this.reqPlanNo = reqPlanNo;
	}

	public String getReqLotNo() {
		return this.reqLotNo;
	}

	public void setReqLotNo(String reqLotNo) {
		this.reqLotNo = reqLotNo;
	}

	public Integer getReqNum() {
		return this.reqNum;
	}

	public void setReqNum(Integer reqNum) {
		this.reqNum = reqNum;
	}

	public Date getStartReqTime() {
		return this.startReqTime;
	}

	public void setStartReqTime(Date startReqTime) {
		this.startReqTime = startReqTime;
	}

	public Date getEndReqTime() {
		return this.endReqTime;
	}

	public void setEndReqTime(Date endReqTime) {
		this.endReqTime = endReqTime;
	}

	public String getPreLotNo() {
		return this.preLotNo;
	}

	public void setPreLotNo(String preLotNo) {
		this.preLotNo = preLotNo;
	}

	public Double getLoadingAbility() {
		return this.loadingAbility;
	}

	public void setLoadingAbility(Double loadingAbility) {
		this.loadingAbility = loadingAbility;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
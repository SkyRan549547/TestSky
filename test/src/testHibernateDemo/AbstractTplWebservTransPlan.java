package testHibernateDemo;

import java.util.Date;

/**
 * AbstractTplWebservTransPlan entity provides the base persistence definition
 * of the TplWebservTransPlan entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTplWebservTransPlan implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String transPlanId;
	private String billId;
	private String pickNum;
	private String operationType;
	private String unitId;
	private String manuId;
	private String providerId;
	private String providerName;
	private Date excuteDate;
	private String transType;
	private String orderUserId;
	private String receiveUnitId;
	private String receiveUnitName;
	private String rainCloth;
	private String ladingSpotId;
	private String ladingSpotName;
	private String ladingSpotAddr;
	private String ladingSpotPhone;
	private String ladingSpotContactor;
	private String destSpotId;
	private String destSpotName;
	private String destSpotAddr;
	private String destSpotPhone;
	private String destSpotContactor;
	private Double totalGrossWeight;
	private Double totalNetWeight;
	private Long totalCount;
	private String electronBill;
	private String consineePhone;
	private String shipId;
	private String shipName;
	private Date createDate;

	// Constructors

	/** default constructor */
	public AbstractTplWebservTransPlan() {
	}

	/** minimal constructor */
	public AbstractTplWebservTransPlan(Long id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractTplWebservTransPlan(Long id, String transPlanId,
			String billId, String pickNum, String operationType, String unitId,
			String manuId, String providerId, String providerName,
			Date excuteDate, String transType, String orderUserId,
			String receiveUnitId, String receiveUnitName, String rainCloth,
			String ladingSpotId, String ladingSpotName, String ladingSpotAddr,
			String ladingSpotPhone, String ladingSpotContactor,
			String destSpotId, String destSpotName, String destSpotAddr,
			String destSpotPhone, String destSpotContactor,
			Double totalGrossWeight, Double totalNetWeight, Long totalCount,
			String electronBill, String consineePhone, String shipId,
			String shipName, Date createDate) {
		this.id = id;
		this.transPlanId = transPlanId;
		this.billId = billId;
		this.pickNum = pickNum;
		this.operationType = operationType;
		this.unitId = unitId;
		this.manuId = manuId;
		this.providerId = providerId;
		this.providerName = providerName;
		this.excuteDate = excuteDate;
		this.transType = transType;
		this.orderUserId = orderUserId;
		this.receiveUnitId = receiveUnitId;
		this.receiveUnitName = receiveUnitName;
		this.rainCloth = rainCloth;
		this.ladingSpotId = ladingSpotId;
		this.ladingSpotName = ladingSpotName;
		this.ladingSpotAddr = ladingSpotAddr;
		this.ladingSpotPhone = ladingSpotPhone;
		this.ladingSpotContactor = ladingSpotContactor;
		this.destSpotId = destSpotId;
		this.destSpotName = destSpotName;
		this.destSpotAddr = destSpotAddr;
		this.destSpotPhone = destSpotPhone;
		this.destSpotContactor = destSpotContactor;
		this.totalGrossWeight = totalGrossWeight;
		this.totalNetWeight = totalNetWeight;
		this.totalCount = totalCount;
		this.electronBill = electronBill;
		this.consineePhone = consineePhone;
		this.shipId = shipId;
		this.shipName = shipName;
		this.createDate = createDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransPlanId() {
		return this.transPlanId;
	}

	public void setTransPlanId(String transPlanId) {
		this.transPlanId = transPlanId;
	}

	public String getBillId() {
		return this.billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public String getPickNum() {
		return this.pickNum;
	}

	public void setPickNum(String pickNum) {
		this.pickNum = pickNum;
	}

	public String getOperationType() {
		return this.operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getUnitId() {
		return this.unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getManuId() {
		return this.manuId;
	}

	public void setManuId(String manuId) {
		this.manuId = manuId;
	}

	public String getProviderId() {
		return this.providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return this.providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Date getExcuteDate() {
		return this.excuteDate;
	}

	public void setExcuteDate(Date excuteDate) {
		this.excuteDate = excuteDate;
	}

	public String getTransType() {
		return this.transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getOrderUserId() {
		return this.orderUserId;
	}

	public void setOrderUserId(String orderUserId) {
		this.orderUserId = orderUserId;
	}

	public String getReceiveUnitId() {
		return this.receiveUnitId;
	}

	public void setReceiveUnitId(String receiveUnitId) {
		this.receiveUnitId = receiveUnitId;
	}

	public String getReceiveUnitName() {
		return this.receiveUnitName;
	}

	public void setReceiveUnitName(String receiveUnitName) {
		this.receiveUnitName = receiveUnitName;
	}

	public String getRainCloth() {
		return this.rainCloth;
	}

	public void setRainCloth(String rainCloth) {
		this.rainCloth = rainCloth;
	}

	public String getLadingSpotId() {
		return this.ladingSpotId;
	}

	public void setLadingSpotId(String ladingSpotId) {
		this.ladingSpotId = ladingSpotId;
	}

	public String getLadingSpotName() {
		return this.ladingSpotName;
	}

	public void setLadingSpotName(String ladingSpotName) {
		this.ladingSpotName = ladingSpotName;
	}

	public String getLadingSpotAddr() {
		return this.ladingSpotAddr;
	}

	public void setLadingSpotAddr(String ladingSpotAddr) {
		this.ladingSpotAddr = ladingSpotAddr;
	}

	public String getLadingSpotPhone() {
		return this.ladingSpotPhone;
	}

	public void setLadingSpotPhone(String ladingSpotPhone) {
		this.ladingSpotPhone = ladingSpotPhone;
	}

	public String getLadingSpotContactor() {
		return this.ladingSpotContactor;
	}

	public void setLadingSpotContactor(String ladingSpotContactor) {
		this.ladingSpotContactor = ladingSpotContactor;
	}

	public String getDestSpotId() {
		return this.destSpotId;
	}

	public void setDestSpotId(String destSpotId) {
		this.destSpotId = destSpotId;
	}

	public String getDestSpotName() {
		return this.destSpotName;
	}

	public void setDestSpotName(String destSpotName) {
		this.destSpotName = destSpotName;
	}

	public String getDestSpotAddr() {
		return this.destSpotAddr;
	}

	public void setDestSpotAddr(String destSpotAddr) {
		this.destSpotAddr = destSpotAddr;
	}

	public String getDestSpotPhone() {
		return this.destSpotPhone;
	}

	public void setDestSpotPhone(String destSpotPhone) {
		this.destSpotPhone = destSpotPhone;
	}

	public String getDestSpotContactor() {
		return this.destSpotContactor;
	}

	public void setDestSpotContactor(String destSpotContactor) {
		this.destSpotContactor = destSpotContactor;
	}

	public Double getTotalGrossWeight() {
		return this.totalGrossWeight;
	}

	public void setTotalGrossWeight(Double totalGrossWeight) {
		this.totalGrossWeight = totalGrossWeight;
	}

	public Double getTotalNetWeight() {
		return this.totalNetWeight;
	}

	public void setTotalNetWeight(Double totalNetWeight) {
		this.totalNetWeight = totalNetWeight;
	}

	public Long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public String getElectronBill() {
		return this.electronBill;
	}

	public void setElectronBill(String electronBill) {
		this.electronBill = electronBill;
	}

	public String getConsineePhone() {
		return this.consineePhone;
	}

	public void setConsineePhone(String consineePhone) {
		this.consineePhone = consineePhone;
	}

	public String getShipId() {
		return this.shipId;
	}

	public void setShipId(String shipId) {
		this.shipId = shipId;
	}

	public String getShipName() {
		return this.shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
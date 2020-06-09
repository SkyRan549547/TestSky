package testHibernateDemo;

import java.util.Date;

/**
 * AbstractTplWebservTransSeq entity provides the base persistence definition of
 * the TplWebservTransSeq entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTplWebservTransSeq implements
		java.io.Serializable {

	// Fields

	private Long id;
	private Long seqId;
	private String transType;
	private String busNumber;
	private String trailerNum;
	private String driCode;
	private String shipId;
	private String shipName;
	private String shipIdM;
	private String trainId;
	private Double grossWeight;
	private Double netWeight;
	private Integer count;
	private Date createDate;

	// Constructors

	/** default constructor */
	public AbstractTplWebservTransSeq() {
	}

	/** minimal constructor */
	public AbstractTplWebservTransSeq(Long id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractTplWebservTransSeq(Long id, Long seqId, String transType,
			String busNumber, String trailerNum, String driCode, String shipId,
			String shipName, String shipIdM, String trainId,
			Double grossWeight, Double netWeight, Integer count, Date createDate) {
		this.id = id;
		this.seqId = seqId;
		this.transType = transType;
		this.busNumber = busNumber;
		this.trailerNum = trailerNum;
		this.driCode = driCode;
		this.shipId = shipId;
		this.shipName = shipName;
		this.shipIdM = shipIdM;
		this.trainId = trainId;
		this.grossWeight = grossWeight;
		this.netWeight = netWeight;
		this.count = count;
		this.createDate = createDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSeqId() {
		return this.seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}

	public String getTransType() {
		return this.transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getBusNumber() {
		return this.busNumber;
	}

	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}

	public String getTrailerNum() {
		return this.trailerNum;
	}

	public void setTrailerNum(String trailerNum) {
		this.trailerNum = trailerNum;
	}

	public String getDriCode() {
		return this.driCode;
	}

	public void setDriCode(String driCode) {
		this.driCode = driCode;
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

	public String getShipIdM() {
		return this.shipIdM;
	}

	public void setShipIdM(String shipIdM) {
		this.shipIdM = shipIdM;
	}

	public String getTrainId() {
		return this.trainId;
	}

	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}

	public Double getGrossWeight() {
		return this.grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Double getNetWeight() {
		return this.netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
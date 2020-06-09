package HibernateDemo;

/**
 * AbstractTplMessageTransStation entity provides the base persistence
 * definition of the TplMessageTransStation entity. @author MyEclipse
 * Persistence Tools
 */

public abstract class AbstractTplMessageTransStation implements
		java.io.Serializable {

	// Fields

	private String stationSeqId;
	private Long id;
	private String operationType;
	private String stationId;
	private String stationName;
	private String stationStatus;
	private String transType;
	private String privateLineId;
	private String privateLineName;
	private String vehicleStationName;
	private String shipPortName;
	private String stationAddr;
	private String area;
	private String province;
	private String city;
	private String modifyUserId;
	private String modifyDate;
	private String remark;
	private String privateLineFullname;

	// Constructors

	/** default constructor */
	public AbstractTplMessageTransStation() {
	}

	/** minimal constructor */
	public AbstractTplMessageTransStation(String stationSeqId, Long id) {
		this.stationSeqId = stationSeqId;
		this.id = id;
	}

	/** full constructor */
	public AbstractTplMessageTransStation(String stationSeqId, Long id,
			String operationType, String stationId, String stationName,
			String stationStatus, String transType, String privateLineId,
			String privateLineName, String vehicleStationName,
			String shipPortName, String stationAddr, String area,
			String province, String city, String modifyUserId,
			String modifyDate, String remark, String privateLineFullname) {
		this.stationSeqId = stationSeqId;
		this.id = id;
		this.operationType = operationType;
		this.stationId = stationId;
		this.stationName = stationName;
		this.stationStatus = stationStatus;
		this.transType = transType;
		this.privateLineId = privateLineId;
		this.privateLineName = privateLineName;
		this.vehicleStationName = vehicleStationName;
		this.shipPortName = shipPortName;
		this.stationAddr = stationAddr;
		this.area = area;
		this.province = province;
		this.city = city;
		this.modifyUserId = modifyUserId;
		this.modifyDate = modifyDate;
		this.remark = remark;
		this.privateLineFullname = privateLineFullname;
	}

	// Property accessors

	public String getStationSeqId() {
		return this.stationSeqId;
	}

	public void setStationSeqId(String stationSeqId) {
		this.stationSeqId = stationSeqId;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperationType() {
		return this.operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationStatus() {
		return this.stationStatus;
	}

	public void setStationStatus(String stationStatus) {
		this.stationStatus = stationStatus;
	}

	public String getTransType() {
		return this.transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getPrivateLineId() {
		return this.privateLineId;
	}

	public void setPrivateLineId(String privateLineId) {
		this.privateLineId = privateLineId;
	}

	public String getPrivateLineName() {
		return this.privateLineName;
	}

	public void setPrivateLineName(String privateLineName) {
		this.privateLineName = privateLineName;
	}

	public String getVehicleStationName() {
		return this.vehicleStationName;
	}

	public void setVehicleStationName(String vehicleStationName) {
		this.vehicleStationName = vehicleStationName;
	}

	public String getShipPortName() {
		return this.shipPortName;
	}

	public void setShipPortName(String shipPortName) {
		this.shipPortName = shipPortName;
	}

	public String getStationAddr() {
		return this.stationAddr;
	}

	public void setStationAddr(String stationAddr) {
		this.stationAddr = stationAddr;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public String getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPrivateLineFullname() {
		return this.privateLineFullname;
	}

	public void setPrivateLineFullname(String privateLineFullname) {
		this.privateLineFullname = privateLineFullname;
	}

}
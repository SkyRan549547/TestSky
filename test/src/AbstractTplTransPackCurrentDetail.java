// default package

/**
 * AbstractTplTransPackCurrentDetail entity provides the base persistence
 * definition of the TplTransPackCurrentDetail entity. @author MyEclipse
 * Persistence Tools
 */

public abstract class AbstractTplTransPackCurrentDetail implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String providerId;
	private String orderNum;
	private String packId;
	private Double netWeight;
	private Double grossWeight;
	private Long unitCount;
	private String shipName;

	// Constructors

	/** default constructor */
	public AbstractTplTransPackCurrentDetail() {
	}

	/** minimal constructor */
	public AbstractTplTransPackCurrentDetail(Long id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractTplTransPackCurrentDetail(Long id, String providerId,
			String orderNum, String packId, Double netWeight,
			Double grossWeight, Long unitCount, String shipName) {
		this.id = id;
		this.providerId = providerId;
		this.orderNum = orderNum;
		this.packId = packId;
		this.netWeight = netWeight;
		this.grossWeight = grossWeight;
		this.unitCount = unitCount;
		this.shipName = shipName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProviderId() {
		return this.providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getPackId() {
		return this.packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public Double getNetWeight() {
		return this.netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public Double getGrossWeight() {
		return this.grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Long getUnitCount() {
		return this.unitCount;
	}

	public void setUnitCount(Long unitCount) {
		this.unitCount = unitCount;
	}

	public String getShipName() {
		return this.shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

}
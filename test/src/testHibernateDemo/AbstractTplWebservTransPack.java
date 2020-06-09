package testHibernateDemo;

import java.util.Date;

/**
 * AbstractTplWebservTransPack entity provides the base persistence definition
 * of the TplWebservTransPack entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTplWebservTransPack implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String transPlanId;
	private String productName;
	private String orderNum;
	private String packId;
	private Double netNeight;
	private Double grossWeight;
	private Long unitCount;
	private Double unitWeight;
	private String specDesc;
	private String shopSign;
	private String wrapMode;
	private Date createDate;

	// Constructors

	/** default constructor */
	public AbstractTplWebservTransPack() {
	}

	/** minimal constructor */
	public AbstractTplWebservTransPack(Long id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractTplWebservTransPack(Long id, String transPlanId,
			String productName, String orderNum, String packId,
			Double netNeight, Double grossWeight, Long unitCount,
			Double unitWeight, String specDesc, String shopSign,
			String wrapMode, Date createDate) {
		this.id = id;
		this.transPlanId = transPlanId;
		this.productName = productName;
		this.orderNum = orderNum;
		this.packId = packId;
		this.netNeight = netNeight;
		this.grossWeight = grossWeight;
		this.unitCount = unitCount;
		this.unitWeight = unitWeight;
		this.specDesc = specDesc;
		this.shopSign = shopSign;
		this.wrapMode = wrapMode;
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

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public Double getNetNeight() {
		return this.netNeight;
	}

	public void setNetNeight(Double netNeight) {
		this.netNeight = netNeight;
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

	public Double getUnitWeight() {
		return this.unitWeight;
	}

	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	public String getSpecDesc() {
		return this.specDesc;
	}

	public void setSpecDesc(String specDesc) {
		this.specDesc = specDesc;
	}

	public String getShopSign() {
		return this.shopSign;
	}

	public void setShopSign(String shopSign) {
		this.shopSign = shopSign;
	}

	public String getWrapMode() {
		return this.wrapMode;
	}

	public void setWrapMode(String wrapMode) {
		this.wrapMode = wrapMode;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
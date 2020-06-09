package src.spring.java.com;

import java.math.BigDecimal;

/**
 * AbstractLgsReadyData entity provides the base persistence definition of the
 * LgsReadyData entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractLgsReadyData implements java.io.Serializable {

	// Fields

	private BigDecimal id;
	private String manuId;
	private String readyNum;
	private String companyCode;
	private String orderNum;
	private String readyDateChr;
	private String readyT;
	private String qltDecideCode;
	private String productCodeFb1;
	private String productCodeFb2;
	private String productCodeFb3;
	private String productName;
	private String storehouseCode;
	private String readyStatus;
	private Double weightReadyTot;
	private String NReadyTot;
	private String trnpModeCode;
	private String dlvSpotCode;
	private String privateRouteCode;
	private String inDate;

	// Constructors

	/** default constructor */
	public AbstractLgsReadyData() {
	}

	/** minimal constructor */
	public AbstractLgsReadyData(BigDecimal id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractLgsReadyData(BigDecimal id, String manuId, String readyNum,
			String companyCode, String orderNum, String readyDateChr,
			String readyT, String qltDecideCode, String productCodeFb1,
			String productCodeFb2, String productCodeFb3, String productName,
			String storehouseCode, String readyStatus, Double weightReadyTot,
			String NReadyTot, String trnpModeCode, String dlvSpotCode,
			String privateRouteCode, String inDate) {
		this.id = id;
		this.manuId = manuId;
		this.readyNum = readyNum;
		this.companyCode = companyCode;
		this.orderNum = orderNum;
		this.readyDateChr = readyDateChr;
		this.readyT = readyT;
		this.qltDecideCode = qltDecideCode;
		this.productCodeFb1 = productCodeFb1;
		this.productCodeFb2 = productCodeFb2;
		this.productCodeFb3 = productCodeFb3;
		this.productName = productName;
		this.storehouseCode = storehouseCode;
		this.readyStatus = readyStatus;
		this.weightReadyTot = weightReadyTot;
		this.NReadyTot = NReadyTot;
		this.trnpModeCode = trnpModeCode;
		this.dlvSpotCode = dlvSpotCode;
		this.privateRouteCode = privateRouteCode;
		this.inDate = inDate;
	}

	// Property accessors

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getManuId() {
		return this.manuId;
	}

	public void setManuId(String manuId) {
		this.manuId = manuId;
	}

	public String getReadyNum() {
		return this.readyNum;
	}

	public void setReadyNum(String readyNum) {
		this.readyNum = readyNum;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getReadyDateChr() {
		return this.readyDateChr;
	}

	public void setReadyDateChr(String readyDateChr) {
		this.readyDateChr = readyDateChr;
	}

	public String getReadyT() {
		return this.readyT;
	}

	public void setReadyT(String readyT) {
		this.readyT = readyT;
	}

	public String getQltDecideCode() {
		return this.qltDecideCode;
	}

	public void setQltDecideCode(String qltDecideCode) {
		this.qltDecideCode = qltDecideCode;
	}

	public String getProductCodeFb1() {
		return this.productCodeFb1;
	}

	public void setProductCodeFb1(String productCodeFb1) {
		this.productCodeFb1 = productCodeFb1;
	}

	public String getProductCodeFb2() {
		return this.productCodeFb2;
	}

	public void setProductCodeFb2(String productCodeFb2) {
		this.productCodeFb2 = productCodeFb2;
	}

	public String getProductCodeFb3() {
		return this.productCodeFb3;
	}

	public void setProductCodeFb3(String productCodeFb3) {
		this.productCodeFb3 = productCodeFb3;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStorehouseCode() {
		return this.storehouseCode;
	}

	public void setStorehouseCode(String storehouseCode) {
		this.storehouseCode = storehouseCode;
	}

	public String getReadyStatus() {
		return this.readyStatus;
	}

	public void setReadyStatus(String readyStatus) {
		this.readyStatus = readyStatus;
	}

	public Double getWeightReadyTot() {
		return this.weightReadyTot;
	}

	public void setWeightReadyTot(Double weightReadyTot) {
		this.weightReadyTot = weightReadyTot;
	}

	public String getNReadyTot() {
		return this.NReadyTot;
	}

	public void setNReadyTot(String NReadyTot) {
		this.NReadyTot = NReadyTot;
	}

	public String getTrnpModeCode() {
		return this.trnpModeCode;
	}

	public void setTrnpModeCode(String trnpModeCode) {
		this.trnpModeCode = trnpModeCode;
	}

	public String getDlvSpotCode() {
		return this.dlvSpotCode;
	}

	public void setDlvSpotCode(String dlvSpotCode) {
		this.dlvSpotCode = dlvSpotCode;
	}

	public String getPrivateRouteCode() {
		return this.privateRouteCode;
	}

	public void setPrivateRouteCode(String privateRouteCode) {
		this.privateRouteCode = privateRouteCode;
	}

	public String getInDate() {
		return this.inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

}
package src.spring.java.com;

import java.math.BigDecimal;

/**
 * LgsReadyData entity. @author MyEclipse Persistence Tools
 */
public class LgsReadyData extends AbstractLgsReadyData implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public LgsReadyData() {
	}

	/** minimal constructor */
	public LgsReadyData(BigDecimal id) {
		super(id);
	}

	/** full constructor */
	public LgsReadyData(BigDecimal id, String manuId, String readyNum,
			String companyCode, String orderNum, String readyDateChr,
			String readyT, String qltDecideCode, String productCodeFb1,
			String productCodeFb2, String productCodeFb3, String productName,
			String storehouseCode, String readyStatus, Double weightReadyTot,
			String NReadyTot, String trnpModeCode, String dlvSpotCode,
			String privateRouteCode, String inDate) {
		super(id, manuId, readyNum, companyCode, orderNum, readyDateChr,
				readyT, qltDecideCode, productCodeFb1, productCodeFb2,
				productCodeFb3, productName, storehouseCode, readyStatus,
				weightReadyTot, NReadyTot, trnpModeCode, dlvSpotCode,
				privateRouteCode, inDate);
	}

}

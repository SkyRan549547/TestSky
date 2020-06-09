package testHibernateDemo;

import java.util.Date;

/**
 * TplWebservTransPlan entity. @author MyEclipse Persistence Tools
 */
public class TplWebservTransPlan extends AbstractTplWebservTransPlan implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplWebservTransPlan() {
	}

	/** minimal constructor */
	public TplWebservTransPlan(Long id) {
		super(id);
	}

	/** full constructor */
	public TplWebservTransPlan(Long id, String transPlanId, String billId,
			String pickNum, String operationType, String unitId, String manuId,
			String providerId, String providerName, Date excuteDate,
			String transType, String orderUserId, String receiveUnitId,
			String receiveUnitName, String rainCloth, String ladingSpotId,
			String ladingSpotName, String ladingSpotAddr,
			String ladingSpotPhone, String ladingSpotContactor,
			String destSpotId, String destSpotName, String destSpotAddr,
			String destSpotPhone, String destSpotContactor,
			Double totalGrossWeight, Double totalNetWeight, Long totalCount,
			String electronBill, String consineePhone, String shipId,
			String shipName, Date createDate) {
		super(id, transPlanId, billId, pickNum, operationType, unitId, manuId,
				providerId, providerName, excuteDate, transType, orderUserId,
				receiveUnitId, receiveUnitName, rainCloth, ladingSpotId,
				ladingSpotName, ladingSpotAddr, ladingSpotPhone,
				ladingSpotContactor, destSpotId, destSpotName, destSpotAddr,
				destSpotPhone, destSpotContactor, totalGrossWeight,
				totalNetWeight, totalCount, electronBill, consineePhone,
				shipId, shipName, createDate);
	}

}

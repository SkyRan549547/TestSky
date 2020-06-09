package testHibernateDemo;

import java.util.Date;

/**
 * TplWebservTransPack entity. @author MyEclipse Persistence Tools
 */
public class TplWebservTransPack extends AbstractTplWebservTransPack implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplWebservTransPack() {
	}

	/** minimal constructor */
	public TplWebservTransPack(Long id) {
		super(id);
	}

	/** full constructor */
	public TplWebservTransPack(Long id, String transPlanId, String productName,
			String orderNum, String packId, Double netNeight,
			Double grossWeight, Long unitCount, Double unitWeight,
			String specDesc, String shopSign, String wrapMode, Date createDate) {
		super(id, transPlanId, productName, orderNum, packId, netNeight,
				grossWeight, unitCount, unitWeight, specDesc, shopSign,
				wrapMode, createDate);
	}

}

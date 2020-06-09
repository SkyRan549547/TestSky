package testHibernateDemo;

import java.util.Date;

/**
 * TplWebservTransSeq entity. @author MyEclipse Persistence Tools
 */
public class TplWebservTransSeq extends AbstractTplWebservTransSeq implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplWebservTransSeq() {
	}

	/** minimal constructor */
	public TplWebservTransSeq(Long id) {
		super(id);
	}

	/** full constructor */
	public TplWebservTransSeq(Long id, Long seqId, String transType,
			String busNumber, String trailerNum, String driCode, String shipId,
			String shipName, String shipIdM, String trainId,
			Double grossWeight, Double netWeight, Integer count, Date createDate) {
		super(id, seqId, transType, busNumber, trailerNum, driCode, shipId,
				shipName, shipIdM, trainId, grossWeight, netWeight, count,
				createDate);
	}

}

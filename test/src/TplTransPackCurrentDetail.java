// default package

/**
 * TplTransPackCurrentDetail entity. @author MyEclipse Persistence Tools
 */
public class TplTransPackCurrentDetail extends
		AbstractTplTransPackCurrentDetail implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplTransPackCurrentDetail() {
	}

	/** minimal constructor */
	public TplTransPackCurrentDetail(Long id) {
		super(id);
	}

	/** full constructor */
	public TplTransPackCurrentDetail(Long id, String providerId,
			String orderNum, String packId, Double netWeight,
			Double grossWeight, Long unitCount, String shipName) {
		super(id, providerId, orderNum, packId, netWeight, grossWeight,
				unitCount, shipName);
	}

}

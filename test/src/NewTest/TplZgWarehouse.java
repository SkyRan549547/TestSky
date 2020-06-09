package NewTest;

import java.util.Date;

/**
 * TplZgWarehouse entity. @author MyEclipse Persistence Tools
 */
public class TplZgWarehouse extends AbstractTplZgWarehouse implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplZgWarehouse() {
	}

	/** minimal constructor */
	public TplZgWarehouse(Long id) {
		super(id);
	}

	/** full constructor */
	public TplZgWarehouse(Long id, String landingSpotId,
			String landingSpotName, String productType, String paramOne,
			String paramTwo, String createId, Date createDate, String modifyId,
			Date modifyDate, String status) {
		super(id, landingSpotId, landingSpotName, productType, paramOne,
				paramTwo, createId, createDate, modifyId, modifyDate, status);
	}

}

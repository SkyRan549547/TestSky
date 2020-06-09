package NewTest;

import java.util.Date;

/**
 * TplSelfmentionIrontransItem entity. @author MyEclipse Persistence Tools
 */
public class TplSelfmentionIrontransItem extends
		AbstractTplSelfmentionIrontransItem implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplSelfmentionIrontransItem() {
	}

	/** minimal constructor */
	public TplSelfmentionIrontransItem(Long id) {
		super(id);
	}

	/** full constructor */
	public TplSelfmentionIrontransItem(Long id, String reqPlanNo,
			String reqPlanId, String packNo, String pickNo, String storeCode,
			Date createDate) {
		super(id, reqPlanNo, reqPlanId, packNo, pickNo, storeCode, createDate);
	}

}

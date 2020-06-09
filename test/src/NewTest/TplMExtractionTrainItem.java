package NewTest;

import java.util.Date;

/**
 * TplMExtractionTrainItem entity. @author MyEclipse Persistence Tools
 */
public class TplMExtractionTrainItem extends AbstractTplMExtractionTrainItem
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplMExtractionTrainItem() {
	}

	/** minimal constructor */
	public TplMExtractionTrainItem(Long id) {
		super(id);
	}

	/** full constructor */
	public TplMExtractionTrainItem(Long id, String reqPlanNo, String reqPlanId,
			String packNo, String pickNo, String storeCode, Date createDate) {
		super(id, reqPlanNo, reqPlanId, packNo, pickNo, storeCode, createDate);
	}

}

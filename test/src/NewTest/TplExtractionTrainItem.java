package NewTest;

import java.util.Date;

/**
 * TplExtractionTrainItem entity. @author MyEclipse Persistence Tools
 */
public class TplExtractionTrainItem extends AbstractTplExtractionTrainItem
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplExtractionTrainItem() {
	}

	/** minimal constructor */
	public TplExtractionTrainItem(Long id) {
		super(id);
	}

	/** full constructor */
	public TplExtractionTrainItem(Long id, String reqPlanNo, String reqPlanId,
			String packNo, String pickNo, String storeCode, Date createDate) {
		super(id, reqPlanNo, reqPlanId, packNo, pickNo, storeCode, createDate);
	}

}

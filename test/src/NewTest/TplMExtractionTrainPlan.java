package NewTest;

import java.util.Date;

/**
 * TplMExtractionTrainPlan entity. @author MyEclipse Persistence Tools
 */
public class TplMExtractionTrainPlan extends AbstractTplMExtractionTrainPlan
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplMExtractionTrainPlan() {
	}

	/** minimal constructor */
	public TplMExtractionTrainPlan(Long id) {
		super(id);
	}

	/** full constructor */
	public TplMExtractionTrainPlan(Long id, String procType, String reqPlanNo,
			String reqLotNo, Integer reqNum, Date startReqTime,
			Date endReqTime, String preLotNo, Double loadingAbility,
			Date createDate) {
		super(id, procType, reqPlanNo, reqLotNo, reqNum, startReqTime,
				endReqTime, preLotNo, loadingAbility, createDate);
	}

}

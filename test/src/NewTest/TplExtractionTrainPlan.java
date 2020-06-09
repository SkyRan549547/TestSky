package NewTest;

import java.util.Date;

/**
 * TplExtractionTrainPlan entity. @author MyEclipse Persistence Tools
 */
public class TplExtractionTrainPlan extends AbstractTplExtractionTrainPlan
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplExtractionTrainPlan() {
	}

	/** minimal constructor */
	public TplExtractionTrainPlan(Long id) {
		super(id);
	}

	public void sort(InsuranceBillService insurService) throws Exception {
		String[] ids = new String[10];
		insurService.deleteTplInsuranceBillTrain(ids);
	}

	/** full constructor */
	public TplExtractionTrainPlan(Long id, String reqPlanNo, String reqLotNo,
			Integer reqNum, Date startReqTime, Date endReqTime,
			String preLotNo, Double loadingAbility, Date createDate) {
		super(id, reqPlanNo, reqLotNo, reqNum, startReqTime, endReqTime,
				preLotNo, loadingAbility, createDate);
	}

}

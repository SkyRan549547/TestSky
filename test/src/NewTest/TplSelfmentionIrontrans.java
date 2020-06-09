package NewTest;

import java.util.Date;

/**
 * TplSelfmentionIrontrans entity. @author MyEclipse Persistence Tools
 */
public class TplSelfmentionIrontrans extends AbstractTplSelfmentionIrontrans
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplSelfmentionIrontrans() {
	}

	/** minimal constructor */
	public TplSelfmentionIrontrans(Long id) {
		super(id);
	}

	/** full constructor */
	public TplSelfmentionIrontrans(Long id, String reqPlanNo, String reqLotNo,
			Integer reqNum, Date startReqTime, Date endReqTime,
			String preLotNo, String loadingAbility, Date createDate) {
		super(id, reqPlanNo, reqLotNo, reqNum, startReqTime, endReqTime,
				preLotNo, loadingAbility, createDate);
	}

}

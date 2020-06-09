package NewTest;

import java.util.Date;

/**
 * TplDepotException entity. @author MyEclipse Persistence Tools
 */
public class TplDepotException extends AbstractTplDepotException implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplDepotException() {
	}

	/** minimal constructor */
	public TplDepotException(Long id) {
		super(id);
	}

	/** full constructor */
	public TplDepotException(Long id, String checkType, String deviceType,
			String stockCode, String devNo, String devName, Date planTimeBegin,
			Date planTimeEnd, String remark, Date createDate) {
		super(id, checkType, deviceType, stockCode, devNo, devName,
				planTimeBegin, planTimeEnd, remark, createDate);
	}

}

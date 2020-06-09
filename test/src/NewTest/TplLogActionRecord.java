package NewTest;

import java.util.Date;

/**
 * TplLogActionRecord entity. @author MyEclipse Persistence Tools
 */
public class TplLogActionRecord extends AbstractTplLogActionRecord implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplLogActionRecord() {
	}

	/** minimal constructor */
	public TplLogActionRecord(Long id) {
		super(id);
	}

	/** full constructor */
	public TplLogActionRecord(Long id, String functionName,
			String operationPerson, Date intoDate, Date excuteDate,
			String paramOne, String paramTwo, String paramThree) {
		super(id, functionName, operationPerson, intoDate, excuteDate,
				paramOne, paramTwo, paramThree);
	}

}

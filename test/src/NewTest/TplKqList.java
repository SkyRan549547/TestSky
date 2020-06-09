package NewTest;

import java.util.Date;

/**
 * TplKqList entity. @author MyEclipse Persistence Tools
 */
public class TplKqList extends AbstractTplKqList implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplKqList() {
	}

	/** minimal constructor */
	public TplKqList(Long id) {
		super(id);
	}

	/** full constructor */
	public TplKqList(Long id, String storeCode, Date createDate, String laneNo) {
		super(id, storeCode, createDate, laneNo);
	}

}

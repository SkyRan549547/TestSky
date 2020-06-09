package NewTest;

import java.util.Date;

/**
 * AbstractTplLogActionRecord entity provides the base persistence definition of
 * the TplLogActionRecord entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTplLogActionRecord implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String functionName;
	private String operationPerson;
	private Date intoDate;
	private Date excuteDate;
	private String paramOne;
	private String paramTwo;
	private String paramThree;

	// Constructors

	/** default constructor */
	public AbstractTplLogActionRecord() {
	}

	/** minimal constructor */
	public AbstractTplLogActionRecord(Long id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractTplLogActionRecord(Long id, String functionName,
			String operationPerson, Date intoDate, Date excuteDate,
			String paramOne, String paramTwo, String paramThree) {
		this.id = id;
		this.functionName = functionName;
		this.operationPerson = operationPerson;
		this.intoDate = intoDate;
		this.excuteDate = excuteDate;
		this.paramOne = paramOne;
		this.paramTwo = paramTwo;
		this.paramThree = paramThree;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getOperationPerson() {
		return this.operationPerson;
	}

	public void setOperationPerson(String operationPerson) {
		this.operationPerson = operationPerson;
	}

	public Date getIntoDate() {
		return this.intoDate;
	}

	public void setIntoDate(Date intoDate) {
		this.intoDate = intoDate;
	}

	public Date getExcuteDate() {
		return this.excuteDate;
	}

	public void setExcuteDate(Date excuteDate) {
		this.excuteDate = excuteDate;
	}

	public String getParamOne() {
		return this.paramOne;
	}

	public void setParamOne(String paramOne) {
		this.paramOne = paramOne;
	}

	public String getParamTwo() {
		return this.paramTwo;
	}

	public void setParamTwo(String paramTwo) {
		this.paramTwo = paramTwo;
	}

	public String getParamThree() {
		return this.paramThree;
	}

	public void setParamThree(String paramThree) {
		this.paramThree = paramThree;
	}

}
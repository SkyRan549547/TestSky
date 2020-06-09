package src.spring.java.com;

/**
 * AbstractTplCarrierWharf entity provides the base persistence definition of
 * the TplCarrierWharf entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTplCarrierWharf implements java.io.Serializable {

	// Fields

	private Long id;
	private String carrierCode;
	private String wharfCode;
	private String status;
	private String modifyDate;

	// Constructors

	/** default constructor */
	public AbstractTplCarrierWharf() {
	}

	/** minimal constructor */
	public AbstractTplCarrierWharf(Long id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractTplCarrierWharf(Long id, String carrierCode,
			String wharfCode, String status, String modifyDate) {
		this.id = id;
		this.carrierCode = carrierCode;
		this.wharfCode = wharfCode;
		this.status = status;
		this.modifyDate = modifyDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCarrierCode() {
		return this.carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getWharfCode() {
		return this.wharfCode;
	}

	public void setWharfCode(String wharfCode) {
		this.wharfCode = wharfCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

}
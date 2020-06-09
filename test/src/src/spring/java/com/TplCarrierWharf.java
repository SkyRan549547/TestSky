package src.spring.java.com;

/**
 * TplCarrierWharf entity. @author MyEclipse Persistence Tools
 */
public class TplCarrierWharf extends AbstractTplCarrierWharf implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplCarrierWharf() {
	}

	/** minimal constructor */
	public TplCarrierWharf(Long id) {
		super(id);
	}

	/** full constructor */
	public TplCarrierWharf(Long id, String carrierCode, String wharfCode,
			String status, String modifyDate) {
		super(id, carrierCode, wharfCode, status, modifyDate);
	}

}

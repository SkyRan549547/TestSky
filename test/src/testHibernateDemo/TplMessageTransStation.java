package testHibernateDemo;

/**
 * TplMessageTransStation entity. @author MyEclipse Persistence Tools
 */
public class TplMessageTransStation extends AbstractTplMessageTransStation
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplMessageTransStation() {
	}

	/** minimal constructor */
	public TplMessageTransStation(Long id) {
		super(id);
	}

	/** full constructor */
	public TplMessageTransStation(Long id, String operationType,
			String stationSeqId, String stationId, String stationName,
			String stationStatus, String transType, String privateLineId,
			String privateLineName, String vehicleStationName,
			String shipPortName, String stationAddr, String area,
			String province, String city, String modifyUserId,
			String modifyDate, String remark, String privateLineFullname) {
		super(id, operationType, stationSeqId, stationId, stationName,
				stationStatus, transType, privateLineId, privateLineName,
				vehicleStationName, shipPortName, stationAddr, area, province,
				city, modifyUserId, modifyDate, remark, privateLineFullname);
	}

}

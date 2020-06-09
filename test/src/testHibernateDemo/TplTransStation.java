package testHibernateDemo;

import java.util.Date;

/**
 * TplTransStation entity. @author MyEclipse Persistence Tools
 */
public class TplTransStation extends AbstractTplTransStation implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TplTransStation() {
	}

	/** minimal constructor */
	public TplTransStation(Long id) {
		super(id);
	}

	/** full constructor */
	public TplTransStation(Long id, String stationSeqId, String stationId,
			String stationName, String stationStatus, String transType,
			String privateLineId, String privateLineName,
			String vehicleStationName, String shipPortName, String stationAddr,
			String area, String province, String city, String modifyUserId,
			Date modifyDate, String remark, String privateLineFullname) {
		super(id, stationSeqId, stationId, stationName, stationStatus,
				transType, privateLineId, privateLineName, vehicleStationName,
				shipPortName, stationAddr, area, province, city, modifyUserId,
				modifyDate, remark, privateLineFullname);
	}

}

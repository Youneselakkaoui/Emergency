package com.emergency.dto;

import java.util.List;


import com.emergency.entity.AlerteRecue;

public class SyncAlertesOut {
	private List<AlerteRecue> alerte;
	private EmergencyAnomalieDTO anomalie;

	public List<AlerteRecue> getAlerte() {
		return alerte;
	}

	public EmergencyAnomalieDTO getAnomalie() {
		return anomalie;
	}

	public void setAlerte(final List<AlerteRecue> alerte) {
		this.alerte = alerte;
	}

	public void setAnomalie(final EmergencyAnomalieDTO anomalie) {
		this.anomalie = anomalie;
	}
}

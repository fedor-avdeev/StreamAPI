package org.fedoravdeev.task05.streamapi;

import java.time.Duration;

public class Pilot {
	final private String idPilot;
	final private Duration bestTime;
	final private String namePilot;
	final private String team;

	public Pilot(String idPilot, Duration duration, String namePilot, String team) {
		this.idPilot = idPilot;
		this.bestTime = duration;
		this.namePilot = namePilot;
		this.team = team;
	}

	public String getIdPilot() {
		return idPilot;
	}

	public String getNamePilot() {
		return namePilot;
	}

	public String getTeam() {
		return team;
	}

	public Duration getBestTime() {
		return bestTime;
	}

	public String getStringBestTime() {
		return String.format("%02d:%02d.%03d", bestTime.toMinutes(), bestTime.getSeconds() % 60, bestTime.toMillis() % 1000);
	}
};

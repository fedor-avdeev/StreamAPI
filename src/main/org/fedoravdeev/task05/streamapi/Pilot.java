package org.fedoravdeev.task05.streamapi;

import java.time.Duration;

public class Pilot {
	final private String pilotCode;
	final private Duration bestTime;
	final private String pilotName;
	final private String teamName;

	public Pilot(String pilotCode, Duration bestTime, String pilotName, String teamName) {
		this.pilotCode = pilotCode;
		this.bestTime = bestTime;
		this.pilotName = pilotName;
		this.teamName = teamName;
	}

	public String getPilotCode() {
		return pilotCode;
	}

	public String getPilotName() {
		return pilotName;
	}

	public String getTeamName() {
		return teamName;
	}

	public Duration getBestTime() {
		return bestTime;
	}

	public String getStringBestTime() {
		return String.format("%02d:%02d.%03d", bestTime.toMinutes(), bestTime.getSeconds() % 60, bestTime.toMillis() % 1000);
	}
};

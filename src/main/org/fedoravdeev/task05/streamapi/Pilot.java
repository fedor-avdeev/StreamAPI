
import java.time.Duration;

public class Pilot {
	private static final String OUTPUT_FORMAT = "%-17s | %-25s | %-11s\n";
	final private Duration bestTime;
	final private String pilotName;
	final private String teamName;

	public Pilot(String pilotCode, Duration bestTime, String pilotName, String teamName) {
		this.bestTime = bestTime;
		this.pilotName = pilotName;
		this.teamName = teamName;
	}

	@Override
	public String toString() {
		return String.format(OUTPUT_FORMAT, getPilotName(), getTeamName(), getStringBestTime());
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
		return String.format("%02d:%02d.%03d", bestTime.toMinutes(), bestTime.getSeconds() % 60,
				bestTime.toMillis() % 1000);
	}
};

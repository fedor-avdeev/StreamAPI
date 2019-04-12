package org.fedoravdeev.task05.streamapi;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.parse;
import static java.util.Comparator.comparing;
import static java.util.stream.IntStream.range;

import java.io.IOException;

public class Qualification {

	private static final int NUMBER_SYMBOLS_STRING = 72;
	private static final int FIRST_FIFTEEN_PLACE = 15;
	private static final String LINE_SEPARATOR = "-";
	private static final String DATE_FORMAT_QUALIFICATION = "yyyy-MM-dd_HH:mm:ss.SSS";
	private static final String OUTPUT_FORMAT = "%2d. %s";

	public void getConsoleResultQualification() {
		try {
			List<Pilot> listPilotsRacing = getListPilotsRacing();
			listPilotsRacing.sort(comparing(Pilot::getBestTime));
			print(range(0, listPilotsRacing.size()).mapToObj(i -> getLinePilot(i, listPilotsRacing))
					.collect(Collectors.toList()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void print(List<String> qualificationResult) {
		qualificationResult.forEach(System.out::print);
	}

	private static String getLinePilot(int i, List<Pilot> pilots) {
		return (i == FIRST_FIFTEEN_PLACE)
				? (assemblyString(LINE_SEPARATOR, NUMBER_SYMBOLS_STRING) + "\n"
						+ String.format(OUTPUT_FORMAT, i + 1, pilots.get(i).toString()))
				: (String.format(OUTPUT_FORMAT, i + 1, pilots.get(i).toString()));
	}

	private static String assemblyString(String symbol, int numberOfSymbols) {
		return range(0, numberOfSymbols).mapToObj(i -> symbol).collect(Collectors.joining());
	}

	private List<Pilot> getListPilotsRacing() throws IOException {

		List<String> listStartTime = FileStore.getSortedLinesFromFile("start.log");
		List<String> listEndTime = FileStore.getSortedLinesFromFile("end.log");
		List<String> listPilots = FileStore.getSortedLinesFromFile("abbreviations.txt");

		return listStartTime.stream().map(lineStartTime -> getPilot(lineStartTime, listEndTime, listPilots))
				.collect(Collectors.toList());
	}

	private Pilot getPilot(String lineStartTime, List<String> listTimeEnd, List<String> listPilots) {
		LocalDateTime startTime = getQualificationTime(lineStartTime);
		LocalDateTime endTime = listTimeEnd.stream()
				.filter(stringTimeEnd -> stringTimeEnd.substring(0, 3).equals(lineStartTime.substring(0, 3)))
				.findFirst().map(this::getQualificationTime).orElse(getQualificationTime(lineStartTime));

		String[] pilotTeamName = listPilots.stream()
				.filter(pilot -> lineStartTime.substring(0, 3).equals(pilot.substring(0, 3))).findFirst()
				.map(pilot -> pilot.split("_")).orElse(new String[] { "defaultPilotName", "defaultTeamName" });
		return new Pilot(lineStartTime.substring(0, 3), Duration.between(startTime, endTime), pilotTeamName[1],
				pilotTeamName[2]);
	}

	private LocalDateTime getQualificationTime(String lineStart) {
		DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(DATE_FORMAT_QUALIFICATION);
		return parse(lineStart.substring(3, 26), inputFormat);
	}
}

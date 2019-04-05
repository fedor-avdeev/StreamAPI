package org.fedoravdeev.task05.streamapi;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Qualification {
	
	private static final int NUMBER_SYMBOLS_STRING = 72;
	private static final int FIRST_FIFTEEN_PLACE = 15;
	private static final String LINE_SEPARATOR = "-";
	private static final String DATE_FORMAT_QUALIFICATION = "yyyy-MM-dd_HH:mm:ss.SSS";
	private static final String OUTPUT_FORMAT = "%-2d.%-17s | %-25s | %-11s\n";	
	private final AtomicInteger count = new AtomicInteger();	
	private List<Pilot> listPilotsRacing;
	
	public Qualification() {
		this.listPilotsRacing = new ArrayList<>();
	}

	public void getResultQualification() {
		if (listPilotsRacing.isEmpty()) {
			getListPilotsRacing();
		}

		listPilotsRacing.stream()
			.sorted(Comparator.comparing(Pilot::getBestTime))
			.limit(FIRST_FIFTEEN_PLACE)
			.forEach(this::printLinePilot);		
		listPilotsRacing.stream()
			.limit(1)
			.forEach(this::printLineSeparator);
		listPilotsRacing.stream()
			.sorted(Comparator.comparing(Pilot::getBestTime))
			.skip(FIRST_FIFTEEN_PLACE)
			.forEach(this::printLinePilot);
	}

	private void printLineSeparator(Pilot p) {
		System.out.print(String.format(assemblyString(LINE_SEPARATOR, NUMBER_SYMBOLS_STRING) + "\n"));
	}

	private void printLinePilot(Pilot p) {		
		System.out.print(String.format(OUTPUT_FORMAT,
			count.incrementAndGet(),
			p.getPilotName(),
			p.getTeamName(), 
			p.getStringBestTime()));
	}

	private String assemblyString(String symbol, int numberOfSymbols) {
		StringBuilder stringSeparator = new StringBuilder();
		for (int i = 0; i < numberOfSymbols; i++) {
			stringSeparator.append(symbol);
		}
		return stringSeparator.toString();
	}

	private List<Pilot> getListPilotsRacing() {

		List<String> listStartTime = ParseFiles.getSortedLinesFromFile("start.log");
		List<String> listEndTime   = ParseFiles.getSortedLinesFromFile("end.log");
		List<String> listPilots    = ParseFiles.getSortedLinesFromFile("abbreviations.txt");

		for (String lineStartTime : listStartTime) {
			listPilotsRacing.add(getPilot(lineStartTime, listEndTime, listPilots));
		}
		return listPilotsRacing;
	}

	private Pilot getPilot(String lineStartTime, List<String> listTimeEnd, List<String> listPilots) {
		LocalDateTime startTime = getQualificationTime(lineStartTime);
		LocalDateTime endTime   = getQualificationTime(lineStartTime);
		String fullNamePilot = null;
		String nameTeamPilot = null;

		for (String stringTimeEnd : listTimeEnd) {
			if (stringTimeEnd.substring(0, 3).equals(lineStartTime.substring(0, 3))) {
				endTime = getQualificationTime(stringTimeEnd);
				break;
			}
		}
		
		for (String pilot : listPilots) {
			if (pilot.substring(0, 3).equals(lineStartTime.substring(0, 3))) {
				String[] allNames = pilot.split("_");
				fullNamePilot = allNames[1];
				nameTeamPilot = allNames[2];
				break;
			}
		}
		return new Pilot(lineStartTime.substring(0, 3), Duration.between(startTime, endTime), fullNamePilot, nameTeamPilot);
	}

	private LocalDateTime getQualificationTime(String lineStart) {
		DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(DATE_FORMAT_QUALIFICATION);
		return LocalDateTime.parse(lineStart.substring(3, 26),inputFormat);
	}
}

package org.fedoravdeev.task05.streamapi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;
import static java.util.stream.Collectors.toList;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Qualification {
	private static final String WORKING_DIRECTORY = Paths.get("").toAbsolutePath().toString() + "/src/data/";
	private static final String MINUS = "-";
	private static final int NUMBER_SYMBOLS_STRING = 72;
	private static final int FIRST_FIFTEEN_PLACE = 15;
	private final AtomicInteger count = new AtomicInteger();

	private List<Pilot> listPilotsRacing;

	public Qualification() {
		this.listPilotsRacing = new ArrayList<>();
	}

	public void getResultQualification() throws ParseException {

		if (listPilotsRacing.isEmpty()) {
			getListPilotsRacing();
		}
		
		IntStream.iterate(1, (i) -> i + 1).peek(System.out::println).allMatch(i -> i < FIRST_FIFTEEN_PLACE);
		//IntStream.iterate(1, n -> n + 1).limit(FIRST_FIFTEEN_PLACE).forEach(System.out.println(n)));
		
		listPilotsRacing.stream()
			.sorted(Comparator.comparing(Pilot::getBestTime))
			.limit(FIRST_FIFTEEN_PLACE)
			.forEach(this::formatStringPilot);
		listPilotsRacing.stream()
			.limit(1)
			.forEach(this::formatStringSeparator);
		listPilotsRacing.stream()
			.sorted(Comparator.comparing(Pilot::getBestTime))
			.skip(FIRST_FIFTEEN_PLACE)
			.forEach(this::formatStringPilot);
	}

	private void formatStringSeparator(Pilot p) {
		System.out.print(String.format(assemblyString(MINUS, NUMBER_SYMBOLS_STRING) + "\n"));
	}

	private void formatStringPilot(Pilot p) {
		System.out.print(String.format("%-2d.%-17s | %-25s | %-11s\n", 
			count.incrementAndGet(), 
			p.getNamePilot(),
			p.getTeam(), 
			p.getStringBestTime()));
	}

	private String assemblyString(String symbol, int numberOfSymbols) {
		StringBuilder stringSeparator = new StringBuilder();
		for (int i = 0; i < numberOfSymbols; i++) {
			stringSeparator.append(symbol);
		}
		return stringSeparator.toString();
	}

	private List<Pilot> getListPilotsRacing() throws ParseException {

		List<String> linesStart = getSortedLinesFromFile("start.log");
		List<String> linesEnd = getSortedLinesFromFile("end.log");
		List<String> listPilots = getSortedLinesFromFile("abbreviations.txt");

		for (String stringStart : linesStart) {
			listPilotsRacing.add(getPilot(stringStart, linesEnd, listPilots));
		}
		return listPilotsRacing;
	}

	private List<String> getSortedLinesFromFile(String fileName) {
		List<String> linesFromFile = new ArrayList<>();
		Path pathFile = Paths.get(WORKING_DIRECTORY + fileName);
		try (Stream<String> lineStreamFile = Files.newBufferedReader(pathFile).lines()) {
			linesFromFile = lineStreamFile.sorted().collect(toList());
		} catch (IOException ignored) {
		}
		return linesFromFile;
	}

	private Pilot getPilot(String stringTimeStart, List<String> listTimeEnd, List<String> listPilots) {
		LocalDateTime startTime = getQualificationTime(stringTimeStart);
		LocalDateTime endTime   = getQualificationTime(stringTimeStart);
		String fullNamePilot = null;
		String nameTeamLipot = null;

		for (String stringTimeEnd : listTimeEnd) {
			if (stringTimeEnd.substring(0, 3).equals(stringTimeStart.substring(0, 3))) {
				endTime = getQualificationTime(stringTimeEnd);
				break;
			}
		}
		
		for (String pilot : listPilots) {
			if (pilot.substring(0, 3).equals(stringTimeStart.substring(0, 3))) {
				String[] nameTeamPilot = pilot.split("_");
				fullNamePilot = nameTeamPilot[1];
				nameTeamLipot = nameTeamPilot[2];
				break;
			}
		}
		return new Pilot(stringTimeStart.substring(0, 3), Duration.between(startTime, endTime), fullNamePilot, nameTeamLipot);
	}

	private LocalDateTime getQualificationTime(String lineStart) {
		DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
		return LocalDateTime.parse(lineStart.substring(3, 26),inputFormat);
	}
}

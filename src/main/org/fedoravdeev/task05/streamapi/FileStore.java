
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileStore {
	private static final String WORKING_DIRECTORY = Paths.get("").toAbsolutePath().toString() + "/src/data/";
	private static List<String> listStartTime;
	private static List<String> listEndTime;
	private static List<String> listPilots;

	public FileStore() {
		FileStore.listStartTime = getSortedLinesFromFile("start.log");
		FileStore.listEndTime = getSortedLinesFromFile("end.log");
		FileStore.listPilots = getSortedLinesFromFile("abbreviations.txt");
	}

	public List<String> getSortedLinesFromFile(String fileName) {
		try {
			return Files.newBufferedReader(Paths.get(WORKING_DIRECTORY + fileName)).lines().sorted().collect(toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public List<String> getListStartTime() {
		return listStartTime;
	}

	public List<String> getListEndTime() {
		return listEndTime;
	}

	public List<String> getListPilots() {
		return listPilots;
	}
}
package org.fedoravdeev.task05.streamapi;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileStore {
	private static final String WORKING_DIRECTORY = Paths.get("").toAbsolutePath().toString() + "/src/data/";

	public static List<String> getSortedLinesFromFile(String fileName) throws IOException {
		return Files.newBufferedReader(Paths.get(WORKING_DIRECTORY + fileName)).lines().sorted().collect(toList());
	}
}

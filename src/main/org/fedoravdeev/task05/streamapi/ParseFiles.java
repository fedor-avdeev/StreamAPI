package org.fedoravdeev.task05.streamapi;

import static java.util.stream.Collectors.toList;

//import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ParseFiles {
	private static final String WORKING_DIRECTORY = Paths.get("").toAbsolutePath().toString() + "/src/data/";

//	public static File parseAccessFile(String acessFileArg) {
//		File file = new File(acessFileArg);
//		if (!file.exists() || file.isDirectory()) {
//			throw new IllegalArgumentException("File not exists or it is a directory");
//		}
//		return file;
//	}

	public static List<String> getSortedLinesFromFile(String fileName) {
		List<String> linesFromFile = new ArrayList<>();
		Path pathFile = Paths.get(WORKING_DIRECTORY + fileName);
		try (Stream<String> lineStreamFile = Files.newBufferedReader(pathFile).lines()) {
			linesFromFile = lineStreamFile.sorted().collect(toList());
		} catch (IOException ignored) {
		}
		return linesFromFile;
	}
}
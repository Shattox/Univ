package fr.uge.poo.paint.ex2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Drawing {
	public static List<String> readFile(String fileName) throws IOException {
		Path path = Paths.get(fileName);
	    
		try(Stream<String> lines = Files.lines(path)) {
	         return lines.collect(Collectors.toList());
	    }
	}
}

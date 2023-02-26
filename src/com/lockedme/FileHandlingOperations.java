package com.lockedme;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileHandlingOperations {
	String location = "G:\\SimpliLearn FSD Java Program\\VirtualKeyforYourRepositories\\Files Directory";
	File dir = new File(location);
	Path path = Path.of(location);

	public Path locateFilePath(Path path) {

		try {
			if (location == null || location.isEmpty() || location.isBlank())
				throw new NullPointerException("Path cannot be Empty or null");
			if (dir.exists()) {
				path = Path.of(dir.getAbsolutePath());
			} else
				throw new IllegalArgumentException("Path does not exist");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getClass().getName());
		}
		try {
			if (location != null && !location.isEmpty() && !location.isBlank()) {
				if (!dir.exists()) {
					path = Files.createDirectories(path);
					System.out.println("Directory successfully created at path :" + path);
				}
			}
		} catch (IOException e) {
			System.out.println("Failed to create  the file at: " + path);
			System.out.println(e.getClass().getName());
		}
		return path;

	}

	public void listAllFilesinAscendingOrder() {
		FileHandlingOperations fHO = new FileHandlingOperations();
		fHO.locateFilePath(path);
		System.out.println("Displaying all files with directory structure in ascending order\n");

		// listFilesInDirectory displays files along with folder structure
		List<String> filesListNames = FileHandlingOperations.listFilesInDirectory(path.toString(), 0,
				new ArrayList<String>());

		System.out.println("Displaying all files in ascending order\n");
		Collections.sort(filesListNames, String.CASE_INSENSITIVE_ORDER);
		filesListNames.stream().forEach(System.out::println);
	}

	public static List<String> listFilesInDirectory(String path, int indentationCount, List<String> fileListNames) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		List<File> filesList = Arrays.asList(files);

		Collections.sort(filesList);

		if (files != null && files.length > 0) {
			for (File file : filesList) {

				System.out.print(" ".repeat(indentationCount * 2));

				if (file.isDirectory()) {
					System.out.println("`-- " + file.getName());

					// Recursively indent and display the files
					fileListNames.add(file.getName());
					listFilesInDirectory(file.getAbsolutePath(), indentationCount + 1, fileListNames);
				} else {
					System.out.println("|-- " + file.getName());
					fileListNames.add(file.getName());
				}
			}
		} else {
			System.out.print(" ".repeat(indentationCount * 2));
			System.out.println("|-- Empty Directory");
		}
		System.out.println();
		return fileListNames;
	}

	public void createNewFileInDirectory(String fileName, Scanner sc) {
		FileHandlingOperations fHO = new FileHandlingOperations();
		Path addFileToPath = Paths.get(fHO.locateFilePath(path).toString() + "\\" + fileName);
		try {
			Files.createFile(addFileToPath);
			System.out.println(fileName + " created successfully");

			System.out.println("Would you like to add some content to the file? (Y/N)");
			String choice = sc.next().toLowerCase();

			sc.nextLine();
			if (choice.equals("y")) {
				System.out.println("\n\nInput content and press enter\n");
				String content = sc.nextLine();
				Files.write(addFileToPath, content.getBytes());
				System.out.println("\n Content written to file " + fileName);
				System.out.println("Content can be read using Notepad or Notepad++");
			}
		} catch (IOException e) {
			System.out.println("Failed to create file " + fileName);
			System.out.println(e.getClass().getName());
		}

	}

	public void searchFile(String pathValue, String fileName, List<String> fileListNames) {
		File dir = new File(pathValue);
		File[] files = dir.listFiles();
		List<File> filesList = Arrays.asList(files);

		if (files != null && files.length > 0) {
			for (File file : filesList) {

				if (file.getName().startsWith(fileName)) {
					fileListNames.add(file.getAbsolutePath());
				}

				// Need to search in directories separately to ensure all files of required
				// fileName are searched
				if (file.isDirectory()) {
					searchFile(file.getAbsolutePath(), fileName, fileListNames);
				}
			}
		}
	}

	public void deleteFile(String fileToBeDeleted) {
		File currFile = new File(fileToBeDeleted);
		File[] files = currFile.listFiles();
		boolean isFileDeleted = false;
		boolean isFolderDeleted = false;

		if (files != null && files.length > 0) {
			for (File file : files) {

				String fileName = file.getName() + " at " + file.getParent();
				if (file.isDirectory()) {
					deleteFile(file.getAbsolutePath());
				}

				if (file.delete()) {
					isFileDeleted = true;
					System.out.println(fileName + " deleted successfully");
				}
				if (isFileDeleted == false) {
					System.out.println("Failed to delete " + fileName);
				}
			}

		}

		String currFileName = currFile.getName() + " at " + currFile.getParent();
		if (currFile.delete()) {
			isFolderDeleted = true;
			System.out.println(currFileName + " deleted successfully");
		}
		if (isFolderDeleted == false) {
			System.out.println("Failed to delete " + currFileName);
		}
	}

	public List<String> displayFileLocations(String fileName) {
		List<String> fileListNames = new ArrayList<>();
		FileHandlingOperations fHO = new FileHandlingOperations();
		Path searchPath = fHO.locateFilePath(path);
		fHO.searchFile(searchPath.toString(), fileName, fileListNames);

		if (fileListNames.isEmpty()) {
			System.out.println("\n\n***** Couldn't find any file with given file name \"" + fileName + "\" *****\n\n");
		} else {
			System.out.println("\n\nFound file at below location(s):");

			List<String> files = IntStream.range(0, fileListNames.size())
					.mapToObj(index -> (index + 1) + ": " + fileListNames.get(index)).collect(Collectors.toList());

			files.forEach(System.out::println);
		}

		return fileListNames;
	}

}

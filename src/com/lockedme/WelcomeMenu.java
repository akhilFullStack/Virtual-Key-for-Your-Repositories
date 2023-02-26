package com.lockedme;

import java.util.List;
import java.util.Scanner;


public class WelcomeMenu {

	Scanner sc = new Scanner(System.in);
	FileHandlingOperations dao = new FileHandlingOperations();

	public void displayWelcomeScreen() {
		System.out.println();
		System.out.println("*************************************************");
		System.out.println("*               LOCKEDME.COM                    *");
		System.out.println("*************************************************");
		System.out.println("*      DEVELOPED BY AKHIL KUMAR SRIVASTAVA      *");
		System.out.println("*************************************************");
		System.out.println("\n\n");

	}

	public void mainMenuUI() {

		int choice = 0;
		char decision = 0;
		do {
			System.out.println("=====================================");
			System.out.println("|            MAIN MENU              |");
			System.out.println("=====================================");
			System.out.println("| Select any one of the following:  |");
			System.out.println("|   1 - List All Files              |");
			System.out.println("|   2 - More Options                |");
			System.out.println("|   3 - Exit                        |");
			System.out.println("=====================================");
			System.out.println("Enter your choice : ");

			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("\nInvalid Input \nValid Input Integers:(1-3)\n");
				mainMenuUI();
			}

			switch (choice) {

			case 1:
				System.out.println();
				try {
					dao.listAllFilesinAscendingOrder();
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				System.out.println("\n***********************************\n");
				break;

			case 2:
				System.out.println();
				subMenu();
				break;

			case 3:
				System.out.println("\n Are you sure you want to exit ? ");
				System.out.println("  (Y) ==> Yes    (N) ==> No        ");
				decision = sc.nextLine().toUpperCase().charAt(0);
				if (decision == 'Y') {
					System.out.println("\n");
					exitScreen();
					System.exit(1);
				} else if (decision == 'N') {
					System.out.println("\n");
					mainMenuUI();
				} else {
					System.out.println("\nInvalid Input \nValid Inputs :(Y/N)\n");
					mainMenuUI();
				}

			default:
				System.out.println("\nInvalid Input \nValid Input Integers:(1-3)\n");
				mainMenuUI();

			}

		} while (true);
	}

	public void subMenu() {
		String file = null;
		String fileName = null;
		int choice = 0;

		do {
			System.out.println("=====================================");
			System.out.println("|            SUB MENU               |");
			System.out.println("=====================================");
			System.out.println("| Select any one of the following:  |");
			System.out.println("|   1 - Add a file                  |");
			System.out.println("|   2 - Delete a file               |");
			System.out.println("|   3 - Search a file               |");
			System.out.println("|   4 - Go Back                     |");
			System.out.println("=====================================");
			System.out.println("Enter your choice : ");

			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid Input \nValid Input Integers:(1-4)");
				subMenu();
			}

			switch (choice) {
			case 1:
				System.out.println("\n==> Adding a File...");
				System.out.println("Please enter a file name : ");
				file = sc.nextLine();
				fileName = file.trim();

				try {
					dao.createNewFileInDirectory(fileName, sc);
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					System.out.println("Error occurred while adding file..");
					System.out.println("Please try again...");
				}

				System.out.println("\n**********************************\n");
				break;

			case 2:
				System.out.println("\n==> Deleting a File...");
				System.out.println("Please enter a file name to Delete : ");
				String fileToDelete = sc.nextLine();

				List<String> filesToDelete = dao.displayFileLocations(fileToDelete);

				String deletionPrompt = "\nSelect index of which file to delete?"
						+ "\n(Enter 0 if you want to delete all elements)";
				System.out.println(deletionPrompt);

				int idx = sc.nextInt();
				try {
					if (idx != 0) {
						dao.deleteFile(filesToDelete.get(idx - 1));
					} else {
						for (String path : filesToDelete) {
							// If idx == 0, delete all files displayed for the name
							dao.deleteFile(path);
						}
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				System.out.println("\n***********************************\n");
				break;

			case 3:
				System.out.println("\n==> Searching a File...");
				System.out.println("Please enter a file name to Search : ");
				file = sc.nextLine();
				fileName = file.trim();
				try {
					dao.displayFileLocations(fileName);
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				System.out.println("\n***********************************\n");
				break;
			case 4:
				mainMenuUI();
				break;

			default:
				System.out.println("Invalid Input \nValid Input Integers:(1-4)");
				subMenu();

			}

			file = null;
			fileName = null;

		} while (true);

	}

	public void exitScreen() {

		System.out.println("*************************************************");
		System.out.println("*************************************************");
		System.out.println("*                                               *");
		System.out.println("*    THANK YOU FOR VISITING LOCKEDME.COM        *");
		System.out.println("*                                               *");
		System.out.println("*************************************************");
		System.out.println("*************************************************");
		System.out.println("\n\n");

	}
}

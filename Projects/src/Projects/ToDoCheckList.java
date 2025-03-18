package Projects;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFileChooser;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.Desktop;
public class ToDoCheckList{
	
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		 HashMap<String,Boolean> checkList = new HashMap<String, Boolean>();
	
		 
	
		File currentFile = null;
		Desktop desktop = Desktop.getDesktop();
		JFileChooser chooser = new JFileChooser();
		boolean response = true;
		int choice;
		while(response == true) {
			System.out.println("[1] Create a file" );
			System.out.println("[2] load a file" );
			System.out.println("[3] write to a file" );
			System.out.println("[4] read a file" );
			System.out.println("[5] open a file" );
			choice = scanner.nextInt();
			switch(choice) {
			case 1:
				currentFile = createFile(chooser);
				break;
			case 2:
				currentFile = loadFile(chooser);
				
				break;
			case 3:
				checkList = writeToFile(checkList,currentFile);
				break;
			case 4:
				checkList = populateHash(checkList, currentFile);
				readHash(checkList);
				break;
				
				
			case 5:
				try {
					desktop.open(currentFile);
				} catch (IOException e) {
				
					e.printStackTrace();
				}
				catch(NullPointerException w) {
					w.printStackTrace();
				}
				break;
			default:
				System.out.println("Error");
				break;
				
			}
			System.out.println("Press 1 to continue and 0 to stop");
			choice = scanner.nextInt();
			if(choice == 0) {
				response = false;
			}
		}
		
		
	}
	public static HashMap writeToFile(HashMap checkList, File file) {
		try {
			FileWriter myWriter = new FileWriter(file.getAbsolutePath());
			System.out.println("Please input the task to be done.");
			String input = scanner.next();
			System.out.println("Please input if that task is complete (done or no)");
			String input2 = scanner.next();
			boolean value = false;
			if(input2.toLowerCase().equals("done")) {
				value = true;
			}
			else {
				value = false;
			}
			checkList.put(input, value);
			myWriter.write(input + "|" + input2);
			myWriter.close();
			return checkList;
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return checkList;
	}
	public static void readHash(HashMap checkList) {
		Iterator it = checkList.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
		 
	}
	
	public static HashMap populateHash(HashMap checkList, File file) {
		//Enumeration<String> keys = (Enumeration<String>) checkList.keySet();
		try {	
			
			Scanner fileScanner = new Scanner(file);
			String currentKey = "";
			String currentValue = "";
			boolean isKey = true;
			boolean booleanValue = true;
			String currentline = fileScanner.nextLine();
			fileScanner = new Scanner(file);
			while(fileScanner.hasNextLine()) {
				
				
				for(int i = 0; i < currentline.length(); i++) {
					
					if(currentline.charAt(i) !='|' && isKey) {
						currentKey += currentline.charAt(i);
					}
					else if(currentline.charAt(i) == '|'){
						isKey = false;
						
					}
					else if(currentline.charAt(i) != ' '){
						currentValue += currentline.charAt(i);
					}
				}
				if(currentValue.toLowerCase().equals("done")) {
					booleanValue = true;
				}
				else {
					booleanValue = false;
					
				}
				checkList.put(currentKey, booleanValue);
				System.out.println(currentValue.toLowerCase() );
				System.out.println(checkList.keySet());
				System.out.println(checkList.values());
				currentKey = "";
				currentValue = "";
				isKey = true;
				currentline = fileScanner.nextLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(StringIndexOutOfBoundsException gf) {
			gf.printStackTrace();
		}
		
		return checkList;
		
		
	}
	public static File loadFile(JFileChooser chooser) {
		System.out.println("Please select the file you want to load");
		String filePath = getFilePath(chooser);
		File file = new File(filePath);
		return file;
	}
	public static File createFile(JFileChooser chooser) {
		System.out.println("Please select folder to put file in");
		String directoryPath = getDirectoryPath(chooser);
		try {
		System.out.println("Please input the name of the file. Make sure to include the .txt or any other extension");
		String fileName = scanner.next(); 
		File file = new File(directoryPath+"\\"+ fileName);
		
			if (file.createNewFile()) {
				System.out.println("File created");
				return file;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file;
		return file = new File(directoryPath);
		
	}
	public static String getFilePath(JFileChooser chooser) {
		String filepath = "nopath";
		
		chooser.setCurrentDirectory(new File("Documents"));
		int result = chooser.showOpenDialog(chooser);
		if(result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			filepath = selectedFile.getAbsolutePath();
		}
		return filepath;
	}
	public static String getDirectoryPath(JFileChooser chooser) {
		String directorypath = "nopath";
		chooser.setCurrentDirectory(new File("Documents"));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = chooser.showOpenDialog(chooser);
		if(result == JFileChooser.APPROVE_OPTION) {
			File selectedDirectory = chooser.getSelectedFile();
			directorypath = selectedDirectory.getAbsolutePath();
		}
		return directorypath;
	}
	
    }

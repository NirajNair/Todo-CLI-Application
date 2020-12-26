import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class Todo {

	private static Scanner reader;

	public static ArrayList<String> fileToList(String filename) throws FileNotFoundException {
		File todo = new File("todo.txt");
		reader = new Scanner(todo);
		ArrayList<String> taskArray = new ArrayList<>();
		while(reader.hasNextLine()){
			taskArray.add(reader.nextLine());
		}
		return taskArray;
	}

	public static int countLines(String fileName) throws IOException {
		int lines = 0;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			while (reader.readLine() != null) lines++;
			reader.close();
		} catch(Exception e) {
			lines = 0;
		}
		return lines;
	}

	public static void emptyFile(String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(filename)
						,StandardCharsets.UTF_8));
		;
		writer.write("");
		writer.close();
	}

	public static ArrayList<String> reverseList(ArrayList<String> list){
		ArrayList<String> reversedList = new ArrayList<>();
		for(int i = list.size()-1; i>= 0; i--){
			reversedList.add(list.get(i));
		}
		return reversedList;
	}

	public static String displayIntro(){
		String intro = "Usage :-\n"
				+"$ ./todo add \"todo item\"  # Add a new todo\n"
				+"$ ./todo ls               # Show remaining todos\n"
				+"$ ./todo del NUMBER       # Delete a todo\n"
				+"$ ./todo done NUMBER      # Complete a todo\n"
				+"$ ./todo help             # Show usage\n"
				+"$ ./todo report           # Statistics\n";
		return intro;
	}
	public static boolean checkRedundancy ( String newTask) throws IOException {
		File file = new File("todo.txt");

		boolean flag = false;
		try {
			Scanner scanner = new Scanner(file);

			//now read the file line by line...
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				if(line == newTask) {
					flag = true;
				}
			}
		} catch(FileNotFoundException e) {
			//handle this
		}
		return flag;
	}

	public static void addTask(String newTask) throws IOException {

		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("todo.txt", true)
						,StandardCharsets.UTF_8));

		writer.write(newTask+"\n");
		writer.close();
	}

	public static void addDoneTask( String newTask) throws IOException {
		LocalDate date = LocalDate.now();
		BufferedWriter writer = new BufferedWriter(
				new FileWriter("done.txt", true));
		writer.write("x " + date + " " + newTask + "\n");
		writer.close();
	}

	public static void displayList() throws IOException {
		int totalLines = countLines("todo.txt");
		if(totalLines != 0){
			ArrayList<String> taskArray = fileToList("todo.txt");
			ArrayList<String> reversedList = reverseList(taskArray);
			for (int i = 0; i < reversedList.size(); i++)
				System.out.printf("[%d] %s\n",reversedList.size()-i,reversedList.get(i));
		} else System.out.println("There are no pending todos!");
	}

	public static void deleteTask(int number) throws IOException {
		ArrayList<String> taskArray = fileToList("todo.txt");
		int totalLines = countLines("todo.txt");
		emptyFile("todo.txt");
		if(number <= totalLines && number != 0) {
			for (int i = 0; i < taskArray.size(); i++) {
				if (i == number - 1) continue;
				addTask(taskArray.get(i));
			}
		} else System.out.printf("Error: todo #%d does not exist. Nothing deleted.\n", number);
		System.out.println("Deleted todo #" + number);
	}

	public static void completeTask(int number) throws IOException {
		ArrayList<String> taskArray = fileToList("todo.txt");
		int totalLines = countLines("todo.txt");
		if(number <= totalLines && number != 0){
			emptyFile("todo.txt");
			for (int i = 0; i < taskArray.size(); i++){
				if(i == number-1) addDoneTask(taskArray.get(i));
				else addTask( taskArray.get(i));
			}
		} else System.out.printf("Error: todo #%d does not exist.\n", number);
		System.out.printf("Marked todo #%d as done.\n", number);
	}


	public static void displayReport() throws IOException {
		LocalDate date = LocalDate.now();
		int todoLines = countLines("todo.txt");
		int doneLines = countLines("done.txt");
		System.out.printf("%tF Pending : %d Completed : %d\n", date,
				todoLines, doneLines);
	}


	public static void main(String args[]) throws IOException {
		if(args.length == 0 || "help".equals(args[0])) {
			System.out.println(displayIntro());
		}
		else if ("add".equals(args[0])) {
			try {
				System.out.println("Added todo: \"" + args[1]+"\"");
				addTask(args[1]);
			} catch (Exception e){
				System.out.println("Error: Missing todo string. Nothing added!");
			}
		}
		else if ("ls".equals(args[0])) {
			displayList();
		}
		else if ("del".equals(args[0])){
			try {
				int number = Integer.parseInt(args[1]);
				deleteTask(number);
			} catch (Exception e){
				System.out.println("Error: Missing NUMBER for deleting todo.");
			}
		}
		else if ("done".equals(args[0])){
			try{
				int number = Integer.parseInt(args[1]);
				completeTask(number);
			} catch (Exception e){
				System.out.println("Error: Missing NUMBER for marking todo as done.");
			}
		}
		else if ("report".equals(args[0])) {
			displayReport();
		}
	}
}

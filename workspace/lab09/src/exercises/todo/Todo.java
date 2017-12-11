package exercises.todo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Todo {
	private final static String todoPath = "TODO.txt";
	private List<Task> tasks;

	private void load() throws FileNotFoundException, IOException {
		tasks = new ArrayList<Task>();
		/*BufferedReader br = new BufferedReader(new FileReader(todoPath));
		String str;
		while((str = br.readLine()) != null) {
			String[] split = str.split("\t");
			Task task = new Task(Integer.parseInt(split[0]), split[1]);
			tasks.add(task);
		}
		br.close();*/
		Files.lines(Paths.get(todoPath)).forEach(s -> {
												String[] split = s.split("\t");
												tasks.add(new Task(Integer.parseInt(split[0]), split[1]));});
	}

	private void add(int priority, String msg) throws IOException {
		BufferedWriter br = new BufferedWriter(new FileWriter(todoPath, true));
		br.append(priority + "\t" + msg);
		br.newLine();
		br.close();
	}

	private void writeTasksToFile() throws IOException {
		BufferedWriter br = new BufferedWriter(new FileWriter(todoPath));
		tasks.stream().forEach(t -> {try {
											br.append(t.info());
											br.newLine();
										} catch (Exception e) {
											e.printStackTrace();
										}});
		br.close();
	}

	private void print(boolean priorityIncreasing) {
		System.out.println("Num.\tPrior.\tTask");
		if(priorityIncreasing) {
			Collections.sort(tasks);
			/*for(Task t: tasks) {
				System.out.println(t.toString());
			}*/
			tasks.stream().sorted().forEachOrdered(System.out::println);
		}
		else {
			tasks.stream().sorted(Collections.reverseOrder()).forEachOrdered(System.out::println);
			//tasks.stream().sorted((o1, o2) -> (o2.priority - o1.priority)).forEachOrdered(System.out::println);
		}
	}

	private void delete() throws IOException {
		System.out.println("Num.\tPrior.\tTask");
		tasks.stream().forEachOrdered(System.out::println);
		//Console reader = System.console();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));//read standard input
		System.out.print("Choose an element to delete: ");
		boolean correct = false;
		while(!correct) {
			String line = reader.readLine();
			try {
				int number = Integer.parseInt(line);
				if(number > tasks.size() || number <= 0) {
					System.out.print("Insert a valid task number: ");
				}
				else {
					System.out.println("Removing " + tasks.remove(number - 1));
					correct = true;
					writeTasksToFile();
				}
			}
			catch(NumberFormatException n) {
				System.out.print("Insert a valid number: ");
			}
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Todo todo = new Todo();
		todo.load();
		switch(args[0]) {
			case "-a":
				todo.add(Integer.parseInt(args[1]), args[2]);
				break;
			case "-l":
				todo.print(false);
				break;
			case "-r":
				todo.print(true);
				break;
			case "-d":
				todo.delete();
				break;
			default:
				break;
		}
	}
}

class Task implements Comparable<Task> {
	private static int counter = 0;
	int priority;
	private String msg;
	private int taskNumber;

	public Task(int priority, String msg) {
		this.priority = priority;
		this.msg = msg;
		taskNumber = ++counter;
	}

	@Override
	public int compareTo(Task o) {
		return this.priority - o.priority;
	}

	public String info() {
		return priority + "\t" + msg;
	}

	@Override
	public String toString() {
		return taskNumber + "\t" + priority + "\t" + msg;
	}
}

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// Class to manage daily tasks
public class DailyTaskManager {
    // Part A: Task Array
    private static String[] taskArray = {
        "Check email",
        "Attend lecture",
        "Exercise",
        "Read book",
        "Cook dinner"
    };

    // Part B: Task Undo Stack
    private static Stack completedTasks = new Stack(10); // Stack with a capacity of 10

    // Part C: Dynamic Task List with Linked List
    private static LinkedList dynamicTaskList = new LinkedList();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            clearScreen();
            displayCurrentTime();
            System.out.println("\nDaily Task Manager");
            System.out.println("1. View tasks (Array)");
            System.out.println("2. Update task (Array)");
            System.out.println("3. Mark task as completed (push to stack)");
            System.out.println("4. Undo task completion (pop from stack)");
            System.out.println("5. Add task to dynamic list");
            System.out.println("6. Remove task from dynamic list");
            System.out.println("7. View all tasks in dynamic list");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewTasks();
                    break;
                case 2:
                    updateTask(scanner);
                    break;
                case 3:
                    markTaskAsCompleted(scanner);
                    break;
                case 4:
                    undoTaskCompletion();
                    break;
                case 5:
                    addTaskToDynamicList(scanner);
                    break;
                case 6:
                    removeTaskFromDynamicList(scanner);
                    break;
                case 7:
                    dynamicTaskList.displayTasks();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            if (choice != 8) {
                System.out.print("Press Enter to continue...");
                scanner.nextLine(); // Wait for the user to press Enter
            }
        } while (choice != 8);
        scanner.close(); // Close the scanner
    }

    // Method to clear the console screen
    public static void clearScreen() {
        // ANSI escape code to clear the screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Method to display the current local time
    public static void displayCurrentTime() {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println("Current Time: " + time);
    }

    // Part A: View all tasks in the array
    private static void viewTasks() {
        animatedLoading("Loading tasks...");
        System.out.println("Tasks in the array:");
        for (String task : taskArray) {
            System.out.println("- " + task);
        }
        System.out.println("Total number of tasks: " + taskArray.length);
    }

    // Part A: Update a task in the array
    private static void updateTask(Scanner scanner) {
        System.out.print("Enter task index to update (0 to " + (taskArray.length - 1) + "): ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (index >= 0 && index < taskArray.length) {
            System.out.print("Enter new task: ");
            String newTask = scanner.nextLine();
            taskArray[index] = newTask;
            System.out.println("Task updated!");
        } else {
            System.out.println("Invalid index!");
        }
    }

    // Part B: Mark a task as completed
    private static void markTaskAsCompleted(Scanner scanner) {
        System.out.print("Enter task to mark as completed: ");
        String task = scanner.nextLine();
        completedTasks.push(task);
        System.out.println("Task marked as completed and pushed to stack: " + task);
    }

    // Part B: Undo task completion
    private static void undoTaskCompletion() {
        String task = completedTasks.pop();
        if (task != null) {
            System.out.println("Task undone: " + task);
        }
    }

    // Part C: Add a task to the dynamic linked list
    private static void addTaskToDynamicList(Scanner scanner) {
        System.out.print("Enter new task to add to dynamic list: ");
        String newTask = scanner.nextLine();
        dynamicTaskList.add(newTask);
        System.out.println("Task added to dynamic list: " + newTask);
    }

    // Part C: Remove a task from the dynamic linked list
    private static void removeTaskFromDynamicList(Scanner scanner) {
        System.out.print("Enter task to remove from dynamic list: ");
        String taskToRemove = scanner.nextLine();
        if (dynamicTaskList.remove(taskToRemove)) {
            System.out.println("Task removed from dynamic list: " + taskToRemove);
        } else {
            System.out.println("Task not found in dynamic list!");
        }
    }

    // Method for animated loading
    private static void animatedLoading(String message) {
        String[] dots = {".  ", ".. ", "..."};
        for (int i = 0; i < 3; i++) {
            System.out.print("\r" + message + dots[i]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("\r" + message + " Done!");
    }
}

// Part B: Stack implementation for undo functionality
class Stack {
    private String[] stack;
    private int top;
    private int capacity;

    public Stack(int size) {
        stack = new String[size];
        capacity = size;
        top = -1;
    }

    public void push(String task) {
        if (top == capacity - 1) {
            System.out.println("Stack overflow! Cannot push task: " + task);
            return;
        }
        stack[++top] = task;
    }

    public String pop() {
        if (top == -1) {
            System.out.println("Stack underflow! No tasks to pop.");
            return null;
        }
        return stack[top--];
    }

    public String peek() {
        if (top == -1) {
            System.out.println("Stack is empty!");
            return null;
        }
        return stack[top];
    }
}

// Part C: Linked List implementation for dynamic task management
class LinkedList {
    private Node head;

    private class Node {
        String data;
        Node next;

        Node(String data) {
            this.data = data;
            this.next = null;
        }
    }

    public void add(String task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public boolean remove(String task) {
        if (head == null) {
            return false;
        }
        if (head.data.equals(task)) {
            head = head.next;
            return true;
        }
        Node current = head;
        while (current.next != null) {
            if (current.next.data.equals(task)) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void displayTasks() {
        if (head == null) {
            System.out.println("No tasks available in the dynamic list!");
            return;
        }
        System.out.println("Tasks in the dynamic list:");
        Node current = head;
        while (current != null) {
            System.out.println("- " + current.data);
            current = current.next;
        }
    }
}

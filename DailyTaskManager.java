import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

// ANSI color codes
class ANSI {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[31m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";
    public static final String BLUE = "\033[34m";
    public static final String PURPLE = "\033[35m";
    public static final String CYAN = "\033[36m";
    public static final String WHITE = "\033[37m";
}

public class DailyTaskManager {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] taskArray = {"Check email", "Attend lecture", "Exercise", "Read book", "Cook dinner"};
    private static final Stack<String> completedTaskStack = new Stack<>(); // Stack untuk menyimpan tugas yang sudah selesai
    private static final LinkedList<String> taskList = new LinkedList<>(); // LinkedList untuk menyimpan daftar tugas

    // Membersihkan layar konsol
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Animasi loading
    public static void animatedLoading(String message) {
        String[] dots = {".  ", ".. ", "..."};
        for (int i = 0; i < 3; i++) {
            System.out.print("\r" + ANSI.YELLOW + message + dots[i] + ANSI.RESET);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("\r" + ANSI.GREEN + message + " Done!   " + ANSI.RESET);
    }

    // Menampilkan tugas dari array
    public static void viewTasks() {
        animatedLoading("Loading tasks");
        for (String task : taskArray) {
            System.out.println(ANSI.CYAN + "- " + task + ANSI.RESET);
        }
        System.out.println(ANSI.YELLOW + "\nPress Enter to continue..." + ANSI.RESET);
        scanner.nextLine();
    }

    // Memperbarui tugas di array
    public static void updateTask() {
        System.out.println(ANSI.PURPLE + "\n--- Update Task ---" + ANSI.RESET);
        for (int i = 0; i < taskArray.length; i++) {
            System.out.println((i + 1) + ". " + taskArray[i]);
        }
        System.out.print(ANSI.YELLOW + "Enter task number to update: " + ANSI.RESET);
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Membersihkan newline
        if (index >= 0 && index < taskArray.length) {
            System.out.print(ANSI.CYAN + "Enter new task: " + ANSI.RESET);
            taskArray[index] = scanner.nextLine();
            System.out.println(ANSI.GREEN + "Task updated!" + ANSI.RESET);
        } else {
            System.out.println(ANSI.RED + "Invalid index!" + ANSI.RESET);
        }
    }

    // Menyelesaikan tugas dan memindahkannya ke stack
    public static void completeTask() {
        System.out.println(ANSI.PURPLE + "\n--- Complete Task ---" + ANSI.RESET);
        if (taskList.isEmpty()) {
            System.out.println(ANSI.RED + "No tasks available to complete!" + ANSI.RESET);
            return;
        }
        String completedTask = taskList.removeFirst(); // Menghapus tugas pertama dari LinkedList
        completedTaskStack.push(completedTask); // Menambahkan tugas yang selesai ke stack
        System.out.println(ANSI.GREEN + "Task completed and added to undo stack: " + completedTask + ANSI.RESET);
    }

    // Membatalkan penyelesaian tugas terakhir
    public static void undoTask() {
        System.out.println(ANSI.PURPLE + "\n--- Undo Last Completed Task ---" + ANSI.RESET);
        if (completedTaskStack.isEmpty()) {
            System.out.println(ANSI.RED + "No tasks to undo!" + ANSI.RESET);
            return;
        }
        String undoneTask = completedTaskStack.pop(); // Mengambil tugas terakhir dari stack
        taskList.addFirst(undoneTask); // Mengembalikan tugas ke LinkedList
        System.out.println(ANSI.YELLOW + "Task restored: " + undoneTask + ANSI.RESET);
    }

    // Menambahkan tugas baru ke LinkedList
    public static void addTask() {
        System.out.print(ANSI.CYAN + "Enter new task: " + ANSI.RESET);
        String newTask = scanner.nextLine();
        taskList.add(newTask); // Menambahkan tugas ke LinkedList
        System.out.println(ANSI.GREEN + "Task added!" + ANSI.RESET);
    }

    // Menghapus tugas dari LinkedList
    public static void removeTask() {
        System.out.print(ANSI.CYAN + "Enter task to remove: " + ANSI.RESET);
        String taskToRemove = scanner.nextLine();
        if (taskList.remove(taskToRemove)) {
            System.out.println(ANSI.GREEN + "Task removed!" + ANSI.RESET);
        } else {
            System.out.println(ANSI.RED + "Task not found!" + ANSI.RESET);
        }
    }

    // Menampilkan tugas dari LinkedList
    public static void viewLinkedListTasks() {
        System.out.println(ANSI.PURPLE + "\n--- Linked List Tasks ---" + ANSI.RESET);
        if (taskList.isEmpty()) {
            System.out.println(ANSI.RED + "No tasks available!" + ANSI.RESET);
            return;
        }
        for (String task : taskList) {
            System.out.println(ANSI.CYAN + "- " + task + ANSI.RESET);
        }
    }

    // Menu utama
    public static void main(String[] args) {
        while (true) {
            clearScreen();
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            System.out.println(ANSI.PURPLE + "\n--- Daily Task Manager --- " + ANSI.YELLOW + time + ANSI.RESET);
            System.out.println(ANSI.BLUE + "1. View tasks (Array)" + ANSI.RESET);
            System.out.println(ANSI.BLUE + "2. Update task (Array)" + ANSI.RESET);
            System.out.println(ANSI.BLUE + "3. Complete task (Stack)" + ANSI.RESET);
            System.out.println(ANSI.BLUE + "4. Undo last completed task (Stack)" + ANSI.RESET);
            System.out.println(ANSI.BLUE + "5. Add task (Linked List)" + ANSI.RESET);
            System.out.println(ANSI.BLUE + "6. Remove task (Linked List)" + ANSI.RESET);
            System.out.println(ANSI.BLUE + "7. View tasks (Linked List)" + ANSI.RESET);
            System.out.println(ANSI.BLUE + "8. Exit" + ANSI.RESET);
            System.out.print(ANSI.YELLOW + "Choose an option: " + ANSI.RESET);

            if (!scanner.hasNextInt()) {
                System.out.println(ANSI.RED + "Invalid input. Please enter a number." + ANSI.RESET);
                scanner.next(); // Membersihkan input yang tidak valid
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Membersihkan newline

            switch (choice) {
                case 1 -> viewTasks();
                case 2 -> updateTask();
                case 3 -> completeTask();
                case 4 -> undoTask();
                case 5 -> addTask();
                case 6 -> removeTask();
                case 7 -> viewLinkedListTasks();
                case 8 -> {
                    System.out.println(ANSI.RED + "Exiting..." + ANSI.RESET);
                    return;
                }
                default -> System.out.println(ANSI.RED + "Invalid choice! Please select a valid option." + ANSI.RESET);
            }

            // Jeda sebelum kembali ke menu
            System.out.println(ANSI.YELLOW + "\nPress Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
        }
    }
}
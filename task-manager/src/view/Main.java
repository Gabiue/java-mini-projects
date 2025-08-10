package view;

import model.Task;
import model.TaskStatus;
import service.TaskService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final TaskService service = new TaskService();
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String option = read("Choose an option: ").trim();

            switch (option) {
                case "1" -> addTaskFlow();
                case "2" -> listTasksFlow();
                case "3" -> searchByTitleFlow();
                case "4" -> updateStatusFlow();
                case "5" -> removeTaskFlow();
                case "6" -> renameTaskFlow();           // bônus: editar título
                case "7" -> updateDescriptionFlow();    // bônus: editar descrição
                case "0" -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
            System.out.println(); // espaçamento
        }
    }

    // -------------------- MENU --------------------

    private static void printMenu() {
        System.out.println("""
                ===== KaueTasks =====
                1) Add task
                2) List tasks
                3) Find by title
                4) Update status
                5) Remove by title
                6) Rename task      (optional)
                7) Update description (optional)
                0) Exit
                """);
    }

    // -------------------- FLOWS --------------------

    private static void addTaskFlow() {
        String title = readNonEmpty("Title: ");
        String desc  = read("Description (optional): ");

        boolean ok = service.addTask(title, desc);
        System.out.println(ok ? "Task created." : "Could not create task (empty or duplicate title).");
    }

    private static void listTasksFlow() {
        List<Task> list = service.listTasks();
        if (list.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }
        int i = 1;
        for (Task t : list) {
            System.out.printf("%d) %s%n", i++, formatTaskLine(t));
        }
    }

    private static void searchByTitleFlow() {
        String title = readNonEmpty("Title to search: ");
        Task t = service.findByTitle(title);
        if (t == null) {
            System.out.println("Task not found.");
        } else {
            printTaskDetails(t);
        }
    }

    private static void updateStatusFlow() {
        String title = readNonEmpty("Title to update: ");
        Task t = service.findByTitle(title);
        if (t == null) {
            System.out.println("Task not found.");
            return;
        }
        TaskStatus ns = chooseStatus();
        boolean ok = service.updateStatus(title, ns);
        System.out.println(ok ? "Status updated." : "Could not update status.");
    }

    private static void removeTaskFlow() {
        String title = readNonEmpty("Title to remove: ");
        boolean ok = service.removeByTitle(title);
        System.out.println(ok ? "Task removed." : "Task not found.");
    }

    private static void renameTaskFlow() {
        String oldTitle = readNonEmpty("Current title: ");
        String newTitle = readNonEmpty("New title: ");
        boolean ok = service.renameTask(oldTitle, newTitle);
        System.out.println(ok ? "Task renamed." :
                "Could not rename (old not found, new empty, or new duplicates existing).");
    }

    private static void updateDescriptionFlow() {
        String title = readNonEmpty("Title to edit: ");
        Task t = service.findByTitle(title);
        if (t == null) {
            System.out.println("Task not found.");
            return;
        }
        String newDesc = read("New description (blank = empty): ");
        boolean ok = service.updateDescription(title, newDesc);
        System.out.println(ok ? "Description updated." : "Could not update description.");
    }

    // -------------------- HELPERS --------------------

    private static String read(String prompt) {
        System.out.print(prompt);
        return in.nextLine();
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            String s = read(prompt);
            if (s != null && !s.trim().isEmpty()) return s.trim();
            System.out.println("Value cannot be empty.");
        }
    }

    private static TaskStatus chooseStatus() {
        while (true) {
            System.out.println("""
                    Choose status:
                    1) PENDING
                    2) IN_PROGRESS
                    3) COMPLETED
                    """);
            String opt = read("Option: ").trim();
            switch (opt) {
                case "1": return TaskStatus.PENDING;
                case "2": return TaskStatus.IN_PROGRESS;
                case "3": return TaskStatus.COMPLETED;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static String formatTaskLine(Task t) {
        return "%s [%s]".formatted(t.getTaskName(), t.getStatus());
    }

    private static void printTaskDetails(Task t) {
        System.out.println("---- Task ----");
        System.out.println("Title: " + t.getTaskName());
        System.out.println("Status: " + t.getStatus());
        String d = t.getTaskDescription();
        System.out.println("Description: " + (d == null || d.isBlank() ? "(none)" : d));
    }
}

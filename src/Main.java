import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            scanner.nextLine();
                            inputTask(scanner);
                            break;
                        case 2:
                            scanner.nextLine();
                            deleteTask(scanner);
                            break;
                        case 3:
                            scanner.nextLine();
                            getTasksForDate(scanner);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }
    private static void printMenu() {
        System.out.println("Меню программы:");
        System.out.println("1. Добавить задачу");
        System.out.println("2. Удалить задачу");
        System.out.println("3. Получить задачу на указанный день");
        System.out.println("0. Выход");
    }

    // добавить Задачу
    private static void inputTask(Scanner scanner) {
        System.out.println();
        System.out.print("Введите название задачи: ");
        String taskName = scanner.nextLine();
        System.out.println();
        System.out.print("Введите описание задачи: ");
        String taskDescription = scanner.nextLine();
        System.out.println();
        Task.TaskType taskType = getType(scanner);
        System.out.println();
        System.out.println("Cрок задачи: ");
        LocalDateTime deadLine = getDateTime(scanner);
        System.out.println();
        Task.TaskPeriods repeatability = getRepeatability(scanner);

        Task newTask = new Task(taskName, taskDescription, taskType, deadLine, repeatability);
        Task.listTasks.add(newTask);
        System.out.println("**ЗАДАЧА ЗАПИСАНА В ЕЖЕДНЕВНИК!**");
        System.out.println("   " + newTask);
        System.out.println();
    }
    private static int cheakEnter(Scanner scanner) {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            scanner.next();
            return -1;
        }
    }
    private static Task.TaskType getType(Scanner scanner) {
        System.out.println("Типы задач:");
        System.out.println("1. Личная задача");
        System.out.println("2. Рабочая задача");
        System.out.print("Выберите тип задачи: ");
        if (scanner.hasNextInt()) {
            int menu = scanner.nextInt();
            scanner.nextLine();
            switch (menu) {
                case 1:
                    return Task.TaskType.PERSONAL;
                case 2:
                    return Task.TaskType.WORKING;
            }
        }
        scanner.next();
        System.out.println("**ВЫ ВВЕЛИ НЕ ВЕРНЫЕ ДАННЫЕ!**");
        return getType(scanner);
    }
    private static Task.TaskPeriods getRepeatability(Scanner scanner) {
        System.out.println("Переодичности задач:");
        System.out.println("1. однократная");
        System.out.println("2. ежедневная");
        System.out.println("3. еженедельная");
        System.out.println("4. ежемесячная");
        System.out.println("5. ежегодная");
        System.out.print("Выберите периодичность задачи: ");
        if (scanner.hasNextInt()) {
            int menu = scanner.nextInt();
            switch (menu) {
                case 1:
                    return Task.TaskPeriods.SINGLE;
                case 2:
                    return Task.TaskPeriods.DAILY;
                case 3:
                    return Task.TaskPeriods.WEEKLY;
                case 4:
                    return Task.TaskPeriods.MONTHLY;
                case 5:
                    return Task.TaskPeriods.YEARLY;
            }
        }
        scanner.next();
        System.out.println("**ВЫ ВВЕЛИ НЕ ВЕРНЫЕ ДАННЫЕ!**");
        return getRepeatability(scanner);
    }

    // получить Задачи по дате
    private static void getTasksForDate(Scanner scanner) {
        System.out.println();
        LocalDateTime dateTimeTasks = LocalDateTime.of(getDate(scanner), LocalTime.of(0,0));

        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Задания на дату: " + dateTimeTasks.format(formatDate));
        for (Task task : Task.listTasks)
        {
            if (task.getDeadline().isAfter(dateTimeTasks)
                    && task.getDeadline().isBefore(dateTimeTasks.plusDays(1))) {
                System.out.println(" " + task.toString());
            }
        }
        System.out.println();
        scanner.nextLine();
    }

    // удалить Задачу по id
    private static void deleteTask(Scanner scanner) {
        System.out.println();
        System.out.print("Введите id задачи: ");
        int idTask = cheakEnter(scanner);

        for (Task task : Task.listTasks)
        {
            if (task.getId() == idTask) {
                if (Task.listTasks.remove(task)) {
                    System.out.println(" УДАЛЕНА " + task.toString());
                } else {
                    System.out.println(" ЗАДАЧА НЕ УДАЛЕНА, ОШИБКА!");
                }
                System.out.println();
                return;
            }
        }
    }

    // сервисные методы
    private static LocalDate getDate(Scanner scanner) {
//        LocalDateTime deadline;
//        System.out.print("Введите год: ");
//        int year = cheakEnter(scanner);
//        System.out.print("Введите месяц: ");
//        int mounth = cheakEnter(scanner);
//        System.out.print("Введите день: ");
//        int day = cheakEnter(scanner);
//        try {
//            return LocalDateTime.of(year, mounth, day, 0, 0, 0);
//        } catch (DateTimeException e) {
//            System.out.println("**ВЫ ВВЕЛИ НЕ ВЕРНЫЕ ДАННЫЕ!**");
//            return getDate(scanner);
//        }
        System.out.print("Введите дату в формате *dd-MM-yyyy*: ");
        try {
            return LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("**ВЫ ВВЕЛИ НЕ ВЕРНЫЕ ДАННЫЕ!**");
            return getDate(scanner);
        }
    }
    private static LocalDateTime getDateTime(Scanner scanner) {
//        LocalDateTime deadline;
//        System.out.print("Введите год: ");
//        int year = cheakEnter(scanner);
//        System.out.print("Введите месяц: ");
//        int mounth = cheakEnter(scanner);
//        System.out.print("Введите день: ");
//        int day = cheakEnter(scanner);
//        System.out.print("Введите час: ");
//        int hour = cheakEnter(scanner);
//        System.out.print("Введите минуты: ");
//        int minutes = cheakEnter(scanner);
//        try {
//            return LocalDateTime.of(year, mounth, day, hour, minutes, 0);
//        } catch (DateTimeException e) {
//            System.out.println("**ВЫ ВВЕЛИ НЕ ВЕРНЫЕ ДАННЫЕ!**");
//            return getDateTime(scanner);
//        }
        System.out.print("Введите дату в формате *dd-MM-yyyy HH-mm*: ");
        try {
            return LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm"));
        } catch (DateTimeParseException e) {
            System.out.println("**ВЫ ВВЕЛИ НЕ ВЕРНЫЕ ДАННЫЕ!**");
            return getDateTime(scanner);
        }
    }
}


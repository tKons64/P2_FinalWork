import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
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
                        case 4:
                            scanner.nextLine();
                            getDeletedTasks(scanner);
                            break;
                        case 5:
                            scanner.nextLine();
                            editTask(scanner);
                            break;
                        case 6:
                            scanner.nextLine();
                            getTasksGroupedByDates(scanner);
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
        System.out.println("4. Получить удаленные задачи");
        System.out.println("5. Отредактировать задачу");
        System.out.println("6. Сгрупировать задачаи по датам");
        System.out.println("0. Выход");
    }

    // БАЗОВЫЙ УРОВЕНЬ
    // добавить Задачу
    public static void inputTask(Scanner scanner) {
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
    public static int cheakEnter(Scanner scanner) {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            scanner.next();
            return -1;
        }
    }
    public static Task.TaskType getType(Scanner scanner) {
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
    public static Task.TaskPeriods getRepeatability(Scanner scanner) {
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
    public static void getTasksForDate(Scanner scanner) {
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
        System.out.println("Нажмите Enter чтобы вернуться к меню");
        scanner.nextLine();
    }

    // удалить Задачу по id
    public static void deleteTask(Scanner scanner) {
        System.out.println();
        System.out.print("Введите id задачи: ");
        int idTask = cheakEnter(scanner);

        for (Task task : Task.listTasks)
        {
            if (task.getId() == idTask) {

                if (Task.listTasks.remove(task)) {
                    System.out.println(" УДАЛЕНА " + task.toString());
                    Task.archiveTasks.add(task);
                } else {
                    System.out.println(" ЗАДАЧА НЕ УДАЛЕНА, ОШИБКА!");
                }
                System.out.println();
                System.out.println("Нажмите Enter чтобы вернуться к меню");
                return;
            }
        }
    }

    // СРЕДНИЙ УРОВЕНЬ
    public static void getDeletedTasks(Scanner scanner) {
        System.out.println();
        System.out.println("Удаленные задачи:");
        for (Task task : Task.archiveTasks)
        {
            System.out.println(" " + task.toString());
        }
        System.out.println();
        System.out.println("Нажмите Enter чтобы вернуться к меню");
        scanner.nextLine();
    }

    public static void editTask(Scanner scanner) {
        System.out.println();
        System.out.print("Введите id задачи: ");
        int idTask = cheakEnter(scanner);

        for (Task task : Task.listTasks)
        {
            if (task.getId() == idTask) {
                System.out.println();
                scanner.nextLine();
                System.out.print("Введите новое название задачи: ");
                String newTaskName = scanner.nextLine();
                task.setTitle(newTaskName);
                System.out.print("Введите новое описание задачи: ");
                String newTaskDescription = scanner.nextLine();
                task.setDescription(newTaskDescription);

                System.out.println("**ЗАДАЧА ОТРЕДАКТИРОВАНА!**");
                System.out.println("   " + task);
                return;
            }
        }
        System.out.println("**ЗАДАЧА C ID = " + idTask + " НЕ НАЙДЕНА!**");
        System.out.println();
    }

    public static void getTasksGroupedByDates(Scanner scanner) {

        List<LocalDate> arrayOfDates = new ArrayList<>();
        LocalDate groupingDate;
        LocalDateTime dateTime;
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Task task : Task.listTasks)
        {
            dateTime = task.getDeadline();
            groupingDate = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
            if (!arrayOfDates.contains(groupingDate)) {
                arrayOfDates.add(groupingDate);
            }
        }

        System.out.println();

        for (LocalDate date: arrayOfDates)
        {
            System.out.println("Задания на дату: " + date.format(formatDate));
            dateTime = LocalDateTime.of(date, LocalTime.of(0,0));
            for (Task task : Task.listTasks)
            {
                if (task.getDeadline().isAfter(dateTime)
                        && task.getDeadline().isBefore(dateTime.plusDays(1))) {
                    System.out.println(" " + task.toString());
                }
            }
        }

        System.out.println();
        System.out.println("Нажмите Enter чтобы вернуться к меню");
        scanner.nextLine();
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


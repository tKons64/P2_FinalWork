import java.time.LocalDateTime;
import java.util.HashSet;

public class Task {

    public enum TaskType{

        PERSONAL("личная задача"),
        WORKING ("рабочая задача");
        private String description;

        TaskType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

    }
    public enum TaskPeriods{

        SINGLE("однократная"),
        DAILY("ежедневная"),
        WEEKLY("еженедельная"),
        MONTHLY("ежемесячная"),
        YEARLY("ежегодная");
        private String description;

        TaskPeriods(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public LocalDateTime getNextDeadline(LocalDateTime currentDeadline) {
            switch (this) {
                case SINGLE:
                    return currentDeadline;
                case DAILY:
                    return currentDeadline.plusDays(1);
                case WEEKLY:
                    return currentDeadline.plusWeeks(1);
                case MONTHLY:
                    return currentDeadline.plusMonths(1);
                case YEARLY:
                    return currentDeadline.plusYears(1);
            }
            return currentDeadline;
        }
    }

    static HashSet<Task> listTasks = new HashSet<>();

    static HashSet<Task> archiveTasks = new HashSet<>();
    private int id;
    private String title;
    private String description;
    private TaskType type;
    private LocalDateTime deadline;
    private TaskPeriods repeatability;
    private static int count = 0;

    public Task(String title, String description, TaskType type, LocalDateTime deadline, TaskPeriods repeatability) {

        if (!cheakStringParametr(title)) {
            throw new IllegalArgumentException("Незаполнено, или заполнено не верно, название задачи!");
        } else if (!cheakStringParametr(description)) {
            throw new IllegalArgumentException("Незаполнено, или заполнено не верно, описание задачи!");
        } else if (repeatability == null) {
            throw new IllegalArgumentException("Незаполнена пповторяемость задачи!");
        }

        this.id = count++;
        this.title = title;
        this.description = description;
        this.type = type;
        this.deadline = deadline;
        this.repeatability = repeatability;
        }

    private boolean cheakStringParametr(String prt) {
        if (prt == null || prt.isEmpty() || prt.isBlank()) {
            return false;
        } else {
            return true;
        }
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Задача - *" + title + "*" +
                ", id: " + id +
                ", описание: " + description +
                ", тип: " + type.getDescription() +
                ", срок: " + deadline +
                ", повторяемость: " + repeatability.getDescription();
    }
}

import java.util.Objects;

public class Task {
    protected Integer index;
    protected String name;
    protected String description;
    protected TaskStatus status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    public Task(String name, String description, TaskStatus status) {
        this(name, description);
        this.status = status;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return Objects.equals(index, task.index) && Objects.equals(name, task.name) &&
            Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public String toString() {
        return String.format("%s; %s; %s", getName(), getDescription(), getStatus());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (index == null ? 0 : index);
        hash = 31 * hash + (name == null ? 0 : name.hashCode());
        hash = 31 * hash + (description == null ? 0 : description.hashCode());
        return hash;
    }
}

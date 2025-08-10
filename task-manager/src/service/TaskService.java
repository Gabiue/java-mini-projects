package service;

import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TaskService {
    private final Map<String, Task> taskByTitle = new LinkedHashMap<>();

    //Helpers 
    private String keyOf(String title){
        if ( title == null) return "";
        return title.trim().toLowerCase();
    }

    // CREATE

    public boolean addTask(String taskName, String taskDescription){
        String key = keyOf(taskName);
        if (key.isEmpty()) return false;
        if (taskByTitle.containsKey(key)) return false; 

        String desc = (taskDescription == null) ? "" : taskDescription.trim();
        Task task = new Task(taskName.trim(), desc, TaskStatus.PENDING);
        taskByTitle.put(key, task);
        return true;
    }

    // READ

    public List<Task> listTasks(){
        return new ArrayList<>(taskByTitle.values());
    }

    public Task findByTitle(String title){
        return taskByTitle.get(keyOf(title));
    }

    // Update 

    public boolean updateStatus(String title, TaskStatus newStatus){
        Task t = findByTitle(title);
        if ( t == null) return false;
        t.setStatus(newStatus);
        return true;
    }
        public boolean updateDescription(String title, String newDescription) {
        Task t = findByTitle(title);
        if (t == null) return false;
        t.setTaskDescription(newDescription == null ? "" : newDescription.trim());
        return true;
    }

    public boolean renameTask(String oldTitle, String newTitle){
        String oldKey = keyOf(oldTitle);
        String newKey = keyOf(newTitle);
        

        if (!taskByTitle.containsKey(oldKey)) return false;
        if (newKey.isEmpty() || taskByTitle.containsKey(newKey)) return false;

        Task t = taskByTitle.remove(oldKey);
        t.setTaskName(newTitle.trim());
        taskByTitle.put(newKey, t);
        return true;
    }

    // DELETE 

    public boolean removeByTitle(String title){
        return taskByTitle.remove(keyOf(title)) != null;
    }

    public int count(){
        return taskByTitle.size();
    }
}

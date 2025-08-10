package model;

public class Task {

    private String taskName;
    private String taskDescription;
    private TaskStatus status;

    //Construtor
    public Task(String taskName, String taskDescription, TaskStatus status){
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
    }
    
    // GETTERS 
    public String getTaskName(){
        return taskName;
    }

    public String getTaskDescription(){
        return taskDescription;
    }

    public TaskStatus getStatus(){
        return status;
    }

    //SETTERS 

    public void setTaskName(String taskName){
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription){
        this.taskDescription = taskDescription;
    }

    public void setStatus(TaskStatus status){
        this.status = status;
    }

    @Override
    public String toString(){
        return "📌 Tarefa: " + taskName +
           "\n📝 Descrição: " + (taskDescription.isEmpty() ? "(sem descrição)" : taskDescription) +
           "\n📊 Status: " + status; 
    }
}

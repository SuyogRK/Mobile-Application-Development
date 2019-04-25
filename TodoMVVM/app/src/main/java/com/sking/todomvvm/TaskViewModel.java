package com.sking.todomvvm;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;
    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    public void insert(Task task){
        repository.insert(task);
    }
    public void update(Task task){
        repository.update(task);
    }

    public void delete(Task task){

        repository.delete(task);
    }

    public void deleteAllTasks(){

        repository.deleteAllTasks();
    }

    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }


    public void archive(Task task) {
        repository.archive(task);
    }

    public void archiveAllTasks() {
        repository.archiveAllTasks();
    }

    public void completeAllTasks() {
        repository.completeAllTasks();
    }

    public void markAllTasksIncomplete() {
        repository.markAllTasksIncomplete();
    }

    public void bringAllFromArchive() {
        repository.bringAllFromArchive();
    }

    public void complete(Task task) {
        repository.complete(task);
    }

    public void deleteAllArchived() {
        repository.deleteAllArchived();
    }
}

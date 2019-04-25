package com.sking.todomvvm;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application){

        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();


    }

    public void insert (Task task){

        new InsertTaskAsyncTask(taskDao).execute(task);
    }

    public void update(Task task){

        new UpdateTaskAsyncTask(taskDao).execute(task);
    }

    public void delete(Task task){

        new DeleteTaskAsyncTask(taskDao).execute(task);

    }

    public void deleteAllTasks(){

        new DeleteAllTasksAsyncTask(taskDao).execute();

    }

    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }

    public void archive(Task task) {
        new ArchiveTaskAsyncTask(taskDao).execute(task);
    }

    public void archiveAllTasks() {
        new ArchiveAllTasksAsyncTask(taskDao).execute();
    }

    public void completeAllTasks() {
        new CompleteAllTasksAsyncTask(taskDao).execute();
    }

    public void markAllTasksIncomplete() {
        new MarkAllTasksIncompleteAsyncTask(taskDao).execute();
    }

    public void bringAllFromArchive() {
        new BringAllFromArchiveAsyncTask(taskDao).execute();
    }

    public void complete(Task task) {
        new CompleteTaskAsyncTask(taskDao).execute(task);
    }

    public void deleteAllArchived() {
        new DeleteAllArchivedAsyncTask(taskDao).execute();
    }



    private static class CompleteTaskAsyncTask extends AsyncTask<Task, Void,Void>{

        private TaskDao taskDao;

        private CompleteTaskAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }


        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.complete(tasks[0].getId());
            return null;
        }
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void,Void>{

        private TaskDao taskDao;

        private InsertTaskAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }


        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    private static class ArchiveTaskAsyncTask extends AsyncTask<Task, Void,Void>{

        private TaskDao taskDao;

        private ArchiveTaskAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }


        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.archive(tasks[0].getId());
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void,Void>{

        private TaskDao taskDao;

        private UpdateTaskAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }


        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void,Void>{

        private TaskDao taskDao;

        private DeleteTaskAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }


        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }

    private static class DeleteAllTasksAsyncTask extends AsyncTask<Void, Void,Void>{

        private TaskDao taskDao;

        private DeleteAllTasksAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteAllTasks();
            return null;
        }
    }

    private static class ArchiveAllTasksAsyncTask extends AsyncTask<Void, Void,Void>{

        private TaskDao taskDao;

        private ArchiveAllTasksAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.archiveAllTasks();
            return null;
        }
    }
    private static class CompleteAllTasksAsyncTask extends AsyncTask<Void, Void,Void>{

        private TaskDao taskDao;

        private CompleteAllTasksAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.completeAllTasks();
            return null;
        }
    }

    private static class MarkAllTasksIncompleteAsyncTask extends AsyncTask<Void, Void,Void>{

        private TaskDao taskDao;

        private MarkAllTasksIncompleteAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.markAllTasksIncomplete();
            return null;
        }
    }

    private static class BringAllFromArchiveAsyncTask extends AsyncTask<Void, Void,Void>{

        private TaskDao taskDao;

        private BringAllFromArchiveAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.bringAllFromArchive();
            return null;
        }
    }

    private static class DeleteAllArchivedAsyncTask extends AsyncTask<Void, Void,Void>{

        private TaskDao taskDao;

        private DeleteAllArchivedAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteAllArchived();
            return null;
        }
    }


}

package com.sking.todomvvm;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM task_table")
    void deleteAllTasks();

    @Query("SELECT * FROM task_table WHERE is_archived = 0 ORDER BY priority DESC")
    LiveData<List<Task>> getAllTasks();

    @Query("UPDATE task_table SET is_archived = 1 WHERE id =:id ")
    void archive(int id);

    @Query("UPDATE task_table SET is_archived =1")
    void archiveAllTasks();

    @Query("UPDATE task_table set is_completed=1 WHERE is_archived=0")
    void completeAllTasks();

    @Query("UPDATE task_table set is_completed=0 WHERE is_archived=0")
    void markAllTasksIncomplete();

    @Query("UPDATE task_table set is_archived=0")
    void bringAllFromArchive();

    @Query(("UPDATE task_table set is_completed=1 where id =:id"))
    void complete(int id);

    @Query("DELETE from task_table WHERE is_archived=1")
    void deleteAllArchived();
}

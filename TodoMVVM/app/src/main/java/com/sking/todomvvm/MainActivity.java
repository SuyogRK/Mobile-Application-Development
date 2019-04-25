package com.sking.todomvvm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_TASK_REQUEST =1;
    public static final int EDIT_TASK_REQUEST =2;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton buttonAddTask = findViewById(R.id.button_add_task);
        buttonAddTask.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);


        final TaskAdapter adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                //update recyclerview
               // Toast.makeText(MainActivity.this,"unchanged",Toast.LENGTH_SHORT).show();
           adapter.setTasks(tasks);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                taskViewModel.archive(adapter.getTaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Task archive", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Get RecyclerView item from the ViewHolder
                    View itemView = viewHolder.itemView;

                    Paint p = new Paint();
                    if (dX > 0) {
                        /* Set your color for positive displacement */
                        p.setColor(Color.RED);

                        // Draw Rect with varying right side, equal to displacement dX
                        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                                (float) itemView.getBottom(), p);
                    } else {
                        /* Set your color for negative displacement */
                        p.setColor(Color.BLUE);

                        // Draw Rect with varying left side, equal to the item's right side plus negative displacement dX
                        c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                (float) itemView.getRight(), (float) itemView.getBottom(), p);
                    }

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }

        }).attachToRecyclerView(recyclerView);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                taskViewModel.complete(adapter.getTaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Task completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Get RecyclerView item from the ViewHolder
                    View itemView = viewHolder.itemView;

                    Paint p = new Paint();
                    if (dX > 0) {
                        /* Set your color for positive displacement */
                        p.setColor(Color.GREEN);

                        // Draw Rect with varying right side, equal to displacement dX
                        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                                (float) itemView.getBottom(), p);
                    } else {
                        /* Set your color for negative displacement */
                        p.setColor(Color.BLUE);

                        // Draw Rect with varying left side, equal to the item's right side plus negative displacement dX
                        c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                (float) itemView.getRight(), (float) itemView.getBottom(), p);
                    }

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);

                Log.d("task id" , Integer.toString(task.getId()));

                intent.putExtra(AddEditTaskActivity.EXTRA_ID, task.getId());
                intent.putExtra(AddEditTaskActivity.EXTRA_TITLE, task.getTitle());
                intent.putExtra(AddEditTaskActivity.EXTRA_DESCRIPTION, task.getDescription());
                intent.putExtra(AddEditTaskActivity.EXTRA_PRIORITY, task.getPriority());
                intent.putExtra(AddEditTaskActivity.EXTRA_DUEDATE,task.getDuedate());

                Log.d("This intent" , String.valueOf(intent.getExtras()));
                startActivityForResult(intent, EDIT_TASK_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_TASK_REQUEST && resultCode==RESULT_OK){
            String title = data.getStringExtra(AddEditTaskActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTaskActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTaskActivity.EXTRA_PRIORITY,1);
            String dueDate = data.getStringExtra(AddEditTaskActivity.EXTRA_DUEDATE);

            Task task = new Task(title,description,priority,dueDate);
            taskViewModel.insert(task);

         Toast.makeText(this,"Task saved",Toast.LENGTH_SHORT).show();


        }
        else if (requestCode == EDIT_TASK_REQUEST && resultCode==RESULT_OK) {

        int id = data.getIntExtra(AddEditTaskActivity.EXTRA_ID, -1);




            Log.d("Intent task id" , data.getExtras().toString());
        if(id == -1){
            Toast.makeText(this,"Task can't be updated",Toast.LENGTH_SHORT).show();
            return;
        }

            String title = data.getStringExtra(AddEditTaskActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTaskActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTaskActivity.EXTRA_PRIORITY,1);
            String dueDate = data.getStringExtra(AddEditTaskActivity.EXTRA_DUEDATE);

            Task task = new Task(title,description,priority,dueDate);
            task.setId(id);
            taskViewModel.update(task);

            Toast.makeText(this,"Task updated",Toast.LENGTH_SHORT).show();
        }

        else{

            Toast.makeText(this,"Task not saved",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch(item.getItemId()){

          case R.id.archive_all_tasks:
              taskViewModel.archiveAllTasks();

              Toast.makeText(this, "All tasks archived",Toast.LENGTH_SHORT).show();
              return true;

          case R.id.complete_all_tasks:
              taskViewModel.completeAllTasks();
                  Toast.makeText(this, "All tasks completed",Toast.LENGTH_SHORT).show();
              return true;

          case R.id.mark_all_incomplete:
              taskViewModel.markAllTasksIncomplete();
              Toast.makeText(this, "All tasks marked incomplete",Toast.LENGTH_SHORT).show();
              return true;

          case R.id.bring_all_from_archive:
              taskViewModel.bringAllFromArchive();
              Toast.makeText(this, "Brought all back from archive",Toast.LENGTH_SHORT).show();
              return true;


          case R.id.delete_all_archived:
              taskViewModel.deleteAllArchived();
              Toast.makeText(this,"Deleted all from archive",Toast.LENGTH_SHORT).show();
              default:
                  return super.onOptionsItemSelected(item);

      }


    }
}

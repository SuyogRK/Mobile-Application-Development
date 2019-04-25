package com.sking.todomvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_ID =
            "com.sking.todomvvm.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.sking.todomvvm.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.sking.todomvvm.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.sking.todomvvm.EXTRA_PRIORITY";
    public static final String EXTRA_DUEDATE ="com.sking.todomvvm.EXTRA_DUEDATE";
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editDueDate;
    private NumberPicker numberPickerPriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_add_task);


        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editDueDate=findViewById(R.id.edit_due_date);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMaxValue(10);
        numberPickerPriority.setMinValue(1);



        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        if(intent.hasExtra(EXTRA_ID)){

            setTitle("Edit Task");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
            editDueDate.setText(intent.getStringExtra(EXTRA_DUEDATE));

        }
        else {
            setTitle("Add Task");
        }

        editDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date d = format1.parse(editDueDate.getText().toString());
                    DialogFragment datePicker = new DatePickerFragment();
                    datePicker.show(getSupportFragmentManager(),"date picker");
                } catch (ParseException e) {
                    DialogFragment datePicker = new DatePickerFragment();
                    datePicker.show(getSupportFragmentManager(),"date picker");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_task_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save_task:
                saveTask();
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }
    }

    private void saveTask() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();
        String dueDate = editDueDate.getText().toString();


        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title and description" , Toast.LENGTH_SHORT);
            return;
        }

        Intent data = new Intent();

        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);
        data.putExtra(EXTRA_DUEDATE,dueDate);


        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if(id != -1){

            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK,data);
        finish();
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c =Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        editDueDate.setText(format1.format(c.getTime()));






    }


    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }

    }


}
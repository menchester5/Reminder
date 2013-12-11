package com.example.reminder;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.reminder.dao.TaskDAO;
import com.example.reminder.entity.TaskEntity;

public class MainActivity extends Activity {
	
	private ImageButton addReminder;
	private ListView taskList;
	private TaskDAO taskDAO;
	private OnClickListener addReminderListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(getApplicationContext(), AddReminderActivity.class));
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskList = (ListView)findViewById(R.id.taskList);
        addReminder = (ImageButton)findViewById(R.id.addReminder);
        addReminder.setOnClickListener(addReminderListener);    
        
       
        
        taskDAO = new TaskDAO(this);
        taskDAO.open();
        
        List<TaskEntity> values = taskDAO.getAllTasks();
        Log.e(STORAGE_SERVICE, Integer.toString(values.size()));
        
        ArrayAdapter<TaskEntity> adapter = new ArrayAdapter<TaskEntity>(this, android.R.layout.simple_list_item_checked, values);
        taskList.setAdapter(adapter);
        
        
        
    }
      

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		taskDAO.close();
		super.onDestroy();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		taskDAO.close();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		taskDAO.open();
	}
    
}
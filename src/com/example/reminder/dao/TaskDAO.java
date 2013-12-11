package com.example.reminder.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.reminder.DataBaseHelper;
import com.example.reminder.entity.TaskEntity;

public class TaskDAO {

	private static final String TASK_NAME_COLUMN = "TASK_NAME";
	private static final String TASK_STATUS_COLUMN = "TASK_STATUS";
	private static final String TASK_DUE_DATE_COLUMN = "TASK_DUE_DATE";
	private static final String TASK_GEO_COLUMN = "TASK_GEO";
	private static final String TASK_ID_COLUMN = "_ID";
	private static final String TASK_TABLE = "TASK_T";

	private static final String[] ALL_COLUMNS = {TASK_ID_COLUMN, TASK_NAME_COLUMN,
			TASK_STATUS_COLUMN, TASK_DUE_DATE_COLUMN, TASK_GEO_COLUMN };

	private DataBaseHelper dbHelper;
	private SQLiteDatabase db;

	public TaskDAO(Context context) {
		this.dbHelper = new DataBaseHelper(context);

	}

	public void open() throws SQLException {
		this.db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public long save(TaskEntity task) {
		ContentValues values = new ContentValues();
		values.put(TASK_NAME_COLUMN, task.getTaskName());
		values.put(TASK_DUE_DATE_COLUMN, task.getTaskDueDate());
		if (task.getTaskStatus() == null) {
			values.put(TASK_STATUS_COLUMN, "NOTDONE");
		} else {
			values.put(TASK_STATUS_COLUMN, task.getTaskStatus());
		}
		values.put(TASK_GEO_COLUMN, task.getTaskGeo());
		return db.insert(TASK_TABLE, null, values);
	}

	public List<TaskEntity> getAllTasks() {

		List<TaskEntity> tasks = new ArrayList<TaskEntity>();

		Cursor cursor = db.query(TASK_TABLE, null, null, null, null,
				null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			TaskEntity task = cursorToTaskEntity(cursor);
			tasks.add(task);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return tasks;

	}

	public TaskEntity cursorToTaskEntity(Cursor cursor) {
		TaskEntity task = new TaskEntity();
		task.setTaskName(cursor.getString(0));
		task.setTaskStatus(cursor.getString(1));
		task.setTaskDueDate(cursor.getString(2));
		task.setTaskGeo(cursor.getString(3));
		task.setId(cursor.getInt(0));

		return task;
	}

}

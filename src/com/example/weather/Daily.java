package com.example.weather;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;

public class Daily extends Activity{
	private TextView dateView;
	//String date = getIntent().getStringExtra("date"); 
    @Override	   
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily);
        final String date= getIntent().getStringExtra("date"); 
        dateView =(TextView) this.findViewById(R.id.date);
        dateView.setText(date);
        final EditText content = (EditText)this.findViewById(R.id.content);
        //�򿪻򴴽�test.db���ݿ�  
        final SQLiteDatabase db = openOrCreateDatabase("weather.db", Context.MODE_PRIVATE, null);
        //db.execSQL("INSERT INTO daily VALUES (NULL, '"+date+"', '�������㿪��')");  
        
        Cursor cursor = db.rawQuery("select * from daily where timestamp='"+date+"'", null);
        while (cursor.moveToNext()) {
        String res = cursor.getString(2);//��ȡ�ڶ��е�ֵ
        	Log.e("content",res);
        	content.setText(res);
        }
        //cursor.close();
        
        Button update = (Button)this.findViewById(R.id.update_button);
        update.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				db.execSQL("INSERT INTO daily VALUES (NULL, '"+date+"', '"+content.getText().toString()+"')");
			}
        	
        });
        //ContentValues cv = new ContentValues();  
        //cv.put("content", "kaihui");  
        //db.update("person", cv, "name = ?", new String[]{"john"});  
        /*db.execSQL("DROP TABLE IF EXISTS daily");  
        //����person��  
        db.execSQL("CREATE TABLE daily (_id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp DATE, content VARCHAR)");  
        
        //��������  
        db.execSQL("INSERT INTO daily VALUES (NULL, '"+date+"', '�������㿪��')");  
          
        person.name = "david";  
        person.age = 33;  
        //ContentValues�Լ�ֵ�Ե���ʽ�������  
        ContentValues cv = new ContentValues();  
        cv.put("name", person.name);  
        cv.put("age", person.age);  
        //����ContentValues�е�����  
        db.insert("person", null, cv);  
          
        cv = new ContentValues();  
        cv.put("age", 35);  
        //��������  
        db.update("person", cv, "name = ?", new String[]{"john"});  
          
        Cursor c = db.rawQuery("SELECT * FROM person WHERE age >= ?", new String[]{"33"});  
        while (c.moveToNext()) {  
            int _id = c.getInt(c.getColumnIndex("_id"));  
            String name = c.getString(c.getColumnIndex("name"));  
            int age = c.getInt(c.getColumnIndex("age"));  
            Log.i("db", "_id=>" + _id + ", name=>" + name + ", age=>" + age);  
        }  
        c.close();  
          
        //ɾ������  
        db.delete("person", "age < ?", new String[]{"35"});  
          
        //�رյ�ǰ���ݿ�  
*/      //   db.close();  
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id){
			case R.id.weather:
				startActivity(new Intent(Daily.this,MainActivity.class));
				break;
			case R.id.calendar: 
				startActivity(new Intent(Daily.this,CalendarActivity.class));
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}

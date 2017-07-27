package com.example.weather;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class CalendarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        /*DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
        datePicker.init(2017, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                Log.i("date", i+" "+i1+" "+i2);
            }
        });*/
        
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView1);
        Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH)+1;
        
        int day=cal.get(Calendar.DAY_OF_MONTH);
        Log.i("nian",String.valueOf(year));
        Log.i("nian",String.valueOf(month));
        Log.i("nian",String.valueOf(day));
        
        //String date = "今天是 "+year + "年" + month + "月" + day +"日";
        //Toast.makeText(CalendarActivity.this, date, Toast.LENGTH_LONG).show();

                calendarView.setOnDateChangeListener(new OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        String date = year + "年" + (month+1) + "月" + dayOfMonth +"日";
                        Toast.makeText(CalendarActivity.this, date, Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent (MainActivity.this,RenwuriliActivity.class);
                        Intent intent = new Intent(CalendarActivity.this,Daily.class);
        				intent.putExtra("date", date);  
        				startActivity(intent);
//             startActivity(intent);

                    }
                });
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
				Intent intent = new Intent(CalendarActivity.this,MainActivity.class);
				//intent.putExtra("account", account);  
				startActivity(intent);
				break;
			case R.id.calendar:
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

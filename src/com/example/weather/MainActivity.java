package com.example.weather;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.weather.Weather.ResultsBean;
import com.example.weather.Weather.ResultsBean.DailyBean;
import com.google.gson.Gson;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Spinner sr1;
	private Spinner sr2;
	private TextView textNow;
	private TextView tempNow;
	private TextView day1;
	private TextView temp1;
	private TextView day2;
	private TextView temp2;
	private TextView day3;
	private TextView temp3;
	private Button share;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sr1 = (Spinner) this.findViewById(R.id.spin_province);
		sr2 = (Spinner) this.findViewById(R.id.spin_city);
		day1 =(TextView) this.findViewById(R.id.textView12);
		temp1 =(TextView) this.findViewById(R.id.textView13);
		day2 =(TextView) this.findViewById(R.id.textView22);
		temp2 =(TextView) this.findViewById(R.id.textView23);
		day3 =(TextView) this.findViewById(R.id.textView32);
		temp3 =(TextView) this.findViewById(R.id.textView33);
		textNow = (TextView) this.findViewById(R.id.nowText);
		tempNow = (TextView) this.findViewById(R.id.nowTemperature);
		share = (Button) this.findViewById(R.id.share);
		
		
		final EditText et = new EditText(this);  
		
		final AlertDialog.Builder builder =  new AlertDialog.Builder(MainActivity.this);
		//builder = builder.create();
		builder.setTitle("分享");
		
		builder.setView(et);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String phone = et.getText().toString().trim();
                Log.e("电话",phone);
                Uri uri = Uri.parse("smsto:"+phone);            
				Intent it = new Intent(Intent.ACTION_SENDTO, uri);   
				Spinner city = (Spinner)MainActivity.this.findViewById(R.id.spin_city);
				Object x = city.getSelectedItem();
				
				if (city.getSelectedItem()==null){
					Toast.makeText(MainActivity.this, "请先选择城市", Toast.LENGTH_SHORT).show();
				}else{
					it.putExtra("sms_body",city.getSelectedItem().toString()+"现在"+textNow.getText()+"天气"+tempNow.getText()+"°");            
					startActivity(it); 
				}
			} 
        });
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
            
            }
        });	
		final AlertDialog dialog = builder.create();
		share.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub 
				//builder.create();
				
				dialog.show();
				//alert.show();
				
			}

			/*@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(this).setTitle("请输入号码").setIcon(
							android.R
						)
			}*/
			
		});
		
		
		
		// 添加String数组
		String[] province = getResources().getStringArray(R.array.province);

		// 将数组配置器链接上预置的文件
		// ArrayAdapter<CharSequence> aa = new ArrayAdapter<CharSequence>(this,
		// android.R.layout.simple_spinner_item,province);
		// 这样就可以直接获取xml中的数组了
		ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this,
				R.array.province, android.R.layout.simple_spinner_item);
		// 设置下拉菜单的样式
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将数据绑定到spinner上
		sr1.setAdapter(aa);
		// 添加监听事件
		sr1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// 得到事件中选中的值
				Spinner s = (Spinner) arg0;
				String pro = (String) s.getItemAtPosition(arg2);
				ArrayAdapter<CharSequence> cityAdapter = null;
				// 获取所在省含有哪些市(从资源数组文件中获取数据)
				if (pro.equals("黑龙江")) {
					cityAdapter = ArrayAdapter.createFromResource(
							MainActivity.this, R.array.hlj,
							android.R.layout.simple_spinner_item);
				} else if (pro.equals("辽宁")) {
					cityAdapter = ArrayAdapter.createFromResource(
							MainActivity.this, R.array.ln,
							android.R.layout.simple_spinner_item);
				} 
				sr2.setAdapter(cityAdapter);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
			}

		});

		sr2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Spinner s = (Spinner) arg0;
				String city = (String) s.getItemAtPosition(arg2);
				if (city.equals("-城市-")){
					return;
				}
				Log.i("省份",sr1.getSelectedItem().toString());
				Log.i("城市",city);
				
				String urlRealTime = "https://api.seniverse.com/v3/weather/now.json?key=kuw7pbduduh35zvd&location="+city+"&language=zh-Hans&unit=c";
				OkHttpClient mOkHttpClientRealTime = new OkHttpClient();
				final Request requestRealTime = new Request.Builder()
            	.url(urlRealTime)
            	.build();
				Call callRealTime = mOkHttpClientRealTime.newCall(requestRealTime); 
				callRealTime.enqueue(new Callback(){
					@Override
					public void onFailure(Request request, IOException e){
						e.printStackTrace();
					}

					@Override
					public void onResponse(final Response response) throws IOException{
						try{
							Gson gson = new Gson();
							WeatherRealTime weatherRealTime = gson.fromJson(response.body().string(), WeatherRealTime.class); 
							List<com.example.weather.WeatherRealTime.ResultsBean> results = weatherRealTime.getResults();
							final String textRealTime = results.get(0).getNow().getText();
							final String temperatureRealTime = results.get(0).getNow().getTemperature();
							MainActivity.this.runOnUiThread(new Runnable(){
								@Override
								public void run() {
									textNow.setText(textRealTime);
									tempNow.setText(temperatureRealTime);
								}
								
							});
						} catch (Exception e){	
							 e.printStackTrace();
						}
						String htmlStr =  response.body().string();
						Log.e("res",htmlStr);
					}
				});
				
				String url = "https://api.seniverse.com/v3/weather/daily.json?key=kuw7pbduduh35zvd&location="+city+"&language=zh-Hans&unit=c&start=0&days=3";
				OkHttpClient mOkHttpClient = new OkHttpClient();
				final Request request = new Request.Builder()
                	.url(url)
                	.build();
					//new call
				Call call = mOkHttpClient.newCall(request); 
				//请求加入调度
				call.enqueue(new Callback(){
					@Override
					public void onFailure(Request request, IOException e){
						e.printStackTrace();
					}

					@Override
					public void onResponse(final Response response) throws IOException{
						try{
							Gson gson = new Gson();
							Weather weather = gson.fromJson(response.body().string(), Weather.class); 
							List<ResultsBean> results = weather.getResults();
							final List<DailyBean> daily = results.get(0).getDaily();
							Log.e("high:",daily.get(0).getHigh());
							MainActivity.this.runOnUiThread(new Runnable(){
								@Override
								public void run() {
									// TODO Auto-generated method stub
									day1.setText(daily.get(0).getText_day());
									temp1.setText(daily.get(0).getHigh()+"° / "+daily.get(0).getLow()+"°");
									day2.setText(daily.get(1).getText_day());
									temp2.setText(daily.get(1).getHigh()+"° / "+daily.get(1).getLow()+"°");
									day3.setText(daily.get(2).getText_day());
									temp3.setText(daily.get(2).getHigh()+"° / "+daily.get(2).getLow()+"°");
								}
							});
						} catch (Exception e){	
							 e.printStackTrace();
						}
					}
				});            
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
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
			case R.id.calendar:
				Intent intent = new Intent(MainActivity.this,CalendarActivity.class);
				//intent.putExtra("account", account);  
				startActivity(intent);
				break;
			case R.id.weather:
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/*Handler handler = new Handler() {    
	    @Override    
	    public void handleMessage(Message msg) {    
	        super.handleMessage(msg);    
	        Bundle data = msg.getData();    
	        String val = data.getString("value");    
	        Log.i("mylog", "请求结果为-->" + val);    
	        // UI界面的更新等相关操作    
	    }    
	};    */
	    
	/**  
	 * 网络操作相关的子线程  
	 */    
	/*Runnable networkTask = new Runnable() {        
	    @Override    
	    public void run() {    
	        // 在这里进行 http request.网络请求相关操作    
	    	//MobEventService.postonKillProcess(context); 
	    	String res = MainActivity.getWeather("哈尔滨");
	        Message msg = new Message();    
	        Bundle data = new Bundle();    
	        data.putString("value", res);    
	        msg.setData(data);    
	        handler.sendMessage(msg);    
	    }    
	};    */
	
	/*public static String getWeather(String city){
		try {
			city = URLEncoder.encode(city, "utf-8");
		}catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String url = "https://116.62.81.138/v3/weather/daily.json?key=kuw7pbduduh35zvd&location="+city+"&language=zh-Hans&unit=c&start=0&days=3";
	    String result = "";
	    BufferedReader in = null;
	    try {
	    	URL realUrl = new URL(url);
	        // 打开和URL之间的连接
	        URLConnection connection = realUrl.openConnection();
	        // 设置通用的请求属性  */
	        //connection.setRequestProperty("accept", "*/*");
	       /* connection.setRequestProperty("connection", "Keep-Alive");
	        connection.setRequestProperty("user-agent",
	        		"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	        // 建立实际的连接
	        connection.connect();
	        // 获取所有响应头字段
	        Map<String, List<String>> map = connection.getHeaderFields();
	        // 遍历所有的响应头字段
	        for (String key : map.keySet()) {
	        	System.out.println(key + "--->" + map.get(key));
	        }
	        // 定义 BufferedReader输入流来读取URL的响应
	        in = new BufferedReader(new InputStreamReader(
	        		connection.getInputStream(),"UTF-8"));
	        String line;
	        while ((line = in.readLine()) != null) {
	        	result += line;
	        }
	    } catch (Exception e) {
	    	System.out.println("发送GET请求出现异常！" + e);
	        e.printStackTrace();
	    }
	    // 使用finally块来关闭输入流
	    finally {
	    	try {
	    		if (in != null) {
	    			in.close();
	    		}
	        } catch (Exception e2) {
	        	e2.printStackTrace();
	        }
	    }
	    return result;

	}*/
}

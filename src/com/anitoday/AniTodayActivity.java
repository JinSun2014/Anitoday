package com.anitoday;



import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class AniTodayActivity extends Activity implements OnClickListener,OnGestureListener{
	private static final int[] monthIds = { R.id.tv_month_1_1,
		R.id.tv_month_1_2, R.id.tv_month_1_3, R.id.tv_month_1_4,
		R.id.tv_month_1_5, R.id.tv_month_1_6, R.id.tv_month_1_7,
		R.id.tv_month_2_1, R.id.tv_month_2_2, R.id.tv_month_2_3,
		R.id.tv_month_2_4, R.id.tv_month_2_5, R.id.tv_month_2_6,
		R.id.tv_month_2_7, R.id.tv_month_3_1, R.id.tv_month_3_2,
		R.id.tv_month_3_3, R.id.tv_month_3_4, R.id.tv_month_3_5,
		R.id.tv_month_3_6, R.id.tv_month_3_7, R.id.tv_month_4_1,
		R.id.tv_month_4_2, R.id.tv_month_4_3, R.id.tv_month_4_4,
		R.id.tv_month_4_5, R.id.tv_month_4_6, R.id.tv_month_4_7,
		R.id.tv_month_5_1, R.id.tv_month_5_2, R.id.tv_month_5_3,
		R.id.tv_month_5_4, R.id.tv_month_5_5, R.id.tv_month_5_6,
		R.id.tv_month_5_7, R.id.tv_month_6_1, R.id.tv_month_6_2,
		R.id.tv_month_6_3, R.id.tv_month_6_4, R.id.tv_month_6_5,
		R.id.tv_month_6_6, R.id.tv_month_6_7, };
	private static final int[] weekIds = { R.id.tv_week1, R.id.tv_week2,
		R.id.tv_week3, R.id.tv_week4, R.id.tv_week5, R.id.tv_week6,
		R.id.tv_week7 };
	private TextView calendarTv,yearTv;
	private Button upBtn, nextBtn;
	private int paYear, paMonth, paWeek, paDay, beginWeek, endDay;
	private int wordColor = R.color.word7,weekColor = R.color.week7,
			dayColor = R.color.day7,todayColor = R.color.today7,layoutBackground = R.drawable.background7;
	private Calendar calendar;
	private Typeface tf;
	private	LinearLayout ll;
	private ViewFlipper flipper;
	private GestureDetector detector;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ll = (LinearLayout)findViewById(R.id.linear_layout);
		tf=Typeface.createFromAsset(getAssets(), "fonts/katongti.ttf");
        calendarTv = (TextView) findViewById(R.id.tv_calendar);
        calendarTv.setTypeface(tf);
		yearTv = (TextView) findViewById(R.id.tv_year);
		yearTv.setTypeface(tf);
		upBtn = (Button) findViewById(R.id.btn_up);
		nextBtn = (Button) findViewById(R.id.btn_next);
		calendarTv.setOnClickListener(this);
		yearTv.setOnClickListener(this);
		upBtn.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
		detector = new GestureDetector(this);
		flipper = (ViewFlipper)findViewById(R.id.viewFlipper);		
		calendar=Calendar.getInstance();
		setValues(calendar);
    }
    public void onClick(View view){
    	int id=view.getId();
    	if(id==R.id.tv_year){
    		calendar=Calendar.getInstance();
    		new DatePickerDialog(this, new OnDateSetListener() {			
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					paYear=year;
					paMonth=monthOfYear+1;
					paDay=dayOfMonth;
					calendar.set(paYear, paMonth-1, 1);
					cancel();
					setValues(calendar);
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();
    	}else if(id==R.id.btn_up){
    		paMonth--;
    		if(paMonth==0){
    			paMonth=12;
    			paYear--;
    		}
    		calendar.set(paYear,paMonth-1, 1);
    		cancel();
    		setValues(calendar);
    	}else if(id==R.id.btn_next){
    		paMonth++;
    		if(paMonth==13){
    			paMonth=1;
    			paYear++;
    		}
    		calendar.set(paYear, paMonth-1, 1);
    		cancel();
    		setValues(calendar);
    	}else if(id==R.id.tv_calendar){
    		Toast.makeText(this, "回到今天", Toast.LENGTH_LONG).show();
    		calendar=Calendar.getInstance();
    		cancel();
    		setValues(calendar);
    	}
    	for(int i=0;i<monthIds.length;i++){
    		if(id==monthIds[i]){
    			TextView selectTv=(TextView)findViewById(monthIds[i]);    			
    			if(!selectTv.getText().toString().equals("")){
    				selectTv.setBackgroundResource(weekColor);
	    			int day=Integer.valueOf(selectTv.getText().toString());
	    			Intent intent=new Intent();
	        		intent.setClass(AniTodayActivity.this, AniInfo.class);
	    			Bundle bundle=new Bundle();    			
	    			bundle.putInt("month", paMonth);
	    			bundle.putInt("day", day);
	    			intent.putExtras(bundle);
	    			startActivity(intent);
    			}
    		}
    	}    	
    }
    private void setWeek(){
    	for(int i=0;i<weekIds.length;i++){
    		TextView temp=(TextView)findViewById(weekIds[i]);
    		try {
				weekColor = (Integer)R.color.class.getField("week" + paMonth).get(null);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		temp.setBackgroundResource(weekColor);
    		temp.setTextSize(20f);
    		temp.setTextColor(Color.WHITE);
    		temp.setTypeface(tf);
    		switch (weekIds[i]) {
			case R.id.tv_week1:temp.setText("周一");break;
			case R.id.tv_week2:temp.setText("周二");break;
			case R.id.tv_week3:temp.setText("周三");break;
			case R.id.tv_week4:temp.setText("周四");break;
			case R.id.tv_week5:temp.setText("周五");break;
			case R.id.tv_week6:temp.setText("周六");break;
			case R.id.tv_week7:temp.setText("周日");break;
			}
    	}
    }
    private void setValues(Calendar calendar){
    	paYear=calendar.get(Calendar.YEAR);
    	paMonth=calendar.get(Calendar.MONTH) + 1;
    	paDay=calendar.get(Calendar.DAY_OF_MONTH);
    	paWeek=calendar.get(Calendar.DAY_OF_WEEK) == 1 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
    	try {
			wordColor = (Integer)R.color.class.getField("word" + paMonth).get(null);
			dayColor = (Integer)R.color.class.getField("day" + paMonth).get(null);
			todayColor = (Integer)R.color.class.getField("today" + paMonth).get(null);
			layoutBackground = (Integer)R.drawable.class.getField("background" + paMonth).get(null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	yearTv.setText(paYear + "年" + paMonth + "月");
    	setWeek();
    	switch(paMonth){
    	case 4:
		case 6:
		case 9:
		case 11:
			endDay = 30;
			break;
		case 2:
			if (paYear % 4 == 0) {
				if (paYear % 100 == 0) {
					if (paYear % 400 == 0) {
						endDay = 29;
					} else {
						endDay = 28;
					}
				} else {
					endDay = 29;
				}
			} else {
				endDay = 28;
			}
			break;
		default:
			endDay=31;
			break;
    	}
    	beginWeek = paWeek - (paDay % 7 - 1) ;
    	if(beginWeek == 0){
    		beginWeek=7;
    	}
    	ll.setBackgroundResource(layoutBackground);
    	calendarTv.setTextColor(getResources().getColor(wordColor));
    	yearTv.setTextColor(getResources().getColor(wordColor));
    	int j = 1;
    	Calendar calendarTemp = Calendar.getInstance();
    	for(int i = 0 ; i < monthIds.length ; i++){
    		TextView temp = (TextView) findViewById(monthIds[i]);
    		temp.setTextSize(25f);
    		temp.setBackgroundResource(dayColor);
    		temp.setTextColor(Color.WHITE);
    		temp.setTypeface(tf);
    		if(i >= beginWeek - 1 && j <= endDay){
    			temp.setText(j+""); 
    			int year = calendarTemp.get(Calendar.YEAR);
				int month = calendarTemp.get(Calendar.MONTH) + 1;
				int day = calendarTemp.get(Calendar.DAY_OF_MONTH);
    			if(year == paYear && month == paMonth && j == day){
    				temp.setBackgroundResource(todayColor);
    			}
    			j++;
    			temp.setOnClickListener(this);
    		}else {
    			temp.setBackgroundColor(0x00000000);
    		}
    	}
    }
    private void cancel(){
    	for (int i = 0; i < monthIds.length; i++) {
			TextView temp = (TextView) findViewById(monthIds[i]);
			temp.setTextSize(25f);
			temp.setText("");
			temp.setBackgroundColor(0x00000000);
		}
    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		menu.add(0,0,menu.NONE,"动漫词典").setIcon(R.drawable.icon_search);
		menu.add(0,1,menu.NONE,"使用说明").setIcon(R.drawable.icon_about);
		return true;
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch(item.getItemId()){		
		case 0:			
			intent.setClass(AniTodayActivity.this, Search.class);
			startActivity(intent);
			return true;
		case 1:
			intent.setClass(AniTodayActivity.this, About.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.detector.onTouchEvent(event);
	}
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if(e1.getX() > e2.getX()){
			paMonth++;
    		if(paMonth==13){
    			paMonth=1;
    			paYear++;
    		}
    		calendar.set(paYear, paMonth-1, 1);
    		cancel();
    		setValues(calendar);
    		return true;
		}else if(e1.getX() < e2.getX()){
			paMonth--;
    		if(paMonth==0){
    			paMonth=12;
    			paYear--;
    		}
    		calendar.set(paYear,paMonth-1, 1);
    		cancel();
    		setValues(calendar);
    		return true;
		}
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}
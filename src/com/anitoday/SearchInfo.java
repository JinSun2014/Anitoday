package com.anitoday;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class SearchInfo extends Activity {
	private TextView tv_name,tv_anime,tv_date,tv_info;
	private ImageView im_name;
	private Intent intent;
	private int id_this,date;
	private String name,anime,info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_info);
		tv_name = (TextView)findViewById(R.id.tv_name_search);
		tv_anime = (TextView)findViewById(R.id.tv_anime_search);
		tv_date = (TextView)findViewById(R.id.tv_date_search);
		tv_info = (TextView)findViewById(R.id.tv_info_search);
		im_name = (ImageView)findViewById(R.id.im_name_search);
		
		final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/katongti.ttf");
        tv_name.setTypeface(tf);
        tv_anime.setTypeface(tf);
        tv_info.setTypeface(tf);
        tv_date.setTypeface(tf);
        
		intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		id_this = bundle.getInt("id");
		date = bundle.getInt("date");
		int month = date / 100;
		int day = date % 100;
		name = bundle.getString("name");
		anime = bundle.getString("anime");
		info = bundle.getString("info");
		tv_name.setText(name);
		tv_anime.setText("——" + anime);
		tv_info.setText("      " + info);
		tv_date.setText("出生日期：\n           " + month + "月" + day + "日");
		
		String imageName ="a" + id_this;
		int id_image = 0;
		try {
			id_image = (Integer)R.drawable.class.getField(imageName).get(null);
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		im_name.setImageResource(id_image);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, menu.NONE, "相关人物").setIcon(R.drawable.icon_relative);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case 0:
			SearchInfo.this.setResult(RESULT_OK, intent);
			SearchInfo.this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

}

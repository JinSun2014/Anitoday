package com.anitoday;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;



public class AniInfo extends Activity{
	private final String DATABASE_PATH = "/data/data/com.anitoday/databases";
	public static final String PATH = "/databases";
	private final String DATABASE_FILENAME = "anitoday.s3db";
	private TextView tv_name,tv_anime,tv_info,tv_date;
	private Button btn_left,btn_right;
	private Spinner spinner;
	private int infoColor = R.color.info7,downBackground = R.drawable.down7,
			layoutBackground = R.drawable.information7,currentPostition,pageNum;
	private ImageView im_name;
	private ScrollView scrollView;
	private LinearLayout ll;
	public interface AniSchema {
		String TABLE_NAME = "anime";
		String ID = "id";
		String DATE = "date";
		String NAME = "name";
		String ANIME = "ani_name";
		String INFO = "info";
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ani_info);
        final SQLiteDatabase db = openDatabase();
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_anime = (TextView)findViewById(R.id.tv_anime);
        tv_info = (TextView)findViewById(R.id.tv_info);
        tv_date = (TextView)findViewById(R.id.tv_date);
        btn_left = (Button)findViewById(R.id.btn_left);
        btn_right = (Button)findViewById(R.id.btn_right);
        im_name = (ImageView)findViewById(R.id.im_name);
        scrollView = (ScrollView)findViewById(R.id.scrolView);
        
        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/katongti.ttf");
        tv_name.setTypeface(tf);
        tv_anime.setTypeface(tf);
        tv_info.setTypeface(tf);
        tv_date.setTypeface(tf);
        
        final String[] FROM=
        {
        	AniSchema.ID,
        	AniSchema.DATE,
        	AniSchema.NAME,
        	AniSchema.ANIME,
        	AniSchema.INFO
        };
        
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        int month = bundle.getInt("month");
        int day = bundle.getInt("day");
        tv_date.setText("出生日期：\n           " + month + "月" + day + "日");
        try {
			infoColor = (Integer)R.color.class.getField("info" + month).get(null);
			downBackground = (Integer)R.drawable.class.getField("down" + month).get(null);
			layoutBackground = (Integer)R.drawable.class.getField("information" + month).get(null);
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
        
        ll = (LinearLayout)findViewById(R.id.search_linear_layout);
        ll.setBackgroundResource(layoutBackground);
        tv_name.setTextColor(getResources().getColor(infoColor));
        tv_anime.setTextColor(getResources().getColor(infoColor));
        tv_date.setTextColor(getResources().getColor(infoColor));
        tv_info.setTextColor(getResources().getColor(infoColor));
        
        int date = month * 100 + day;
        final String where = AniSchema.DATE + "=" + date;
        Cursor c = db.query(AniSchema.TABLE_NAME, new String[] {AniSchema.NAME}, where, null, null, null, "id");
        pageNum = c.getCount();
        c.moveToFirst();
        CharSequence[] list = new CharSequence[pageNum];
        for(int i=0;i<list.length;i++){
        	list[i]=c.getString(0);
        	c.moveToNext();
        }
        c.close();
        
        spinner = (Spinner)findViewById(R.id.sp_name);
        spinner.setBackgroundResource(downBackground);
        spinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.select_dialog_item, list));
        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				scrollView.scrollTo(0, 0);
				currentPostition = position;
				TextView temp = (TextView)view;
				temp.setTypeface(tf);
				temp.setTextColor(getResources().getColor(infoColor));
				temp.setTextSize(25f);
				
				Cursor c = db.query(AniSchema.TABLE_NAME, FROM , where, null, null, null, null);
				c.moveToFirst();
				c.move(position);				
				int id_this = c.getInt(c.getColumnIndex(AniSchema.ID));
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

				String name_this = c.getString(c.getColumnIndex(AniSchema.NAME));
				String ani_name_this = c.getString(c.getColumnIndex(AniSchema.ANIME));
				String info_this = c.getString(c.getColumnIndex(AniSchema.INFO));
				c.close();
				im_name.setImageResource(id_image);
				tv_name.setText(name_this);
				tv_anime.setText("——" + ani_name_this);
				tv_info.setText("      " + info_this);
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
        btn_left.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentPostition > 0){
					currentPostition--;
					spinner.setSelection(currentPostition);
				}
			}
		});
        btn_right.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentPostition < pageNum -1){
					currentPostition++;
					spinner.setSelection(currentPostition);
				}
			}
		});
    }
	
	private SQLiteDatabase openDatabase(){
		try{
			File myDataPath = new File(DATABASE_PATH+PATH);
			String databaseFilename = myDataPath + "/" + DATABASE_FILENAME;
			if(!myDataPath.exists()){
				myDataPath.mkdirs();
			}
			if(!(new File(databaseFilename)).exists()){
				InputStream is = getResources().openRawResource(R.raw.anitoday);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0){
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			SQLiteDatabase database = SQLiteDatabase.openDatabase(databaseFilename, null, RESULT_OK);
			return database;
		}
		catch (Exception e)
		{
			Log.i("DB","DB_DIR_Exception: ");
		}
		return null;
	}
}

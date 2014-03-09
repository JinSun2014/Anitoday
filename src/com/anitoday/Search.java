package com.anitoday;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Search extends Activity {
	private final String DATABASE_PATH = "/data/data/com.anitoday/databases";
	public static final String PATH = "/databases";
	private final String DATABASE_FILENAME = "anitoday.s3db";
	private SQLiteDatabase db;
	private Cursor c;
	private ListView listView;
	private Button btn_search;
	private EditText ed_search;
	private int id_this,date;
	private String name,anime,info;
	private final String[] FROM=
        {
        	AniSchema.ID,
        	AniSchema.DATE,
        	AniSchema.NAME,
        	AniSchema.ANIME,
        	AniSchema.INFO
    };
	public interface AniSchema {
		String TABLE_NAME = "anime";
		String ID = "id";
		String DATE = "date";
		String NAME = "name";
		String ANIME = "ani_name";
		String INFO = "info";
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		db = openDatabase();
		listView = (ListView)findViewById(R.id.ListView01);
		btn_search = (Button)findViewById(R.id.btn_search);
		ed_search = (EditText)findViewById(R.id.ed_search);
		btn_search.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String search = ed_search.getText().toString();
				c =Search(search);
				if(c.getCount() != 0){
					listView.setAdapter(new CursorAdapter(Search.this,c));
					listView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							// TODO Auto-generated method stub
							c.moveToFirst();
							c.move(position);
							id_this = c.getInt(0);
							date = c.getInt(1);
							name = c.getString(2);
							anime = c.getString(3);
							info = c.getString(4);
							
							Intent intent = new Intent();
							intent.setClass(Search.this, SearchInfo.class);
							Bundle bundle = new Bundle();
							bundle.putInt("id", id_this);
							bundle.putInt("date", date);
							bundle.putString("name", name);
							bundle.putString("anime", anime);
							bundle.putString("info", info);	
							intent.putExtras(bundle);
							startActivityForResult(intent, 0);
						}
					});
				}else {
					new AlertDialog.Builder(Search.this).setTitle("找不到该内容").setMessage("可以尝试模糊搜索，如：输入“街”、“霸”、“街%霸”等可找到《街头霸王》\n" +
							"（英文符号的“%”可代替缺少的字词）").setPositiveButton("确定", null).show(); 
				}
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(resultCode){
		case RESULT_OK :
			Bundle bundle = data.getExtras();
			String search = bundle.getString("anime");
			c = Search(search);
			listView.setAdapter(new CursorAdapter(Search.this,c));
			break;
		default:
			break;
		}
	}

	private Cursor Search(String search){
			String like = "%" + search + "%";
			Cursor c = db.query(AniSchema.TABLE_NAME, FROM, "name like ? or ani_name like ?", new String[] {like,like}, null, null, null);

			return c;		
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

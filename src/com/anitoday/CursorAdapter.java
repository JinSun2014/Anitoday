package com.anitoday;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CursorAdapter extends BaseAdapter {
	private Context context;
	private Cursor cursor;
	private LayoutInflater inflater;
	private LinearLayout pageLayout;
	
	public CursorAdapter(Context context, Cursor cursor) {
		super();
		this.context = context;
		this.cursor = cursor;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		cursor.moveToPosition(position);
		pageLayout = (LinearLayout)inflater.inflate(R.layout.list, null);
		TextView text_name = (TextView) pageLayout.findViewById(R.id.TextView01);
		TextView text_anime = (TextView) pageLayout.findViewById(R.id.TextView02);
		text_name.setText(cursor.getString(2));
		text_anime.setText(cursor.getString(3));
		ImageView image = (ImageView) pageLayout.findViewById(R.id.ImageView01);
		int id = cursor.getInt(0);
		String imageName = "a" + id;
		int id_image = 0;
		try {
			id_image = (Integer)R.drawable.class.getField(imageName).get(null);
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
		image.setImageDrawable(context.getResources().getDrawable(id_image));
		return pageLayout;
	}
}

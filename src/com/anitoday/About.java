package com.anitoday;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class About extends Activity {
	private TextView tv_title,tv_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/katongti.ttf");
		tv_title = (TextView)findViewById(R.id.about_title);
		tv_content = (TextView)findViewById(R.id.about_content);
		tv_title.setTypeface(tf);
		tv_content.setTypeface(tf);
		String content = "���������������Ƕ���������������\n"
						+"һ�������棺\n" 
						+"        1.���AniToday�ص�����\n"
						+"        2.���xxxx��xx��ʵ����ת\n"
						+"        3.��ť�����һ��������������л�\n"
						+"        4.������ڿ��Բ鿴���쵮���Ķ������Ｐ����\n"
						+"        5.���Menu�ɽ��붯���ʵ��ʹ��˵��ҳ��\n"
						+"�����鿴��Ϣ\n"
						+"        ��������˵����ײ����Ҽ�ͷѡ��鿴����\n"
						+"��������\n"
						+"        �������������ݣ����Գ���ģ�������������롰�֡������ԡ�������%�ԡ��ȿ��ҵ�����ͷ��������Ӣ�ķ��ŵġ�%���ɴ���ȱ�ٵ��ִʣ�\n"
						+"        �����������б�ѡ��ɽ���������Ϣ�鿴ҳ��\n"
						+"�ġ�������Ϣ�鿴\n"
						+"        ���Menu�������������\n\n"
						+"��������ּ��󲿷������԰ٶȰٿƣ�wiki�ٿƣ�ȭ������־��wikia-type moon �ȴ�����"
						+"���������Լ��༭�Ĵ�����ͼƬ������Թٷ���Ʒ,ͼ������������ͼ�⡣��Ȩ����������δ���������޸Ļ�����ҵ��;��\n"
						+"���ߣ�(SYSU)Su   Xujian\n              (SYSU)Sun   Jin\n              (SYSU)Peng   Jufu\n"
						+"������(SYSU)Zhang   Mengdan\n              (SYSU)Wu   Chaobiao\n"
						+"����ָ����Lin   Qiaojun\n"
						+"�����κν�������������ϵstardust09@qq.com";
		tv_content.setText(content);
	}
}

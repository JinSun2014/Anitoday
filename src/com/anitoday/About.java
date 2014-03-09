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
		String content = "声明：我们做的是动漫，不是日历！\n"
						+"一、主界面：\n" 
						+"        1.点击AniToday回到今天\n"
						+"        2.点击xxxx年xx月实现跳转\n"
						+"        3.按钮及左右滑屏可以上下月切换\n"
						+"        4.点击日期可以查看当天诞生的动漫人物及节日\n"
						+"        5.点击Menu可进入动漫词典和使用说明页面\n"
						+"二、查看信息\n"
						+"        点击下拉菜单及底部左右箭头选择查看内容\n"
						+"三、搜索\n"
						+"        若搜索不到内容，可以尝试模糊搜索，如输入“街”、“霸”、“街%霸”等可找到《街头霸王》（英文符号的“%”可代替缺少的字词）\n"
						+"        点击搜索结果列表选项可进入搜索信息查看页面\n"
						+"四、搜索信息查看\n"
						+"        点击Menu可搜索相关人物\n\n"
						+"本软件文字简介大部分引用自百度百科，wiki百科，拳皇人物志，wikia-type moon 等词条。"
						+"另有作者自己编辑的词条。图片大多来自官方作品,图标大多来自懒人图库。版权归作者所有未经允许不得修改或作商业用途。\n"
						+"作者：(SYSU)Su   Xujian\n              (SYSU)Sun   Jin\n              (SYSU)Peng   Jufu\n"
						+"美工：(SYSU)Zhang   Mengdan\n              (SYSU)Wu   Chaobiao\n"
						+"美术指导：Lin   Qiaojun\n"
						+"如有任何建议或意见，请联系stardust09@qq.com";
		tv_content.setText(content);
	}
}

package com.itcast01.togglebutton;

import com.itcast01.togglebutton.interf.OnToggleStateChangedListener;
import com.itcast01.togglebutton.view.ToggleView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ToggleView mToggleView = (ToggleView) findViewById(R.id.toggleview);
        
//        // 设置开关的状态为: 打开
//        mToggleView.setCurrentToggleState(false);
//        
//        // 设置开关的背景id
//        mToggleView.setSwitchBackgroundID(R.drawable.switch_background);
//        
//        // 设置开关的滑动块的背景
//        mToggleView.setSlideButtonBackgroundID(R.drawable.slide_button_background);
        
        // 设置开关状态监听
        mToggleView.setOnToggleStateChangedListener(new OnToggleStateChangedListener() {
			
			@Override
			public void onToggleStateChanged(boolean currentToggleState) {
				if(currentToggleState) {
					Toast.makeText(MainActivity.this, "开关打开", 0).show();
				} else {
					Toast.makeText(MainActivity.this, "开关关闭", 0).show();
				}
			}
		});
    }
    
}

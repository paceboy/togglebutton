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
        
//        // ���ÿ��ص�״̬Ϊ: ��
//        mToggleView.setCurrentToggleState(false);
//        
//        // ���ÿ��صı���id
//        mToggleView.setSwitchBackgroundID(R.drawable.switch_background);
//        
//        // ���ÿ��صĻ�����ı���
//        mToggleView.setSlideButtonBackgroundID(R.drawable.slide_button_background);
        
        // ���ÿ���״̬����
        mToggleView.setOnToggleStateChangedListener(new OnToggleStateChangedListener() {
			
			@Override
			public void onToggleStateChanged(boolean currentToggleState) {
				if(currentToggleState) {
					Toast.makeText(MainActivity.this, "���ش�", 0).show();
				} else {
					Toast.makeText(MainActivity.this, "���عر�", 0).show();
				}
			}
		});
    }
    
}

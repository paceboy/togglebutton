package com.itcast01.togglebutton.view;

import com.itcast01.togglebutton.interf.OnToggleStateChangedListener;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ToggleView extends View {

	private static final String TAG = "ToggleView";
	private Bitmap switchBackgroundBitmap; // ����ͼƬ
	private Bitmap slideButtonBackgroundBitmap; // ������ı���ͼƬ
	private boolean currentToggleState = false; // �����鵱ǰ��״̬, Ĭ��Ϊ: �ر�
	private int currentX; // x�����µ�ƫ����
	private boolean isTouching = false; // �Ƿ����ڴ�����, Ĭ��ֵ: δ����
	private ObjectAnimator _oaLeft, _oaRight;
	
	private OnToggleStateChangedListener mOnToggleStateChangedListener;

	/**
	 * @param context
	 * @param attrs ��߷�װ���ڲ����ļ��ж������������
	 */
	public ToggleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// �Զ������Ե������ռ�
		String namespace = "http://schemas.android.com/apk/res/com.itcast01.togglebutton";
		
		// ȡ���Զ������Ե�ֵ.
		
		// 1. ��ǰ��״̬
		currentToggleState = attrs.getAttributeBooleanValue(namespace, "currentState", false);

		// 2. ȡ������ͼƬ��id
		int switchBackgroundID = attrs.getAttributeResourceValue(namespace, "switchBackgroundID", -1);
		setSwitchBackgroundID(switchBackgroundID);
		
		// 3. ȡ��������ͼƬ��id
		int slideButtonBackgroundID = attrs.getAttributeResourceValue(namespace, "slideButtonBackgroundID", -1);
		setSlideButtonBackgroundID(slideButtonBackgroundID);

		_oaLeft = ObjectAnimator.ofFloat(slideButtonBackgroundBitmap, "x", slideButtonBackgroundBitmap.getWidth() / 2, 0).setDuration(2000);
		_oaRight = ObjectAnimator.ofFloat(slideButtonBackgroundBitmap, "x", 0, slideButtonBackgroundBitmap.getWidth()/2).setDuration(2000);

	}
	
	/**
	 * ��androidϵͳ��Ҫ�����˿ؼ�ʱ, �ص��˷���.
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		// �Լ����ÿؼ��Ŀ�͸�, ���ñ���ͼƬ�Ŀ�͸�������.
		setMeasuredDimension(switchBackgroundBitmap.getWidth(), switchBackgroundBitmap.getHeight());
	}

	/**
	 * ��androidϵͳ��Ҫ���ƴ˿ؼ�ʱ, �ص��˷���
	 * canvas ������
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw ...");
		// 1. �ѱ���ͼƬ�����ؼ������Ͻ���.
		canvas.drawBitmap(switchBackgroundBitmap, 0, 0, null); // �ѱ���ͼƬ���ڿؼ������Ͻ�
		
		// 2. �ѻ����黭��ָ����λ����, ����״̬����������λ��.
		
		// ��״̬����߽��x���ַ
		int left = switchBackgroundBitmap.getWidth() - slideButtonBackgroundBitmap.getWidth();
		
		if(isTouching) {
			// ��ǰ��ָ���ڴ�����, ���ݵ�ǰ��x���λ�û��ƻ������λ��.
			
			// ���ڻ����еĻ��������ߵ�ֵ
			int slidingLeft = currentX - slideButtonBackgroundBitmap.getWidth() / 2;
			
			if(slidingLeft < 0) {
				// ��������߽�, һֱ��ֵΪ0
				slidingLeft = 0;
			} else if(slidingLeft > left) { // �����ǰ�Ļ��������� ������ ����ͼƬ��� - �������� , �����˱߽�.
				slidingLeft = left;
			}
			
			canvas.drawBitmap(slideButtonBackgroundBitmap, slidingLeft, 0, null);
		} else {
			// û�д���������, ����currentToggleState��״̬���ƻ������λ��
			if(currentToggleState) {
				// ��ǰ�Ǵ򿪵�״̬: top=0, left=����ͼƬ�Ŀ�� - ������Ŀ��
				canvas.drawBitmap(slideButtonBackgroundBitmap, left, 0, null);
			} else {
				// ��ǰ�ǹرյ�״̬: top=0, left=0
				canvas.drawBitmap(slideButtonBackgroundBitmap, 0, 0, null);
			}
		}
	}

	/**
	 * ����ָ����ʱ�����˷���
	 * ����, �ƶ�, ̧��. �����¼�����
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (_oaLeft.isRunning() || _oaRight.isRunning()) return false;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // ���µ��¼�
			isTouching = true;
			currentX = (int) event.getX();
			Log.d(TAG, "onTouchEvent "+event.getAction() +", curentX = "+currentX);
			break;
		case MotionEvent.ACTION_MOVE: // �ƶ����¼�
			currentX = (int) event.getX();

			Log.d(TAG, "onTouchEvent "+event.getAction() +", curentX = "+currentX);
			break;
		case MotionEvent.ACTION_UP: // ̧����¼�
			isTouching = false;
			currentX = (int) event.getX();
			Log.d(TAG, "onTouchEvent "+event.getAction() +", curentX = "+currentX);

			// ���������ĵ�, ��ǰ��x���ƫ����
			boolean state = currentX > (switchBackgroundBitmap.getWidth() / 2);

			// �ص��û����¼�
			if(state != currentToggleState && mOnToggleStateChangedListener != null) {
				mOnToggleStateChangedListener.onToggleStateChanged(state);
			}
			currentToggleState = state;
			break;
		default:
			break;
		}

		invalidate(); // ˢ�µ�ǰ�ؼ�, ����onDraw�����ĵ���.
		return true; // ���ĵ����¼�
	}

	/**
         * alt + shift + J
         * ���ÿ��ص�״̬
         * @param b
         */
	public void setCurrentToggleState(boolean b) {
		currentToggleState  = b;
	}

	/**
	 * ���ÿ��ر�����id
	 * @param switchBackground
	 */
	public void setSwitchBackgroundID(int switchBackground) {
		switchBackgroundBitmap = BitmapFactory.decodeResource(getResources(), switchBackground);
	}

	/**
	 * ���û�����ı���
	 * @param slideButtonBackground
	 */
	public void setSlideButtonBackgroundID(int slideButtonBackground) {
		slideButtonBackgroundBitmap = BitmapFactory.decodeResource(getResources(), slideButtonBackground);
	}
	
	public void setOnToggleStateChangedListener(OnToggleStateChangedListener listener) {
		mOnToggleStateChangedListener = listener;
	}
}

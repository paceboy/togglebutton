package com.itcast01.togglebutton.interf;

/**
 * @author andong
 * 监听开关状态的事件
 */
public interface OnToggleStateChangedListener {

	/**
	 * 开关状态改变时回调此方法
	 * @param currentToggleState 当前开关的状态
	 */
	void onToggleStateChanged(boolean currentToggleState);
}

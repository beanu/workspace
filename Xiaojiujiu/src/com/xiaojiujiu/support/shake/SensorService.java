package com.xiaojiujiu.support.shake;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.Vibrator;

import com.google.zxing.CaptureActivity;
import com.xiaojiujiu.support.shake.ShakeEventListener.OnShakeListener;

/**
 * 摇一摇监听
 * 
 * @author beanu
 * 
 */
public class SensorService extends Service {

	private SensorManager mySensorManager;
	private Vibrator vibrator;
	private ShakeEventListener myShakeEventListener;

	@Override
	public void onCreate() {
		super.onCreate();

		mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); // 获得传感器管理对象
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		myShakeEventListener = new ShakeEventListener();
		myShakeEventListener.setOnShakeListener(new OnShakeListener() {
			@Override
			public void onShake() {
				try {
					vibrator.vibrate(100);
				} catch (Exception e) {

				}
				Intent intent = new Intent(getBaseContext(), CaptureActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplication().startActivity(intent);
			}
		});

		// 注册监听
		// 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
		mySensorManager.registerListener(myShakeEventListener,
				mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		mySensorManager.unregisterListener(myShakeEventListener); // 不在当前界面 解除监听
		mySensorManager = null;
		vibrator.cancel();
		vibrator = null;
		super.onDestroy();
	}

}

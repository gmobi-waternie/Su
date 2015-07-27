package com.su.app;



import com.su.service.AppBroadcastReceiver;
import com.su.service.UpdateService;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;

public class SuApplication extends Application {
	public static Application app;
	public static Handler msghandler;
	
	
	public static final String SP_NAME = "Su_Sp";
	@Override
	public void onCreate() {
		super.onCreate();
		IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK); 
		AppBroadcastReceiver receiver = new AppBroadcastReceiver(app); 
		registerReceiver(receiver, filter); 
		app = this;
		msghandler = new Handler();

	}
	
	@SuppressLint("NewApi")
	@Override
	public void onTrimMemory(int level) {
		
		super.onTrimMemory(level);
		Log.e(Tag,"onTrimMemory");
		Intent i = new Intent(app, UpdateService.class); 
		app.startService(i); 
	}
	
	private static final String Tag = "[SuWidget]";
	
}

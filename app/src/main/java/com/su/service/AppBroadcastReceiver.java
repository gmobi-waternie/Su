package com.su.service;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AppBroadcastReceiver extends BroadcastReceiver {
	private Context mContext;
	private Application suapp;
	private static final String Tag = "[SuWidget]";
	
	public AppBroadcastReceiver(Context ctx) {
		mContext = ctx;
		suapp=(Application)ctx;
		
	}
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		if (arg1.getAction().equals(Intent.ACTION_TIME_TICK)) { 
			  
			boolean isServiceRunning = false; 
			ActivityManager manager = (ActivityManager)arg0.getSystemService(Context.ACTIVITY_SERVICE); 
			for (RunningServiceInfo service :manager.getRunningServices(Integer.MAX_VALUE)) { 
				if("com.waternie.service.UpdateService".equals(service.service.getClassName())) 
				{ 
					isServiceRunning = true; 
					Log.e(Tag, "UpdateService is alive!!!");
				}   
			} 
			if (!isServiceRunning) {
				Log.e(Tag, "UpdateService is Dead!!!");
				Intent i = new Intent(arg0, UpdateService.class); 
				arg0.startService(i); 
			} 
			  
		} 

	}

}


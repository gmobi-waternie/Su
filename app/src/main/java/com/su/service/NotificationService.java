package com.su.service;

import com.su.activity.MainActivity;
import com.su.app.SuApplication;
import com.su.datautil.Lunar;
import com.waternie.su.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class NotificationService {
	private Context mContext;
	
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	
	private static final String SP_KEY_NOTIFY_FLAG = "todayNotified";
	
	public NotificationService(Context mContext) {
		super();
		this.mContext = mContext;
	}


	public void Notify(String notifyString)
	{
		sp = mContext.getSharedPreferences(SuApplication.SP_NAME, Context.MODE_WORLD_WRITEABLE);
		int flag = sp.getInt(SP_KEY_NOTIFY_FLAG, 0);
		

			NotificationManager nm = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification n = new Notification(R.drawable.su_active,"Hello,there!", System.currentTimeMillis());
			n.flags = Notification.FLAG_AUTO_CANCEL;
			Intent i = new Intent(mContext, MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
			//PendingIntent
			PendingIntent contentIntent = PendingIntent.getActivity(mContext,R.string.app_name,i,PendingIntent.FLAG_UPDATE_CURRENT);
			
			String format;
			String notifyFullString;
			String notifyHint = mContext.getResources().getString(R.string.notify_hint);
			
			if(notifyString != null)
			{
				format = mContext.getResources().getString(R.string.notify_title_format);
				notifyFullString = String.format(format, notifyString);
			}
			else
				notifyFullString = mContext.getResources().getString(R.string.notify_title_default);
			
			n.setLatestEventInfo(mContext,notifyFullString,notifyHint,contentIntent);
			nm.notify(R.string.app_name, n);
			
			sp = mContext.getSharedPreferences(SuApplication.SP_NAME, Context.MODE_WORLD_WRITEABLE);
			editor = sp.edit();
			
			
	
			editor.putInt(SP_KEY_NOTIFY_FLAG,1);
			editor.commit();
		
	}
}

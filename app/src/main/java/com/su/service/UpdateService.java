package com.su.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.su.datautil.Lunar;
import com.su.provider.SuProvider;
import com.waternie.su.R;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.app.ActivityManager.RunningServiceInfo;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;

public class UpdateService extends Service {
    public static final boolean DEBUG = true;    
    private BroadcastReceiver receiver;  
    private Context mService;
  
    @Override  
    public IBinder onBind(Intent arg0) {  
    	
    	
        return null ;  
    }  
    private static final String Tag = "[SuWidget]";
    
    /** 
     * �������񲢶�̬ע��ϵͳ��ACTION_TIME_TICK���� 
     */  
    @Override  
    public void onCreate() {  
        // TODO Auto-generated method stub  
        super.onCreate();  
        mService = this;
        /*
        Notification notification = new Notification();  
        notification.flags = Notification.FLAG_ONGOING_EVENT;  
        notification.flags |= Notification.FLAG_NO_CLEAR;  
        notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;  
        startForeground(1, notification); 
        */


        
        // ʵ��������ϵͳACTION_TIME_TICK��BroadcastReceiver  
        receiver = new BroadcastReceiver(){  
            private static final String ACTION_TIME_TICK = Intent.ACTION_TIME_TICK ;  
             
            @Override  
            public void onReceive(Context context, Intent intent) {  
                // TODO Auto-generated method stub  
          
                 
                if(intent.getAction().equals(ACTION_TIME_TICK)){//ע�⣺ʹ�õ���equals����������"=="  
                    Intent tmp = new Intent();  
                    tmp.setAction("com.nwd.android.calendarwidget.timetick" );  
          
                    context.sendBroadcast(tmp);  
                }  
            }  
             
        };  
        this.registerReceiver(receiver , new IntentFilter(Intent.ACTION_TIME_TICK ));  
         
    }  
  
    /** 
     * ��onDestroy()������������UpdateService 
     */  
    @Override  
    public void onDestroy() {  
        // TODO Auto-generated method stub  
        super.onDestroy();  
        this.unregisterReceiver(receiver);
        
        Log.e(Tag,"Restart Service!!");
        Intent intent = new Intent();  
        intent.setClass( this, UpdateService.class);  
        startService(intent);  
    }  
  
    @Override  
    public int onStartCommand(Intent intent, int flags, int startId) {  
        // TODO Auto-generated method stub  
        return START_STICKY ;  
    }  

	 private void UpdateWidget(Context context) 
	    {    
	        RemoteViews updateView = new RemoteViews(context.getPackageName(),
	                R.layout.main_widget);
	        Calendar c = Calendar.getInstance();

	        Lunar lunar = new Lunar(c);

	        String lunarStr = "";
	        lunarStr=lunar.animalsYear()+"(";
	        lunarStr +=lunar.cyclical()+")";
	        lunarStr +=lunar.toString();
	        
	        int   hour   =   c.get(Calendar.HOUR_OF_DAY);    
	        int   minute   =   c.get(Calendar.MINUTE);   
	        
	        
	        
	        
	        if(lunar.getChinaDayString(lunar.getDaynum()).equals("初一") || lunar.getChinaDayString(lunar.getDaynum()).equals("十五"))
	        	updateView.setImageViewResource(R.id.widget_icon, R.drawable.su_active);
	        else
	        	updateView.setImageViewResource(R.id.widget_icon, R.drawable.su_inactive);
	        
	        updateView.setTextViewText(R.id.widget_lunar,lunarStr);
	        updateView.setTextViewText(R.id.widget_time,hour+":"+minute);


	        
	        AppWidgetManager awg = AppWidgetManager.getInstance(context);
	        awg.updateAppWidget(new ComponentName(context, SuProvider.class),
	                updateView);
	    }

}

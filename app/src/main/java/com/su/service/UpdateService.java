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
    

    @Override  
    public void onCreate() {  

        super.onCreate();  
        mService = this;

        

        receiver = new BroadcastReceiver(){  
            private static final String ACTION_TIME_TICK = Intent.ACTION_TIME_TICK ;  
             
            @Override  
            public void onReceive(Context context, Intent intent) {  

                if(intent.getAction().equals(ACTION_TIME_TICK)){
                    Intent tmp = new Intent();  
                    tmp.setAction("com.nwd.android.calendarwidget.timetick" );  
          
                    context.sendBroadcast(tmp);  
                }  
            }  
             
        };  
        this.registerReceiver(receiver , new IntentFilter(Intent.ACTION_TIME_TICK ));  
         
    }  
  

    @Override  
    public void onDestroy() {
        super.onDestroy();  
        this.unregisterReceiver(receiver);
        
        Log.e(Tag,"Restart Service!!");
        Intent intent = new Intent();  
        intent.setClass( this, UpdateService.class);  
        startService(intent);  
    }  
  
    @Override  
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY ;  
    }  



}

package com.su.provider;

import java.util.Calendar;

import com.su.activity.MainActivity;
import com.su.datautil.Lunar;
import com.su.service.NotificationService;
import com.su.service.UpdateService;
import com.waternie.su.R;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class SuProvider extends AppWidgetProvider {

	private static final String DATE_CHANGED = Intent.ACTION_DATE_CHANGED;

	private static final String TIME_CHANGED = Intent.ACTION_TIME_CHANGED;

	private static final String TIME_TICK = "com.nwd.android.calendarwidget.timetick";
	private RemoteViews mRv;
	private static String imei = "";

	private static final String Tag = "[SuWidget]";

	private static final String FRESH_CLICK_ACTION = "Su.Widget.Button.Click";

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);

		context.startService(new Intent(context, UpdateService.class));
	}

	@SuppressLint("NewApi")
	@Override
	public void onAppWidgetOptionsChanged(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId,
			Bundle newOptions) {

		super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId,
				newOptions);

		Log.e(Tag, "onAppWidgetOptionsChanged");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		String action = intent.getAction();

		if (mRv == null) {
			mRv = new RemoteViews(context.getPackageName(),
					R.layout.main_widget);
		}

		if (FRESH_CLICK_ACTION.equals(action)) {
			Log.e(Tag, "=====Recv Button Click=========");
			Intent in = new Intent(context, MainActivity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(in);
		}

		if (DATE_CHANGED.equals(action)) {

			updateDate(context, mRv);

		}
		if (TIME_CHANGED.equals(action)) {
			Log.e(Tag, "=====Recv TIME_CHANGED=========");
			updateDate(context, mRv);
			updateTime(context, mRv);

		}
		if (TIME_TICK.equals(action)) {
			Log.e(Tag, "=====Recv TIME_TICK=========");
			updateTime(context, mRv);
			updateDate(context, mRv);
		}

		int appWidgetId = intent.getIntExtra(
				AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);
		updateButton(appWidgetId, context, mRv);

		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		int[] appIds = appWidgetManager.getAppWidgetIds(new ComponentName(
				context, SuProvider.class));

		appWidgetManager.updateAppWidget(appIds, mRv);

		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		imei = mTelephonyMgr.getDeviceId();

	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		super.onUpdate(context, appWidgetManager, appWidgetIds);

		for (int i = 0; i < appWidgetIds.length; i++) {

			if (mRv == null) {
				mRv = new RemoteViews(context.getPackageName(),
						R.layout.main_widget);
			}
			//
			updateButton(appWidgetIds[i], context, mRv);
			updateTime(context, mRv);
			updateDate(context, mRv);

			appWidgetManager.updateAppWidget(appWidgetIds, mRv);

		}

	}

	public void updateDate(Context context, RemoteViews rv) {
		Calendar c = Calendar.getInstance();

		Lunar lunar = new Lunar(c);

		String lunarStr = "";
		lunarStr = lunar.animalsYear() + "(";
		lunarStr += lunar.cyclical() + ")";
		lunarStr += lunar.toString();
		String notifyString = lunar.needSu();
		if (notifyString != null)
		{
			rv.setImageViewResource(R.id.widget_icon, R.drawable.su_active);
			new NotificationService(context).Notify(notifyString);
		} else
			rv.setImageViewResource(R.id.widget_icon, R.drawable.su_inactive);

		rv.setTextViewText(R.id.widget_lunar, lunarStr);

	}

	public void updateTime(Context context, RemoteViews rv) {
		Calendar c = Calendar.getInstance();

		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		rv.setTextViewText(R.id.widget_time, hour + ":"
				+ (minute < 10 ? "0" + minute : minute));

	}

	public void updateButton(int appWidgetId, Context context, RemoteViews rv) {
		Intent in = new Intent();
		in.setAction(FRESH_CLICK_ACTION);
		in.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				in, 0);
		rv.setOnClickPendingIntent(R.id.widget_fresh, pendingIntent);

	}

	public static String getImei() {
		return imei;
	}

}

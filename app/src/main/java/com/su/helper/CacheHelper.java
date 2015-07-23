package com.su.helper;




import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.su.app.CacheNames;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheHelper {
	private static Context mContext;
	private static CacheHelper ins;
	private SharedPreferences appSp;
	private SharedPreferences.Editor editor; 
	
	private CacheHelper(Context ctx)
	{
		mContext = ctx;	

		
	}
	
	synchronized public static CacheHelper getInstance(Context ctx)
	{
		if(ins == null)
			ins = new CacheHelper(ctx);
		
		return ins;
	}
	
	public ArrayList<String> getAllReadSentence()
	{
		ArrayList<String> allSt = new ArrayList<String>();
		appSp = mContext.getSharedPreferences(CacheNames.SU_SP_NAME, Context.MODE_WORLD_READABLE);
		String rmString = appSp.getString(CacheNames.SU_SP_KEY_READMACHINE, "");
		if(rmString.equals(""))
			return allSt;
		
		
		JSONObject rmJo;
		try {
			rmJo = new JSONObject(rmString);
			Iterator it = rmJo.keys();  
	        while (it.hasNext()) {  
	            String key = (String) it.next();  
	            int value = rmJo.getInt(key);  
	            if(value >= 0)
	            	allSt.add(key);
	            	
	        } 
		} catch (JSONException e) {
			e.printStackTrace();

		}
		return allSt;		
		
 
		
		
	}
	
	public int getReadMachineCount(String key)
	{
		appSp = mContext.getSharedPreferences(CacheNames.SU_SP_NAME, Context.MODE_WORLD_READABLE);
		String rmString = appSp.getString(CacheNames.SU_SP_KEY_READMACHINE, "");

		
		try {
			if(rmString.equals(""))
				return 0;
			
			JSONObject rmJo = new JSONObject(rmString);
			if(!rmJo.has(key))
				return 0;
			
			return rmJo.getInt(key);
		} catch (JSONException e) {
			return 0;
		}
		
			
	}
	
	public void setReadMachineCount(String key ,int count)
	{
		appSp = mContext.getSharedPreferences(CacheNames.SU_SP_NAME, Context.MODE_WORLD_WRITEABLE);
		editor = appSp.edit();
		try {
			String rmString = appSp.getString(CacheNames.SU_SP_KEY_READMACHINE, "");
			JSONObject rmJo = null;
			if(rmString.equals(""))
			{
				rmJo = new JSONObject();
				rmJo.put(key, count);
			}
			else
			{
				rmJo = new JSONObject(rmString);
				rmJo.put(key, count);
			}
			editor.putString(CacheNames.SU_SP_KEY_READMACHINE, rmJo.toString());
			editor.commit();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}

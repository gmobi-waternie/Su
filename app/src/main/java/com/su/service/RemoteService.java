package com.su.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.su.helper.Logger;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.util.Log;
import android.widget.SlidingDrawer;


public class RemoteService {
	public interface ICallback{
		void onResult(JSONObject dn);
	}
	public interface IJsonCallback{
		void onResult(int code ,JSONObject dn);
	}
	
	public interface IRspCallback{
		void onResult(int code, String rsp);
	}
	
	public interface IImgRspCallback{
		void onResult(Bitmap bmp);
	}	
	public interface IImgRspWithUrlCallback{
		void onResult(Bitmap bmp,String Url);
	}		
	
	public interface IImgRspWithIdxCallback{
		void onResult(Bitmap bmp, int idx);
	}	
	
	public static class Response {
		int statusCode;
		String body;
		public int getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
	}
	public static final String DEFAULT_API_URL_BASE = "http://as{mcc}";
	public static final String DEFAULT_PATH_EARN = "trade/earn";
	public static final String DEFAULT_PATH_GET_BALANCE = "android/member";
	
	public static final String PARAM_PATH_CONNECT = "PathConnect";		
	public static final String PARAM_PATH_EARN = "PathEarn";
	public static final String PARAM_PATH_GET_BALANCE = "PathGetBalance";	

	String session = null;
	String apiUrlBase;
	String pathConnect;
	String pathEarn;
	String pathGetBalance;

	
    private static final int SOCKET_OPERATION_TIMEOUT = 60 * 1000 * 5;
	AndroidHttpClient httpClient = null;

    private static RemoteService m_intance=null;

    private RemoteService(){
		httpClient = AndroidHttpClient.newInstance("Go2Reward SDK");
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), SOCKET_OPERATION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), SOCKET_OPERATION_TIMEOUT);
    	
    }

    synchronized public static RemoteService getInstance(){
        if(m_intance==null){
            m_intance=new RemoteService();
        }
        return m_intance;
    }


    Response doGet(String url)
    {
    	return doGet(url,null);
    }

	Response doGet(String url,Header[] headers){

        final DefaultHttpClient client = new DefaultHttpClient();
        
        //String encodeUrl = java.net.URLEncoder.encode(url);
        HttpGet httpget = new HttpGet(url);
        if(headers != null)
        	httpget.setHeaders(headers);
        //Logger.debug("encode url = " + encodeUrl+", url = "+url);
        

		Response resp = new Response();
		try {
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				resp.statusCode = response.getStatusLine().getStatusCode();
				if(resp.statusCode == 200)
					resp.body = EntityUtils.toString(entity);
				else if (resp.statusCode == 302)
				{
					Header[] rspheaders = response.getHeaders("Location"); 
					resp.body = rspheaders[0].getValue();
				}
					
				

			}
		} catch (Exception e) {

		}
		return resp;
	}



	Response doPost(String url, String data){		
		HttpPost httppost = new HttpPost(url);
		Response resp = new Response();
		try {
			httppost.setEntity(new StringEntity(data, "UTF-8"));
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				resp.body = EntityUtils.toString(entity);
				resp.statusCode = response.getStatusLine().getStatusCode();
			}
		} catch (Exception e) {

		}
		return resp;
	}
	
	
	public void postData(final JSONObject json, final String url, final ICallback callback, boolean jsonformat){
		new Thread(){			
			public void run() {	
				Response resp = doPost(url,json.toString());

				Logger.debug("Connect Resp1 : " + resp.getBody() + "(" + resp.getStatusCode() + ")");
				
				if (resp.getStatusCode() == 200){
					
					JSONObject jo = null;
					try {
						jo = new JSONObject(resp.getBody());
					} catch (JSONException e) {
						callback.onResult(null);
						e.printStackTrace();
						return;
					}
					callback.onResult(jo);
				} else {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						Logger.debug(e.toString());
					}
				}
			}
			
		}.start();
	}	
	
	public void connectWithHeader(final Header[] headers, final String url, final ICallback callback, boolean jsonformat){
		new Thread(){			
			public void run() {	
				Response resp = doGet(url,headers);

				Logger.debug("Connect Resp4 : " + resp.getBody() + "(" + resp.getStatusCode() + ")");
				
				if (resp.getStatusCode() == 200){
					
					JSONObject jo = null;
					try {
						jo = new JSONObject(resp.getBody());
					} catch (JSONException e) {
						callback.onResult(null);
						e.printStackTrace();
						return;
					}
					callback.onResult(jo);
				} else {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						Logger.debug(e.toString());
					}
				}
			}
			
		}.start();
	}
	
	
	public void connect(final JSONObject json, final String url, final ICallback callback, boolean jsonformat){
		new Thread(){			
			public void run() {	
				Response resp = doGet(url);

				Logger.debug("Connect Resp2 : " + resp.getBody() + "(" + resp.getStatusCode() + ")");
				
				if (resp.getStatusCode() == 200){
					
					JSONObject jo = null;
					try {
						jo = new JSONObject(resp.getBody());
					} catch (JSONException e) {
						callback.onResult(null);
						e.printStackTrace();
						return;
					}
					callback.onResult(jo);
				} else {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						Logger.debug(e.toString());
					}
				}
			}
			
		}.start();
	}

	
	public void connect(final JSONObject json, final String url, final IJsonCallback callback, boolean jsonformat){
		new Thread(){			
			public void run() {	
				Response resp = doGet(url);

				Logger.debug("Connect Resp2 : " + resp.getBody() + "(" + resp.getStatusCode() + ")");
				
				if (resp.getStatusCode() == 200){
					
					JSONObject jo = null;
					try {
						jo = new JSONObject(resp.getBody());
					} catch (JSONException e) {
						callback.onResult(200,null);
						e.printStackTrace();
						return;
					}
					callback.onResult(200,jo);
				} else {
					try {
						callback.onResult(resp.getStatusCode(),null);
						sleep(2000);
					} catch (InterruptedException e) {
						Logger.debug(e.toString());
					}
				}
			}
			
		}.start();
	}
	
	
	public void connectHtml(final JSONObject json, final String url, final IRspCallback callback){
		new Thread(){			
			public void run() {	
				Logger.debug("HTMLurl="+url);
				Response resp = doGet(url);

				Logger.debug("Connect Resp3 : " + resp.getBody() + "(" + resp.getStatusCode() + ")");
				
				if (resp.getStatusCode() == 200 || resp.getStatusCode() == 302){

					
					callback.onResult(resp.getStatusCode(), resp.getBody());
				} else {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						Logger.debug(e.toString());
					}
				}
			}
			
		}.start();
	}
	
	
	public void connectHtmlWithHeader(final JSONObject json, final String url,final Header[] headers, final IRspCallback callback){
		new Thread(){			
			public void run() {	
				Logger.debug("HTMLurl="+url);
				Response resp = doGet(url,headers);

				Logger.debug("Connect Resp3 : " + resp.getBody() + "(" + resp.getStatusCode() + ")");
				
				if (resp.getStatusCode() == 200 || resp.getStatusCode() == 302){

					
					callback.onResult(resp.getStatusCode(), resp.getBody());
				} else {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						Logger.debug(e.toString());
					}
				}
			}
			
		}.start();
	}

	
	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	void executeInSession(final Runnable task){	
		new Thread(){

			
			public void run() {
				while(session == null)
					try {
						sleep(100);
					} catch (InterruptedException e) {
						Logger.debug(e.toString());
					}
				if (session != null)
					task.run();
				
			}
			
		}.start();
	}


	
public static  Bitmap loadImageFromUrl(String url,IImgRspCallback cbFunc) throws Exception  {
		
		//Logger.debug("imageurl="+url);
		if(url == null || url.equals(""))
			return null;
        final DefaultHttpClient client = new DefaultHttpClient();
        final HttpGet getRequest = new HttpGet(url);
        
        HttpResponse response = client.execute(getRequest);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK)  {
        	Logger.debug("Request URL failed, error code =" + statusCode);
        }
        
        HttpEntity entity = response.getEntity();
        if (entity == null) {
        	Logger.debug("HttpEntity is null");
        }
        InputStream is = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            is = entity.getContent();
            byte[] buf = new byte[1024];
            int readBytes = -1;
            while ((readBytes = is.read(buf)) != -1) {
                baos.write(buf, 0, readBytes);
            }
        } finally {
            if (baos != null) {
                baos.close();
            }
            if (is != null) {
                is.close();
            }
        }
        byte[] imageArray = baos.toByteArray();
        Bitmap bmp = BitmapFactory.decodeByteArray(
                imageArray, 0, imageArray.length);
        if(cbFunc != null)
        	cbFunc.onResult(bmp);
        
        return bmp;
	}	
	
	public static  Bitmap loadImageFromUrl(String url,int idx, IImgRspWithIdxCallback cbFunc) throws Exception  {
		
		//Logger.debug("imageurl="+url);
		if(url == null || url.equals(""))
			return null;
        final DefaultHttpClient client = new DefaultHttpClient();
        final HttpGet getRequest = new HttpGet(url);
        
        HttpResponse response = client.execute(getRequest);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK)  {
        	Logger.debug("Request URL failed, error code =" + statusCode);
        }
        
        HttpEntity entity = response.getEntity();
        if (entity == null) {
        	Logger.debug("HttpEntity is null");
        }
        InputStream is = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            is = entity.getContent();
            byte[] buf = new byte[1024];
            int readBytes = -1;
            while ((readBytes = is.read(buf)) != -1) {
                baos.write(buf, 0, readBytes);
            }
        } finally {
            if (baos != null) {
                baos.close();
            }
            if (is != null) {
                is.close();
            }
        }
        
        
        byte[] imageArray = baos.toByteArray();
        Bitmap bmp = BitmapFactory.decodeByteArray(
                imageArray, 0, imageArray.length);
        if(cbFunc != null)
        	cbFunc.onResult(bmp, idx);
        
        return bmp;
	}	
	
	
	public static  Bitmap loadImageFromUrl(String url,String Path, IImgRspWithUrlCallback cbFunc) throws Exception  {
		
		Logger.debug("Ad:imageurl="+url);
		if(url == null || url.equals(""))
			return null;
        final DefaultHttpClient client = new DefaultHttpClient();
        final HttpGet getRequest = new HttpGet(url);
        
        HttpResponse response = client.execute(getRequest);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK)  {
        	Logger.warn("Request URL failed, error code =" + statusCode);
        }
        
        HttpEntity entity = response.getEntity();
        if (entity == null) {
        	Logger.debug("HttpEntity is null");
        }
        InputStream is = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            is = entity.getContent();
            byte[] buf = new byte[1024];
            int readBytes = -1;
            while ((readBytes = is.read(buf)) != -1) {
                baos.write(buf, 0, readBytes);
            }
        } finally {
            if (baos != null) {
                baos.close();
            }
            if (is != null) {
                is.close();
            }
        }
        
        
        byte[] imageArray = baos.toByteArray();
        Bitmap bmp = BitmapFactory.decodeByteArray(
                imageArray, 0, imageArray.length);
        if(cbFunc != null)
        	cbFunc.onResult(bmp, Path);
        
        return bmp;
	}		
}

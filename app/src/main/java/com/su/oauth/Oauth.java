package com.su.oauth;

import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import com.su.provider.SuProvider;

import android.content.Context;
import android.telephony.TelephonyManager;
;

public class Oauth {

	//public final static String VRE_SRV_BASE_SIGNATURE_STRING1 = "GET&http%3A%2F%2Fmtk-mobile.query.yahooapis.com%2Fv1%2Fyql&diagnostics%3Dfalse%26env%3Dstore%253A%252F%252F169FyUsNjzk9RLnBA3fiEH%26format%3Djson%26oauth_consumer_key%3Ddj0yJmk9QTNrMHV0aU9YbWxtJmQ9WVdrOU5ISk5TM2huTjJVbWNHbzlPREF4TWpFM016WXkmcz1jb25zdW1lcnNlY3JldCZ4PWVh%26oauth_nonce%3D1234%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D";
	public final static String VRE_SRV_BASE_SIGNATURE_STRING1_NEWS = "GET&http%3A%2F%2Fmtk-mobile.query.yahooapis.com%2Fv1%2Fyql&diagnostics%3Dfalse%26env%3Dstore%253A%252F%252F169FyUsNjzk9RLnBA3fiEH%26format%3Djson%26oauth_consumer_key%3Ddj0yJmk9VjlwMUVNcG11V1RjJmQ9WVdrOWQzQmhSVTl6TjJrbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1jYg--%26oauth_nonce%3D1234%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D";
	public final static String VRE_SRV_BASE_SIGNATURE_STRING1_WEATHER = "GET&http%3A%2F%2Fmtk-mobile.query.yahooapis.com%2Fv1%2Fyql&diagnostics%3Dfalse%26env%3Dstore%253A%252F%252F169FyUsNjzk9RLnBA3fiEH%26format%3Djson%26oauth_consumer_key%3Ddj0yJmk9Wkd6b2Q0QXdRVktUJmQ9WVdrOWNtaHJhbmRZTnpJbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1iYg--%26oauth_nonce%3D1234%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D";
	public final static String VRE_SRV_BASE_SIGNATURE_STRING1_FINANCE = "GET&http%3A%2F%2Fmtk-mobile.query.yahooapis.com%2Fv1%2Fyql&diagnostics%3Dfalse%26env%3Dstore%253A%252F%252F169FyUsNjzk9RLnBA3fiEH%26format%3Djson%26oauth_consumer_key%3Ddj0yJmk9ZXNCYmJYTWFNTzhrJmQ9WVdrOVpYZzFRak4yTTJNbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD0xZQ--%26oauth_nonce%3D1234%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D";
	
	
	public final static String VRE_SRV_BASE_SIGNATURE_STRING2 = "%26oauth_version%3D1.0%26q%3D";
	//public final static String VRE_SRV_OAUTH_HEADER1 = "OAuth oauth_consumer_key=\"dj0yJmk9QTNrMHV0aU9YbWxtJmQ9WVdrOU5ISk5TM2huTjJVbWNHbzlPREF4TWpFM016WXkmcz1jb25zdW1lcnNlY3JldCZ4PWVh\",oauth_nonce=\"1234\",oauth_signature=\"";
	
	public final static String VRE_SRV_OAUTH_HEADER1_NEWS = "OAuth oauth_consumer_key=\"dj0yJmk9VjlwMUVNcG11V1RjJmQ9WVdrOWQzQmhSVTl6TjJrbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1jYg--\",oauth_nonce=\"1234\",oauth_signature=\"";
	public final static String VRE_SRV_OAUTH_HEADER1_WEATHER = "OAuth oauth_consumer_key=\"dj0yJmk9Wkd6b2Q0QXdRVktUJmQ9WVdrOWNtaHJhbmRZTnpJbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1iYg--\",oauth_nonce=\"1234\",oauth_signature=\"";
	public final static String VRE_SRV_OAUTH_HEADER1_FINANCE = "OAuth oauth_consumer_key=\"dj0yJmk9ZXNCYmJYTWFNTzhrJmQ9WVdrOVpYZzFRak4yTTJNbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD0xZQ--\",oauth_nonce=\"1234\",oauth_signature=\"";
	
	public final static String VRE_SRV_OAUTH_HEADER2 = "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"";
	public final static String VRE_SRV_OAUTH_HEADER3 = "\",oauth_version=\"1.0\"";
	public final static String VRE_XHEADER_UUID_MT6253_YN = "476d6f6269-505945";	// MT6253 & Yahoo News(YN)in hex
	public final static String VRE_XHEADER_UUID_VERSION = "-4100-";		// Version 4 of UUID and version 1.0.0 of Yahoo News Client
	public final static String VRE_XHEADER_UUID_VARIANT_1 = "afff-";		// variant part of UUID( when IMEI is 12 hex nibbles,with a as variant and f's are dummy nibbles)
	public final static String VRE_XHEADER_UUID_VARIANT_2 = "aff";		// variant part of UUID( when IMEI is of 13 hex nibbles, with a as variant and f's are dummy nibbles)
	
	//public final static String VRE_YN_SRV_SHARED_SECRET_KEY	= "486a118af5de2b0fd7d5db68f557fee03d2ab02f&";
	public final static String VRE_YN_SRV_SHARED_SECRET_KEY_NEWS 	= "354612cdcb0caa2fb22be73edd65e14ab4d396db&";
	public final static String VRE_YN_SRV_SHARED_SECRET_KEY_WEATHER = "01f0fc1654b5c206938584fb77b4625407e3b2ff&";
	public final static String VRE_YN_SRV_SHARED_SECRET_KEY_FINANCE = "cf827fb04d2cd4bc34e61a32892ec65bbec629e6&";
	
	
	public final static String VRE_SRV_YAHOO_SERVICES_URL = "http://mtk-mobile.query.yahooapis.com/v1/yql?diagnostics=false&env=store%3A%2F%2F169FyUsNjzk9RLnBA3fiEH&format=json&q=";
	
	//private RemoteService rs = RemoteService.getInstance();
	
	public final static String SENDER_NEWS = "news";
	public final static String SENDER_WEATHER = "weatehr";
	public final static String SENDER_FINANCE = "finance";
	
	public Header[] getOauthHeaders(String SelectUrl,String sender)
	{
		String VRE_YN_SRV_SHARED_SECRET_KEY = null;
		String VRE_SRV_BASE_SIGNATURE_STRING1 = null;
		String VRE_SRV_OAUTH_HEADER1 = null;
		if(sender.equals(SENDER_NEWS))
		{
			VRE_YN_SRV_SHARED_SECRET_KEY = VRE_YN_SRV_SHARED_SECRET_KEY_NEWS;
			VRE_SRV_BASE_SIGNATURE_STRING1 = VRE_SRV_BASE_SIGNATURE_STRING1_NEWS;
			VRE_SRV_OAUTH_HEADER1 = VRE_SRV_OAUTH_HEADER1_NEWS;
		}
		else if (sender.equals(SENDER_WEATHER))
		{
			VRE_YN_SRV_SHARED_SECRET_KEY = VRE_YN_SRV_SHARED_SECRET_KEY_WEATHER;
			VRE_SRV_BASE_SIGNATURE_STRING1 = VRE_SRV_BASE_SIGNATURE_STRING1_WEATHER;
			VRE_SRV_OAUTH_HEADER1 = VRE_SRV_OAUTH_HEADER1_WEATHER;
			
		}
		else if (sender.equals(SENDER_FINANCE))
		{
			VRE_SRV_BASE_SIGNATURE_STRING1 = VRE_SRV_BASE_SIGNATURE_STRING1_FINANCE;
			VRE_YN_SRV_SHARED_SECRET_KEY = VRE_YN_SRV_SHARED_SECRET_KEY_FINANCE;
			VRE_SRV_OAUTH_HEADER1 = VRE_SRV_OAUTH_HEADER1_FINANCE;
		}
			
		
		
		String authHeadStr = null;
		String base_signature_string;
		byte[] oauth_signature = null;
		String oauth_signature_base_64;
		
		long curUnixTime = System.currentTimeMillis()/1000;
		String curTimeStamp = Long.toString(curUnixTime);

		String encodeSelect = java.net.URLEncoder.encode(SelectUrl);
		base_signature_string = VRE_SRV_BASE_SIGNATURE_STRING1 + curTimeStamp + VRE_SRV_BASE_SIGNATURE_STRING2 + encodeSelect;
		 

		
	    /* Generate Oauth signature */
		try {
			oauth_signature = HMACSHA1.getSignature(base_signature_string.getBytes(), VRE_YN_SRV_SHARED_SECRET_KEY.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		
		oauth_signature_base_64 = new BASE64Encoder().encode(oauth_signature);
	    oauth_signature_base_64 = java.net.URLEncoder.encode(oauth_signature_base_64);
	    
	    /* Generate HTTP Header */
	    authHeadStr = VRE_SRV_OAUTH_HEADER1 + oauth_signature_base_64 + VRE_SRV_OAUTH_HEADER2 + curTimeStamp + VRE_SRV_OAUTH_HEADER3;
	    Header[] headers = new Header[5];    
	    headers[0] = new BasicHeader("Authorization", authHeadStr);
	    

		//adding header X-Client-UUID
	    String uuidHeaderStr = "0000000000000000000000000000000000000000";
	    if(SuProvider.getImei() != null)
	    {
		    long imsiLong = Long.parseLong(SuProvider.getImei());
		    String l_imei = Long.toHexString(imsiLong);
		    uuidHeaderStr = VRE_XHEADER_UUID_MT6253_YN + VRE_XHEADER_UUID_VERSION; 

			if(l_imei.length()== 12)
			{
				uuidHeaderStr = uuidHeaderStr + VRE_XHEADER_UUID_VARIANT_1 + l_imei;
			}
			else 
			{
				uuidHeaderStr = uuidHeaderStr + VRE_XHEADER_UUID_VARIANT_2 + l_imei.charAt(0) + "-" + l_imei.substring(1);				
			}
	    }
	    headers[1] = new BasicHeader("X-Client-UUID", uuidHeaderStr);

	    //adding header X-Client-Info	    
	    headers[2] = new BasicHeader("X-Client-Info", "vendor=\"Gmobi\"; model=\"YahooPanel\"; version=\"1.0.0.0\"");

	    //adding header X-Device-Info
	    headers[3] = new BasicHeader("X-Device-Info","make=\""+android.os.Build.MANUFACTURER+"\"; model=\""+android.os.Build.MODEL+"\"; os=\"Android\"; osver=\" "+android.os.Build.VERSION.RELEASE+"\"");
	    //adding header X-Device-User-Agent
 
	    headers[4] = new BasicHeader("X-Device-User-Agent","YahooPanel");
	    
	    
	    return headers;
	    
		
	}
	
	
	
}

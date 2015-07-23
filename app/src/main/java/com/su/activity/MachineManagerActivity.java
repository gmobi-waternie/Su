package com.su.activity;

import com.waternie.su.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MachineManagerActivity extends Activity {
	TextView btn_close;
	TextView btn_add;
	TextView btn_del;
	TextView btn_save;
	TextView btn_cancel;
	
	EditText edt_new;
	ListView lv_sentence;
	LinearLayout ll_new;
	
	private boolean editMode = false; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_manager);
        
        
        
	}
}

package com.su.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.su.app.MessageNames;
import com.su.app.SuApplication;
import com.su.helper.CacheHelper;
import com.su.utils.DipHelper;
import com.waternie.su.R;

import java.util.ArrayList;

import static android.view.View.*;

public class MachineManagerActivity extends Activity {

	
	private boolean editMode = true;
	private ArrayList<String> allSentence ;
	private CacheHelper ch;
	private BaseAdapter sentenceAdapter;

	private ImageView btnmanageradd;

	private EditText edtmanagernew;
	private ImageView btnmanagersave;
	private ImageView btnmanagercancel;
	private LinearLayout llNew;
	private ListView lvmachinesentence;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_manager);

		initialize();
		handleMsg();

		ch = CacheHelper.getInstance(this);
		allSentence = ch.getAllReadSentence();

		sentenceAdapter = new MachineSentenceAdapter(this,allSentence);
		lvmachinesentence.setAdapter(sentenceAdapter);

		btnmanagersave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String newSt = edtmanagernew.getText().toString();
				ch.addMachineSentence(newSt);
				allSentence.add(newSt);
				edtmanagernew.setText("");
				SuApplication.msghandler.sendEmptyMessage(MessageNames.MSG_UI_VALIDATE_SENTENCE_MANAGER);

			}
		});

		btnmanageradd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				llNew.setVisibility(VISIBLE);
			}
		});
		btnmanagercancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				edtmanagernew.setText("");
				llNew.setVisibility(GONE);
			}
		});




	}

	private void initialize() {
		btnmanageradd = (ImageView) findViewById(R.id.btn_manager_add);
		edtmanagernew = (EditText) findViewById(R.id.edt_manager_new);
		btnmanagersave = (ImageView) findViewById(R.id.btn_manager_save);
		btnmanagercancel = (ImageView) findViewById(R.id.btn_manager_cancel);
		lvmachinesentence = (ListView) findViewById(R.id.lv_machine_sentence);

		llNew = (LinearLayout)findViewById(R.id.ll_add_sentence);
		llNew.setVisibility(GONE);
	}

	public void handleMsg() {
		SuApplication.msghandler= new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what)
				{
					case MessageNames.MSG_UI_VALIDATE_SENTENCE_MANAGER:
						sentenceAdapter.notifyDataSetChanged();
						break;
				}
			}
		};
	}



	static class ViewHolder
	{
		public ImageView del;
		public TextView title;
		public LinearLayout ll_item;
	}

	class MachineSentenceAdapter extends BaseAdapter {
		private ArrayList<String> dataSrc;
		private Context mContext;
		private LayoutInflater mInflater = null;
		private MachineSentenceAdapter self;

		public MachineSentenceAdapter(Context ctx, ArrayList<String> src) {
			dataSrc = src;
			mContext = ctx;
			mInflater = LayoutInflater.from(ctx);
			self = this;
		}


		public int getCount() {
			return dataSrc.size();
		}

		public Object getItem(int position) {
			return dataSrc.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null) {
				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.list_item_sentence, null);
				holder.del = (ImageView) convertView.findViewById(R.id.iv_del);
				holder.title = (TextView) convertView.findViewById(R.id.tv_sentence);
				holder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_sentence_item);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.title.setText((String) dataSrc.get(position));
			holder.del.setVisibility(editMode ? VISIBLE : GONE);
			holder.ll_item.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, DipHelper.dip2px(50)));
			if(editMode)
			{
				holder.del.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						ch.removeMachineSentence(dataSrc.get(position));
						dataSrc.remove(position);
						SuApplication.msghandler.sendEmptyMessage(MessageNames.MSG_UI_VALIDATE_SENTENCE_MANAGER);

					}
				});
			}




			return convertView;

		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();


		finish();

	}
}

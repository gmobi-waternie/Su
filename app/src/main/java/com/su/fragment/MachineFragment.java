package com.su.fragment;

import java.util.ArrayList;

import com.su.activity.MachineManagerActivity;
import com.su.helper.CacheHelper;
import com.su.view.MachineView;
import com.waternie.su.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

@SuppressLint("NewApi") 
public class MachineFragment extends Fragment {
	
	private static final String[] defSentence ={"南无阿弥陀佛","南无观世音菩萨"};
	private String curSentence = null;
	private CacheHelper ch;
	private Spinner buddhaSentenceSpinner;
	private ArrayAdapter<String> sentenceAdapter;

	private MachineView mv;

	private Button readBtn;
	private TextView managerBtn;
	private TextView resetBtn;
	
	private void openManager()
	{
		startActivity(new Intent(getActivity(),MachineManagerActivity.class));
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ch = CacheHelper.getInstance(getActivity());
		
		View v = inflater.inflate(R.layout.fragment_machine, container,false);
		mv = (MachineView) v.findViewById(R.id.machine);

		readBtn = (Button) v.findViewById(R.id.btn_read);
		managerBtn = (TextView) v.findViewById(R.id.btn_manager);
		resetBtn = (TextView) v.findViewById(R.id.btn_reset);
		buddhaSentenceSpinner = (Spinner)v.findViewById(R.id.spinner_sentence);



		buddhaSentenceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {

				curSentence = sentenceAdapter.getItem(position);
				int count = ch.getReadMachineCount(curSentence);
				mv.setCount(count);
				readBtn.setClickable(true);
			}

			public void onNothingSelected(AdapterView<?> parent) {
				curSentence = "";
				mv.setCount(0);
				readBtn.setClickable(false);

			}
		});

		updateSentenceList();
		int count = ch.getReadMachineCount(curSentence);
		mv.setCount(count);
		
		
		managerBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {				
				openManager();
			}
		});
		
		readBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {				
				mv.incCount();
				ch.setReadMachineCount(curSentence, mv.getCount());
			}
		});
		
		resetBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {				
				mv.setCount(0);
				ch.setReadMachineCount(curSentence, mv.getCount());
			}
		});
		return v;
	}


	@Override
	public void onStart() {
		super.onStart();
		updateSentenceList();
	}

	public void updateSentenceList()
	{
		ArrayList<String> allSentence = ch.getAllReadSentence();

		if(allSentence.isEmpty())
		{
			sentenceAdapter=new ArrayAdapter<String>(getActivity(), R.layout.sentence_spinner, defSentence);
			sentenceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			buddhaSentenceSpinner.setAdapter(sentenceAdapter);
			curSentence = defSentence[0];
		}
		else
		{
			sentenceAdapter=new ArrayAdapter<String>(getActivity(), R.layout.sentence_spinner, allSentence);
			sentenceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			buddhaSentenceSpinner.setAdapter(sentenceAdapter);
			curSentence = allSentence.get(0);
		}
	}

}

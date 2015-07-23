package com.su.fragment;


import java.util.Calendar;
import java.util.Date;

import com.su.datautil.Lunar;
import com.su.view.CalendarView;
import com.su.view.CalendarView.OnItemClickListener;
import com.waternie.su.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") 
public class CalendarFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View v = inflater.inflate(R.layout.fragment_calendar, container,false);
		
		

        final CalendarView calendar = (CalendarView) v.findViewById(R.id.calendar);
        TextView tvLunar = (TextView) v.findViewById(R.id.tvLunar);
        final TextView tvCal = (TextView) v.findViewById(R.id.tvCal);
        String yearandmonth = calendar.getYearAndmonth();
		tvCal.setText(yearandmonth);
		
		ImageButton preMonth = (ImageButton)v.findViewById(R.id.btn_premonth);
        ImageButton nextMonth = (ImageButton)v.findViewById(R.id.btn_nextmonth);
        
        Lunar lunar = new Lunar(Calendar.getInstance());

        String lunarStr = "";
        lunarStr=lunar.animalsYear()+"(";
        lunarStr +=lunar.cyclical()+")";
        lunarStr +=lunar.toString();
        tvLunar.setText(lunarStr);
        
        
        
        preMonth.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String yearandmonth = calendar.clickLeftMonth();
				tvCal.setText(yearandmonth);
			}
		});
        nextMonth.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String yearandmonth = calendar.clickRightMonth();
				tvCal.setText(yearandmonth);
			}
		});
       
        calendar.setOnItemClickListener(new calendarItemClickListener());
		return v;
	}


	 

	 private class calendarItemClickListener implements OnItemClickListener{  
			public void OnItemClick(Date date) {
				Toast.makeText(getActivity(), date+"", Toast.LENGTH_SHORT).show();
				
			}  
     }	
	 

	
	
}

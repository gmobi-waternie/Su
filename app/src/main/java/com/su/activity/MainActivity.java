package com.su.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.su.adapter.MenuAdapter;
import com.su.app.MessageNames;
import com.su.app.SuApplication;
import com.su.datautil.Lunar;
import com.su.fragment.CalendarFragment;
import com.su.fragment.MachineFragment;
import com.su.model.MenuItem;
import com.su.utils.DipHelper;
import com.su.view.CalendarView;
import com.su.view.CalendarView.OnItemClickListener;
import com.waternie.su.R;
import com.waternie.su.R.array;
import com.waternie.su.R.id;
import com.waternie.su.R.layout;
import com.waternie.su.R.menu;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") 
public class MainActivity extends Activity {
	private String[] mMenuTitles;
	private ArrayList<MenuItem> mMenuList;
	private MenuAdapter mMenuAdp;
	 private ListView mDrawerList;
	 private DrawerLayout mDrawerLayout;
	
	private Fragment Fragment_Cal,Fragment_Mac;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    @SuppressLint("NewApi") @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DipHelper.init(this);


        mMenuTitles = getResources().getStringArray(R.array.menu_array);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        
        mMenuList=new ArrayList<MenuItem>();
        mMenuList.add(new MenuItem(R.drawable.menu_cal, mMenuTitles[0]));
        mMenuList.add(new MenuItem(R.drawable.menu_sound, mMenuTitles[1]));
        
        mMenuAdp = new MenuAdapter(getApplicationContext(), mMenuList);
        
        
        mDrawerList.setAdapter(mMenuAdp);
        
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());




        Fragment_Cal = new CalendarFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_frame, Fragment_Cal,CalendarFragment.class.getName());
        fragmentTransaction.commit();
        
    }

	 /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
        Toast.makeText(this, "Toggle "+mMenuTitles[position], Toast.LENGTH_SHORT).show();
        fragmentManager = getFragmentManager();
        
        if(position == 0)
        {
            if(Fragment_Cal == null)
            	Fragment_Cal = new CalendarFragment();
            
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, Fragment_Cal,CalendarFragment.class.getName());
            fragmentTransaction.commit();
        }
        else if(position == 1)
        {
        	if(Fragment_Mac == null)
        		Fragment_Mac = new MachineFragment();
            
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, Fragment_Mac,MachineFragment.class.getName());
            fragmentTransaction.commit();
        }
        
    }
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
			
			
			
		}
    }



    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


}

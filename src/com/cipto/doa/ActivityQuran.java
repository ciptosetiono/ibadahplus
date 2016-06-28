package com.cipto.doa;

import java.util.HashMap;
import java.util.List;

import com.cipto.doa.R.id;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;

public class ActivityQuran extends Activity implements OnQueryTextListener{
	DBAdapter dbAdapter;
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quran);
		
		ActionBar ab = getActionBar(); 
		// ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    dbAdapter = new DBAdapter(getApplicationContext());
	    dbAdapter.openDataBase();
			
		lv = (ListView)findViewById(id.listSurah);
		List<HashMap<String,String>> listUsers = dbAdapter.getAllSurah();
			
	 	String[] from = new String[] { "teks_indo","teks_arab","id_surah" };
		int[] to = new int[] { R.id.judul, R.id.teksArab, R.id.idDoa};
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), listUsers, R.layout.list_row_surah, from, to);
		
		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new ListClickHandler());
	}
	
	public class ListClickHandler implements OnItemClickListener{
	  	@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				TextView listText = (TextView) view.findViewById(R.id.idDoa);
		        String text = listText.getText().toString();
				Intent intent = new Intent(ActivityQuran.this, ActivityDisplaySurah.class);
		        intent.putExtra("id",text);
		        startActivity(intent);  
			}
	  
	}
    @SuppressLint("NewApi") @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu_search, menu);
    	 
    	// Associate searchable configuration with the SearchView
    	SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
    	//searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    	//searchView.setSubmitButtonEnabled(true);
    	searchView.setOnQueryTextListener(this);
    	
    	return super.onCreateOptionsMenu(menu);
    }
	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		SimpleAdapter ca = (SimpleAdapter)lv.getAdapter();
		String[] words=newText.split(" ");
		if (TextUtils.isEmpty(newText)) {
			ca.getFilter().filter(null);
		    } else {    	
		    	for(String item :words){
		    		ca.getFilter().filter(item);
		    	} 
		    }
		   return true;
	}


	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	        	 Intent home = new Intent(ActivityQuran.this, Main.class);
		            home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        startActivity(home);  
	            return true;
	            default:
	            return super.onOptionsItemSelected(item);       
	    }
	}
}

package nosafespot.todo.activitys;

import java.util.ArrayList;
import java.util.List;

import nosafespot.todo.DataSource;
import nosafespot.todo.R;
import nosafespot.todo.Statics;
import nosafespot.todo.containers.Entry;
import nosafespot.todo.containers.TodoList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class EntrysActivity extends Activity implements OnClickListener{
	private DataSource mDataSource;
	private List<Entry> mEntrys;
	private int mID;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entrys_view);
		Bundle extras = getIntent().getExtras();
		mID = extras.getInt(Statics.LIST_ID_EXTRA);
		mDataSource = new DataSource(this);
		setTitleBar();
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == Statics.ADD_ENTRY_BUTTON_ID){
			Intent i = new Intent(this, NewEntryActivity.class);
			i.putExtra(Statics.LIST_ID_EXTRA, mID);
			startActivity(i);
		}
		else if((v.getId() == R.id.btnDeleteList)){
			deleteList();
		}
		else if((v.getId() == R.id.btnRemoveChecked)){
			//TODO
		}
		else{
			//Check uncheck entry
			
		}
	}
	/**
	 * Set the titlebar text to the name of the list
	 */
	private void setTitleBar(){
		TextView txt = (TextView)findViewById(R.id.txtTitleBar);
		String name = "Title";
		mDataSource.open();
		name = mDataSource.getListName(mID);
		mDataSource.close();
		txt.setText(name);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		refreshScrollView();
	}

	private void refreshScrollView() {
		try{
			mDataSource.open();
			mEntrys = mDataSource.getEntrys(mID);
			mDataSource.close();
		}
		catch(Exception e){
			//TODO 
		}
		ScrollView view = (ScrollView)findViewById(R.id.scrollview_entrys_view);
		if(view.getChildCount() > 0){
			view.removeAllViews();
		}
		addEntryItemToScrollView();
	}

	private void deleteList(){
		mDataSource.open();
		mDataSource.deleteList(mID);
		mDataSource.close();
		finish();
	}
	
	private void removeCheckedItems(){
		
	}
	
	private void addEntryItemToScrollView(){
		LinearLayout.LayoutParams llp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		llp.weight = 1.0f;
		View insertPoint = findViewById(R.id.scrollview_entrys_view);
		LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setLayoutParams(llp);
		
		//Add the list items
		for(Entry e : mEntrys){
			View item = inflater.inflate(R.layout.list_item, null);
			if(item != null){ 
				item.setId(e.getID());
				item.setOnClickListener(this); 
				item.setLayoutParams(llp);
			}
			TextView txt = (TextView) item.findViewById(R.id.textview_entrylabel_list_item);
			if(txt != null){
				txt.setText(e.getName());
			}
			linearLayout.addView(item);
		}
		linearLayout.addView(getAddButtonView());
		linearLayout.addView(getRemoveDeleteView());
		((ViewGroup) insertPoint).addView(linearLayout, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
	}
	/**
	 * returns a delete remove checked view with onclicklisteners
	 * @return View
	 */
	private View getRemoveDeleteView(){
		LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View item = inflater.inflate(R.layout.entry_header, null);
		View v = item.findViewById(R.id.btnDeleteList);
		v.setOnClickListener(this);
		v = item.findViewById(R.id.btnRemoveChecked);
		v.setOnClickListener(this);
		return item;
	}
	
	
	/**
	 * Returns a add view button to be added last in the view
	 * @return View
	 */
	private View getAddButtonView(){
		LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View item = inflater.inflate(R.layout.list_item, null);
		if(item != null){ 
			item.setId(Statics.ADD_ENTRY_BUTTON_ID);
			item.setOnClickListener(this); 
		}
		TextView txt = (TextView) item.findViewById(R.id.textview_entrylabel_list_item);
		if(txt != null){
			txt.setText("+");
		}
		return item;
	}//--getAddButtonView
	
	
	private List<Entry> getTestEntrys(){
		List<Entry> entrys = new ArrayList<Entry>();
		entrys.add(new Entry(0, 0, "Falu Korv", 0));
		entrys.add(new Entry(1, 0, "Frukost Korv", 0));
		entrys.add(new Entry(2, 0, "Lunch Korv", 0));
		entrys.add(new Entry(3, 0, "Choritzu", 0));
		entrys.add(new Entry(4, 0, "DFFDGDFGDFG", 0));
		return entrys;
	}

}

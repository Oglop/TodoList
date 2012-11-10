package nosafespot.todo.activitys;

import java.util.ArrayList;
import java.util.List;

import nosafespot.todo.DataSource;
import nosafespot.todo.R;
import nosafespot.todo.Statics;
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
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ListsActivity extends Activity implements OnClickListener {
	private List<TodoList> mLists = new ArrayList<TodoList>();
	private DataSource mDataSource;
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lists_view);
		mDataSource = new DataSource(this);
		//mLists = mDataSource.getLists();
		mLists = getTestData();
		addListItemsToScrollView();
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == Statics.ADD_LIST_BUTTON_ID){
			//TODO Add a new 
		}
		else{
			Intent intent = new Intent(this, EntrysActivity.class);
			intent.putExtra(Statics.LIST_ID_EXTRA, view.getId());
			startActivity(intent);
		}
	}
	
	/**
	 * Adds list items to the list
	 */
	private void addListItemsToScrollView(){
		LinearLayout.LayoutParams llp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		llp.weight = 1.0f;
		View insertPoint = findViewById(R.id.scrollview_lists_view);
		LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setLayoutParams(llp);
		
		//Add the list items
		for(TodoList lstItem : mLists){
			View item = inflater.inflate(R.layout.list_item, null);
			if(item != null){ 
				item.setId(lstItem.getId());
				item.setOnClickListener(this); 
				item.setLayoutParams(llp);
			}
			TextView txt = (TextView) item.findViewById(R.id.textview_entrylabel_list_item);
			if(txt != null){
				txt.setText(lstItem.getName());
			}
			linearLayout.addView(item);
		}
		
		//--add the addNewList button
		View v = getAddButtonView();
		v.setLayoutParams(llp);
		linearLayout.addView(v);
		
		((ViewGroup) insertPoint).addView(linearLayout, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		
	}
	
	/**
	 * Returns a add view button to be added last in the view
	 * @return
	 */
	private View getAddButtonView(){
		LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View item = inflater.inflate(R.layout.list_item, null);
		if(item != null){ 
			item.setId(Statics.ADD_LIST_BUTTON_ID);
			item.setOnClickListener(this); 
		}
		TextView txt = (TextView) item.findViewById(R.id.textview_entrylabel_list_item);
		if(txt != null){
			txt.setText("+");
		}
		return item;
	}
	
	private List<TodoList> getTestData(){
		List<TodoList> testList = new ArrayList<TodoList>();
		testList.add(new TodoList(0, "Korv"));
		testList.add(new TodoList(1, "Handla"));
		testList.add(new TodoList(2, "Ringa"));
		return testList;
	}
	
}

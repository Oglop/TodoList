package nosafespot.todo.activitys;

import java.util.ArrayList;
import java.util.List;

import nosafespot.todo.DataSource;
import nosafespot.todo.R;
import nosafespot.todo.SQLiteHelper;
import nosafespot.todo.Statics;
import nosafespot.todo.containers.TodoList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class ListsActivity extends Activity implements OnClickListener {
	private List<TodoList> mLists = new ArrayList<TodoList>();
	private DataSource mDataSource;
	private Dialog mDialogNewList;
	private EditText mListEditText;
	private int mOrderByState;
	private String mDescAsc;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lists_view);
		mOrderByState = 0;
		mDataSource = new DataSource(this);
		mLists = new ArrayList<TodoList>();
		setClickListener();
	}
	
	private void setClickListener() {
		View view = findViewById(R.id.imgReturnArrow);
		view.setOnClickListener(this);
		view = findViewById(R.id.txtTitleBar);
		view.setOnClickListener(this);
		view = findViewById(R.id.txtOrderBy);
		view.setOnClickListener(this);
		view = findViewById(R.id.imgRefresh);
		view.setOnClickListener(this);
		view = findViewById(R.id.txtOrderClause);
		view.setOnClickListener(this);
	}



	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences sharedPrefs = getSharedPreferences(Statics.KEY_PREFERENCES, 0);
		mOrderByState = sharedPrefs.getInt(Statics.KEY_ORDER_LIST_BY_NUMBER, 0);
		refreshScrollView();
		
	}

	private void refreshScrollView(){
		try{
			mDataSource.open();
			mLists = mDataSource.getLists(getOrderByString(), mDescAsc);
			mDataSource.close();
		}
		catch(Exception e){
			//TODO 
		}
		ScrollView view = (ScrollView)findViewById(R.id.scrollview_lists_view);
		if(view.getChildCount() > 0){
			view.removeAllViews();
		}
		addListItemsToScrollView();
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == Statics.ADD_LIST_BUTTON_ID){
			//addButtonPressed();
			showNewListDialog();
		}
		else if(view.getId() == R.id.imgReturnArrow || view.getId() == R.id.txtTitleBar){
			finish();
		}
		else if(view.getId() == R.id.btnNewListOk){
			mDialogNewList.dismiss();
			addNewList();
			refreshScrollView();
		}
		else if(view.getId() == R.id.txtOrderBy ||
				view.getId() == R.id.txtOrderClause ||
				view.getId() == R.id.imgRefresh){
			setOrderByNumber();
			refreshScrollView();
		}
		else{
			Intent intent = new Intent(this, EntrysActivity.class);
			intent.putExtra(Statics.LIST_ID_EXTRA, view.getId());
			mDataSource.open();
			mDataSource.addOneViewToList(view.getId());
			mDataSource.close();
			startActivity(intent);
		}
	}
	
	private void showNewListDialog(){
		mDialogNewList = new Dialog(ListsActivity.this, android.R.style.Theme_Translucent);
		mDialogNewList.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialogNewList.setCancelable(true);
		mDialogNewList.setContentView(R.layout.new_list_view);
		Button btnOk = (Button) mDialogNewList.findViewById(R.id.btnNewListOk);
		btnOk.setOnClickListener(this);
		mListEditText = (EditText) mDialogNewList.findViewById(R.id.txtNewListName);
		mDialogNewList.show();
	}
	
	private void addNewList(){
		String name = mListEditText.getText().toString();
		if(!name.equals(" ")  && name.length() > 0){
			mDataSource.open();
			mDataSource.insertList(name);
			mDataSource.close();
			refreshScrollView();
		}
	}
	
	/**
	 * Adds list items to the list
	 */
	private void addListItemsToScrollView(){
		LinearLayout.LayoutParams llp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
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
		insertPoint.refreshDrawableState();
	}//----addListItemsToScrollView
	
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
	}//--getAddButtonView
	
	private String getOrderByString(){
		TextView view = (TextView) findViewById(R.id.txtOrderClause);
		
		switch(mOrderByState){
		case 0:
			view.setText(R.string.Newest);
			mDescAsc = Statics.ORDER_DESC;
			return SQLiteHelper.COL_0_LISTS_ID;
		case 1:
			view.setText(R.string.Oldest);
			mDescAsc = Statics.ORDER_ASC;
			return SQLiteHelper.COL_0_LISTS_ID;
		case 2:
			view.setText(R.string.Name);
			mDescAsc = Statics.ORDER_ASC;
			return SQLiteHelper.COL_1_LISTS_NAME;
		case 3:
			view.setText(R.string.Views);
			mDescAsc = Statics.ORDER_DESC;
			return SQLiteHelper.COL_2_LISTS_VIEWS;
		}
		view.setText(R.string.Date);
		return SQLiteHelper.COL_0_LISTS_ID;
	}
	
	private void setOrderByNumber(){
		SharedPreferences prefs = getSharedPreferences(Statics.KEY_PREFERENCES, 0);
		SharedPreferences.Editor editor = prefs.edit();
		switch(mOrderByState){
			case 0:		mOrderByState = 1;	editor.putInt(Statics.KEY_ORDER_LIST_BY_NUMBER, 1);		break;
			case 1:		mOrderByState = 2; 	editor.putInt(Statics.KEY_ORDER_LIST_BY_NUMBER, 2);		break;
			case 2:		mOrderByState = 3; 	editor.putInt(Statics.KEY_ORDER_LIST_BY_NUMBER, 3);		break;
			case 3:		mOrderByState = 0; 	editor.putInt(Statics.KEY_ORDER_LIST_BY_NUMBER, 0);		break;
		}
		editor.commit();
	}

}

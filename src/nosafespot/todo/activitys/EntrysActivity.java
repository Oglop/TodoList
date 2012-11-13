package nosafespot.todo.activitys;

import java.util.ArrayList;
import java.util.List;

import nosafespot.todo.DataSource;
import nosafespot.todo.R;
import nosafespot.todo.Statics;
import nosafespot.todo.containers.Entry;
import nosafespot.todo.containers.TodoList;
import nosafespot.todo.dialogs.ConfirmDialog;
import android.R.style;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class EntrysActivity extends Activity implements OnClickListener {
	private DataSource mDataSource;
	private List<Entry> mEntrys;
	private int mID;
	private Dialog mDialogDelete;
	private Dialog mDialogRemove;
	
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
		setClickListeners();
	}

	private void setClickListeners() {
		View view = findViewById(R.id.imgReturnArrow);
		view.setOnClickListener(this);
		view = findViewById(R.id.txtTitleBar);
		view.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == Statics.ADD_ENTRY_BUTTON_ID){
			Intent i = new Intent(this, NewEntryActivity.class);
			i.putExtra(Statics.LIST_ID_EXTRA, mID);
			startActivity(i);
		}
		else if((v.getId() == R.id.btnDeleteList)){
			showDeleteConfirmDialog();
		}
		else if((v.getId() == R.id.btnRemoveChecked)){
			showRemoveConfirmDialog();
		}
		else if(v.getId() == R.id.imgReturnArrow || v.getId() == R.id.txtTitleBar){
			finish();
		}
		else if(v.getId() == R.id.btnOKDelete){
			mDialogDelete.dismiss();
			deleteList();
		}
		else if(v.getId() == R.id.btnCancelDelete){
			mDialogDelete.dismiss();
		}
		else if(v.getId() == R.id.btnOKRemove){
			mDialogRemove.dismiss();
			removeCheckedEntrys();
		}
		else if(v.getId() == R.id.btnCancelRemove){
			mDialogRemove.dismiss();
		}
		else{
			//Check uncheck entry
			setChecked(v);
		}
	}
	
	/**
	 * sets and updates checked entry, view and db
	 * @param v
	 */
	private void setChecked(View v){
		int id = v.getId();
		Entry entry = new Entry();
		for(Entry e : mEntrys){
			if(e.getID() == id){
				entry = e;
				break;
			}
		}
		ImageView img = (ImageView) v.findViewById(R.id.imgChecked);
		if(entry.isChecked()){
			entry.setChecked(false);
			img.setImageResource(R.drawable.unchecked);
		} 
		else{
			entry.setChecked(true);
			img.setImageResource(R.drawable.checked);
		}
		mDataSource.open();
		mDataSource.updateEntryChecked(entry.isChecked(), id);
		mDataSource.close();
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
			View item = inflater.inflate(R.layout.entry_item, null);
			if(item != null){ 
				item.setId(e.getID());
				item.setOnClickListener(this); 
				item.setLayoutParams(llp);
			}
			TextView txt = (TextView) item.findViewById(R.id.txt_entry_item);
			if(txt != null){
				txt.setText(e.getName());
			}
			if(!e.isChecked()){
				ImageView img = (ImageView) item.findViewById(R.id.imgChecked);
				img.setImageResource(R.drawable.unchecked);
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
	
	private void removeCheckedEntrys(){
		mDataSource.open();
		mDataSource.deleteCheckedEntrys(mID);
		mDataSource.close();
		refreshScrollView();
	}
	
	private void showDeleteConfirmDialog(){
		mDialogDelete = new Dialog(EntrysActivity.this, android.R.style.Theme_Translucent);
		mDialogDelete.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialogDelete.setCancelable(true);
		mDialogDelete.setContentView(R.layout.confirm_delete_view);
		Button btnOk = (Button) mDialogDelete.findViewById(R.id.btnOKDelete);
		Button btnCancel = (Button) mDialogDelete.findViewById(R.id.btnCancelDelete);
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		mDialogDelete.show();
	}

	private void showRemoveConfirmDialog(){
		mDialogRemove = new Dialog(EntrysActivity.this, android.R.style.Theme_Translucent);
		mDialogRemove.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialogRemove.setCancelable(true);
		mDialogRemove.setContentView(R.layout.confirm_remove_view);
		Button btnOk = (Button) mDialogRemove.findViewById(R.id.btnOKRemove);
		Button btnCancel = (Button) mDialogRemove.findViewById(R.id.btnCancelRemove);
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		mDialogRemove.show();
	}
	
}

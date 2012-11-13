package nosafespot.todo;

import java.util.List;

import nosafespot.todo.activitys.EntrysActivity;
import nosafespot.todo.activitys.ListsActivity;
import nosafespot.todo.containers.Entry;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ToDoActivity extends Activity implements OnClickListener{
	private DataSource mDataSource;
	private List<Entry> mEntrys;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button b = (Button) findViewById(R.id.main_button_next);
        b.setOnClickListener(this);
        mDataSource = new DataSource(this);
    }
    

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		
		
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		refreshScrollView();
	}


	@Override
	public void onClick(View v) {
		Intent intent = null;
		if(v.getId() == R.id.main_button_next){
			intent = new Intent(this, ListsActivity.class);
			startActivity(intent);
		}
		else{
			intent = new Intent(this, EntrysActivity.class);
			intent.putExtra(Statics.LIST_ID_EXTRA, v.getId());
			startActivity(intent);
		}
	}
	
	private void refreshScrollView(){
		try{
			mDataSource.open();
			mEntrys = mDataSource.getUncheckedEntrys();
			mDataSource.close();
		}
		catch(Exception e){
			//TODO 
		}
		ScrollView view = (ScrollView)findViewById(R.id.scrollViewMain);
		if(view.getChildCount() > 0){
			view.removeAllViews();
		}
		addViewsToScrollView();
	}
	
	private void addViewsToScrollView(){
		LinearLayout.LayoutParams llp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		llp.weight = 1.0f;
		View insertPoint = findViewById(R.id.scrollViewMain);
		LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setLayoutParams(llp);
		try{
			mDataSource.open();
			for(Entry e : mEntrys){
				View item = inflater.inflate(R.layout.list_item, null);
				if(item != null){ 
					item.setId(e.getListID());
					item.setOnClickListener(this); 
					item.setLayoutParams(llp);
				}
				String listName = mDataSource.getListName(e.getListID());
				TextView txt = (TextView) item.findViewById(R.id.textview_entrylabel_list_item);
				if(txt != null){
					txt.setText(String.format("%s - %s", listName, e.getName()));
				}
				linearLayout.addView(item);
			}
		}
		catch(SQLException sqlEx){
			//TODO
		}
		catch(Exception e){
			//TODO
		}
		finally{
			mDataSource.close();
		}
		((ViewGroup) insertPoint).addView(linearLayout, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
	}
}
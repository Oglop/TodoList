package nosafespot.todo.activitys;

import nosafespot.todo.DataSource;
import nosafespot.todo.R;
import nosafespot.todo.Statics;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class RemoveCheckedActivity extends Activity implements OnClickListener {
	private DataSource mDataSource;
	private int mID;
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_remove_view);
		Bundle extras = getIntent().getExtras();
		mID = extras.getInt(Statics.LIST_ID_EXTRA);
		setClickListeners();
		
	}
	/**
	 * set onClickListeners to ok and cancel buttons
	 */
	private void setClickListeners() {
		View v = findViewById(R.id.btnOKRemove);
		v.setOnClickListener(this);
		v.findViewById(R.id.btnCancelRemove);
		v.setOnClickListener(this);
	}



	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnOKRemove){
			removeChecked();
		}
		else if(v.getId() == R.id.btnCancelRemove){
			finish();
		}
	}
	/**
	 * Removes checked items and finishes the activity
	 */
	private void removeChecked() {
		try{
			mDataSource.open();
			mDataSource.deleteCheckedEntrys(mID);
		}
		catch(Exception e){
			Log.d("ERROR", "ERROR: " + e.getMessage());
		}
		finally{
			mDataSource.close();
			finish();
		}
	}

}

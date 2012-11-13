package nosafespot.todo.activitys;

import nosafespot.todo.DataSource;
import nosafespot.todo.R;
import nosafespot.todo.Statics;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class DeleteConfirmActivity extends Activity implements OnClickListener {
	private DataSource mDataSource;
	private int mID;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_delete_view);
		Bundle extras = getIntent().getExtras();
		mID = extras.getInt(Statics.LIST_ID_EXTRA);
		mDataSource = new DataSource(this);
		setClickListeners();
	}
	/**
	 * Set onclicklisteners to ok and cancel buttons
	 */
	private void setClickListeners() {
		View v = findViewById(R.id.btnOKDelete);
		v.setOnClickListener(this);
		v = findViewById(R.id.btnCancelDelete);
		v.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.btnOKDelete){
			deleteList();
		}
		else if(view.getId() == R.id.btnCancelDelete){
			finish();
		}
	}
	private void deleteList() {
		try{
			mDataSource.open();
			mDataSource.deleteList(mID);
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

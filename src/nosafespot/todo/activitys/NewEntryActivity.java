package nosafespot.todo.activitys;

import nosafespot.todo.DataSource;
import nosafespot.todo.R;
import nosafespot.todo.Statics;
import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class NewEntryActivity extends Activity implements OnClickListener {
	private int mId;
	private DataSource mDataSource;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_entry_view);
		mDataSource = new DataSource(this);
		Bundle extras = getIntent().getExtras();
		mId = extras.getInt(Statics.LIST_ID_EXTRA);
		setOnClickListeners();
	}
	/**
	 * Set the onClickListeners
	 */
	private void setOnClickListeners(){
		View v = findViewById(R.id.btnNewEntryOk);
		v.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.btnNewEntryOk){
			EditText edit = (EditText) findViewById(R.id.txtNewEntryName);
			String name = edit.getText().toString();
			
			if(name.length() > 0){
				try{
					mDataSource.open();
					mDataSource.insertEntry(name, mId);
					mDataSource.close();
				}
				catch(SQLException sqlEx){
					Log.d("ERROR", "SQLException: " + sqlEx.getMessage());
				}
				catch(Exception e){
					Log.d("ERROR", "Exception: " + e.getMessage());
				}
			}
			finish();
		}
	}

}

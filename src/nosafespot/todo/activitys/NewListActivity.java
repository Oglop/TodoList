package nosafespot.todo.activitys;

import nosafespot.todo.DataSource;
import nosafespot.todo.R;
import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewListActivity extends Activity implements OnClickListener {
	DataSource mDataSource;
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_list_view);
		mDataSource = new DataSource(this);
		setOnClickListeners();
	}
	
	private void setOnClickListeners(){
		View v = findViewById(R.id.btnNewListOk);
		v.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.btnNewListOk){
			EditText edit = (EditText) findViewById(R.id.txtNewListName);
			String name = edit.getText().toString();
			
			if(name.length() > 0){
				try{
					mDataSource.open();
					mDataSource.insertList(name);
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

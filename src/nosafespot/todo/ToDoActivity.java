package nosafespot.todo;

import nosafespot.todo.activitys.ListsActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ToDoActivity extends Activity implements OnClickListener{
	DataSource mDataSource;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button b = (Button) findViewById(R.id.main_button_next);
        b.setOnClickListener(this);
        mDataSource = new DataSource(this);
        //mDataSource.open();
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
		
		
	}


	@Override
	public void onClick(View v) {
		Intent intent = null;
		if(v.getId() == R.id.main_button_next){
			intent = new Intent(this, ListsActivity.class);
			startActivity(intent);
		}
	}
}
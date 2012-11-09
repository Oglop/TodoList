package nosafespot.todo;

import java.util.List;

import nosafespot.todo.containers.Entry;
import nosafespot.todo.containers.TodoList;
import nosafespot.todo.interfaces.DatabaseInterface;

public class DataAccessLayer implements DatabaseInterface {
	private DataSource mDataSource;
	
	public DataAccessLayer(){
		mDataSource = new DataSource(null);
	}
	
	@Override
	public void createNewList(String name) {
		
	}

	@Override
	public void createNewEntry(int list, String name) {
		
	}

	@Override
	public int getListID(String list) {

		return 0;
	}

	@Override
	public List<Entry> getEntrys(int listID) {

		return null;
	}

	@Override
	public List<TodoList> getLists() {

		return null;
	}

}

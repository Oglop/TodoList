package nosafespot.todo.interfaces;

import java.util.List;

import nosafespot.todo.containers.Entry;
import nosafespot.todo.containers.TodoList;

public interface DatabaseInterface {
	public void createNewList(String name);
	public void createNewEntry(int list, String name);
	public int getListID(String list);
	public List<Entry> getEntrys(int listID);
	public List<TodoList> getLists();
}

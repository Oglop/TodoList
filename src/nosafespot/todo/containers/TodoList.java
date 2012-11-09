package nosafespot.todo.containers;

public class TodoList {
	private int mID;
	private String mName;
	
	public TodoList(){
		this(0, "");
	}
	
	public TodoList(int id, String name){
		mID = id;
		mName = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.mName = name;
	}

	public int getId(){
		return mID;
	}
}

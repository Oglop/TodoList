package nosafespot.todo.containers;

public class TodoList {
	private int mID;
	private String mName;
	private int mViews;
	
	public TodoList(){
		this(0, "", 0);
	}
	
	public TodoList(int id, String name, int views){
		mID = id;
		mName = name;
		mViews = views;
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

	public int getViews() {
		return mViews;
	}

	public void setViews(int views) {
		this.mViews = views;
	}
}

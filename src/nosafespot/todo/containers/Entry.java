package nosafespot.todo.containers;

public class Entry {
	private int mID;
	private int mListID;
	private String mName;
	private boolean mChecked;
	
	public Entry(){
		this(0, 0, "", false);
	}
	
	public Entry(int id, int listId, String name, boolean checked){
		mID = id;
		mListID = listId;
		mName = name;
		mChecked = checked;
	}
	
	public Entry(int id, int listId, String name, int checked){
		mID = id;
		mListID = listId;
		mName = name;
		mChecked = (checked == 0) ? false : true;
	}

	/**
	 * @return the mID
	 */
	public int getID() {
		return mID;
	}

	/**
	 * @return the mListID
	 */
	public int getListID() {
		return mListID;
	}

	/**
	 * @param mListID the mListID to set
	 */
	public void setListID(int listID) {
		this.mListID = listID;
	}

	/**
	 * @return the mName
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param mName the mName to set
	 */
	public void setName(String name) {
		this.mName = name;
	}

	/**
	 * @return the mChecked
	 */
	public boolean isChecked() {
		return mChecked;
	}

	/**
	 * @param mChecked the mChecked to set
	 */
	public void setChecked(boolean checked) {
		this.mChecked = checked;
	}
	
	
}

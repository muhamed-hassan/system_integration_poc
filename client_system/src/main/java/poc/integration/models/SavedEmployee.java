package poc.integration.models;

public class SavedEmployee {

	private int id;

	private String name;

	private String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public SavedEmployee withId(int id) {
		this.id = id;
		return this;
	}

	public SavedEmployee withName(String name) {
		this.name = name;
		return this;
	}

	public SavedEmployee withTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that)
			return true;
		if (that == null)
			return false;
		if (getClass() != that.getClass())
			return false;
		SavedEmployee other = (SavedEmployee) that;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SavedEmployee [id=" + id + ", name=" + name + ", title=" + title + "]";
	}
	
}

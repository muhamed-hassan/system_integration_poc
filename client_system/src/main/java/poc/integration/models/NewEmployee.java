package poc.integration.models;

public class NewEmployee {

	private String name;

	private String title;

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

	@Override
	public String toString() {
		return "NewEmployee [name=" + name + ", title=" + title + "]";
	}

}

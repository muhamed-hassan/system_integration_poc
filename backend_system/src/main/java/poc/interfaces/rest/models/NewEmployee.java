package poc.interfaces.rest.models;

import javax.validation.constraints.NotEmpty;

public class NewEmployee {

	@NotEmpty(message = "Name should not blank")
	private String name;

	@NotEmpty(message = "Title should not blank")
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

}

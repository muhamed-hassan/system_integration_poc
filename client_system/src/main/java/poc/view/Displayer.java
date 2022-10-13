package poc.view;

import org.springframework.stereotype.Component;

@Component
public class Displayer {
	
	public void print(String message) {
		System.out.println(message);
	}
	
	public void print(Throwable throwable) {
		System.out.println(throwable.getMessage());
	}

}

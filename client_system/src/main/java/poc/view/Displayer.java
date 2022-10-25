package poc.view;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class Displayer {
	
	public void print(String message) {
		System.out.println(message);
	}
	
	public void print(Throwable throwable) {
		System.out.println(throwable.getMessage());
	}
	
	public void print(Set<Entry<String, List<String>>> headers) {
		var formattedHeaders = headers.stream()
										.map(header -> header.getKey() + ":" + header.getValue()
																						.stream()
																						.collect(Collectors.joining(",")))
										.collect(Collectors.joining(" | "));
		System.out.println(formattedHeaders);
	}

}

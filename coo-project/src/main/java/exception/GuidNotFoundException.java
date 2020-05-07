package exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="guid not found")
public class GuidNotFoundException extends RuntimeException {



	
}

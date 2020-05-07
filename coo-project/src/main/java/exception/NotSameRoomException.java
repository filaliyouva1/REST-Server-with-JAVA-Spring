package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="Vous n'êtes pas dans la même Salle")
public class NotSameRoomException extends RuntimeException  {

}

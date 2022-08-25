package bot.union.sw.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> catchSWUException(SWUException e) {
        log.error("SWUException, {}", e.getMessage() + "\n" + e.getStackTrace()[0]);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<?> catchOtherException(Exception e) {
        log.error("Exception, {}", e.getMessage() + "\n" + e.getStackTrace()[0]);
        return new ResponseEntity<>(e.getMessage() + "\n" + e.getStackTrace()[0], HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

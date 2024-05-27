package ru.nsu.fit.battle_fw.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoBabosException.class)
    public ResponseEntity<String> handleNoBabosException(NoBabosException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not enough babos");
    }

    @ExceptionHandler(BadCellException.class)
    public ResponseEntity<String> handleBadCellException(BadCellException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad cell selected");
    }

//    @ExceptionHandler(WrongPhaseException.class)
//    public ResponseEntity<String> handleWrongPhaseException(WrongPhaseException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can not do that in this phase");
//    }
//
//    @ExceptionHandler(NotYourTurnException.class)
//    public ResponseEntity<String> handleNotYourTurnException(NotYourTurnException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not your turn now");
//    }
}
package ro.siit.HRS.exceptions;

public class InsufficientLeaveDaysException extends RuntimeException {

    public InsufficientLeaveDaysException(String message){
        super(message);
    }
}

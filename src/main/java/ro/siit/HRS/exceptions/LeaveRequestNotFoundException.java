package ro.siit.HRS.exceptions;

public class LeaveRequestNotFoundException extends RuntimeException{

    public LeaveRequestNotFoundException(String message){
        super(message);
    }
}

package ro.siit.HRS.exceptions;

public class ManagerNotFoundException extends RuntimeException{

    public ManagerNotFoundException(String message){
        super(message);
    }
}

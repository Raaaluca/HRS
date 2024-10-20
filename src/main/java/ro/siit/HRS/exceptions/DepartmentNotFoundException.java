package ro.siit.HRS.exceptions;

public class DepartmentNotFoundException extends RuntimeException{

    public DepartmentNotFoundException(String message){
        super(message);
    }
}

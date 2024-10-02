package model;

public class LeaveRequest {

    private String type;
    private int numberOfDays;
    private Employee sender;
    private Manager approver;
    private boolean isApproved;
}

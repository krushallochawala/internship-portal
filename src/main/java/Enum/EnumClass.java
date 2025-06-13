/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Enum;

/**
 *
 * @author DELL
 */
public class EnumClass {

    public enum Gender {
        Female,
        Male,
        Other
    }
    
    public enum InternshipType{
        OnSite, 
        WFH,
        Hybrid
    }
    
    public enum InternshipStatus{
        Open, 
        Closed, 
        Onhold
    }
    
    public enum InterviewStatus{
        Scheduled,
        Completed,
        Rescheduled,
        Canceled
    }
    
    public enum StudentInternshipStatus{
        Enrolled,
        InProgress,
        Completed,
        Dropped
    }

    public enum Status {
        Pending,
        Shortlisted,
        Selected,
        Rejected
    }
}

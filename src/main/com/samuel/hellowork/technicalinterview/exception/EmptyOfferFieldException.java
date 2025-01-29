package main.com.samuel.hellowork.technicalinterview.exception;

public class EmptyOfferFieldException extends Exception {
    public EmptyOfferFieldException() {
        super("One or more field is empty in a job offer");
    }
}

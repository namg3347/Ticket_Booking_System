package Ticket_booking.entities;

import java.util.List;

public class User {
    private String name;

    private String password;

    private String hashedPassword;

    private List<Ticket> ticketBooked;

    private String userID;

    public User(String name, String password, String hashedPassword, List<Ticket> ticketBooked, String userID) {
        this.name = name;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.ticketBooked = ticketBooked;
        this.userID = userID;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<Ticket> getTicketBooked() {
        return ticketBooked;
    }    

    public String getUserID() {
        return userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setTicketBooked(List<Ticket> ticketBooked) {
        this.ticketBooked = ticketBooked;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    

    
}

package Ticket_booking.entities;



public class Ticket {
    private String ticketID;

    private String userID;

    private String source;

    private String destination;

    private String dateTime;

    private String tier;

    private Train train;

    public Ticket(String ticketID, String userID, String source, String destination, String dateTime, String tier,
            Train train) {
        this.ticketID = ticketID;
        this.userID = userID;
        this.source = source;
        this.destination = destination;
        this.dateTime = dateTime;
        this.tier = tier;
        this.train = train;
    }

    public Ticket() {
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    
    
}

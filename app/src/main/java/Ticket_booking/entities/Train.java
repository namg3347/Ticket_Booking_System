package Ticket_booking.entities;

import java.util.List;
import java.util.Map;


public class Train{
    private String train_ID;

    private String train_NO;

    private List<List<Boolean>> seats;

    private Map<String,String> station_Times;

    private List<String> stations;


    public Train(String train_ID, String train_NO, List<List<Boolean>> seats, Map<String, String> station_Times,
            List<String> stations){
        this.train_ID = train_ID;
        this.train_NO = train_NO;
        this.seats = seats;
        this.station_Times = station_Times;
        this.stations = stations;
    }
    
    public Train() {
    }

    public String getTrain_ID(){
        return train_ID;
    }

    public String getTrain_NO(){
        return train_NO;
    }

    public List<List<Boolean>> getSeats(){
        return seats;
    }

    public Map<String, String> getStation_Times(){
        return station_Times;
    }

    public List<String> getStations(){
        return stations;
    }
    

    public void setTrain_ID(String train_ID){
        this.train_ID = train_ID;
    }

    public void setTrain_NO(String train_NO){
        this.train_NO = train_NO;
    }

    public void setSeats(List<List<Boolean>> seats){
        this.seats = seats;
    }

    public void setStation_Times(Map<String, String> station_Times){
        this.station_Times = station_Times;
    }

    public void setStations(List<String> stations){
        this.stations = stations;
    }    

}

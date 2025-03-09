package Ticket_booking.services;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Ticket_booking.entities.Train;


public class TrainService {

    private Train train;

    private static final String TRAIN_PATH= "app/src/main/java/Ticket_booking/localdb/trains.json"; // train db
    private static final ObjectMapper objectMapper = new ObjectMapper(); 

    List<Train> trainList;

    public TrainService() throws IOException{
        trainList = loadTrains();
    }

    
    public TrainService(Train train) throws IOException {
        this.train = train;
        trainList = loadTrains();
    }


    public List<Train> loadTrains() throws IOException {
        File trains = new File(TRAIN_PATH);
        return objectMapper.readValue(trains,new TypeReference<List<Train>>(){});
    }

    public void saveTraintoFile() throws IOException {
        File trains = new File(TRAIN_PATH);
        objectMapper.writeValue(trains, trainList);
    }

    public Optional<Train> searchTrainByNum(int train_no){ // train found through train_no
        return trainList.stream().filter(train1 -> 
        train1 != null 
        && train1.getTrain_NO() != null 
        && train1.getTrain_NO().matches("\\d+") // Ensures it's a valid integer
        && Integer.parseInt(train1.getTrain_NO()) == train_no).findFirst();
    }

    public List<Train> searchTrainBySD(String source,String destination) {
        if (trainList == null) return Collections.emptyList();

        return trainList.stream().filter(train1 -> trainValidator(train1, source, destination)).toList();
    }

    public Boolean trainValidator (Train train1,String source,String destination) {
        return train1!=null && train1.getStations()!=null
        && train1.getStations().indexOf(source)!=-1 
        && train1.getStations().indexOf(destination)!=-1
        && train1.getStations().indexOf(source) < train1.getStations().indexOf(destination);
    }

    public Boolean updateTrain() {
        if(train == null ) {
            return Boolean.FALSE;
        }

        for (int i =0; i<trainList.size();i++) {
            if (trainList.get(i).getTrain_ID().equals(train.getTrain_ID())) {
                trainList.set(i, train); // Updates train in trainList
            }
        }

        //save trainList to file
        try {
            saveTraintoFile();
        } catch (IOException e) {
            System.err.println("Error saving train data: " + e.getMessage());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    // public void printTrainInfo() {
    //     for(Train train : trainList) {
    //        getTrainInfo(train);
    //     }
    // }

    public String getTrainInfo(Train train){
        return String.format("Train_ID: %s Train_NO: %s",train.getTrain_ID(),train.getTrain_NO());
    }

    public void printStations(Train train) {
        train.getStation_Times().forEach((key,value) -> System.out.println(key+" -> "+value));
    }
}

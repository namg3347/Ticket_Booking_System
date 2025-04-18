package Ticket_booking.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;

import Ticket_booking.entities.Ticket;
import Ticket_booking.entities.Train;
import Ticket_booking.entities.User;
import Ticket_booking.util.UserServiceUtil;

public class UserBookingService {
    
    private User user;

    private static final String Users_Path = "app/src/main/java/Ticket_booking/localdb/users.json";//json file
    private static final ObjectMapper objectMapper = new ObjectMapper();
    //object-mapper is used to map a json file from data base into an object of a java class file
    //readValue converts or deserialize json to java object
    //The methods writeValueAsString and writeValueAsBytes of ObjectMapper class generate a JSON from a Java object 
    //and return the generated JSON as a string or as a byte array:
    List<User> usersList;

    public UserBookingService(User user) throws IOException {
        this.user = user; 
        usersList = loadUserList();
    }

    
    public UserBookingService() throws IOException {
        usersList = loadUserList();

    }

    public List<User> loadUserList() throws IOException {
        File users = new File(Users_Path);
        return objectMapper.readValue(users,new TypeReference<List<User>>() {});
        //We used TypeRefrence here to store our generic datatype(User) and use it later in runtime to deserialize
        //json to java as Generic datatype info is lost in runtime due to java data-eraser drawback
        //we can't use .class here to denote class type of Users as we passed it as List<User> so then
        //object would be of type List instead of List<User> as generic always is lost in runtime
    }

    public void saveUserToFile() throws IOException{
        File userFile = new File(Users_Path);
        objectMapper.writeValue(userFile, usersList);
        //writeValue serialize java into json file 
    }


    //Optional<T> is like a gift box. Sometimes, it contains something valuable (a real object), 
    // and sometimes, it’s empty (null). Instead of checking for null manually, 
    //Optional helps you handle cases where a value might not be present—cleanly and safely.
    public boolean userValidator() {//maybe used to validate the user
        Optional<User> foundUsers =  usersList.stream().filter(user1 ->{
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(),user1.getHashedPassword());
        }).findFirst();
        return foundUsers.isPresent();
    }

    public Optional<User> signIn(String userName,String userPass) {
        return usersList.stream().filter(user1 ->{
            return user1.getName().equals(userName) && UserServiceUtil.checkPassword(userPass,user1.getHashedPassword());
        }).findFirst();
    }

    //stream passes the list as a series of elements and filter filers that stream using a predicate which takes in the generic
    //and return another gereric of same type after its been filered by provided boolean conditions
    //findFirst() is a terminal operation in Java Streams that returns the first element from a stream in encounter order as an optional(or empty if the stream is empty)
    //isPresent returns false if value is present or false if value is null
    public boolean signUp(User user) {
        try {
            usersList.add(user);
            saveUserToFile();
            return Boolean.TRUE;
        } catch (IOException e) {
            return Boolean.FALSE;
        }
    }

    

    public void fetchBookings() {

        if(user == null || user.getTicketBooked() == null || user.getTicketBooked().isEmpty()) {
            System.out.println("No tickets found for current user...");
        }
        System.out.println("Your Bookings:");
        for (Ticket ticket : user.getTicketBooked()) {
            
            new TicketService(ticket).printTickets();  // Directly calling printTickets
        }
    }
    public Boolean cancelBookings(String ticket_ID) {
        if (user == null || user.getTicketBooked() == null) { //checks if user is null or no tickets booked
            System.err.println("No tickets found or user is null.");
            return Boolean.FALSE; // Failure
        }
        Boolean removed = user.getTicketBooked().removeIf(ticket -> ticket.getTicketID().equals(ticket_ID));
        //removeif removes the all tickets if certain conditions are met,here since ticketid is unique,only that ticket  
        if(!removed) { // means no ticket was removed
            System.err.println("Ticket ID not found");
            return Boolean.FALSE;
        }

        usersList.removeIf(u -> u.getUserID().equals(user.getUserID())); // removes user with unique userid

        signUp(user);//adds the modified user again,hence replacing the original user

        return Boolean.TRUE;
    }

    public Boolean addBookings(Ticket ticket) {
        // Ensure user ticket list is initialized
        if (user.getTicketBooked() == null) {
            user.setTicketBooked(new ArrayList<>());
        }

        // Add ticket to the user
        user.getTicketBooked().add(ticket);

        // Update user in usersList 
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUserID().equals(user.getUserID())) {
                usersList.set(i, user); // Update the user object in the list
                break;
            }
        }

        // Save updated users list
        try {
            saveUserToFile();
        } catch (IOException e) {
            System.err.println("Error saving user data: " + e.getMessage());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }


    public List<List<Boolean>> fetchSeats(Train trainSelectedForBooking,int tier) {
        List<List<Boolean>> seats = trainSelectedForBooking.getSeats(); // Ensure train has seats

        if (seats == null) {
            System.err.println("Seats not found for this train!");
            return Collections.emptyList(); // Prevents NullPointerException
        }
        return seats;
    }

    public void randomSeatAllocator(List<List<Boolean>> seats) {

        int[] seat = seatRandomizer(seats);
        System.out.println(seat == null ? "No seats available." : "Seat Allotted at Row: " + seat[0] + ", Column: " + seat[1]);
    }

    public int[] seatRandomizer(List<List<Boolean>> seats) {
        List<int[]> availableSeats = new ArrayList<>();
        Random random = new Random();

        // Find all available seats
        for (int i = 0; i < seats.size(); i++)
            for (int j = 0; j < seats.get(i).size(); j++)
                if (seats.get(i).get(j)) availableSeats.add(new int[]{i, j});

        // Return null if no seats available
        if (availableSeats.isEmpty()) return null;

        // Pick and book a random seat
        int[] selected = availableSeats.get(random.nextInt(availableSeats.size()));
        seats.get(selected[0]).set(selected[1], false);
        return selected;
    }
    
}

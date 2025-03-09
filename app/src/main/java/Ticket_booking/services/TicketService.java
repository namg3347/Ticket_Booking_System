package Ticket_booking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Ticket_booking.entities.Ticket;

public class TicketService {
    private Ticket ticket;

    public TicketService(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getTicketInfo() {

        return String.format("Ticket_ID: %s belongs to user: %s from %s to %s on time: %s ",
        ticket.getTicketID(),ticket.getUserID(),ticket.getSource(),ticket.getDestination(),ticket.getDateTime());
    }

    public void printTickets() {
        System.out.println(getTicketInfo());
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

package Controllers;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import Models.Slot;
import Repository.InMemorySlotRepository;
import Repository.InMemoryUserBookingRepository;
import Services.BookingService;

public class FitnessBookingCLI {
    public static void main(String[] args) {
        // Initialize repositories and service
        var slotRepo = new InMemorySlotRepository();
        var userRepo = new InMemoryUserBookingRepository();
        var service = new BookingService(slotRepo, userRepo);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            /*
            ADD_SLOT S1 Yoga 2025-09-01T07:00 2025-09-01T08:00 2
			ADD_SLOT S2 HIIT 2025-09-01T08:00 2025-09-01T09:00 1
			LIST_SLOTS 2025-09-01
			BOOK u1 S1
			BOOK u2 S1
			BOOK u3 S1
			BOOK u1 S2
			LIST_BOOKINGS u1
			CANCEL u2 S1
			LIST_SLOTS 2025-09-01
			BOOK u3 S1
			LIST_BOOKINGS u3
			CANCEL u4 S1
			LIST_SLOTS
			exit
             */
            String line = scanner.nextLine();//ADD_SLOT S1 Yoga 2025-09-01T07:00 2025-09-01T08:00 2
            if (line.equalsIgnoreCase("exit")) break;

            String[] parts = line.split(" ");//[ADD_SLOT(0), S1(1), Yoga(2), 2025-09-01T07:00(3), 2025-09-01T08:00(4), 2(5)]
            try {
                switch (parts[0]) {
                    case "ADD_SLOT" -> {
                        // parts: ADD_SLOT <slotId> <title> <start> <end> <capacity>
                    	//Models.Slot.Slot(String slotId, String title, LocalDateTime start, LocalDateTime end, int capacity)
                        Slot slot = new Slot(
                                parts[1],                         // slotId
                                parts[2],                         // title
                                LocalDateTime.parse(parts[3]),    // start
                                LocalDateTime.parse(parts[4]),    // end
                                Integer.parseInt(parts[5])        // capacity
                        );
                        System.out.println(service.addSlot(slot));
                    }
                    case "LIST_SLOTS" -> {
                    	//LIST_SLOTS 2025-09-01
                    	//LIST_SLOTS
                        LocalDate date = parts.length > 1 ? LocalDate.parse(parts[1]) : null;
                        List<Slot> slots = service.listSlots(date);
                        for (Slot s : slots) {
                            System.out.printf("%s %s %s-%s capacity=%d booked=%d%n",
                                    s.getSlotId(),
                                    s.getTitle(),
                                    s.getStart().toLocalTime(),
                                    s.getEnd().toLocalTime(),
                                    s.getCapacity(),
                                    s.getBookedCount());
                        }
                    }
                    case "BOOK" -> System.out.println(service.bookSlot(parts[1], parts[2]));
                    case "CANCEL" -> System.out.println(service.cancelBooking(parts[1], parts[2]));
                    case "LIST_BOOKINGS" -> {
                        List<Slot> bookings = service.listUserBookings(parts[1]);
                        System.out.println(parts[1] + " -> " + bookings.stream()
                                .map(s -> s.getSlotId() + " " + s.getStart() + "-" + s.getEnd())
                                .toList());
                    }
                    default -> System.out.println("Unknown command");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}

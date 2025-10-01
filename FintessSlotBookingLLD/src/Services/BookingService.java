package Services;


import java.time.LocalDate;
import java.util.List;

import Models.Slot;
import Repository.SlotRepository;
import Repository.UserBookingRepository;

public class BookingService {
    private final SlotRepository slotRepo;
    private final UserBookingRepository userRepo;

    public BookingService(SlotRepository slotRepo, UserBookingRepository userRepo) {
        this.slotRepo = slotRepo;
        this.userRepo = userRepo;
    }

    public String addSlot(Slot slot) {
        if (slotRepo.addSlot(slot)) return "Slot " + slot.getSlotId() + " added";
        return "Slot " + slot.getSlotId() + " already exists";
    }

    public List<Slot> listSlots(LocalDate date) {
        return slotRepo.listSlots(date);
    }

    public String bookSlot(String userId, String slotId) {
        var optSlot = slotRepo.getSlotById(slotId);
        if (optSlot.isEmpty()) return "Slot " + slotId + " does not exist";

        Slot slot = optSlot.get();

        // Check overlap
        for (Slot s : userRepo.getUserBookings(userId)) {
            if (overlaps(s, slot)) return "User " + userId + " already has an overlapping booking";
        }

        if (!slot.addBooking(userId)) return "Slot " + slotId + " is full";

        userRepo.addBooking(userId, slot);
        return "Booked " + userId + " in " + slotId + " (" + slot.getBookedCount() + "/" + slot.getCapacity() + ")";
    }

    public String cancelBooking(String userId, String slotId) {
        var optSlot = slotRepo.getSlotById(slotId);
        if (optSlot.isEmpty()) return "Slot " + slotId + " does not exist";

        Slot slot = optSlot.get();
        if (!slot.removeBooking(userId)) return "No booking found for " + userId + " in " + slotId;

        userRepo.removeBooking(userId, slot);
        return "Cancelled booking for " + userId + " in " + slotId;
    }

    public List<Slot> listUserBookings(String userId) {
        return userRepo.getUserBookings(userId).stream()
                .filter(slot -> slot.getStart().isAfter(java.time.LocalDateTime.now()))
                .sorted((a, b) -> a.getStart().compareTo(b.getStart()))
                .toList();
    }

    private boolean overlaps(Slot s1, Slot s2) {
        return s1.getStart().isBefore(s2.getEnd()) && s2.getStart().isBefore(s1.getEnd());
    }
}

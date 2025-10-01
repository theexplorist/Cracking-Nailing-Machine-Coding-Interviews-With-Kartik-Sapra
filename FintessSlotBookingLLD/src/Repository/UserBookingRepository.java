package Repository;

import java.util.List;

import Models.Slot;

public interface UserBookingRepository {
    List<Slot> getUserBookings(String userId);
    void addBooking(String userId, Slot slot);
    void removeBooking(String userId, Slot slot);
}

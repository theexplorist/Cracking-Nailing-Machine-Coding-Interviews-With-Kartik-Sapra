package Models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Slot {
    private final String slotId;//s1
    private final String title;//yoga
    private final LocalDateTime start;//2025-09-01,07:00
    private final LocalDateTime end;//2025-09-01,08:00
    private final int capacity;//2
    private final Set<String> bookedUsers;//[u1, u2] but [u1, u1] not possible

    public Slot(String slotId, String title, LocalDateTime start, LocalDateTime end, int capacity) {
        this.slotId = slotId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.capacity = capacity;
        this.bookedUsers = new HashSet<>();
    }

    public String getSlotId() { return slotId; }
    public String getTitle() { return title; }
    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
    public int getCapacity() { return capacity; }
    public Set<String> getBookedUsers() { return bookedUsers; }

    public boolean addBooking(String userId) {
        if (bookedUsers.size() >= capacity) return false;
        return bookedUsers.add(userId);
    }

    public boolean removeBooking(String userId) {
        return bookedUsers.remove(userId);
    }

    public int getBookedCount() {
        return bookedUsers.size();
    }
}

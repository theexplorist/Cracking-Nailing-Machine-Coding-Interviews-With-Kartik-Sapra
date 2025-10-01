package Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Slot;

public class InMemoryUserBookingRepository implements UserBookingRepository {
	private final Map<String, List<Slot>> userBookings = new HashMap<>();

	@Override
	public List<Slot> getUserBookings(String userId) {
		return userBookings.getOrDefault(userId, new ArrayList<>());
	}

	@Override
	public void addBooking(String userId, Slot slot) {
		userBookings.computeIfAbsent(userId, k -> new ArrayList<>()).add(slot);
	}

	@Override
	public void removeBooking(String userId, Slot slot) {
		List<Slot> slots = userBookings.get(userId);
		if (slots != null)
			slots.remove(slot);
	}
}

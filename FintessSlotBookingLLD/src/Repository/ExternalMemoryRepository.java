package Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import Models.Slot;

public class ExternalMemoryRepository implements SlotRepository {

	@Override
	public boolean addSlot(Slot slot) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<Slot> getSlotById(String slotId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Slot> listSlots(LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

}

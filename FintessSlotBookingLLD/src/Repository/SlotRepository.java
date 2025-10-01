package Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import Models.Slot;

public interface SlotRepository {
    boolean addSlot(Slot slot);
    Optional<Slot> getSlotById(String slotId);
    List<Slot> listSlots(LocalDate date);
}

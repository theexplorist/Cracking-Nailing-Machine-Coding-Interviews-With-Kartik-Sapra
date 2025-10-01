package Repository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import Models.Slot;

public class InMemorySlotRepository implements SlotRepository {
    private final Map<String, Slot> slots = new HashMap<>();//ephimeral
    //S1 -> [S1, Yoga, start, end, cap]
    //S2 -> [S2, HIIT, start, end, cap]
    //S3 -> [S3, gym]
    @Override //[S3, Gym]
    public boolean addSlot(Slot slot) {
        if (slots.containsKey(slot.getSlotId())) return false;
        slots.put(slot.getSlotId(), slot);
        return true;
    }

    @Override
    public Optional<Slot> getSlotById(String slotId) {
        return Optional.ofNullable(slots.get(slotId));
    }

    @Override//01-09
    public List<Slot> listSlots(LocalDate date) {
        return slots.values().stream()
                .filter(s -> date == null || s.getStart().toLocalDate().equals(date))
                .sorted(Comparator.comparing(Slot::getStart))
                .collect(Collectors.toList());//[[S1, Yoga, 01-09,7, end, cap], [S3, gym, 01-09,8]]
    }//key      value
    //S1 -> [S1, Yoga, start, end, cap]
    //S2 -> [S2, HIIT, start, end, cap]
    //S3 -> [S3, gym]
   // [[S1, Yoga, 01-09,7, end, cap], [S2, HIIT, 02-09, end, cap], [S3, gym, 01-09,8]]
}

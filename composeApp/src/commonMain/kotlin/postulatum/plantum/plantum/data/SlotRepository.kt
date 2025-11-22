package postulatum.plantum.plantum.data

import androidx.compose.runtime.mutableStateListOf
import postulatum.plantum.plantum.model.Slot

/**
 * Repository for managing slots (academic terms).
 * Provides CRUD operations and maintains state.
 */
class SlotRepository {
    private val _slots = mutableStateListOf<Slot>()
    val slots: List<Slot> get() = _slots
    
    init {
        // Load initial dummy data
        _slots.addAll(DummyData.dummySlots)
    }
    
    /**
     * Add a new slot to the repository.
     */
    fun addSlot(slot: Slot) {
        _slots.add(slot)
    }
    
    /**
     * Remove a slot by ID.
     */
    fun removeSlot(slotId: String) {
        _slots.removeAll { it.id == slotId }
    }
    
    /**
     * Update an existing slot.
     */
    fun updateSlot(slot: Slot) {
        val index = _slots.indexOfFirst { it.id == slot.id }
        if (index != -1) {
            _slots[index] = slot
        }
    }
    
    /**
     * Get a slot by ID.
     */
    fun getSlotById(slotId: String): Slot? {
        return _slots.firstOrNull { it.id == slotId }
    }
}

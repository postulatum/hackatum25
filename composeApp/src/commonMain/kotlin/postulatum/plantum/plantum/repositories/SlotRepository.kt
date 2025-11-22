package postulatum.plantum.plantum.repositories

import androidx.compose.runtime.mutableStateListOf
import postulatum.plantum.plantum.data.DummyData
import postulatum.plantum.plantum.model.Slot
import postulatum.plantum.plantum.model.Semester

/**
 * Repository for managing slots (academic terms).
 * Provides CRUD operations and maintains state.
 */
class SlotRepository {
    private val _slots = mutableStateListOf<Slot>()

    init {
        // Load initial dummy data
        _slots.addAll(DummyData.dummySlots)
    }

    fun getSlots(): List<Slot> {
        return _slots
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

    fun addSemesterToSlot(slotId: String, semester: Semester) {
        val index = _slots.indexOfFirst { it.id == slotId }
        if (index != -1) {
            val existing = _slots[index]
            _slots[index] = existing.copy(semester = existing.semester + semester)
        }
    }

    /**
     * Get a slot by ID.
     */
    fun getSlotById(slotId: String): Slot? {
        return _slots.firstOrNull { it.id == slotId }
    }
}
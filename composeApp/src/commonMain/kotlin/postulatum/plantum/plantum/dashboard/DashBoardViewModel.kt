package postulatum.plantum.plantum.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import postulatum.plantum.plantum.repositories.SlotRepository
import postulatum.plantum.plantum.model.Semester
import postulatum.plantum.plantum.model.Slot

class DashboardViewModel (
    private val slotRepository: SlotRepository = SlotRepository()
) : ViewModel() {
    
    // UI State
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    init {
        loadSlots()
    }
    
    // Actions
    private fun loadSlots() {
        _uiState.update { 
            it.copy(
                slots = slotRepository.getSlots(),
                isLoading = false
            )
        }
    }
    
    fun showAddDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }
    
    fun hideAddDialog() {
        _uiState.update { 
            it.copy(
                showAddDialog = false,
                slotToEdit = null
            )
        }
    }
    
    fun addSlot(slot: Slot) {
        // Für jetzt synchron, später async mit viewModelScope
        slotRepository.addSlot(slot)
        _uiState.update { 
            it.copy(
                slots = slotRepository.getSlots(),
                showAddDialog = false
            )
        }
        
        // Wenn Repository später suspend functions hat:
        // viewModelScope.launch {
        //     repository.addSlot(slot)
        //     _uiState.update { it.copy(slots = repository.slots) }
        // }
    }
    
    fun showEditDialog(slot: Slot) {
        _uiState.update { it.copy(slotToEdit = slot) }
    }
    
    fun updateSlot(slot: Slot) {
        slotRepository.updateSlot(slot)
        _uiState.update { 
            it.copy(
                slots = slotRepository.getSlots(),
                slotToEdit = null
            )
        }
    }
    
    fun deleteSlot(slotId: String) {
        slotRepository.removeSlot(slotId)
        _uiState.update { it.copy(slots = slotRepository.getSlots()) }
    }

    // Semester Dialog Handling
    fun showAddSemesterDialogFor(slotId: String) {
        _uiState.update { it.copy(showAddSemesterDialog = true, slotIdForSemester = slotId) }
    }

    fun hideAddSemesterDialog() {
        _uiState.update { it.copy(showAddSemesterDialog = false, slotIdForSemester = null) }
    }

    fun addSemesterToSelected(semester: Semester) {
        val slotId = _uiState.value.slotIdForSemester ?: return
        slotRepository.addSemesterToSlot(slotId, semester)
        _uiState.update {
            it.copy(
                slots = slotRepository.getSlots(),
                showAddSemesterDialog = false,
                slotIdForSemester = null
            )
        }
    }
    
    // ViewModel wird automatisch cleared, wenn nicht mehr gebraucht
    override fun onCleared() {
        super.onCleared()
        // Cleanup wenn nötig
    }
}

// UI State Data Class
data class DashboardUiState(
    val slots: List<Slot> = emptyList(),
    val isLoading: Boolean = true,
    val showAddDialog: Boolean = false,
    val slotToEdit: Slot? = null,
    val errorMessage: String? = null,
    val showAddSemesterDialog: Boolean = false,
    val slotIdForSemester: String? = null
)
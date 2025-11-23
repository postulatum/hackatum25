package postulatum.plantum.plantum.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import postulatum.plantum.plantum.repositories.SlotRepository
import postulatum.plantum.plantum.model.Semester
import postulatum.plantum.plantum.model.Slot
import kotlin.random.Random

class DashboardViewModel (
    private val slotRepository: SlotRepository = SlotRepository() ,
    private val creditCalculationService: CreditCalculationService = CreditCalculationService()

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

    // Semester management
    fun startAddingSemesterFor(slotId: String) {
        val slot = slotRepository.getSlots().find { it.id == slotId } ?: return
        val nextVariantNumber = slot.semester.size + 1
        val suggestedName = "Variante $nextVariantNumber"

        _uiState.update {
            it.copy(
                slotIdAddingSemester = slotId,
                suggestedSemesterName = suggestedName
            )
        }
    }

    fun cancelAddingSemester() {
        _uiState.update {
            it.copy(
                slotIdAddingSemester = null,
                suggestedSemesterName = null
            )
        }
    }

    fun saveSemester(slotId: String, semesterName: String) {
        if (semesterName.isBlank()) return

        val randomId = Random.nextInt(100000, 999999)
        val semester = Semester(
            id = "semester_$randomId",
            name = semesterName,
            modules = emptyList()
        )
        slotRepository.addSemesterToSlot(slotId, semester)
        _uiState.update {
            it.copy(
                slots = slotRepository.getSlots(),
                slotIdAddingSemester = null,
                suggestedSemesterName = null
            )
        }
    }

    fun addModuleToSemester(slotId: String, semesterId: String, module: postulatum.plantum.plantum.model.Module) {
        slotRepository.addModuleToSemester(slotId, semesterId, module)
        _uiState.update {
            it.copy(slots = slotRepository.getSlots())
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
    val slotIdAddingSemester: String? = null,
    val suggestedSemesterName: String? = null
)
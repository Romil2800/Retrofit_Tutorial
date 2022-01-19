package romilp.retrofittutorial.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import romilp.retrofittutorial.api.models.QuoteList
import romilp.retrofittutorial.repository.QuoteRepository
import romilp.retrofittutorial.repository.Response

class MainViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            quoteRepository.getQuotes(1)
        }

    }

    val quote: LiveData<Response<QuoteList>>
        get() = quoteRepository.quotes
}
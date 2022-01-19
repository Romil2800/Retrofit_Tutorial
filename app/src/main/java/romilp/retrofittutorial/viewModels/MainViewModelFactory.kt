package romilp.retrofittutorial.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import romilp.retrofittutorial.repository.QuoteRepository

class MainViewModelFactory(private val quoteRepository: QuoteRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(quoteRepository) as T
    }
}
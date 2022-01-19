package romilp.retrofittutorial.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import romilp.retrofittutorial.QuoteApplication

class QuoteWorker(private val context: Context,params: WorkerParameters):Worker(context,params) {
    override fun doWork(): Result {

        Log.d("TAG", "doWork: Worker Called")
        val repository=(context as QuoteApplication).quoteRepository
        CoroutineScope(Dispatchers.IO).launch {
            repository.getQuoteBackground()
        }
        return Result.success()
    }
}
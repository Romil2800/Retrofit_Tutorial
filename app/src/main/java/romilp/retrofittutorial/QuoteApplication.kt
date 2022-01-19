package romilp.retrofittutorial

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import romilp.retrofittutorial.api.RetrofitHelper
import romilp.retrofittutorial.api.models.QuotesService
import romilp.retrofittutorial.db.QuoteDatabase
import romilp.retrofittutorial.repository.QuoteRepository
import romilp.retrofittutorial.worker.QuoteWorker
import java.util.concurrent.TimeUnit

class QuoteApplication : Application() {

    lateinit var quoteRepository: QuoteRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
        setUpWorker()
    }

    private fun setUpWorker() {
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workerRequest =
            PeriodicWorkRequest.Builder(QuoteWorker::class.java, 1, TimeUnit.MINUTES)
                .setConstraints(constraint).build()

        WorkManager.getInstance(this).enqueue(workerRequest)
    }

    private fun initialize() {
        val quotesService = RetrofitHelper.getInstance().create(QuotesService::class.java)
        val database = QuoteDatabase.getDatabase(applicationContext)
        quoteRepository = QuoteRepository(quotesService, database, applicationContext)
    }
}
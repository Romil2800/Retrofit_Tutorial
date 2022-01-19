package romilp.retrofittutorial.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import romilp.retrofittutorial.api.models.QuotesService
import romilp.retrofittutorial.api.models.QuoteList
import romilp.retrofittutorial.db.QuoteDatabase
import romilp.retrofittutorial.utils.NetworkUtils
import java.lang.Exception

class QuoteRepository(
    private val quotesService: QuotesService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {

    private val quotesLiveData = MutableLiveData<Response<QuoteList>>()

    val quotes: LiveData<Response<QuoteList>>
        get() = quotesLiveData

    suspend fun getQuotes(page: Int) {

        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            try {
                val result = quotesService.getQuotes(page)
                if (result?.body() != null) {
                    quoteDatabase.quoteDao().addQuote(result.body()!!.results)
                    quotesLiveData.postValue(Response.Success(result.body()))
                }
            } catch (e: Exception) {
                quotesLiveData.postValue(Response.Error(e.message.toString()))
            }
        } else {
            val quotes = quoteDatabase.quoteDao().getQuotes()
            val quoteList = QuoteList(1, 1, 1, quotes, 1, 1)
            quotesLiveData.postValue(Response.Success(quoteList))
        }

    }

    suspend fun getQuoteBackground() {
        val randomNumber = (Math.random() * 10).toInt()
        val result = quotesService.getQuotes(randomNumber)
        if (result?.body() != null) {
            quoteDatabase.quoteDao().addQuote(result.body()!!.results)
        }
    }
}
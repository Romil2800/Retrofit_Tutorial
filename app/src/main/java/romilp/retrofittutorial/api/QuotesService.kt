package romilp.retrofittutorial.api.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import romilp.retrofittutorial.api.models.QuoteList

interface QuotesService {

    @GET("quotes/")
    suspend fun getQuotes(@Query("page")page:Int):Response<QuoteList>


}
package romilp.retrofittutorial.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import romilp.retrofittutorial.api.models.Result

@Dao
interface QuoteDao {

    @Insert
    suspend fun addQuote(quotes: List<Result>)

    @Query("Select * from quote")
    suspend fun getQuotes():List<Result>
}
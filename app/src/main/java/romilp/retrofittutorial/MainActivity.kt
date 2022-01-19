package romilp.retrofittutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import romilp.retrofittutorial.repository.Response
import romilp.retrofittutorial.viewModels.MainViewModel
import romilp.retrofittutorial.viewModels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val quoteRepository = (application as QuoteApplication).quoteRepository

        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(quoteRepository)
        ).get(MainViewModel::class.java)

        mainViewModel.quote.observe(this, Observer {
            when (it) {
                is Response.Loading -> {}
                is Response.Success -> {
                    it.data?.let {
                        Toast.makeText(this, it.results.size.toString(), Toast.LENGTH_SHORT).show()
                    }

                }
                is Response.Error -> {
                    it.errorMessage
                    Toast.makeText(
                        this,
                        "Some error Occrued:" + it.errorMessage.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })


    }
}
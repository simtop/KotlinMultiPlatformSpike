package com.example.kotlinmultiplatformspike

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {

    val viewmodel by lazy {
        BeersListViewModel(
            BeerRepository(DatabaseDriverFactory(this)),
            CorroutineDispatcherProvider()
        )
    }

    private lateinit var getBeersObserver : (beers: List<BeerModel>) -> Unit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        getBeersObserver = { doSomething(viewmodel.beerList.value) }
        viewmodel.beerList.addObserver(getBeersObserver)

        findViewById<Button>(R.id.main_button).setOnClickListener {
            viewmodel.getBeerList()
        }
    }

    private fun doSomething(value: List<BeerModel>) {
        if (value.isEmpty()) return
        val pairing = value.map{
            it.foodPairing
        }
        Toast.makeText(this, pairing.toString(), Toast.LENGTH_LONG).show()
        println("Beers $value")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewmodel.beerList.removeObserver(getBeersObserver)
    }
}
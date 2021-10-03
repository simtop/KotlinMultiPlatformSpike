package com.example.kotlinmultiplatformspike

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class MainActivity: AppCompatActivity(), KoinComponent {

    private val viewmodel: BeersListViewModel by viewModel()

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
    }

    override fun onDestroy() {
        super.onDestroy()
        viewmodel.beerList.removeObserver(getBeersObserver)
    }
}
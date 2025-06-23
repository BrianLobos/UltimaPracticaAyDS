package com.example.parcialvuelosayd.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.parcialvuelosayd.controller.MainScreenController
import com.example.parcialvuelosayd.R
import com.example.parcialvuelosayd.injector.VuelosInjector
import com.example.parcialvuelosayd.model.VuelosRepository

class MainScreen : AppCompatActivity() {
    private lateinit var controller: MainScreenController
    private lateinit var spinner: Spinner
    private lateinit var textMensaje: TextView
    private lateinit var textVuelo: TextView
    private lateinit var listaPaises: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewProperties()
        getDependencies()
        initListaDePaises()
        initObserver()

        setSpinnerAdapter()
        setSpinnerListener()

    }
    private fun initViewProperties(){
        setContentView(R.layout.activity_main)
        spinner = findViewById<Spinner>(R.id.spinnerPaises)
        textMensaje = findViewById<TextView>(R.id.textMensaje)
        textVuelo = findViewById<TextView>(R.id.textVuelo)
    }
    private fun getDependencies(){
        VuelosInjector.initInjector(this)
        controller = VuelosInjector.controller
    }
    private fun initListaDePaises(){
        listaPaises = controller.getPaises()
    }
    private fun initObserver(){
        controller.listaDeVuelosObservable.subscribe { result ->
            onVuelo(result.first, result.second)
        }
    }
    private fun setSpinnerAdapter(){
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaPaises)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
    private fun setSpinnerListener(){
        //Se le agrega un listener al spinner para reaccionar a los cambios de selección de items
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val pais = listaPaises[position]
                controller.fetchVuelos(pais)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
    fun onVuelo(pais: String, vuelos: MutableList<String>) {
        val mensaje = "Vuelos sobre $pais"
        val mensajeVuelos = buildString {
            vuelos.forEachIndexed{index,vuelo ->
                append("${index + 1}. $vuelo\n")//es un solo string con saltos de linea
            }
        }

        //esto se ejecuta en la interfaz grafica
        runOnUiThread {
            textMensaje.setText(mensaje)
            textVuelo.setText(mensajeVuelos)
        }
    }
}
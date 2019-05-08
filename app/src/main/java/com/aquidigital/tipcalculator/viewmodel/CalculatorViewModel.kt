package com.aquidigital.tipcalculator.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.aquidigital.tipcalculator.R
import com.aquidigital.tipcalculator.model.Calculator
import com.aquidigital.tipcalculator.model.TipCalculation

class CalculatorViewModel @JvmOverloads constructor (
    app: Application,
    val calculator: Calculator = Calculator()) : ObservableViewModel(app) {

    private var lastTipCalculated = TipCalculation()

    var inputCheckAmount = ""
    var inputTipPercentage = ""


    val outputCheckAmount get() = getApplication<Application>().getString(R.string.pound_amount, lastTipCalculated.checkAmount)
    val outputTipAmount get() = getApplication<Application>().getString(R.string.pound_amount, lastTipCalculated.tipAmount)
    val outputTotalAmount get() = getApplication<Application>().getString(R.string.pound_amount, lastTipCalculated.grandTotal)
    val locationName get() = lastTipCalculated.locationName

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tipCalculation: TipCalculation) {
        lastTipCalculated = tipCalculation
        notifyChange()
    }

    fun calculateTip() {

        Log.d(javaClass.simpleName, "calculateTip invoked")

        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPercentage = inputTipPercentage.toIntOrNull()

        if (checkAmount != null && tipPercentage != null) {
            updateOutputs(calculator.getTipCalculation(checkAmount, tipPercentage))
        }
    }

    fun saveCurrentTip(name: String) {
        val tipToSave = lastTipCalculated.copy(locationName = name)

        calculator.saveCalculation(tipToSave)
        updateOutputs(tipToSave)
    }

    fun loadTipCalculation(name:String) {
        val tipCalculation = calculator.loadTipCalculationByLocation(name)

        if (tipCalculation != null) {
            inputCheckAmount = tipCalculation.checkAmount.toString()
            inputTipPercentage = tipCalculation.tipPct.toString()

            updateOutputs(tipCalculation)
            notifyChange()
        }
    }

    fun loadSavedTipCalculationSummaries() : LiveData<List<TipCalculationSummaryItem>> {
        return Transformations.map(calculator.loadSavedTipCalculations()) { tipCalculationObjects ->
            tipCalculationObjects.map {
                TipCalculationSummaryItem(it.locationName,
                    getApplication<Application>().getString(R.string.pound_amount, it.grandTotal))
            }
        }
    }
}
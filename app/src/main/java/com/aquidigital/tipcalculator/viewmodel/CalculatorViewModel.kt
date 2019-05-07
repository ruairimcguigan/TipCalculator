package com.aquidigital.tipcalculator.viewmodel

import android.app.Application
import android.util.Log
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
}
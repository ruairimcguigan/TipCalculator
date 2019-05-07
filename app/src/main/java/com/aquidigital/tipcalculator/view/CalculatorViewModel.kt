package com.aquidigital.tipcalculator.view

import android.app.Application
import android.util.Log
import androidx.databinding.BaseObservable
import com.aquidigital.tipcalculator.R
import com.aquidigital.tipcalculator.model.Calculator
import com.aquidigital.tipcalculator.model.TipCalculation

class CalculatorViewModel(
    val app: Application,
    val calculator: Calculator = Calculator()) : BaseObservable() {

    var inputCheckAmount = ""
    var inputTipPercentage = ""

    var outputTotalAmount = ""
    var outputTipAmount =""
    var outputCheckAmount = ""

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tipCalculation: TipCalculation) {
        outputCheckAmount = app.getString(R.string.pound_amount, tipCalculation.checkAmount)
        outputTipAmount = app.getString(R.string.pound_amount,tipCalculation.tipAmount)
        outputTotalAmount  = app.getString(R.string.pound_amount, tipCalculation.grandTotal)
    }

    fun calculateTip() {

        Log.d(javaClass.simpleName, "calculateTip invoked")

        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPercentage = inputTipPercentage.toIntOrNull()

        if (checkAmount != null && tipPercentage != null) {
            updateOutputs(calculator.getTipCalculation(checkAmount, tipPercentage))
            clearInputs()
        }
    }

    private fun clearInputs() {
        inputCheckAmount = "0.00"
        inputTipPercentage = "0"
        notifyChange()
    }


}
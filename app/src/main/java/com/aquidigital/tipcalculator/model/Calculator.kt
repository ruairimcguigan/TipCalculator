package com.aquidigital.tipcalculator.model

import androidx.lifecycle.LiveData
import java.math.RoundingMode

class Calculator(val repository: TipCalculationRepository = TipCalculationRepository()) {

    fun getTipCalculation(checkAmount: Double, tipPct: Int): TipCalculation {

        val tipAmount = (checkAmount * (tipPct.toDouble() / 100.0))
            .toBigDecimal()
            .setScale(2, RoundingMode.HALF_UP)
            .toDouble()

        val grandTotal = checkAmount + tipAmount

        return TipCalculation(
            checkAmount = checkAmount,
            tipPct = tipPct,
            tipAmount = tipAmount,
            grandTotal = grandTotal
        )
    }

    fun saveCalculation(tipCalc: TipCalculation) {
        repository.saveTipCalculation(tipCalc)
    }

    fun loadTipCalculationByLocation(location: String) : TipCalculation? {
        return repository.loadTipCalculationByLocation(location)
    }

    fun loadSavedTipCalculations(): LiveData<List<TipCalculation>> {
        return repository.loadSavedTipCalculations()
    }
}
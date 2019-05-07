package com.aquidigital.tipcalculator.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TipCalculationRepository {

    private val savedTips = mutableMapOf<String, TipCalculation>()

    fun saveTipCalculation(tip: TipCalculation) {
        savedTips[tip.locationName] = tip
    }

    fun loadTipCalculationByLocation(location: String): TipCalculation? {
        return savedTips[location]
    }

    fun loadSavedTipCalculations(): LiveData<List<TipCalculation>> {
        val tipLiveDate = MutableLiveData<List<TipCalculation>>()
        tipLiveDate.value = savedTips.values.toList()
        return tipLiveDate
    }
}

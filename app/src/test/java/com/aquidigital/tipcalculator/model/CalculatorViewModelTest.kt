package com.aquidigital.tipcalculator.model

import android.app.Application
import com.aquidigital.tipcalculator.R
import com.aquidigital.tipcalculator.view.CalculatorViewModel
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyDouble
import org.mockito.MockitoAnnotations

class CalculatorViewModelTest {

    lateinit var viewmodel: CalculatorViewModel

    @Mock lateinit var mockCalc: Calculator
    @Mock lateinit var app: Application

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        stubResource(0.0, "£0.00")
        viewmodel = CalculatorViewModel(app, mockCalc)
    }

    private fun stubResource(amount: Double, returnStub: String) {
        Mockito.`when`(app.getString(R.string.pound_amount, amount)).thenReturn(returnStub)
    }

    @Test
    fun `test getTipCalculation`() {
        // given
        viewmodel.inputCheckAmount = "10.00"
        viewmodel.inputTipPercentage = "15"

        val stub = TipCalculation(checkAmount = 10.00, tipAmount = 1.50, grandTotal = 11.50 )
        `when`(mockCalc.getTipCalculation(10.00, 15)).thenReturn(stub)
        stubResource(10.0, "£10.00")
        stubResource(1.5, "£1.50")
        stubResource(11.50, "£11.50")

        // when
        viewmodel.calculateTip()

        // then
        assertEquals("£10.00", viewmodel.outputCheckAmount)
        assertEquals("£1.50", viewmodel.outputTipAmount)
        assertEquals("£11.50", viewmodel.outputTotalAmount)
    }

    @Test
    fun `should not call getTipCalculation if tip percentage not present`() {
        // given
        viewmodel.inputCheckAmount = "10.00"
        viewmodel.inputTipPercentage = ""

        val stub = TipCalculation(checkAmount = 10.00, tipAmount = 1.50, grandTotal = 11.50 )
        `when`(mockCalc.getTipCalculation(10.00, 15)).thenReturn(stub)

        // when
        viewmodel.calculateTip()

        // then
        verify(mockCalc, never()).getTipCalculation(anyDouble(), anyInt())
    }

    @Test
    fun `should not call getTipCalculation if bill amount not present`() {
        // given
        viewmodel.inputCheckAmount = ""
        viewmodel.inputTipPercentage = "15"

        val stub = TipCalculation(checkAmount = 10.00, tipAmount = 1.50, grandTotal = 11.50 )
        `when`(mockCalc.getTipCalculation(10.00, 15)).thenReturn(stub)

        // when
        viewmodel.calculateTip()

        // then
        verify(mockCalc, never()).getTipCalculation(anyDouble(), anyInt())
    }
}
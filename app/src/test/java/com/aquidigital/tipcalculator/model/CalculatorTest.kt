package com.aquidigital.tipcalculator.model

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculatorTest {

    lateinit var calculator: Calculator

    @Before
    fun setUp() {
        calculator = Calculator()
    }

    @Test
    fun `test calculator`() {
        val baseTc = TipCalculation(checkAmount = 10.0)

        listOf(
            baseTc.copy(tipPct = 15, tipAmount = 1.50, grandTotal = 11.50),
            baseTc.copy(tipPct = 18, tipAmount = 1.80, grandTotal = 11.80),
            baseTc.copy(tipPct = 20, tipAmount = 2.00, grandTotal = 12.00)
        ).forEach{
            assertEquals(it, calculator.getTipCalculation(it.checkAmount, it.tipPct))
        }


    }
}
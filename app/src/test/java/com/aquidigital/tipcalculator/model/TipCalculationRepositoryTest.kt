package com.aquidigital.tipcalculator.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TipCalculationRepositoryTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    lateinit var repo: TipCalculationRepository

    @Before
    fun setUp() {
        repo = TipCalculationRepository()
    }

    @Test
    fun `test save tip`() {
        // given
        val tip = TipCalculation("Guapo", 15, 25.00, 3.75, 28.75)

        // when
        repo.saveTipCalculation(tip)

        // then
        assertEquals(tip, repo.loadTipCalculationByLocation(tip.locationName))
    }

    @Test
    fun `test load all tips`() {
        // given
        val tip1 = TipCalculation("Guapo", 20, 25.00, 5.00, 30.00)
        val tip2 = TipCalculation("Paolos", 15, 20.00, 3.00, 23.00)
        val tip3 = TipCalculation("Paul's", 10, 15.00, 1.50, 11.50)

        // when
        repo.saveTipCalculation(tip1)
        repo.saveTipCalculation(tip2)
        repo.saveTipCalculation(tip3)

        // then
        repo.loadSavedTipCalculations().observeForever { tipCalcs ->
            assertEquals(3, tipCalcs?.size)

        }
    }
}
package com.bartuciotti.tipsplit

import com.bartuciotti.tipsplit.utilities.Calculator
import org.junit.Test
import org.junit.Assert.*

class CalculatorTest {

    private val calc = Calculator()

    @Test
    fun calculateTotalWithTip() {

        assertEquals(110F, calc.calculateTotalWithTip(100F, 10F))
        assertEquals(157.83F, calc.calculateTotalWithTip(129.37F, 22F))

    }

    @Test
    fun formatStringToCurrency() {

        assertEquals("$1,250.50", calc.formatStringToCurrency("1250.50"))
        assertEquals("$0.50", calc.formatStringToCurrency("0.50"))
        assertEquals("$110.00", calc.formatStringToCurrency("110.00"))

    }

    @Test
    fun formatCurrencyToFloat() {

        assertEquals(50.22F, calc.formatCurrencyToFloat("$50.22"))
        assertEquals(1250.5F, calc.formatCurrencyToFloat("$1,250.50"))

    }
}
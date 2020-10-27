package com.bartuciotti.tipsplit.utilities

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

class Calculator {

    fun calculateTotalWithTip(bill: Float, tip: Float): Float {
        val total = bill * (1 + (tip / 100))

        //Round total. Using only 2 decimals (In case I get results like $15.8367172)
        var decimal = BigDecimal.valueOf(total.toDouble())
        decimal = decimal.setScale(2, RoundingMode.HALF_UP)

        return decimal.toFloat()
    }

    fun formatStringToCurrency(value: String): String {
        val currency = Currency.getInstance(Locale.US)
        val cleanString = value.replace("[${currency.symbol},.]".toRegex(), "")
        val parsed = cleanString.toDouble()
        return NumberFormat.getCurrencyInstance(Locale.US).format(parsed / 100)
    }

    fun formatCurrencyToFloat(value: String): Float {
        val format = NumberFormat.getCurrencyInstance(Locale.US)
        val number = format.parse(value)!!
        return number.toFloat()
    }


}
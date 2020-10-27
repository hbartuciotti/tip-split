package com.bartuciotti.tipsplit

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bartuciotti.tipsplit.utilities.Calculator


class MainViewModel(
        private val calculator: Calculator = Calculator()
) : ViewModel() {

    private val _bill: MutableLiveData<String> = MutableLiveData<String>("")
    private val _tip: MutableLiveData<Float> = MutableLiveData<Float>(0F)
    private val _split: MutableLiveData<Float> = MutableLiveData<Float>(1F)
    private val _total: MutableLiveData<String> = MutableLiveData<String>("$0.00")
    private val _totalPerPerson: MutableLiveData<String> = MutableLiveData<String>("$0.00")
    var bill: LiveData<String> = _bill
    var tip: LiveData<Float> = _tip
    var split: LiveData<Float> = _split
    var total: LiveData<String> = _total
    var totalPerPerson: LiveData<String> = _totalPerPerson


    private fun setTip(value: Float) {
        _tip.value = value
        update()
    }


    private fun setSplit(value: Float) {
        _split.value = value
        update()
    }


    private fun setBill(value: String) {
        _bill.value = value
        update()
    }


    fun clearValues() {
        _bill.value = "$0.00"
        _tip.value = 0F
        _split.value = 1F
        _total.value = "$0.00"
        _totalPerPerson.value = "$0.00"
    }


    private fun update() {
        //Check if bill is empty
        if (_bill.value != "") {
            //Get Bill Amount (Converts currency String $x.xx to float)
            val bill = calculator.formatCurrencyToFloat(_bill.value!!)

            //Calculate totals
            val total = calculator.calculateTotalWithTip(bill, _tip.value!!)
            val totalPerPerson = total / _split.value!!

            //Convert totals to String Currency and update Live Data
            _total.value = calculator.formatStringToCurrency(String.format("%.2f", total))
            _totalPerPerson.value = calculator.formatStringToCurrency(String.format("%.2f", totalPerPerson))
        }
    }


    private fun breakdownMessage(view: View): String {

        return view.context.getString(R.string.bill) + ": ${_bill.value}\n" +
                view.context.getString(R.string.tip) + ": " + String.format("%.0f", _tip.value) + "%\n" +
                view.context.getString(R.string.total) + ": ${_total.value}\n" +
                view.context.getString(R.string.per_perosn) + " (" + String.format("%.0f", _split.value) + "): ${_totalPerPerson.value}"

    }


    /**
     * Event Listeners Bellow
     */
    fun tipValueChanged(value: Float) {

        setTip(value)

    }


    fun splitValueChanged(value: Float) {

        setSplit(value)

    }


    fun billValueChanged(): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                setBill(s.toString())

            }
        }
    }


    fun shareClicked(view: View) {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                    Intent.EXTRA_TEXT,
                    view.context.getString(R.string.breakdown) + "\n" + breakdownMessage(view))
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        view.context.startActivity(shareIntent)

    }


    fun breakdownClicked(view: View) {
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle(view.context.getString(R.string.breakdown))
        builder.setMessage(breakdownMessage(view))
        builder.setPositiveButton(view.context.getString(R.string.share)) { _, _ ->
            shareClicked(view)
        }
        builder.setNegativeButton(view.context.getString(R.string.ok)) { _, _ ->

        }
        builder.show()
    }


}
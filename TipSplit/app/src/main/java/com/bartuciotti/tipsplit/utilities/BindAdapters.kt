package com.bartuciotti.tipsplit.utilities

import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.slider.Slider
import me.abhinay.input.CurrencyEditText


/**
 * Binding adapters to allow Two-way data binding
 * I can now use the attributes bellow in my XML files
 * and send methods in my ViewModel as Listeners
 *
 * I can now control events/listeners (onClick, onTextChanged...) from my ViewModel
 * */


//Edit Text
@BindingAdapter("app:textChanged")
fun bindTextChangedListener(editText: EditText, textWatcher: TextWatcher?) { //Receives TextWatcher Via XML (Check XML File)
    editText.addTextChangedListener(textWatcher)
}

@BindingAdapter("app:addCurrency")
fun bindAddCurrency(etCurrency: CurrencyEditText, currency: String) { //Receives String Via XML (Check XML File)
    etCurrency.setCurrency(currency)
}

//Slider
@BindingAdapter("app:sliderChanged")
fun setSliderListeners(slider: Slider, listener: OnValueChangeListener) {
    slider.addOnChangeListener { _, value: Float, _ ->
        listener.onValueChanged(value)
    }
}

interface OnValueChangeListener {
    fun onValueChanged(value: Float)
}

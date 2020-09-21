package id.itborneo.cekongkir.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

const val COST_INTENT = "cost post"


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
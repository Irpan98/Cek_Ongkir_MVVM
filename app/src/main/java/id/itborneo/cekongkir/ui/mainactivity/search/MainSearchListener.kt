package id.itborneo.cekongkir.ui.mainactivity.search

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import com.mancj.materialsearchbar.MaterialSearchBar
import id.itborneo.cekongkir.R
import id.itborneo.cekongkir.ui.mainactivity.MainActivity
import id.itborneo.cekongkir.utils.hideKeyboard
import id.itborneo.cekongkir.utils.searchbar.SuggestionAdapter

class MainSearchListener(
    private val mainActivity: MainActivity,
    private val ml_root: MotionLayout
) : MaterialSearchBar.OnSearchActionListener {

    private val TAG = "MainActivitySearchListener : Search"

    override fun onButtonClicked(buttonCode: Int) {
        Log.d(TAG, "onButtonClicked")

        if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
            Log.d(TAG, "backclicked")
        }

    }


    override fun onSearchStateChanged(enabled: Boolean) {
        Log.d(TAG, "onSearchStateChanged $enabled")



        if (enabled) { //suggestion kebuka

            ml_root.setTransition(R.id.transition_search_bar)

            ml_root.transitionToEnd()
        } else {
            Log.d(TAG, "onSearchStatChanged $enabled")
            ml_root.hideKeyboard()
            ml_root.transitionToStart()

        }

    }


    override fun onSearchConfirmed(text: CharSequence?) {
        Log.d(TAG, "onSearchConfirmed $text")


    }

    fun searchKota(searchBar: MaterialSearchBar, adapter: SuggestionAdapter) {


        searchBar.setCustomSuggestionAdapter(adapter)


        mainActivity.cityFromApi(adapter)


        searchBar.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG, "sb_text changed $s")
                // send the entered text to our filter and let it manage everything

                if (!s.isNullOrBlank()) {
                    adapter.filter.filter(searchBar.text)
                    Log.d(TAG, "textChanged; not null")
                } else {

                    Log.d(TAG, "textChanged; isNull")
                }

            }
        })

    }


}
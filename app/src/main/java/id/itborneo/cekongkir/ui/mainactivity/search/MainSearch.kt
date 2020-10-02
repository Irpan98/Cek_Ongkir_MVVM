package id.itborneo.cekongkir.ui.mainactivity.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.mancj.materialsearchbar.MaterialSearchBar
import id.itborneo.cekongkir.data.model.City
import id.itborneo.cekongkir.data.model.CostPost
import id.itborneo.cekongkir.ui.mainactivity.MainActivity
import id.itborneo.cekongkir.utils.searchbar.SuggestionAdapter


class MainSearch(private val mainActivity: MainActivity) {

    private var costPost = CostPost()

    private lateinit var sbKotaPengirim: MaterialSearchBar

    fun attachSearchBar(
        sb_kota_pengirim: MaterialSearchBar,
        sb_kota_tujuan: MaterialSearchBar,
        ml_root: MotionLayout

    ) {
        sbKotaPengirim = sb_kota_pengirim

        val suggestionAdapterPengirim: SuggestionAdapter = setSearchAdapter(sb_kota_pengirim)
        val suggestionAdapterTujuan: SuggestionAdapter = setSearchAdapter(sb_kota_tujuan)


        val mainActivitySearch =
            MainSearchListener(mainActivity, ml_root)


        mainActivitySearch.searchKota(sb_kota_pengirim, suggestionAdapterPengirim)
        mainActivitySearch.searchKota(sb_kota_tujuan, suggestionAdapterTujuan)

        sb_kota_pengirim.setOnSearchActionListener(mainActivitySearch)
        sb_kota_tujuan.setOnSearchActionListener(mainActivitySearch)

    }


    private fun setSearchAdapter(
        searchBar: MaterialSearchBar
    ): SuggestionAdapter {
        val inflater =
            mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        searchBar.setOnClickListener {
            searchBar.onClick(it) //not override click
        }

        return SuggestionAdapter(inflater) { city: City, _: View ->

            if (searchBar == sbKotaPengirim) {
                costPost.origin = city.id

            } else {
                costPost.destination = city.id
            }

//            Log.d(TAG, city.name)

            searchBar.apply {
                setPlaceHolder(city.name)
                setMaxSuggestionCount(3)
                closeSearch()
            }
        }
    }

    fun getcost(): CostPost {
        return costPost
    }

}
package id.itborneo.cekongkir.ui.mainactivity

import androidx.lifecycle.ViewModel
import id.itborneo.cekongkir.data.CekOngkirRepository
import id.itborneo.cekongkir.data.model.CostPost
import id.itborneo.cekongkir.utils.searchbar.SuggestionAdapter

class MainViewModel : ViewModel() {

    private lateinit var mainActivity: MainActivity

    private lateinit var cekOngkirRepository: CekOngkirRepository

    fun setMainActivity(mainActivity: MainActivity) {
        this.mainActivity = mainActivity
        cekOngkirRepository = CekOngkirRepository(mainActivity)

    }


    private val TAG = "MainActivity : Network"

    fun cityFromApi(adapter: SuggestionAdapter) {
        cekOngkirRepository.cityFromApi(adapter)
    }

    fun reqCost(costs: MutableList<CostPost>) {
        cekOngkirRepository.reqCost(costs)
    }

}
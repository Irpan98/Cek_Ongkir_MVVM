package id.itborneo.cekongkir.details

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.Style
import id.itborneo.cekongkir.CostKurirRVAdapter
import id.itborneo.cekongkir.R
import id.itborneo.cekongkir.jsonmodel.mapbox.jsonPlaces
import id.itborneo.cekongkir.model.CostResponse
import id.itborneo.cekongkir.model.ItemCostsRespose
import id.itborneo.cekongkir.utils.COST_INTENT
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    val TAG = "DetailActivity"

    private lateinit var detailMap: DetailMap
    override fun onCreate(savedInstanceState: Bundle?) {
        detailMap = DetailMap(this)
        detailMap.initialize()

        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_detail)




        val listCostResposeTemp: MutableList<CostResponse>? =
            intent.getParcelableArrayListExtra<CostResponse>(COST_INTENT)
        Log.d(TAG, "response++ $listCostResposeTemp")

        tv_appbar.text = "${listCostResposeTemp?.get(0)?.originCity} dan  ${listCostResposeTemp?.get(0)?.DestionationCity}"


//        val itemCostTemp = listCostResposeTemp[0].costs

        listCostResposeTemp?.forEach {
            setsCost(it)

        }
        detailMap.onCreate(savedInstanceState, mapView, listCost)



        setRecyclerView(listCost)

        btn_back.setOnClickListener {
            onBackPressed()
        }


    }

    override fun onStart() {
        super.onStart()
        detailMap.onStart()
    }


    var listCost = mutableListOf<CostResponse>()
    private fun setsCost(
        itemCostTemp: CostResponse
    ): MutableList<CostResponse> {

//        var listCostResponse = mutableListOf<CostResponse>()
//        var itemListCostResponse = mutableListOf<ItemCostsRespose>()

//        if (listCostResposeTemp != null) {
//            itemListCostResponse = itemCostTemp
//            listCostResponse = listCostResposeTemp

//            listCostResponse.forEach {
//                Log.d(TAG, "listCostResponse $it")
//                it.costs = itemCostTemp.costs
//            }
        //listCostResponse[0].costs = itemCostTemp
        listCost.add(itemCostTemp)
        Log.d(TAG, "${itemCostTemp}")

//        } else {
//            return
//        }
        return listCost
    }


    private fun setRecyclerView(costResponses: MutableList<CostResponse>) {


        val itemsCost = mutableListOf<ItemCostsRespose>()

        costResponses.forEach { costResponse ->

            costResponse.costs?.forEach {
                itemsCost.add(it)
            }
        }

        //urutkan berdasarkan paling murah
        itemsCost.sortBy { it.price }

        val costAdapter = CostKurirRVAdapter(
            itemsCost
        ) {}
        rv_detail_cost.adapter = costAdapter
        rv_detail_cost.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }


}
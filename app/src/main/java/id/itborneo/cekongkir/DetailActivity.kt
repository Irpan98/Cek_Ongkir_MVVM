package id.itborneo.cekongkir

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.itborneo.cekongkir.model.CostResponse
import id.itborneo.cekongkir.model.ItemCostsRespose
import id.itborneo.cekongkir.utils.COST_INTENT
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    val TAG = "DetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val listCostResposeTemp = intent.getParcelableArrayListExtra<CostResponse>(COST_INTENT)
        Log.d(TAG, "response++ $listCostResposeTemp")

        val listCostResponse: MutableList<CostResponse>
        val itemListCostResponse: MutableList<ItemCostsRespose>

        val itemCostTemp = listCostResposeTemp[0].costs

        if (listCostResposeTemp != null && itemCostTemp != null) {
            itemListCostResponse = itemCostTemp
            listCostResponse = listCostResposeTemp
            listCostResponse[0].costs = itemCostTemp


            val costAdapter = CostKurirRVAdapter(
                itemListCostResponse,
                listCostResposeTemp[0].courierImage!!
            ) {

            }
            rv_detail_cost.adapter = costAdapter
            rv_detail_cost.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        } else {
            return
        }


    }
}
package id.itborneo.cekongkir.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.itborneo.cekongkir.R
import id.itborneo.cekongkir.data.model.CostResponse
import id.itborneo.cekongkir.data.model.ItemCostsRespose
import id.itborneo.cekongkir.utils.COST_INTENT
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    val TAG = "DetailActivity"

    private lateinit var mapReadyCallback: MapReadyCallback

    var listCost = mutableListOf<CostResponse>()
    private var costsIntent: MutableList<CostResponse>? = null

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        initDetailMap()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initViewModel()
        intentData()
        initView()
        initRecyclerView(listCost)
        initMapBigger()
        mapReadyCallback.onCreate(savedInstanceState, mapView, listCost)
    }

    private fun initMapBigger() {

    }

    private fun initDetailMap() {
        mapReadyCallback = MapReadyCallback(this)
        mapReadyCallback.initialize()    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        tv_appbar.text =
            "${costsIntent?.get(0)?.originCity} dan  ${costsIntent?.get(0)?.DestionationCity}"
        btn_back.setOnClickListener {
            onBackPressed()
        }

    }

    private fun intentData() {
        costsIntent =
            intent.getParcelableArrayListExtra<CostResponse>(COST_INTENT)

        costsIntent?.forEach {
            setsCost(it)

        }
    }

    override fun onStart() {
        super.onStart()
        mapReadyCallback.onStart()
    }


    private fun setsCost(
        itemCostTemp: CostResponse
    ): MutableList<CostResponse> {
        listCost.add(itemCostTemp)

        return listCost
    }


    private fun initRecyclerView(costResponses: MutableList<CostResponse>) {


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
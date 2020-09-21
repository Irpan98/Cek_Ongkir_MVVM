package id.itborneo.cekongkir


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.itborneo.cekongkir.model.CostPost
import id.itborneo.cekongkir.model.CostResponse
import id.itborneo.cekongkir.model.ItemCostsRespose
import kotlinx.android.synthetic.main.item_detail_cost.view.*
import kotlinx.android.synthetic.main.item_detail_cost.view.iv_kurir
import kotlinx.android.synthetic.main.item_kurir.view.*

class CostKurirRVAdapter(private val costs: MutableList< ItemCostsRespose>,  val clicklistener: (CostResponse)-> Unit) :
    RecyclerView.Adapter<CostKurirRVAdapter.KurirHolder>() {


    lateinit var context : Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KurirHolder {
        context = parent.context

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_cost,parent,false)
        return KurirHolder(view)

    }

    override fun getItemCount(): Int = costs.size

    override fun onBindViewHolder(holder: KurirHolder, position: Int) {
        holder.bind(costs[position])
    }

    inner class KurirHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cost: ItemCostsRespose){

            itemView.tv_price.text = cost.price.toString()
            itemView.tv_estimate.text = cost.estimate
            itemView.tv_desc.text = "${cost.kurir.kurir} ${cost.layanan?.toLowerCase()}"

            itemView.iv_kurir.setImageResource(context.resources.getIdentifier(cost.kurir.image,"drawable",context.packageName))

        }
    }


}
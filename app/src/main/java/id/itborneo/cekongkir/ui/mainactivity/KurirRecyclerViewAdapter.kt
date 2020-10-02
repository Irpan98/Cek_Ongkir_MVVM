package id.itborneo.cekongkir.ui.mainactivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.itborneo.cekongkir.R
import id.itborneo.cekongkir.data.model.Kurir
import kotlinx.android.synthetic.main.item_kurir.view.*

class KurirRecyclerViewAdapter(private val couriers: MutableList< Kurir>, val clicklistener: (Kurir)-> Unit) :
    RecyclerView.Adapter<KurirRecyclerViewAdapter.KurirHolder>() {


    lateinit var context : Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KurirHolder {
        context = parent.context

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kurir,parent,false)
        return KurirHolder(view)

    }

    override fun getItemCount(): Int = couriers.size

    override fun onBindViewHolder(holder: KurirHolder, position: Int) {
        holder.bind(couriers[position])
    }

    inner class KurirHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind( courier: Kurir){


            //itemView.iv_user.setImageResource(context.resources.getIdentifier(user.avatar,"drawable",context.packageName))

            itemView.iv_kurir.setImageResource(context.resources.getIdentifier(courier.image,"drawable",context.packageName))
        }
    }


}
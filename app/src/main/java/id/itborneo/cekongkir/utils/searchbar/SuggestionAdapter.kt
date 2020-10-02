package id.itborneo.cekongkir.utils.searchbar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import id.itborneo.cekongkir.R
import id.itborneo.cekongkir.data.model.City
import kotlinx.android.synthetic.main.item_kota.view.*

//
//data class kota(
//    var namaKota: String
//)


class SuggestionAdapter(inflater: LayoutInflater, val clickListener: (City, View) -> Unit) :
    SuggestionsAdapter<City, SuggestionAdapter.SuggetionViewHolder>(inflater) {

    private var cities = mutableListOf<City>()

    private var showedCities = mutableListOf<City>()

    fun cities(cities: MutableList<City>) {
        this.cities = cities

        showedCities = cities.subList(0, 3) //limit list cities
        setSuggestions(showedCities)


        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SuggetionViewHolder {


        val view = layoutInflater.inflate(R.layout.item_kota, parent, false)

        return SuggetionViewHolder(view)


    }

    override fun getSingleViewHeight(): Int = 40

    override fun onBindSuggestionHolder(
        kota: City?,
        holder: SuggetionViewHolder?,
        position: Int
    ) {

        holder?.bind(kota!!)


    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val term = constraint.toString()
                //put data first to suggstion clone then filter it
                suggestions = cities

                if (term.isEmpty()) suggestions = cities else {
                    //suggestions = ArrayList<City>()

                    //suggestions = mutableListOf<City>()
                    showedCities = mutableListOf()

//                    Log.d("MainActivity","compare $term")


//                    Log.d("MainActivity","cities filter ${cities.toString()}")
                    cities.forEach {

                        if (it.name.toLowerCase().contains(term.toLowerCase())){
                            showedCities.add(it)
                        }
//
//                        if (it.name.toLowerCase().contains(term.toLowerCase())) {
//
//                            showedCities.add(it)
//                        }
                    }
                }
                val limitSize = 3


                if (showedCities.size >= limitSize) {
                    showedCities = showedCities.subList(0, limitSize) //limit list cities

                }

                Log.d("MainAcitivty", "showedCities$showedCities")

                results.values = showedCities
                return results

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val cities = results?.values as MutableList<City>

                suggestions = cities

                notifyDataSetChanged()


            }

        }

    }

    inner class SuggetionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: City) {
            itemView.tv_kota.text = city.name
//            Log.d("MainActivity", "suggestionAdapter, $city")


            itemView.tv_kota.setOnClickListener {
                clickListener(city, itemView)
            }
        }

    }


}
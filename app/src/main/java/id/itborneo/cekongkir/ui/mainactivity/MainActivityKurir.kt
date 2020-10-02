package id.itborneo.cekongkir.ui.mainactivity

import id.itborneo.cekongkir.data.model.Kurir

class MainActivityKurir {
    private var kurir: MutableList<Kurir>


    init {
        val courierImage = mutableListOf("ic_jne_logo", "ic_pos_logo", "ic_tiki_logo")

        kurir = mutableListOf(
            Kurir("jne", courierImage[0]),
            Kurir("pos", courierImage[1]),
            Kurir("tiki", courierImage[2])
        )
    }


    fun getCourier(): MutableList<Kurir> {
        return kurir
    }
}
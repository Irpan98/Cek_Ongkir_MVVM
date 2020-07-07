package id.itborneo.cekongkir.model

data class Cost(
    var originCity: String? = "",
    var DestionationCity : String = "",
    var codeCourier : String = "",
    var costs : MutableList<Costs> = mutableListOf()
)

data class Costs (
    var layanan: String,
    var description: String,
    var cost: Int,
    var estimate: String
)

package id.itborneo.cekongkir.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CostResponse(
    var originCity: String? = "",
    var DestionationCity: String? = "",
    var codeCourier: String? = "",
    var courierImage: String? = "",
    var costs: MutableList<ItemCostsRespose>? = mutableListOf()
) : Parcelable

@Parcelize
data class ItemCostsRespose(
    var kurir: Kurir,
    var layanan: String?,
    var description: String?,
    var price: Int?,
    var estimate: String?
) : Parcelable

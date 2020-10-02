package id.itborneo.cekongkir.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    var id: String,
    var name: String

):Parcelable
package id.itborneo.cekongkir.jsonmodel

import com.google.gson.annotations.SerializedName

data class JsonCity(

	@field:SerializedName("rajaongkir")
	val rajaongkir: Rajaongkir
)

data class JsonCityItem(

	@field:SerializedName("city_name")
	val cityName: String,

	@field:SerializedName("province")
	val province: String,

	@field:SerializedName("province_id")
	val provinceId: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("postal_code")
	val postalCode: String,

	@field:SerializedName("city_id")
	val cityId: String
)

data class Rajaongkir(

	@field:SerializedName("query")
	val query: List<Any>,

	@field:SerializedName("results")
	val results: List<JsonCityItem>,

	@field:SerializedName("status")
	val status: Status
)

data class Status(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("description")
	val description: String
)

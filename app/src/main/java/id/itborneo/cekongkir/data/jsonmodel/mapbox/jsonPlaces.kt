package id.itborneo.cekongkir.data.jsonmodel.mapbox

import com.google.gson.annotations.SerializedName

data class jsonPlaces(

	@field:SerializedName("features")
	val features: List<FeaturesItem?>? = null,

	@field:SerializedName("query")
	val query: List<String?>? = null,

	@field:SerializedName("attribution")
	val attribution: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class ContextItem(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("wikidata")
	val wikidata: String? = null,

	@field:SerializedName("short_code")
	val shortCode: String? = null
)

data class Properties(

	@field:SerializedName("wikidata")
	val wikidata: String? = null
)

data class Geometry(

	@field:SerializedName("coordinates")
	val coordinates: List<Double?>? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class FeaturesItem(

	@field:SerializedName("place_name")
	val placeName: String? = null,

	@field:SerializedName("place_type")
	val placeType: List<String?>? = null,

	@field:SerializedName("bbox")
	val bbox: List<Double?>? = null,

	@field:SerializedName("center")
	val center: List<Double?>? = null,

	@field:SerializedName("context")
	val context: List<ContextItem?>? = null,

	@field:SerializedName("geometry")
	val geometry: Geometry? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("relevance")
	val relevance: Double? = null,

	@field:SerializedName("properties")
	val properties: Properties? = null
)

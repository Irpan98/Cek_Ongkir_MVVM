package id.itborneo.cekongkir.data.remote.mapbox

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
public const val access_token = "&access_token=pk.eyJ1IjoiaXJwYW4iLCJhIjoiY2tjZzNnenU4MGlvYzJxbzM5cWd3MTI3MCJ9.ZbxgRVOg1NxgXcjjVvUA6w"
const val url_end=".json?country=id&$access_token"
object MapBoxIntance {
    private lateinit var retrofit: Retrofit
    private const val BASE_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places/"


    val retrofitIntance: MapBoxGetDataService?
        get(){
            if(!this::retrofit.isInitialized){
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)

                    .build()
            }

            return retrofit.create(MapBoxGetDataService::class.java)

        }

}
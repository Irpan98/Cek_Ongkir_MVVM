package id.itborneo.cekongkir.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClientInstance {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://api.rajaongkir.com/starter/"
    val retrofitInstance: GetDataService?


        get() {
            if (retrofit == null) {


                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)

                    .build()
            }
            return retrofit?.create(GetDataService::class.java)
        }
}
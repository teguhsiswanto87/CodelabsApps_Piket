package id.codelabs.codelabsapps_piket.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private var retrofit: Retrofit? = null

        fun getClient(): Retrofit {

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl("http://103.112.189.132:5227/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
    }
}
//                    .baseUrl("https://absensi-codelabs.herokuapp.com/")

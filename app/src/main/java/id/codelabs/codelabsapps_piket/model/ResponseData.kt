package id.codelabs.codelabsapps_piket.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseData(

    @SerializedName("token")
    @Expose
    var token : String

)
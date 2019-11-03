package id.codelabs.codelabsapps_piket.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseLogin(

    @SerializedName("status")
    @Expose
    var status : Int,

    @SerializedName("message")
    @Expose
    var message : String,

    @SerializedName("data")
    @Expose
    var data : ResponseData
)
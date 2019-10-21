package id.codelabs.codelabsapps_piket.model

import com.google.gson.annotations.SerializedName

data class ResponseAddPassword (
    @SerializedName("status")
    var status : Int,
    @SerializedName("message")
    var message : String
)

package id.codelabs.codelabsapps_piket.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseCheckPassword(
    @Expose
    @SerializedName("status")
    var status: Int,
    @Expose
    @SerializedName("message")
    var message: String
)

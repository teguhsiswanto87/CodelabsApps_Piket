package id.codelabs.codelabsapps_piket.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseCheckHasPassword(

    @SerializedName("status")
    @Expose
    var status: Int,

    @SerializedName("message")
    @Expose
    var message: String

)

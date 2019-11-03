package id.codelabs.codelabsapps_piket.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponsePermohonanSelesaiPiket(

    @SerializedName("status")
    @Expose
    var status : String,

    @SerializedName("message")
    @Expose
    var message : String,

    @SerializedName("data")
    @Expose
    var data : ModelItem

)
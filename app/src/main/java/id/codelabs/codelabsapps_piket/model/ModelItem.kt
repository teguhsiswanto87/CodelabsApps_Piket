package id.codelabs.codelabsapps_piket.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelItem(

    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("nim")
    @Expose
    var nim: String?,

    @SerializedName("nama_anggota")
    @Expose
    var namaAnggota: String,

    @SerializedName("tanggal_piket")
    @Expose
    var tanggalPiket: String?,

    @SerializedName("jenis_piket")
    @Expose
    var jenisPiket: String,

    @SerializedName("status")
    @Expose
    var status: String?,

    @SerializedName("diperiksa_oleh")
    @Expose
    var diperiksaOleh: String?

) : Parcelable
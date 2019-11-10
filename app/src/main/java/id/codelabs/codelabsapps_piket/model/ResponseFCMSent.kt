package id.codelabs.codelabsapps_piket.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseFCMSent(
    var nama: String?,
    var jenisPiket: String?
) : Parcelable
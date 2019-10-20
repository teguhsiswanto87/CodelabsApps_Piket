package id.codelabs.codelabsapps_piket.model

data class ResponseLogin(
    var status : Int,
    var message : String,
    var token : String
)
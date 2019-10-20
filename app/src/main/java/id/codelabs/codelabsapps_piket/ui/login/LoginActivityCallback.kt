package id.codelabs.codelabsapps_piket.ui.login

interface LoginActivityCallback{
    fun moveNext(to : String)
    fun successLogin()
}
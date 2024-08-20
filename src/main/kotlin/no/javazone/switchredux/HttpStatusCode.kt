package no.javazone.switchredux

data class HttpStatusCode(val status:Int) {
    companion object {
        val OK = HttpStatusCode(200)
        val BAD_REQUEST = HttpStatusCode(400)
        val UNAUTHORIZED = HttpStatusCode(401)
    }


}
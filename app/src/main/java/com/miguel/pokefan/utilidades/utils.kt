package com.miguel.pokefan.utilidades

 fun obtenerNumero(url: String): String {
    /**
     * let number =str.match(/\d+/g); number.shift(); return number
     */
   // val patron = """/\d+/g""".toRegex()
   // val resultado = patron.matchEntire(url)
    //return resultado?.value.toString()
    val quitarurl = url.replace("https://pokeapi.co/api/v2/pokemon/","")
    val numero = quitarurl.replace("/","")
    return numero
}
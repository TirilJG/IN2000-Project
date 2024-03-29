package com.example.in2000project.data

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson

/*
* Kan f.eks hentes ut slik:
viewModel.getSunrise().observe(this) {
        binding.result.text = "Sunrise: " + it?.time?.get(0)?.sunrise?.time ?: "Fant ikke resultat"
    }

    binding.button.setOnClickListener {
        viewModel.fetchSunrise()
}
* */

class SunriseDataSource {
    private val gson = Gson()

    val path = "https://in2000-apiproxy.ifi.uio.no/weatherapi/sunrise/2.0/.json?lat=59.933333&lon=10.716667&date=2022-03-17&offset=+01:00"

    //De forskjellige leddene av url-en
    val mainpath = "https://in2000-apiproxy.ifi.uio.no/weatherapi/sunrise/2.0/.json?"
    val date = "date=2022-03-17"
    val days = "&days=3" //inkluderes om man vil hente data for mer enn én dag og viser da fremover i tid
    //days settes til tom streng om man bare ønsker å hente for date.
    val lat = "&lat=59.9"
    val lon = "&lon=10.7"
    val offset = "&offset=%2B01%3A00" //+01:00
    //kan settes sammen slik:
        val pathString = mainpath+date+days+lat+lon+offset

    //anbefalte måten å sette opp parametere for å kunne lage endringer
    val parameters = listOf("date" to "2022-03-17", "days" to "3", "lat" to "59.9", "lon" to "10.7", "offset" to "%2B01%3A00")

    suspend fun fetchSunrise(): Location? {
        return try {

            val res = Fuel.get(mainpath, parameters).awaitString()
            //Log.d("DATASOURCE", res)
            val response =  gson.fromJson(res, Base::class.java)

            Log.d("DATASOURCE", response.location.toString())
            response.location

        } catch (exception: Exception) {
            Log.d("DATASOURCE","A network request exception was thrown ${exception.message}")
            null
        }
    }
}


// result generated from: https://http4k-data-class-gen.herokuapp.com/json

data class Base(val location: Location?, val meta: Meta?)

data class High_moon(val desc: String?, val elevation: String?, val time: String?)

data class Location(val height: String?, val latitude: String?, val longitude: String?, val time: List<Time>?)

data class Low_moon(val desc: String?, val elevation: String?, val time: String?)

data class Meta(val licenseurl: String?)

data class Moonphase(val desc: String?, val time: String?, val value: String?)

data class Moonposition(val azimuth: String?, val desc: String?, val elevation: String?, val phase: String?, val range: String?, val time: String?)

data class Moonrise(val desc: String?, val time: String?)

data class Moonset(val desc: String?, val time: String?)

data class Moonshadow(val azimuth: String?, val desc: String?, val elevation: String?, val time: String?)

data class Solarmidnight(val desc: String?, val elevation: String?, val time: String?)

data class Solarnoon(val desc: String?, val elevation: String?, val time: String?)

data class Sunrise(val desc: String?, val time: String?)

data class Sunset(val desc: String?, val time: String?)

data class Time(val date: String?, val high_moon: High_moon?, val low_moon: Low_moon?, val moonphase: Moonphase?, val moonposition: Moonposition?, val moonrise: Moonrise?, val moonset: Moonset?, val moonshadow: Moonshadow?, val solarmidnight: Solarmidnight?, val solarnoon: Solarnoon?, val sunrise: Sunrise?, val sunset: Sunset?)


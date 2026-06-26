package com.amonteiro.a06_ynov_kmp.data.remote

import com.amonteiro.a06_ynov_kmp.domain.model.Weather
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

//Suspend sera expliqué dans le chapitre des coroutines
suspend fun main() {

    val weathers = WeatherApiDataSource.loadWeathers("Nice")
    for (w in weathers) {
        println(w.getResume())
    }

}

object WeatherApiDataSource {
    private const val API_URL =
        "https://www.amonteiro.fr/api/weather?cityname="

    //Création et réglage du client
    private val client = HttpClient {
        install(Logging) {
            //(import io.ktor.client.plugins.logging.Logger)
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.INFO  // TRACE, HEADERS, BODY, etc.
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
        //engine { proxy = ProxyBuilder.http("monproxy:1234") }
    }

    suspend fun loadWeathers(cityName: String): List<Weather> {
        val response = client.get("https://api.openweathermap.org/data/2.5/find?q=$cityName&appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr")

        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        var result = response.body<WeatherResultDTO>()

        return result.list.map { w ->
            Weather(
                id = w.id,
                name = w.name,
                temp = w.main.temp,
                description = w.weather.firstOrNull()?.description ?: "-",
                speed = w.wind.speed,
                icon = "https://openweathermap.org/img/wn/${w.weather.firstOrNull()?.icon}@4x.png"
            )
        }
    }

    suspend fun loadWeathersVFacile(cityName: String): List<Weather> {
        val response = client.get(API_URL + cityName)
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        var list = response.body<List<Weather>>()

        //Si je devais faire un Wrapper
        val listSortie = ArrayList<Weather>()
        for (w in list) {
            listSortie.add(
                Weather(
                    id = w.id,
                    name = w.name,
                    temp = w.temp,
                    description = w.description,
                    speed = w.speed,
                    icon = w.icon
                )
            )
        }

        return listSortie
    }
}

/* -------------------------------- */
// DTO
/* -------------------------------- */
@Serializable
data class WeatherResultDTO(
    val list: List<WeatherDTO>
)

@Serializable
data class WeatherDTO(
    val id: Int,
    val name: String,
    val main: TempDTO,
    val wind: WindDTO,
    val weather: List<DescriptionDTO>
)

@Serializable
data class TempDTO(val temp: Double)

@Serializable
data class WindDTO(var speed: Double)

@Serializable
data class DescriptionDTO(val icon: String, val description: String)
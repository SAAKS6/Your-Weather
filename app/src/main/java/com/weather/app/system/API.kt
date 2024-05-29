package com.weather.app.system
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.weather.app.WeatherData.Root
import org.json.JSONObject

// API class
class API(private val context: Context) {
    private val queue: RequestQueue by lazy {
        Volley.newRequestQueue(context)
    }

    fun getWeatherData(apiURL: String, onSuccess: (JSONObject) -> Unit, onError: (Int) -> Unit) {

        val request = JsonObjectRequest(Request.Method.GET, apiURL, null,
            { response ->
                try {
                    Log.d("RESPONSE", response.toString())
//                    val root = parseWeatherData(response)

                    onSuccess(response)
                } catch (e: Exception) {
                    Log.d("JSON PARSING", e.stackTraceToString())
                }
            },
            { error ->
                if(error.networkResponse != null){
                    Log.d("ERROR", error.message.toString())
                    Log.d("ERROR", error.networkResponse.statusCode.toString())
                    onError(error.networkResponse.statusCode)
                }else{
                    onError(0)
                }
            })

        queue.add(request)
    }
    fun parseWeatherData(response: String?): Root? {

        // Using The Jackson ObjectMapper to parse the JSON response into a Root object
//        val objectMapper = ObjectMapper()
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//        var root = objectMapper.readValue(response.toString(), Root::class.java)
//        Toast.makeText(context, "SUCCESS2", Toast.LENGTH_SHORT).show()
        var root: Root? = null

        if(response != null){
            root = Gson().fromJson(response, Root::class.java)
        }
//        Toast.makeText(context, "SUCCESS3", Toast.LENGTH_SHORT).show()
        return root
    }
}
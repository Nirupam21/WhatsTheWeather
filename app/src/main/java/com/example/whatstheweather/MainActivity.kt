package com.example.whatstheweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private val url = "http://api.openweathermap.org/data/2.5/weather"
    private val apikey = "06bf84bb89e490aff89340b5fc024f8d"
    val df = DecimalFormat("#.##")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    fun whatstheweather(view: View) {

          var finalurl = ""
          val etcity = findViewById<EditText>(R.id.query)
          val city_name = findViewById<TextView>(R.id.name)
          val descr = findViewById<TextView>(R.id.descr)
          val temper = findViewById<TextView>(R.id.temp)
          val result = findViewById<TextView>(R.id.result)
          val zip = findViewById<EditText>(R.id.query2)

          val city = etcity.text.toString()
          val z = zip.text.toString()

          if(city.equals("") && z.isNotEmpty()){
                 finalurl = "$url?zip=$z&appid=$apikey"
          }

          else if(z.equals("") && city.isNotEmpty()){
                finalurl = "$url?q=$city&appid=$apikey"
          }

          val jsonObjectRequest = JsonObjectRequest(
                     Request.Method.GET,
                     finalurl,
                     null,
                  {

                           var weather = ""
                           var where = ""
                           var desc = ""
                           var t = ""

                           Log.d("success",it.toString())
                           val jsonweather = it.getJSONArray("weather")
                           val jsonObjectweather = jsonweather.getJSONObject(0)
                           val description = jsonObjectweather.getString("description")
                           val jsonObjectmain = it.getJSONObject("main")
                           val temp = jsonObjectmain.getDouble("temp")
                           val feels_like = jsonObjectmain.getDouble("feels_like")
                           val temp_min = jsonObjectmain.getDouble("temp_min")
                           val temp_max = jsonObjectmain.getDouble("temp_max")
                           val humidity = jsonObjectmain.getInt("humidity")
                           val name = it.getString("name")

                          where += name
                          desc +=  description
                          t += df.format(temp-273.15) + "°"
                          weather += "Feels like "+ df.format(feels_like-273.15) + "°"+
                                      "\n" +df.format(temp_max-273.15)+ "°" + "/"+ df.format(temp_min-273.15) + "°"+
                                      "\n Humidity " + humidity + " %"

                          result.setText(weather)
                          city_name.setText(where)
                          descr.setText(desc)
                          temper.setText(t)


                  },
                  {

                  }
          )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}
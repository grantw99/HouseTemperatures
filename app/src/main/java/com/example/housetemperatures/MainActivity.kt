package com.example.housetemperatures

import android.app.Activity
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.json.JSONArray
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    companion object{
        var location = ""
        var interval = 7
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //downloadJSON("http://kv4uy.com/charts/PHPQueries")

        /*
        Database.connect("jdbc:mysql://192.168.224.88/shaneaw_TempLog", driver = "com.mysql.jdbc.Driver",
            user = "shaneaw_shaneaw", password = "fossil99")

        TransactionManager.current().exec("SELECT `location`.`location`, `DS18B20`.`value`*1.8+32 as value\n" +
                "FROM `DS18B20`,`location`\n" +
                "WHERE (`DS18B20`.`sensor_id` =location.serial)\n" +
                "AND `location`.`location` ='Outside'\n" +
                "ORDER BY DS18B20.measurementid DESC\n" +
                "LIMIT 1\";")
        */

        val spLoc = findViewById<Spinner>(R.id.spLoc)
        val spInt =  findViewById<Spinner>(R.id.spInt)
        val myWebView = WebView(this)
        //myWebView.settings.javaScriptEnabled = true
        //myWebView.webViewClient = WebViewClient()

        ArrayAdapter.createFromResource(this, R.array.locationsArray,android.R.layout.simple_spinner_item).also{
            adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spLoc.adapter = adapter
        }
        ArrayAdapter.createFromResource(this, R.array.intervalsArray,android.R.layout.simple_spinner_item).also{
            adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spInt.adapter = adapter
        }

        spLoc.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long){
                location = parent.getItemAtPosition(pos).toString()
                loadPage()
            }
            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }
        spInt.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long){
                interval = parent.getItemAtPosition(pos).toString().toInt()
                loadPage()
            }
            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }

    }
    fun loadPage(){
        var wvC = findViewById<WebView>(R.id.webView)
        wvC.getSettings().setJavaScriptEnabled(true)
        wvC.webViewClient = WebViewClient()
        wvC.setInitialScale(100)
        wvC.loadUrl("http://www.kv4uy.com/charts/Android/C" + interval + "D_" + location + ".htm")
    }
    /*
    fun loadPhp(){
        var url = URL("http://www.kv4uy.com/charts/PHPQuery/" + interval + "D_" + location + ".php")
        /*
        with(url.openConnection() as HttpURLConnection){
            requestMethod = "GET"

            //println("Sent 'get' Request to URL : $url; Response Code : $responseCode")

        }
        */
        url.openConnection()
        txtOut.text = url.content.toString()
    }
    */
    /*
    fun loadNewPage(){
        var url = "http://www.kv4uy.com/charts/C"
        when(interval){
            7 -> url += "7D_"
            30 -> url += "30D_"
        }
        when(location){
            "Attic" -> url+="Attic.html"
            "Basement" -> url+="Basement.html"
            "Garage" -> url+="Garage.html"
            "Outside" -> url+="Outside.html"
        }
        webView.loadUrl(url)


    }
    */
    /*
    fun downloadJSON(urlWebService: String){
        class DownloadJSON: AsyncTask<Void, Void, String>() {
            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()

                loadIntoListView(result.toString())

            }

            override fun doInBackground(vararg params: Void?): String {
                var url = urlWebService
                var con = URL(url).openConnection() as HttpURLConnection
                var sb: StringBuilder = "" as StringBuilder
                var bufferedReader = con.inputStream as BufferedReader
                var json: String = bufferedReader.readLine()
                while (json != null) {
                    sb.append(json+"\n")
                }
                return sb.toString().trim()
            }
        }

        var getJson: DownloadJSON = null as DownloadJSON
        getJson.execute()
    }

    fun loadIntoListView(json: String){
        var jsonArray: JSONArray = JSONArray(json)
        var data = arrayOfNulls<String>(jsonArray.length())
        var i: Int = 0
        while(i < jsonArray.length()){
            var obj = jsonArray.getJSONObject(i)
            data.set(i, obj.getString("location"))
            i++
        }
        var arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        outMain.setAdapter(arrayAdapter)
    }
    */
}

package io.github.zmsp.opentesla

import android.util.Log
import khttp.get
import org.junit.Test

import org.junit.Assert.*
import khttp.post
import org.json.JSONArray

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    var email= "changeme"
    var password= "changeme"
    @Test
    fun post_key(){

       var r=  post("https://owner-api.teslamotors.com/oauth/token", data = mapOf("grant_type" to "password",
               "client_id" to "81527cff06843c8634fdc09e8ac0abefb46ac849f38fe1e431c2ef2106796384",
               "client_secret" to "c7257eb71a564034f9419ee651c7d0e5f7aa6bfbd18bafb5c5c033b093bb2fa3",
               "email" to email,
               "password" to password))
        var auth_resp_json = r.jsonObject
        var access_token:  Any? = null
        access_token = auth_resp_json.get("access_token")

        access_token = auth_resp_json.get("access_token").toString()


        var resp_vehicles_json = get("https://owner-api.teslamotors.com/api/1/vehicles", headers= mapOf("Authorization" to "Bearer " + access_token))
        var jsonObj = resp_vehicles_json.jsonObject
        System.out.print((jsonObj["response"] as JSONArray).getJSONObject(0).get("id"))

    }
}

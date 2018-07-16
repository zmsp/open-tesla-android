package io.github.zmsp.opentesla

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

import khttp.get
import android.os.StrictMode



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var access_token:  String = ""
    private var first_car: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {

        val extras = intent.extras
        if (extras != null) {
            access_token = extras.getString("access_token")
            first_car = extras.getString("first_car")
            Log.d("MainActivity", access_token)
        }
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        //END
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_state_charge -> {
                var resp = khttp.get("https://owner-api.teslamotors.com/api/1/vehicles/$first_car/data_request/charge_state", headers= mapOf("Authorization" to "Bearer " + access_token))
                val textView: TextView = findViewById(R.id.content_text_view) as TextView
                textView.setText(resp.jsonObject.toString())

            }
            R.id.nav_state_driving -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

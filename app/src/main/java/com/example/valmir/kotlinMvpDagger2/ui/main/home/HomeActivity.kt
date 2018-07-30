package com.example.valmir.kotlinMvpDagger2.ui.main.home

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.example.valmir.kotlinMvpDagger2.R
import com.example.valmir.kotlinMvpDagger2.ui.base.BaseActivity
import com.example.valmir.kotlinMvpDagger2.util.Constants.Companion.NOW_PLAYING
import com.example.valmir.kotlinMvpDagger2.util.Constants.Companion.POPULAR
import com.example.valmir.kotlinMvpDagger2.util.Constants.Companion.TOP_RATED
import com.example.valmir.kotlinMvpDagger2.util.Constants.Companion.UP_COMING
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.title = getString(R.string.popular)
        swapFragmentHome(HomeFragment(), POPULAR)

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_popular -> {
                supportActionBar?.title = getString(R.string.popular)
                swapFragmentHome(HomeFragment(), POPULAR)
            }
            R.id.nav_top_rated -> {
                supportActionBar?.title = getString(R.string.top_rated)
                swapFragmentHome(HomeFragment(), TOP_RATED)
            }
            R.id.nav_now_playing -> {
                supportActionBar?.title = getString(R.string.now_playing)
                swapFragmentHome(HomeFragment(), NOW_PLAYING)
            }
            R.id.nav_up_coming -> {
                supportActionBar?.title = getString(R.string.up_coming)
                swapFragmentHome(HomeFragment(), UP_COMING)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}

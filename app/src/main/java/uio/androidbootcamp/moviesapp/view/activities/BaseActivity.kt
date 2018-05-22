package uio.androidbootcamp.moviesapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.drawer_layout.*
import uio.androidbootcamp.moviesapp.R
import uio.androidbootcamp.moviesapp.R.id.action_find
import uio.androidbootcamp.moviesapp.R.id.action_list
import uio.androidbootcamp.moviesapp.utils.ActivitiesNames
import kotlin.reflect.KClass

abstract class BaseActivity<T : AppCompatActivity> : AppCompatActivity() {

    private val implementingClass: KClass<T>
        get() = getImplementingTypeClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isDrawerEnabled()) {
            enableNavigationDrawer()
        }
    }

    abstract fun isDrawerEnabled(): Boolean

    abstract fun getImplementingTypeClass(): KClass<T>

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun enableNavigationDrawer() {
        setSupportActionBar(toolbar)
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.drawer_close,
                R.string.drawer_open) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                // toast("Con este evento manejas lo que sucede cuando el drawer se cierra")
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                // toast("Con este evento, manejas lo que sucede cuando el drawer se abre")
            }
        }

        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        this.navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {

                action_list -> {
                    if (implementingClass.simpleName != ActivitiesNames.MOVIES_LIST_ACTIVITY.className) {
                        intent = Intent(this, MoviesListActivity::class.java)
                        startActivity(intent)
                    }
                }
                action_find -> {
                    if (implementingClass.simpleName != ActivitiesNames.FIND_MOVIES_ACTIVITY.className) {
                        intent = Intent(this, FindMovieActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }
}

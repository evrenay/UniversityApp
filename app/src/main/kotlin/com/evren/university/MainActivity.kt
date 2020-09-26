package com.evren.university

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import com.evren.core.extensions.observe
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.evren.core.di.ViewModelFactory
import com.evren.repository.interactors.base.Reason
import com.evren.university.navigation.TabManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }



    val universityTabContainer: FrameLayout by lazy { findViewById<FrameLayout>(R.id.universityTabContainer) }

    val placeTabContainer: FrameLayout by lazy { findViewById<FrameLayout>(R.id.placeTabContainer) }

    private val tabManager: TabManager by lazy { TabManager(this, addOnDestinationChangedListener) }

    private val addOnDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            controlDestination(destination.id)
        }

    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigationView)

        bottomNavigation.setOnNavigationItemSelectedListener(this)
        bottomNavigation.setOnNavigationItemReselectedListener {
            tabManager.switchTab(it.itemId)
            tabManager.currentController?.let {
                    currentController->
                currentController.currentDestination?.let {
                        currentDestination->

                    when (it.itemId) {
                        R.id.nav_university -> {
                            if(currentDestination.id != R.id.universityFragment){
                                currentController.navigate(currentController.graph.startDestination)
                            }

                        }
                        R.id.nav_place -> {
                            if(currentDestination.id != R.id.placeFragment){
                                currentController.navigate(currentController.graph.startDestination)
                            }
                        }
                    }
                }
            }
        }


        observeDataChanges()
    }

    private fun observeDataChanges() {

        viewModel.errorData.observe(this){
            showSnackbarWithAction(it){
                viewModel.retry()
            }
        }

        viewModel.successData.observe(this) {
            findViewById<RelativeLayout>(R.id.splashScreen).visibility = View.GONE
            findViewById<LinearLayout>(R.id.container).visibility = View.VISIBLE
            tabManager.currentController = tabManager.navUniversityController
        }
    }


    override fun onBackPressed() {
        onTabBackPressed()
    }

    fun onTabBackPressed(){
        tabManager.onBackPressed{
            it?.let {
                    id->
                controlDestination(id)
            }
        }
    }


     fun showSnackbarWithAction(reason: Reason, block: () -> Unit) {
        Snackbar.make(
            findViewById<FrameLayout>(R.id.rootContainer),
            resources.getString(reason.messageRes),
            Snackbar.LENGTH_INDEFINITE
        ).setAction(R.string.retry) {
            block()
        }.show()
    }


    fun controlDestination(id : Int){
        /*    when (id) {
                 R.id.fragment
               -> {
                   bottomNavigation.visibility = View.GONE
               }
               else -> {
                   bottomNavigation.visibility = View.VISIBLE
               }
           }*/
        }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        tabManager.switchTab(item.itemId)
        tabManager.getCurrentDestinationId()?.let {
            controlDestination(it)
        }
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        if (!NavigationUI.navigateUp(navController, null)) {
            onBackPressed()
        }

        return true
    }


}

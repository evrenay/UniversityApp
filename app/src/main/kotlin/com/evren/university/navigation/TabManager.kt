package com.evren.university.navigation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.evren.university.MainActivity
import com.evren.university.R

class TabManager(private val mainActivity: MainActivity, val addOnDestinationChangedListener: NavController.OnDestinationChangedListener) {

    private val navGraphs = mapOf(
            R.id.nav_university to R.id.nav_university,
            R.id.nav_place to R.id.nav_place
    )

    private val startDestinations = mapOf(
        R.id.nav_university to R.id.universityFragment,
        R.id.nav_place to R.id.placeFragment
    )

    private var currentTabId: Int = R.id.nav_university
    var currentController: NavController? = null
    private var tabHistory = TabHistory().apply {
        activeTab = R.id.nav_university
    }

    val navUniversityControllerDelegate = lazy {
        mainActivity.findNavController(R.id.universityTab).apply {

            graph = navInflater.inflate(R.navigation.nav_main).apply {
                startDestination = navGraphs.getValue(R.id.nav_university)
            }
            addOnDestinationChangedListener(addOnDestinationChangedListener)
        }
    }


    val navUniversityController by navUniversityControllerDelegate

    val navPlaceControllerDelegate = lazy {
        mainActivity.findNavController(R.id.placeTab).apply {
            graph = navInflater.inflate(R.navigation.nav_main).apply {
                startDestination = navGraphs.getValue(R.id.nav_place)

                addOnDestinationChangedListener(addOnDestinationChangedListener)
            }
        }
    }

    val navPlaceController by navPlaceControllerDelegate





    private val universityTabContainer: View by lazy { mainActivity.universityTabContainer }
    private val placeTabContainer: View by lazy { mainActivity.placeTabContainer }


    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(KEY_TAB_HISTORY, tabHistory)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            tabHistory = it.getSerializable(KEY_TAB_HISTORY) as TabHistory

            switchTab(mainActivity.bottomNavigation.selectedItemId, false)
        }
    }

    fun supportNavigateUpTo(upIntent: Intent) {
        currentController?.navigateUp()
    }

    fun onBackPressed(onTabChanged : (id : Int?)->Unit) {
        currentController?.let {
            if (it.currentDestination == null || it.currentDestination?.id == startDestinations.getValue(currentTabId)) {
                if (tabHistory.size > 0) {
                    val tabId = tabHistory.popPrevious()
                    switchTab(tabId, false)
                    mainActivity.bottomNavigation.menu.findItem(tabId)?.isChecked = true
                    onTabChanged(getCurrentDestinationId())
                } else {
                    mainActivity.finish()
                }
            }
            else it.popBackStack()
        } ?: run {
            mainActivity.finish()
        }
    }

    fun switchTab(tabId: Int, addToHistory: Boolean = true) {
        if(currentTabId!=tabId){
            currentTabId = tabId

            when (tabId) {
                R.id.nav_university -> {
                    currentController = navUniversityController
                    invisibleTabContainerExcept(universityTabContainer)
                }
                R.id.nav_place -> {
                    currentController = navPlaceController
                    invisibleTabContainerExcept(placeTabContainer)
                }
            }
            if (addToHistory) {
                tabHistory.push(tabId)
            }
        }

    }


    fun navigateUpAll(){
        if(navUniversityControllerDelegate.isInitialized()) navUniversityController.popBackStack(R.id.universityFragment,false)
        if(navPlaceControllerDelegate.isInitialized())  navPlaceController.popBackStack(R.id.placeFragment,false)
    }

    fun getCurrentDestinationId() = currentController?.currentDestination?.id

    private fun invisibleTabContainerExcept(container: View) {
        universityTabContainer.isInvisible = true
        placeTabContainer.isInvisible = true
        container.isInvisible = false
    }

    companion object {
        private const val KEY_TAB_HISTORY = "key_tab_history"
    }
}
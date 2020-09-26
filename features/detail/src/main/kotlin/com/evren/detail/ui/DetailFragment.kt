package com.evren.detail.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.evren.core.base.lifecycle.BaseDaggerFragment
import com.evren.core.extensions.hideKeyboard
import com.evren.core.extensions.observe
import com.evren.detail.R
import com.evren.detail.databinding.FragmentDetailBinding
import com.evren.repository.interactors.base.State
import com.evren.repository.model.entities.university.detail.Image
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : BaseDaggerFragment<FragmentDetailBinding>() {

    //region Properties

    private val viewModel by viewModels<DetailViewModel> { viewModelFactory }

    private val imageAdapter = DetailImageAdapter()

    private val departmentAdapter = DepartmentAdapter()
    //endregion

    //region Functions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        createViewPager()
        setViewpagerDots()
        setDepartmenRecyclerview()
        binding.viewTitle.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        hideKeyboard()




    }

    private fun setDepartmenRecyclerview() {
        binding.recyclerView.adapter = departmentAdapter
    }


    private fun createViewPager(){
        binding.viewpager.offscreenPageLimit = 2
        binding.viewpager.adapter = imageAdapter
        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun setViewpagerDots(){
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { _, _ ->
        }.attach()
    }

    override fun observeDataChanges() {
        observe(viewModel.stateData){ state->
            when(state){
                is State.Loading->{
                    binding.progressView.progressBar.visibility = View.VISIBLE
                }
                is State.Loaded->{
                    binding.progressView.progressBar.visibility = View.GONE
                }
            }
        }
        observe(viewModel.errorData) {
            showSnackbarWithAction(it) {
                viewModel.retry()
            }
        }
        observe(viewModel.successData){
            imageAdapter.updateList(it.images)
            departmentAdapter.updateList(it.majorDetail)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        binding.viewpager.adapter = null
    }

    override fun getLayoutId(): Int = R.layout.fragment_detail
}
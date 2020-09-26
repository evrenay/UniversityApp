package com.evren.university.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.evren.core.actions.openAuthentication
import com.evren.core.actions.openDetail
import com.evren.core.base.lifecycle.BaseDaggerFragment
import com.evren.core.extensions.hideKeyboard
import com.evren.core.extensions.observe
import com.evren.repository.interactors.base.State
import com.evren.repository.model.entities.university.UniversityEntityItem
import com.evren.repository.model.enum.GrandType
import com.evren.university.R
import com.evren.university.databinding.FragmentUniversityBinding


class UniversityFragment : BaseDaggerFragment<FragmentUniversityBinding>() {

    //region Properties

    private val universityAdapter = UniversityAdapter(::onItemSelected)

    private val viewModel by viewModels<UniversityViewModel> { viewModelFactory }
    //endregion

    //region Functions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.recyclerView.adapter = universityAdapter
        hideKeyboard()



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
            it?.let { list->
                universityAdapter.updateList(list)
            }

        }
    }

     fun onItemSelected(universityEntityItem: UniversityEntityItem) {
         universityEntityItem.id?.let {
        when(viewModel.userInfoInstance.isLoginUser()){
                true ->{
                    openDetail(it)
                }
                false->{
                  openAuthentication(it)
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null

    }

    override fun getLayoutId(): Int = R.layout.fragment_university
    //endregion
}

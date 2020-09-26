package com.evren.authentication.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.evren.authentication.R
import com.evren.authentication.databinding.FragmentAuthenticationBinding
import com.evren.core.actions.openDetail
import com.evren.core.base.lifecycle.BaseDaggerFragment
import com.evren.core.extensions.observe

class AuthenticationFragment : BaseDaggerFragment<FragmentAuthenticationBinding>() {

    //region Properties

    private val viewModel by viewModels<AuthenticationViewModel> { viewModelFactory }
    //endregion

    //region Functions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.viewTitle.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnLogin.setOnClickListener {
            viewModel.login()
        }

    }

    override fun observeDataChanges() {
        observe(viewModel.errorData) {
           Toast.makeText(context,getString(R.string.login_error),Toast.LENGTH_SHORT).show()
        }
        observe(viewModel.successData){
            activity?.onBackPressed()
            openDetail(viewModel.getUniversityId)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_authentication
    //endregion
}

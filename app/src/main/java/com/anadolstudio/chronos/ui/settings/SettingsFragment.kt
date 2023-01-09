package com.anadolstudio.chronos.ui.settings

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.BaseFragment
import com.anadolstudio.chronos.databinding.FragmentSettingsBinding
import com.anadolstudio.chronos.ui.settings.inner_fragment.EmptySettingsInnerFragment
import com.anadolstudio.chronos.ui.settings.inner_fragment.SettingsInnerFragment
import com.anadolstudio.core.adapters.view_pager.FragmentViewPagerAdapter
import com.anadolstudio.core.fragment.state_util.LoadingStateDelegate
import com.anadolstudio.core.viewbinding.viewBinding
import com.anadolstudio.core.viewmodel.obtainViewModel

class SettingsFragment : BaseFragment<SettingsContent, SettingsViewModel>(R.layout.fragment_settings) {

    private lateinit var viewState: LoadingStateDelegate
    private lateinit var viewPagerAdapter: FragmentViewPagerAdapter

    private companion object {
        val OFFSCREEN_PAGE_LIMIT = 2 // const val не принимает метод setOffscreenPageLimit
    }

    override fun createViewModel(): SettingsViewModel = obtainViewModel(SettingsViewModel.Factory())

    override fun showContent(content: SettingsContent) {
        viewPagerAdapter.fragments.clear()
        content.categoriesList.forEachIndexed { index, category ->
            viewPagerAdapter.add(
                    index = index,
                    fragment = if (category.title.isEmpty()) {
                        EmptySettingsInnerFragment.newInstance(category)
                    } else {
                        SettingsInnerFragment.newInstance(category)
                    }
            )
        }

        viewState.showContent()
    }

    override fun showLoading() = viewState.showLoading()

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewState = LoadingStateDelegate(contentView = binding.viewPager, loadingView = binding.loadingView)
        setupViewModel()
        binding.toolbar.setBackIconVisible(true)
        binding.toolbar.setBackClickListener { findNavController().navigateUp() }

        viewPagerAdapter = FragmentViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.apply {
            adapter = viewPagerAdapter
            offscreenPageLimit = OFFSCREEN_PAGE_LIMIT
            isSaveFromParentEnabled = true
        }
    }

}

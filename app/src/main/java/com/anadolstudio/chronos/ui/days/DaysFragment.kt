package com.anadolstudio.chronos.ui.days

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.FragmentDaysBinding
import com.anadolstudio.chronos.extensions.navigateTo
import com.anadolstudio.chronos.navigation.Screens
import com.anadolstudio.chronos.ui.BaseViewState
import com.anadolstudio.chronos.ui.days.model.exampleCategory
import com.anadolstudio.chronos.view.day.DayCategoryItem
import com.anadolstudio.core.fragment.CoreBaseFragment
import com.anadolstudio.core.viewbinding.viewBinding

class DaysFragment : CoreBaseFragment<BaseViewState<Unit>>(R.layout.fragment_days) {

    override fun render(state: BaseViewState<Unit>) {
        TODO("Not yet implemented")
    }

    private val binding by viewBinding(FragmentDaysBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..20) {
            binding.categoryContainer.addView(
                    DayCategoryItem(requireContext()).apply {
                        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                        setCategory(exampleCategory)
                        setOnSubcategoryClickListener{ category, subcategory, subcategoryObject ->
                            Toast.makeText(
                                    requireContext(),
                                    "$category, $subcategory, $subcategoryObject",
                                    LENGTH_SHORT
                            ).show()
                        }
                    }
            )
        }

        binding.buttonFirst.setOnClickListener {
            navigateTo(Screens.SETTINGS_SCREEN)
        }
    }

}

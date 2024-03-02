package com.anadolstudio.chronos.presentation.categories

import androidx.navigation.fragment.navArgs
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.bottom.BaseContentBottom
import com.anadolstudio.chronos.databinding.BottomCategoriesBinding
import com.anadolstudio.ui.adapters.groupie.BaseGroupAdapter
import com.anadolstudio.ui.viewbinding.viewBinding
import com.anadolstudio.ui.viewmodel.assistedViewModel
import com.anadolstudio.ui.viewmodel.livedata.SingleEvent
import com.anadolstudio.utils.util.extentions.setFragmentResult
import com.xwray.groupie.Section

class CategoriesBottom : BaseContentBottom<CategoriesState, CategoriesViewModel, CategoriesController>(R.layout.bottom_categories) {

    private val binding by viewBinding { BottomCategoriesBinding.bind(it) }
    private val section: Section = Section()
    private val args: CategoriesBottomArgs by navArgs()

    override fun createViewModelLazy() = assistedViewModel {
        getSharedComponent()
                .categoriesViewModelFactory()
                .create(args.data.categoryList)
    }

    override fun initView() = with(binding) {
        recycler.adapter = BaseGroupAdapter(section)
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is CategoriesBottomEvents.Result -> setFragmentResult(args.data.requestKey, event.categoryUi)
        else -> super.handleEvent(event)
    }

    override fun render(state: CategoriesState) {
        val items = state.categoryList.map {
            CategoryItem(
                    categoryUi = it,
                    onClick = controller::onCategoryClicked
            )
        }
        section.update(items)
    }

}

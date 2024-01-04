package com.anadolstudio.chronos.presentation.categories

import androidx.navigation.fragment.navArgs
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.bottom.BaseContentBottom
import com.anadolstudio.chronos.databinding.BottomCategoriesBinding
import com.anadolstudio.core.groupie.BaseGroupAdapter
import com.anadolstudio.core.presentation.fragment.state_util.ViewStateDelegate
import com.anadolstudio.core.util.common_extention.setFragmentResult
import com.anadolstudio.core.viewbinding.viewBinding
import com.anadolstudio.core.viewmodel.assistedViewModel
import com.anadolstudio.core.viewmodel.livedata.SingleEvent
import com.xwray.groupie.Section

class CategoriesBottom : BaseContentBottom<CategoriesState, CategoriesViewModel, CategoriesController>(R.layout.bottom_categories) {

    companion object {
        const val TAG = "CategoriesBottom"
    }

    override val viewStateDelegate: ViewStateDelegate = ViewStateDelegate()

    private val binding by viewBinding { BottomCategoriesBinding.bind(it) }
    private val section: Section = Section()
    private val args: CategoriesBottomArgs by navArgs()

    override fun getDialogTag(): String = TAG

    override fun createViewModelLazy() = assistedViewModel {
        getSharedComponent()
                .categoriesViewModelFactory()
                .create(args.data.categoryList)
    }

    override fun initView() = with(binding) {
        recycler.adapter = BaseGroupAdapter(section)
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is CategoriesBottomEvents.Result -> setFragmentResult(getDialogTag(), event.categoryUi)
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

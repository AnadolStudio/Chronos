package com.anadolstudio.chronos.presentation.categories

import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
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

    private companion object {
        const val SPAN_COUNT = 3
    }

    private val binding by viewBinding { BottomCategoriesBinding.bind(it) }
    private val section: Section = Section()
    private val args: CategoriesBottomArgs by navArgs()

    override fun createViewModelLazy() = assistedViewModel {
        getSharedComponent()
                .categoriesViewModelFactory()
                .create(args.data.categoryList)
    }

    override fun initView() = with(binding) {
        val layoutManager = recycler.layoutManager as GridLayoutManager
        val adapter = BaseGroupAdapter(section).apply { spanCount = SPAN_COUNT }

        recycler.adapter = adapter
        layoutManager.spanCount = SPAN_COUNT
        layoutManager.spanSizeLookup = adapter.spanSizeLookup
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is CategoriesBottomEvents.Result -> setFragmentResult(args.data.requestKey, event.categoryUi)
        else -> super.handleEvent(event)
    }

    override fun render(state: CategoriesState) {
        val items = state.categoryMap.flatMap { (parentName, childList) ->
            val header = CategoryHeaderItem(name = parentName)

            val child = childList.map {
                CategorySpanItem(
                        categoryUi = it,
                        onClick = controller::onCategoryClicked
                )
            }

            listOf(header) + child
        }

        section.update(items)
    }

}

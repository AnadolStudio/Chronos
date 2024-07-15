package com.anadolstudio.chronos.presentation.main.behavior

import android.content.Context
import android.content.res.ColorStateList
import android.os.Parcelable
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import androidx.core.view.isInvisible
import androidx.core.view.marginBottom
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.FragmentMainBinding
import com.anadolstudio.utils.util.extentions.getColorByOffset
import com.anadolstudio.utils.util.extentions.scale
import com.anadolstudio.view.coordinator.BaseSavedState
import com.anadolstudio.view.coordinator.ScrollState
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.BaseAppBarBehavior
import kotlinx.android.parcel.Parcelize
import kotlin.math.abs

class MainAppBarBehavior() : BaseAppBarBehavior<MainAppBarBehavior.MainSavedState, FragmentMainBinding>() {

    constructor(context: Context, attrs: AttributeSet) : this()

    private companion object {
        const val PERCENT_WHEN_BACKGROUND_START_CHANGED = 0.5F
        const val MAX_ELEVATION = 16F
    }

    private val startColor by lazy { context.getColor(R.color.colorSecondary) }
    private val endColor by lazy { context.getColor(R.color.colorPrimary) }

    var scrollStateListener: ((state: ScrollState) -> Unit)? = null

    override fun initializeViewBinding(coordinatorLayout: CoordinatorLayout): FragmentMainBinding = FragmentMainBinding.bind(coordinatorLayout)

    override fun onRestoreState(binding: FragmentMainBinding, state: MainSavedState) {
        changeAlphaView(binding = binding, toolbarAlpha = state.toolbarAlpha, todayTextAlpha = state.textAlpha, offsetRatio = state.offsetRatio)
        changeBackgroundView(binding = binding, color = state.color, elevation = state.elevation)
    }

    override fun onSaveState(binding: FragmentMainBinding, supperState: Parcelable): MainSavedState = MainSavedState(
            superState = supperState,
            textAlpha = binding.todayText.alpha,
            toolbarAlpha = binding.toolbar.alpha,
            elevation = binding.toolbar.elevation,
            color = binding.toolbar.backgroundTintList?.defaultColor ?: startColor,
            offsetRatio = abs(topAndBottomOffset) / binding.appBar.totalScrollRange.toFloat()
    )

    override fun canDrag(binding: FragmentMainBinding): Boolean = true

    override fun onScrolling(binding: FragmentMainBinding) {
        super.onScrolling(binding)
        changeOffset(binding)
    }

    override fun onScrollStoped(binding: FragmentMainBinding) {
        super.onScrollStoped(binding)
        snapAppBar(binding.appBar)
    }

    override fun changeOffset(binding: FragmentMainBinding) {
        changeView(binding)
    }

    private fun changeView(binding: FragmentMainBinding, offset: Int = topAndBottomOffset) {
        changeAlpha(binding, offset.toFloat())
        changeBackground(binding, offset.toFloat())
    }

    private fun changeBackground(binding: FragmentMainBinding, offset: Float) {
        val maxOffset = abs(binding.appBar.totalScrollRange).toFloat()
        val startChangeOffset = maxOffset * PERCENT_WHEN_BACKGROUND_START_CHANGED

        val offsetClamp = MathUtils.clamp(abs(offset), startChangeOffset, maxOffset)

        val maxDelta = maxOffset - startChangeOffset
        val delta = maxOffset - offsetClamp
        val valueOffset = 1 - (delta / maxDelta)

        val color = getColorByOffset(startColor, endColor, valueOffset)
        val elevation = MAX_ELEVATION * valueOffset

        changeBackgroundView(binding, color, elevation)
    }

    private fun changeBackgroundView(binding: FragmentMainBinding, color: Int, elevation: Float) = with(binding) {
        appBar.backgroundTintList = ColorStateList.valueOf(color)
        toolbar.backgroundTintList = ColorStateList.valueOf(color)

        appBar.elevation = elevation
        toolbar.elevation = elevation
        appBarAddButton.elevation = elevation
    }

    private fun snapAppBar(appBar: AppBarLayout) {
        if (abs(topAndBottomOffset) / appBar.totalScrollRange.toFloat() > 0.5) {
            appBar.setExpanded(false, true)
        } else {
            appBar.setExpanded(true, true)
        }
    }

    private fun changeAlpha(binding: FragmentMainBinding, offset: Float = topAndBottomOffset.toFloat()) {
        val maxOffsetToToolbar = binding.spaceToTopToolbar.height

        val offsetClampToToolbar = MathUtils.clamp(abs(offset), 0F, maxOffsetToToolbar.toFloat())
        var toolbarAlpha = offsetClampToToolbar / maxOffsetToToolbar

        if (toolbarAlpha.isNaN()) toolbarAlpha = 0F

        changeAlphaView(
                binding = binding,
                toolbarAlpha = toolbarAlpha,
                todayTextAlpha = 1 - toolbarAlpha,
                offsetRatio = abs(topAndBottomOffset) / binding.appBar.totalScrollRange.toFloat()
        )
    }

    private fun changeAlphaView(
            binding: FragmentMainBinding,
            toolbarAlpha: Float,
            todayTextAlpha: Float,
            offsetRatio: Float
    ) = with(binding) {
        val inverseOffsetRatio = 1 - offsetRatio

        toolbar.alpha = toolbarAlpha
        appBarAddButton.alpha = offsetRatio
        appBarAddButton.scale = offsetRatio
        toolbar.isInvisible = toolbarAlpha == 0F
        appBarAddButton.isInvisible = toolbarAlpha == 0F

        todayText.alpha = todayTextAlpha
        todayText.isInvisible = todayTextAlpha == 0F

        bottomAddButton.alpha = inverseOffsetRatio
        bottomAddButton.translationY = (offsetRatio) * (bottomAddButton.height + bottomAddButton.marginBottom)
        bottomAddButton.isInvisible = inverseOffsetRatio == 0F
    }

    @Parcelize
    data class MainSavedState(
            override val superState: Parcelable,
            val textAlpha: Float,
            val toolbarAlpha: Float,
            val elevation: Float,
            val color: Int,
            val offsetRatio: Float,
    ) : BaseSavedState
}

package com.anadolstudio.chronos.presentation.main.behavior

import android.content.Context
import android.content.res.ColorStateList
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import androidx.core.view.ViewCompat
import androidx.core.view.isInvisible
import com.anadolstudio.chronos.R
import com.anadolstudio.utils.util.extentions.getColorByOffset
import com.anadolstudio.view.toolbar.BaseToolbar
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.parcel.Parcelize
import kotlin.math.abs

class MainAppBarBehavior() : AppBarLayout.Behavior() {

    constructor(context: Context, attrs: AttributeSet) : this()

    private companion object {
        const val PERCENT_WHEN_BACKGROUND_START_CHANGED = 0.5F
        const val MAX_ELEVATION = 16F
    }

    private var lastProcessedOffset: Int = Int.MAX_VALUE
    private var scrollState: ScrollState = ScrollState.IDLE // TODO переключение работает криво, не учитывается инерция, посмотри видос
        set(value) {
            if (field != value) {
                scrollStateListener?.invoke(value)
            }
            field = value
        }

    private var _todayText: TextView? = null
    private val todayText: TextView get() = requireNotNull(_todayText)

    private var _toolbar: BaseToolbar? = null
    private val toolbar: BaseToolbar get() = requireNotNull(_toolbar)

    private var _spaceToTopToolbar: View? = null
    private val spaceToTopToolbar: View get() = requireNotNull(_spaceToTopToolbar)

    private var _appBarAddButton: View? = null
    private val appBarAddButton: View get() = requireNotNull(_appBarAddButton)

    private var _bottomAddButton: View? = null
    private val bottomAddButton: View get() = requireNotNull(_bottomAddButton)

    private val startColor by lazy { toolbar.context.getColor(R.color.colorSecondary) }
    private val endColor by lazy { toolbar.context.getColor(R.color.colorPrimary) }

    private val offsetListener: AppBarLayout.OnOffsetChangedListener by lazy {
        AppBarLayout.OnOffsetChangedListener { appBarLayout, _ -> changeView(appBarLayout) }
    }

    var scrollStateListener: ((state: ScrollState) -> Unit)? = null

    init {
        setDragCallback(
                object : DragCallback() {
                    override fun canDrag(appBarLayout: AppBarLayout): Boolean = true
                }
        )
    }

    override fun onLayoutChild(
            parent: CoordinatorLayout,
            appBar: AppBarLayout,
            layoutDirection: Int
    ): Boolean {
        if (_toolbar == null) initView(parent, appBar)
        appBar.outlineProvider = ViewOutlineProvider.BACKGROUND

        return super.onLayoutChild(parent, appBar, layoutDirection)
    }

    private fun initView(parent: CoordinatorLayout, appBar: AppBarLayout) {
        _todayText = parent.findViewById(R.id.todayText)
        _toolbar = parent.findViewById(R.id.toolbar)
        _spaceToTopToolbar = parent.findViewById(R.id.space_to_top_toolbar)
        _appBarAddButton = parent.findViewById(R.id.appBarAddButton)
        _bottomAddButton = parent.findViewById(R.id.bottomAddButton)

        appBar.removeOnOffsetChangedListener(offsetListener)
        appBar.addOnOffsetChangedListener(offsetListener)
    }

    override fun onRestoreInstanceState(parent: CoordinatorLayout, appBar: AppBarLayout, state: Parcelable) {
        super.onRestoreInstanceState(parent, appBar, (state as? SavedState)?.superState ?: state)

        if (state !is SavedState) return

        if (_toolbar == null) initView(parent, appBar)

        changeAlphaView(toolbarAlpha = state.toolbarAlpha, todayTextAlpha = state.textAlpha)
        changeBackgroundView(appBar = appBar, color = state.color, elevation = state.elevation)
    }

    override fun onSaveInstanceState(
            parent: CoordinatorLayout, abl: AppBarLayout
    ): Parcelable? = super.onSaveInstanceState(parent, abl)?.let { supperState ->
        SavedState(
                superState = supperState,
                textAlpha = todayText.alpha,
                toolbarAlpha = toolbar.alpha,
                elevation = toolbar.elevation,
                color = toolbar.backgroundTintList?.defaultColor ?: startColor,
        )
    }

    override fun onStartNestedScroll(
            coordinatorLayout: CoordinatorLayout,
            appBar: AppBarLayout,
            directTargetChild: View,
            target: View,
            axes: Int,
            type: Int
    ): Boolean = axes == ViewCompat.SCROLL_AXIS_VERTICAL

    override fun onNestedPreScroll(
            coordinatorLayout: CoordinatorLayout,
            appBar: AppBarLayout,
            target: View,
            dx: Int,
            dy: Int,
            consumed: IntArray,
            type: Int
    ) {
        if (appBar.isCollapsed() && !isAbsDyBiggerInnerScroll(dy, target)) {
            return super.onNestedPreScroll(coordinatorLayout, appBar, target, dx, dy, consumed, type)
        }

        changeView(appBar, topAndBottomOffset)
        scrollState = ScrollState.SCROLL
        super.onNestedPreScroll(coordinatorLayout, appBar, target, dx, dy, consumed, type)
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, appBar: AppBarLayout, target: View, type: Int) {
        super.onStopNestedScroll(coordinatorLayout, appBar, target, type)
        changeView(appBar)
        scrollState = ScrollState.IDLE
    }

    private fun changeView(appBar: AppBarLayout, offset: Int = topAndBottomOffset) {
        if (lastProcessedOffset == offset) return
        lastProcessedOffset = offset

        changeAlpha(offset.toFloat())
        changeBackground(appBar, offset.toFloat())
    }

    private fun changeBackground(appBar: AppBarLayout, offset: Float) {
        val maxOffset = abs(appBar.totalScrollRange).toFloat()
        val startChangeOffset = maxOffset * PERCENT_WHEN_BACKGROUND_START_CHANGED

        val offsetClamp = MathUtils.clamp(abs(offset), startChangeOffset, maxOffset)

        val maxDelta = maxOffset - startChangeOffset
        val delta = maxOffset - offsetClamp
        val valueOffset = 1 - (delta / maxDelta)

        val color = getColorByOffset(startColor, endColor, valueOffset)
        val elevation = MAX_ELEVATION * valueOffset

        changeBackgroundView(appBar, color, elevation)
    }

    private fun changeBackgroundView(appBar: AppBarLayout, color: Int, elevation: Float) {
        appBar.backgroundTintList = ColorStateList.valueOf(color)
        toolbar.backgroundTintList = ColorStateList.valueOf(color)

        appBar.elevation = elevation
        toolbar.elevation = elevation
        appBarAddButton.elevation = elevation
    }

    private fun changeAlpha(offset: Float = topAndBottomOffset.toFloat()) {
        val maxOffset = spaceToTopToolbar.height

        val offsetClamp = MathUtils.clamp(abs(offset), 0F, maxOffset.toFloat())
        var toolbarAlpha = offsetClamp / maxOffset

        if (toolbarAlpha.isNaN()) toolbarAlpha = 0F

        changeAlphaView(toolbarAlpha = toolbarAlpha, todayTextAlpha = 1 - toolbarAlpha)
    }

    private fun changeAlphaView(toolbarAlpha: Float, todayTextAlpha: Float) {
        toolbar.alpha = toolbarAlpha
        appBarAddButton.alpha = toolbarAlpha
        toolbar.isInvisible = toolbarAlpha == 0F
        appBarAddButton.isInvisible = toolbarAlpha == 0F

        todayText.alpha = todayTextAlpha
        bottomAddButton.alpha = todayTextAlpha
        bottomAddButton.isInvisible = todayTextAlpha == 0F
        todayText.isInvisible = todayTextAlpha == 0F
    }

    private fun isAbsDyBiggerInnerScroll(dy: Int, target: View): Boolean = abs(dy) > target.scrollY

    private fun AppBarLayout.isCollapsed(): Boolean = abs(topAndBottomOffset) == totalScrollRange

    @Parcelize
    private data class SavedState(
            val superState: Parcelable,
            val textAlpha: Float,
            val toolbarAlpha: Float,
            val elevation: Float,
            val color: Int,
    ) : Parcelable
}

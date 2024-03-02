package com.anadolstudio.chronos.view.input

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewEditTextBinding
import com.anadolstudio.chronos.util.TempValidator
import com.anadolstudio.utils.util.extentions.nullIfNotExist
import com.anadolstudio.view.basetextinput.delegates.support_hint.ShowSupportHintMode

class InputView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private companion object {
        const val NO_RESOURCE = -1
    }

    private val binding = ViewEditTextBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.withStyledAttributes(attrs, R.styleable.InputView, defStyleAttr, defStyleRes) {
            binding.editText.apply {
                val drawableEnd = getDrawable(R.styleable.InputView_src)
                drawableEnd?.setTint(context.getColor(R.color.colorAccent))
                setDrawableEnd(drawableEnd)
                setHint(getString(R.styleable.InputView_hint))
                setImeOptions(getInt(R.styleable.InputView_imeOptions, EditorInfo.IME_ACTION_DONE))
                setInputType(getInt(R.styleable.InputView_inputType, InputType.TYPE_CLASS_TEXT))
                setDigits(getString(R.styleable.InputView_digits).orEmpty())
                setMaxLength(getInt(R.styleable.InputView_maxLength, NO_RESOURCE).nullIfNotExist())
                setValidator(TempValidator())
            }
        }
    }

    override fun setBackgroundColor(color: Int) = binding.cardView.setCardBackgroundColor(color)

    fun setBackgroundColor(color: Int?) {
        backgroundTintList = color?.let(ColorStateList::valueOf)
    }

    override fun setBackgroundTintList(tint: ColorStateList?) {
        val color = tint ?: ColorStateList.valueOf(context.getColor(R.color.colorPrimary))
        binding.cardView.setCardBackgroundColor(color)
    }

    fun setOnIconClickListener(listener: (() -> Unit)?) = binding.editText.setOnIconClickListener(listener)

    fun setText(text: String, withValidate: Boolean = true) {
        val selectionEnd = binding.editText.getSelectionEnd()

        binding.editText.setText(text, withValidate)
        binding.editText.setSelectionStart(minOf(selectionEnd, text.length))
    }

    fun setSelectorToEnd() = binding.editText.setSelectorToEnd()

    fun addValidateListener(onSuccess: ((String) -> Unit)) = binding.editText.addValidateListener(onSuccess = onSuccess)

    override fun setEnabled(enabled: Boolean) {
        binding.editText.isEnabled = isEnabled
    }

    fun setDrawableEnd(drawable: Drawable?) = binding.editText.setDrawableEnd(drawable)

    fun setSupportHint(text: String) {
        binding.editText.setSupportHintMode(ShowSupportHintMode.PERMANENT)
        binding.editText.showInformationHint(text)
        binding.editText.showSupportHint()
    }

}

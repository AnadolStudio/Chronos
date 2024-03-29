package com.anadolstudio.chronos.util

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import com.anadolstudio.chronos.R
import org.joda.time.DateTime

typealias DateListener = (year: Int, month: Int, dayOfMonth: Int) -> Unit

object CalendarDialog {

    private val TOMORROW_DATE: DateTime = DateTime.now().plusDay()

    fun show(
            context: Context,
            dateListener: DateListener,
            dismissListener: DialogInterface.OnDismissListener? = null,
            maxDate: DateTime = TOMORROW_DATE,
            minDate: DateTime? = null,
            currentDateTime: DateTime,
            showFromYear: Boolean = true
    ): DatePickerDialog {
        val dataPickerDialog = DatePickerDialog(
                context,
                R.style.DatePickerDialogTheme,
                { _, year, month, dayOfMonth -> dateListener.invoke(year, month + 1, dayOfMonth) },
                currentDateTime.year,
                currentDateTime.monthOfYear - 1,
                currentDateTime.dayOfMonth
        ).apply {
            setButton(DatePickerDialog.BUTTON_POSITIVE, context.getString(R.string.global_choice))
            setButton(DatePickerDialog.BUTTON_NEGATIVE, context.getString(R.string.global_cancel))
            setOnDismissListener(dismissListener)
            datePicker.minDate = minDate?.millis ?: 0
            datePicker.maxDate = maxDate.millis
            if (showFromYear) {
                datePicker.touchables[0].performClick()
            }
        }

        dataPickerDialog.show()

        return dataPickerDialog
    }

    private fun DatePickerDialog.setButton(buttonType: Int, text: String) {
        setButton(buttonType, text, this)
    }

}

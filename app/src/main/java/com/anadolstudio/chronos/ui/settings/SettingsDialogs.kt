package com.anadolstudio.chronos.ui.settings

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.anadolstudio.chronos.R
import com.anadolstudio.core.dialogs.BaseDialogUtil

object SettingsDialogs {

    fun showRemoveDialog(
            context: Context,
            message: String,
            onRemoveAction: () -> Unit
    ): AlertDialog = buildChronosAlertDialog(
            context = context,
            message = message,
            positiveButtonAction = onRemoveAction,
            title = context.getString(R.string.global_remove_title),
    )

    private fun buildChronosAlertDialog(
            context: Context,
            positiveButtonText: String = context.getString(android.R.string.ok),
            positiveButtonAction: (() -> Unit)? = null,
            negativeButtonText: String? = null,
            negativeButtonAction: (() -> Unit)? = null,
            title: String? = null,
            message: String? = null
    ): AlertDialog = BaseDialogUtil.buildAlertDialog(
            context = context,
            buttonsColor = context.getColor(R.color.colorAccent),
            positiveButtonText = positiveButtonText,
            positiveButtonAction = positiveButtonAction,
            negativeButtonText = negativeButtonText,
            negativeButtonAction = negativeButtonAction,
            title = title,
            message = message
    )

}

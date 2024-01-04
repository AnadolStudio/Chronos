package com.anadolstudio.chronos.util

import com.anadolstudio.chronos.R
import com.anadolstudio.core.view.basetextinput.validator.validator.implementation.SimpleValidator

@Deprecated("Необходимо переделать BaseTextInput")
class TempValidator : SimpleValidator(
        condition = { true },
        errorMessage = R.string.common_error_message
)
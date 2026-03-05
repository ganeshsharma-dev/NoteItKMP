package com.note.it.core.presentation


fun UiText.asPlainString(): String {
    return when (this) {
        is UiText.DynamicString -> this.value
        is UiText.StringResourceId -> "Unknown error"
    }
}

package com.note.it.core.presentation

import cmp_noteit.composeapp.generated.resources.Res
import cmp_noteit.composeapp.generated.resources.error_no_internet
import cmp_noteit.composeapp.generated.resources.error_request_timeout
import cmp_noteit.composeapp.generated.resources.error_serialization
import cmp_noteit.composeapp.generated.resources.error_too_many_requests
import cmp_noteit.composeapp.generated.resources.error_unknown
import com.note.it.core.domain.DataError

fun DataError.toUiText(): UiText {
    return when (this) {

        is DataError.Remote.BadRequest ->
            UiText.DynamicString(this.message)

        is DataError.Remote.ExceptionError ->
            UiText.DynamicString(this.reason)

        DataError.Remote.NO_INTERNET ->
            UiText.StringResourceId(Res.string.error_no_internet)

        DataError.Remote.REQUEST_TIMEOUT ->
            UiText.StringResourceId(Res.string.error_request_timeout)

        DataError.Remote.TOO_MANY_REQUESTS ->
            UiText.StringResourceId(Res.string.error_too_many_requests)

        DataError.Remote.SERVER ->
            UiText.StringResourceId(Res.string.error_unknown)

        DataError.Remote.SERIALIZATION ->
            UiText.StringResourceId(Res.string.error_serialization)

        DataError.Remote.UNKNOWN ->
            UiText.StringResourceId(Res.string.error_unknown)

        else ->
            UiText.StringResourceId(Res.string.error_unknown)
    }
}

package com.note.it.app.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotesRequest(
    @SerialName("userId")
    val userId: String,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("isFavorite")
    val isFavorite: Boolean

)

@Serializable
data class NotesResponse(
    @SerialName("data")
    val data: Note
) : BaseResponse()


@Serializable
data class NotesListResponse(
    @SerialName("data")
    val data: List<Note>
) : BaseResponse()

@Serializable
data class Note(
    @SerialName("id")
    val id: String,
    @SerialName("userId")
    val userId: String,
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("date")
    val timestamp: String,
    @SerialName("isFavorite")
    val isFavorite: Boolean
)




@Serializable
data class SharedNoteListResponse(
    @SerialName("data")
    val data: List<SharedNoteData> = emptyList()
) : BaseResponse()

@Serializable
data class SharedNoteData(
    @SerialName("shareId")
    var shareId: String? = null,
    @SerialName("noteId")
    var noteId: String? = null,
    @SerialName("title")
    var title: String? = null,
    @SerialName("contentPreview")
    var contentPreview: String? = null,
    @SerialName("noteCreatedAt")
    var noteCreatedAt: String? = null,
    @SerialName("sharedAt")
    var sharedAt: String? = null,
    @SerialName("permission")
    var permission: String? = null,
    @SerialName("sharedType")
    var sharedType: String? = null,
    @SerialName("sharedBy")
    var sharedBy: SharedBy? = SharedBy(),
    @SerialName("sharedTo")
    var sharedTo: SharedTo? = SharedTo()
)


@Serializable
data class ShareNoteRequest(
    val noteId: String,
    val ownerId: String,
    val identifier: String,   // email or mobile
    val permission: String = "read"
)

@Serializable
class ShareNoteResponse(
    @SerialName("data")
    val data: SharedNoteData,
) : BaseResponse()

/*
@Serializable
data class SharedNoteTo(
    @SerialName("sharedBy")
    var sharedBy: SharedBy? = SharedBy(),
    @SerialName("sharedTo")
    var sharedTo: SharedTo? = SharedTo(),
    @SerialName("sharedAt")
    val sharedAt: String,
    @SerialName("permission")
    val permission: String = "read",
)
*/



@Serializable
data class SharedBy(
    @SerialName("id") var id: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("mobile") var mobile: String? = null
)


@Serializable
data class SharedTo(
    @SerialName("id") var id: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("mobile") var mobile: String? = null

)

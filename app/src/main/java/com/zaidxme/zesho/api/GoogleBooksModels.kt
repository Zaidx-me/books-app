package com.zaidxme.zesho.api

import com.google.gson.annotations.SerializedName

data class GoogleBooksResponse(
    @SerializedName("items") val items: List<BookItem>?
)

data class BookItem(
    @SerializedName("id") val id: String,
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo,
    @SerializedName("accessInfo") val accessInfo: AccessInfo?
)

data class VolumeInfo(
    @SerializedName("title") val title: String,
    @SerializedName("authors") val authors: List<String>?,
    @SerializedName("description") val description: String?,
    @SerializedName("imageLinks") val imageLinks: ImageLinks?,
    @SerializedName("previewLink") val previewLink: String?,
    @SerializedName("infoLink") val infoLink: String?,
    @SerializedName("categories") val categories: List<String>?
)

data class ImageLinks(
    @SerializedName("thumbnail") val thumbnail: String?
)

data class AccessInfo(
    @SerializedName("pdf") val pdf: PdfInfo?,
    @SerializedName("webReaderLink") val webReaderLink: String?
)

data class PdfInfo(
    @SerializedName("isAvailable") val isAvailable: Boolean,
    @SerializedName("acsTokenLink") val acsTokenLink: String?
)

package app.id.technicaltest.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hero(val id: Int, val avatar: Int, val name: String, val desc: String) : Parcelable

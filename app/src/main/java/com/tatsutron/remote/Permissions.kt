package com.tatsutron.remote

enum class Permissions(val id: String) {
    CAMERA(android.Manifest.permission.CAMERA);

    val requestCode: Int
        get() = ordinal
}

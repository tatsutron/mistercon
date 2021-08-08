package com.tatsutron.remote

enum class Permissions(val id: String, val noPermissionStringId: Int) {

    CAMERA(
        id = android.Manifest.permission.CAMERA,
        noPermissionStringId = R.string.no_permission_camera,
    );

    val requestCode: Int
        get() = ordinal
}

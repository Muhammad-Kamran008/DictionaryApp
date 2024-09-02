package com.example.dictionaryapp.core.util

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity

fun Context.toast(massage:String)= Toast.makeText(this,massage,Toast.LENGTH_LONG).show()

 fun Activity.onBackButtonPressed(callback: (() -> Boolean)) {
    (this as? FragmentActivity)?.onBackPressedDispatcher?.addCallback(
        this,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!callback()) {
                    remove()
                    performBackPress()
                }
            }
        })
}

fun Activity.performBackPress() {
    (this as? FragmentActivity)?.onBackPressedDispatcher?.onBackPressed()
}

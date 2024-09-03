package com.example.dictionaryapp.ui.components
import android.app.Activity
import com.example.dictionaryapp.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

object ExitBottomSheet {

    fun showBottomSheetDialog(activity: Activity, onExitClick: () -> Unit) {
        val bottomSheetView = BottomSheetLayoutBinding.inflate(activity.layoutInflater)
        val animationView=bottomSheetView.animationView
        animationView.playAnimation()
        val bottomSheetDialog = BottomSheetDialog(activity).apply {
            setContentView(bottomSheetView.root)
        }

        bottomSheetView.btnExit.setOnClickListener {
            bottomSheetDialog.dismiss()
            onExitClick()
        }

        bottomSheetView.btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
}



package com.example.dictionaryapp.ui.components

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.TextView
import com.example.dictionaryapp.R

class MyDialog(
    context: Context,
    private val onConfirmDelete: () -> Unit,
    private val onCancel: () -> Unit
) : Dialog(context) {

    private lateinit var titleView: TextView
    private lateinit var messageView: TextView
    private lateinit var cancelButton: Button
    private lateinit var confirmButton: Button

    init {
        setContentView(R.layout.sample_my_dialog)
        initViews()
        setButtonListeners()
    }

    private fun initViews() {
        titleView = findViewById(R.id.dialog_title)
        messageView = findViewById(R.id.dialog_message)
        cancelButton = findViewById(R.id.dialog_cancel)
        confirmButton = findViewById(R.id.dialog_confirm)
    }

    private fun setButtonListeners() {
        cancelButton.setOnClickListener {
            onCancel()
            dismiss()
        }

        confirmButton.setOnClickListener {
            onConfirmDelete()
            dismiss()
        }
    }
}

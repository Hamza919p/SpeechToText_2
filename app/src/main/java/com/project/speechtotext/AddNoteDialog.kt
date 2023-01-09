package com.project.speechtotext

import android.app.Activity
import android.app.Dialog
import android.view.ViewGroup
import android.view.Window
import com.project.speechtotext.databinding.NoteDialogBinding


class AddNoteDialog(activity: Activity): Dialog(activity, R.style.dialog_style_simple) {

    private var binding: NoteDialogBinding

    private lateinit var onDismiss: OnDismissListener
    interface OnDismissListener {
        fun onDismiss(value: String)
    }
    fun setOnDismissListener(mCallback: OnDismissListener){
        onDismiss = mCallback
    }

    init {

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setCancelable(false)
        binding = NoteDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.setCancelable(false)


        binding.btnSave.setOnClickListener{
            dismiss()
            onDismiss.onDismiss(binding.etNote.text.toString())
        }

        binding.icCancel.setOnClickListener{
            dismiss()
            onDismiss.onDismiss("")
        }

    }



}
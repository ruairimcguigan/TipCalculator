package com.aquidigital.tipcalculator.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.aquidigital.tipcalculator.R

class TipCalculationSaveDialog: DialogFragment() {

    private var saveTipCallback: TipCalculationSaveDialog.Callback? = null

    interface Callback {
        fun saveTipCallback(name: String)
    }

    companion object {
        val viewId = View.generateViewId()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        saveTipCallback = context as? Callback
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val saveDialog = context?.let { context ->
            val editText = EditText(context)

            editText.id = viewId
            editText.hint = "Enter location"

            AlertDialog.Builder(context)
                .setView(editText)
                .setNegativeButton(R.string.action_cancel, null)
                .setPositiveButton(R.string.action_save, {_,_ -> onSave(editText)})
                .create()
        }
        return saveDialog!!
    }

    private fun onSave(editText: EditText) {
        val text = editText.text.toString()

        if (text.isNotEmpty()) {
            saveTipCallback?.saveTipCallback(text)
        }
    }

    override fun onDetach() {
        super.onDetach()
        saveTipCallback = null
    }
}
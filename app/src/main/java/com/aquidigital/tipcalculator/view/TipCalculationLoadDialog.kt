package com.aquidigital.tipcalculator.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.aquidigital.tipcalculator.R
import com.aquidigital.tipcalculator.viewmodel.CalculatorViewModel
import kotlinx.android.synthetic.main.saved_tip_calculations_list.view.*

class TipCalculationLoadDialog: DialogFragment() {

    private var loadTipCallback: Callback? = null

    interface Callback {
        fun onTipSelected(name: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loadTipCallback = context as? Callback
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val loadDialog = context?.let { context ->
            AlertDialog.Builder(context)
                .setView(createView(context))
                .setNegativeButton(R.string.action_cancel, null)
                .create()
        }
        return loadDialog!!
    }

    private fun createView(context: Context): View? {

        val adapter = TipCalculationAdapter{
            loadTipCallback?.onTipSelected(it.locationName)
            dismiss()
        }

        val list = LayoutInflater
            .from(context)
            .inflate(R.layout.saved_tip_calculations_list, null)
            .savedCalculationsList

        list.adapter = adapter
        list.setHasFixedSize(true)
        list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val vm = ViewModelProviders.of(activity!!).get(CalculatorViewModel::class.java)

        vm.loadSavedTipCalculationSummaries().observe(this, Observer {
            if (it != null) {
                adapter.updateList(it)
            }
        })

        return list
    }


}
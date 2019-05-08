package com.aquidigital.tipcalculator.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.aquidigital.tipcalculator.R
import com.aquidigital.tipcalculator.databinding.ActivityTipCalculatorBinding
import com.aquidigital.tipcalculator.viewmodel.CalculatorViewModel
import com.google.android.material.snackbar.Snackbar

class TipCalculatorActivity : AppCompatActivity(),
    TipCalculationSaveDialog.Callback,
    TipCalculationLoadDialog.Callback {

    lateinit var binding: ActivityTipCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tip_calculator)
        binding.vm = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_tip_calculator, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                showSaveDialog()
                true
            }
            R.id.action_load -> {
                showLoadDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSaveDialog() {
        val saveFragment = TipCalculationSaveDialog()
        saveFragment.show(supportFragmentManager, "SaveDialog")
    }

    private fun showLoadDialog() {
        val loadDialog = TipCalculationLoadDialog()
        loadDialog.show(supportFragmentManager, "LoadDialog")
    }

    override fun onTipSelected(name:String) {
        binding.vm?.loadTipCalculation(name)
        Snackbar.make(binding.root, "Loaded $name", Snackbar.LENGTH_SHORT).show()
    }

    override fun saveTipCallback(name: String) {
        binding.vm?.saveCurrentTip(name)
        Snackbar.make(binding.root, "Saved $name", Snackbar.LENGTH_SHORT).show()
    }
}

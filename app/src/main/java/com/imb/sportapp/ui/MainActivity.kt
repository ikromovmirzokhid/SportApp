package com.imb.sportapp.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.ads.MobileAds
import com.imb.sportapp.R
import com.imb.sportapp.utils.HawkUtils

class MainActivity : AppCompatActivity() {
    private lateinit var progressDialog: AlertDialog
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this)

        closeNightMode()
        initDialog()
    }

    private fun closeNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun initDialog() {
        view = layoutInflater.inflate(R.layout.dialog_progress_view, null)
        progressDialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
    }

    fun hideProgress() {
        if (this::progressDialog.isInitialized) {
            progressDialog.cancel()
        }
    }

    fun showProgress() {
        if (this::progressDialog.isInitialized) {
            progressDialog.show()
        }
    }

    override fun onDestroy() {
        hideProgress()
        super.onDestroy()
    }
}
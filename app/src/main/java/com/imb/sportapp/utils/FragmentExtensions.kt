package com.imb.a20220525_mirzokhidjonikromov_nycschools.utils

import android.app.AlertDialog
import com.imb.sportapp.R
import com.imb.sportapp.ui.fragments.BaseFragment


fun BaseFragment.progressOn() {
    getMain()?.showProgress()
}

fun BaseFragment.progressOff() {
    getMain()?.hideProgress()
}

fun BaseFragment.showError(errorMessage: String) {
    context?.apply {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.attention_text))
            .setMessage(errorMessage)
            .setPositiveButton("OK", null)
            .show()
    }
}
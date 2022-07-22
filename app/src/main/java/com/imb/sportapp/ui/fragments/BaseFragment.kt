package com.imb.sportapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.imb.sportapp.ui.MainActivity

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply {
            firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        }
    }

    fun getMain(): MainActivity? {
        return requireActivity() as MainActivity
    }

    fun pressBack() {
        activity?.let { act ->
            if (act is MainActivity) {
                act.onBackPressed()
            }
        }
    }
}
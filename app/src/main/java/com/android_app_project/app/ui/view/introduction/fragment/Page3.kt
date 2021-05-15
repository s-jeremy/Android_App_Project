package com.android_app_project.app.ui.view.introduction.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android_app_project.app.R
import com.android_app_project.app.ui.view.main.MainActivity
import kotlinx.android.synthetic.main.fragment_page3.*

class Page3 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_page3, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        skip3.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        fun newInstance() = Page3()
    }
}
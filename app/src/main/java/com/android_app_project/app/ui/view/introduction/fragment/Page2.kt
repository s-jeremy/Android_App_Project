package com.android_app_project.app.ui.view.introduction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android_app_project.app.R

//Page d'information de la technologie utilisé
class Page2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_page2, container, false)

        view?.findViewById<ImageView>(R.id.skip2)?.setOnClickListener{
            findNavController().navigate(Page2Directions.actionPage2ToPage3())
        }

        return  view

    }

    companion object {
        fun newInstance() = Page2()
    }
}
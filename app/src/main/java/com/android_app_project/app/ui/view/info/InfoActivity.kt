package com.android_app_project.app.ui.view.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android_app_project.app.R
import com.android_app_project.app.ui.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class InfoActivity : AppCompatActivity() {


    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, InfoActivity::class.java)
        }
    }

    private val myViewModel: InfoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        myViewModel.states.observe(this, Observer { state ->
            when(state){
                is InfoViewModel.GetVersionResult -> showInformation(state.version)
                is Failed -> finish()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        myViewModel.getVersion()
    }

    private fun showInformation(version: String) {
        //Toast.makeText(this,version,Toast.LENGTH_SHORT).show()
        findViewById<TextView>(R.id.version).text = version
    }

}
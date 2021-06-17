package com.android_app_project.app.ui.view.collected_data

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivityCollectedDataBinding
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.user_creation.UserCreationViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CollectedDataActivity : AppCompatActivity()  {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, CollectedDataActivity::class.java)
        }
    }
    private lateinit var binding: ActivityCollectedDataBinding

    private val myViewModel: CollectedDataViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collected_data)

        // Binding de la vue
        binding = ActivityCollectedDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.collectButton.setOnClickListener {
            Log.d("Affichage Data","Afficher Data")
            myViewModel.collecteData(binding.userId.text.toString().toInt())
        }


        myViewModel.states.observe(this, Observer { state ->
            when(state){
                is CollectedDataViewModel.GetCollectDataResult -> showInformation(state.status)
                is Failed -> finish()
            }
        })

    }

    private fun showInformation(status: Array<String>) {
        if(status[0] == "NOT-FOUND"){
            Toast.makeText(this, "L'utilisateur n'a pas était trouver.", Toast.LENGTH_SHORT).show()
        }
        else{
            //Ecriture dans les TextView dédiés
            binding.txtLight.text=status[0]
            binding.txtBattery.text=status[1]
            binding.txtPressure.text=status[2]
            binding.txtTemperature.text=status[3]
            binding.txtPosition.text=status[4]
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_from_left,R.anim.anim_slide_to_right)
    }
}
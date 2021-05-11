package com.android_app_project.app.ui.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android_app_project.app.R
import com.android_app_project.app.data.remote.SampleRemoteDataSource
import com.android_app_project.app.databinding.ActivityLoginBinding
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.user_creation.UserCreationActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity()  {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, LoginActivity::class.java)
        }
    }

    private lateinit var binding: ActivityLoginBinding

    private val myViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Binding de la vue
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCreateUser.setOnClickListener {
            startActivity(UserCreationActivity.getStartIntent(this))
        }

        /*binding.buttonValidate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                runCatching {
                    //SampleRemoteDataSource.instance.readUsers(binding.userLogin.toString(), binding.passwordLogin.toString())
                    val arrStatus = myViewModel.states
                    runOnUiThread{
                        //dataSource.addAll(arrStatus)
                        Toast.makeText(this@LoginActivity,"test",Toast.LENGTH_SHORT).show()
                        //Toast.makeText(this@LoginActivity, "Résultat de l'authentificatin" + arrStatus.value, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }*/
        myViewModel.states.observe(this, { state ->
            when(state) {
                is LoginViewModel.LoginResult -> showInformation(state.status.toString())
                is Failed -> finish()
            }
        })

    }

    override fun onResume() {
        super.onResume()
        myViewModel.login()
    }

    private fun showInformation(status: String) {
        findViewById<Button>(R.id.buttonValidate).text = status
        print("Resultat de Login: " + status)
        //Toast.makeText(this,version,Toast.LENGTH_SHORT).show()
        //Toast.makeText(this,status,Toast.LENGTH_SHORT).show()
        //Toast.makeText(this@LoginActivity, "Résultat de l'authentification" + myViewModel, Toast.LENGTH_SHORT).show()
    }

}
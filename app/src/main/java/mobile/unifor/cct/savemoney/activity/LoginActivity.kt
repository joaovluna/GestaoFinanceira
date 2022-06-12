package mobile.unifor.cct.savemoney.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mobile.unifor.cct.savemoney.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mLoginRegister: TextView
    private lateinit var mLoginSignIn: Button
    private lateinit var mLoginEmail: EditText
    private lateinit var mLoginPassword: EditText

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = Firebase.auth

        mLoginPassword = findViewById(R.id.login_editText_password)
        mLoginEmail = findViewById(R.id.login_editText_email)
        mLoginSignIn = findViewById(R.id.login_button_signin)
        mLoginRegister = findViewById(R.id.login_textView_register)

        mLoginRegister.setOnClickListener(this)
        mLoginSignIn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.login_textView_register -> {
                val it = Intent(this, RegisterActivity::class.java)
                startActivity(it)
            }
            R.id.login_button_signin -> confirmLogin()
        }
    }

    private fun confirmLogin() {
        val email = mLoginEmail.text.toString().trim()
        val password = mLoginPassword.text.toString()

        var isFormFilled = true

        if (email.isBlank()) {
            mLoginEmail.error = "Campo Obrigatório!"
            isFormFilled = false
        }
        if (password.isBlank()) {
            mLoginPassword.error = "Campo Obrigatório!"
            isFormFilled = false
        }


        if (isFormFilled) {
            mAuth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val it = Intent(this, MainActivity::class.java)
                        startActivity(it)
                        finish()
                    } else {
                        showDialogMessage("Usuário ou senha incorretos, tente novamente")
                        //apresenta qnd tem erro de comunicacao ou o usuario nao existe, ou o usuario erra login
                    }

                }


        }
    }

    private fun showDialogMessage(message: String) {
        val dialog = AlertDialog.Builder(this@LoginActivity)
            .setTitle("Save Money")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()

            }.create()
        dialog.show()
    }

}
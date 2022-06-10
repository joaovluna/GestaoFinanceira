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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mobile.unifor.cct.savemoney.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mLoginRegister: TextView
    private lateinit var mLoginSignIn: Button
    private lateinit var mLoginEmail: EditText
    private lateinit var mLoginPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
        val email = mLoginEmail.toString()
        val password = mLoginPassword.toString()

        var isFormFilled = true

        if (email.isBlank()) {
            mLoginEmail.error = "Campo Obrigatório!"
            isFormFilled = false
        }
        if(password.isBlank()) {
            mLoginPassword.error = "Campo Obrigatório!"
            isFormFilled = false
        }

        val handler = Handler(Looper.myLooper()!!)
        if(isFormFilled) {
//            GlobalScope.launch {
//                val userDAO = DatabaseUtil.getInstance(applicationContext).getUserDAO()
//                val user = userDAO.findByEmail(email)
//
//                if(user != null) {
//                    if(user.password == password) {
//                        val it = Intent(applicationContext, MainActivity::class.java)
//                        startActivity(it)
//                        finish()
//                    } else {
//                        handler.post {
//                            mLoginEmail.text.clear()
//                            mLoginPassword.text.clear()
//                            showDialog("Usuário ou senha inválido")
//
//                        }
//
//                    }
//                } else {
//                    handler.post {
//                        mLoginEmail.text.clear()
//                        mLoginPassword.text.clear()
//                        showDialog("Usuário ou senha inválido")
//
//                    }
//                }
//            }
        }

    }
    private fun showDialog(message: String) {

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
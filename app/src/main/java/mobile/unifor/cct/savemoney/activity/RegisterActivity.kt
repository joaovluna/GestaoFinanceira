package mobile.unifor.cct.savemoney.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mobile.unifor.cct.savemoney.R
import mobile.unifor.cct.savemoney.entity.User

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mRegisterName: EditText
    private lateinit var mRegisterEmail: EditText
    private lateinit var mRegisterPhone: EditText
    private lateinit var mRegisterPassword: EditText
    private lateinit var mRegisterPasswordConfirmation: EditText
    private lateinit var mRegisterSave: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = Firebase.auth
        mDatabase = Firebase.database

        mRegisterName = findViewById(R.id.register_editText_name)
        mRegisterEmail = findViewById(R.id.register_editText_email)
        mRegisterPhone = findViewById(R.id.register_editText_phone)
        mRegisterPassword = findViewById(R.id.register_editText_password)
        mRegisterPasswordConfirmation = findViewById(R.id.register_editText_passwordConfirmation)

        mRegisterSave = findViewById(R.id.register_button_save)

        mRegisterSave.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.register_button_save -> saveAction()
        }
    }

    private fun saveAction() {
        val name = mRegisterName.text.toString().trim()
        val phone = mRegisterPhone.text.toString().trim()
        val email = mRegisterEmail.text.toString().trim()
        val password = mRegisterPassword.text.toString().trim()
        val passwordConfirmation = mRegisterPasswordConfirmation.text.toString().trim()

        var isFormFilled = true

        isFormFilled = isNameFilled(name) && isFormFilled
        isFormFilled = isPhoneFilled(phone) && isFormFilled
        isFormFilled = isEmailFilled(email) && isFormFilled
        isFormFilled = isPasswordFilled(password) && isFormFilled
        isFormFilled = isPasswordConfirmationFilled(passwordConfirmation) && isFormFilled

        if (isFormFilled) {
            if (password == passwordConfirmation) {
                // todo: realizar cadastro novo usuario
                val userRef = mDatabase.getReference("/users")
                val key = userRef.push().key ?: ""

                val user = User(key,name,email, phone)
                userRef.child("/$key").setValue(user)

                mAuth
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful) {
                            val dialog = AlertDialog.Builder(this)
                                .setTitle("Save Money")
                                .setMessage("Usuário criado com sucesso!")
                                .setCancelable(false)
                                .setPositiveButton("Ok") { dialog, _ ->
                                    dialog.dismiss()
                                    finish()
                                }.create()
                            dialog.show()

                        } else {
                            showDialogMessage("Erro ao criar usuário, tente novamente.")
                        }

                    }



            } else {
                mRegisterPasswordConfirmation.error = "Senhas incompatíveis"
            }
        }

    }

    private fun isNameFilled(name: CharSequence): Boolean {
        return if (name.isBlank()) {
            mRegisterName.error = "Este campo é obrigatório!"
            false
        } else {
            true
        }
    }

    private fun isPhoneFilled(phone: String): Boolean {
        return if (phone.isBlank()) {
            mRegisterPhone.error = "Este campo é obrigatório!"
            false
        } else {
            true
        }
    }

    private fun isEmailFilled(email: String): Boolean {
        return if (email.isBlank()) {
            mRegisterEmail.error = "Este campo é obrigatório!"
            false
        } else {
            true
        }
    }

    private fun isPasswordFilled(password: String): Boolean {
        return if (password.isBlank()) {
            mRegisterPassword.error = "Este campo é obrigatório!"
            false
        } else {
            true
        }
    }

    private fun isPasswordConfirmationFilled(passwordConfirmation: String): Boolean {
        return if (passwordConfirmation.isBlank()) {
            mRegisterPasswordConfirmation.error = "Este campo é obrigatório!"
            false
        } else {
            true
        }
    }

    private fun showDialogMessage(message: String) {
        val dialog = AlertDialog.Builder(this@RegisterActivity)
            .setTitle("Save Money")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()

            }.create()
        dialog.show()
    }
}
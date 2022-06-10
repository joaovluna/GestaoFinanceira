package mobile.unifor.cct.savemoney.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mobile.unifor.cct.savemoney.R

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mRegisterName: EditText
    private lateinit var mRegisterEmail: EditText
    private lateinit var mRegisterPhone: EditText
    private lateinit var mRegisterPassword: EditText
    private lateinit var mRegisterPasswordConfirmation: EditText
    private lateinit var mRegisterSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mRegisterName = findViewById(R.id.register_editText_name)
        mRegisterEmail = findViewById(R.id.register_editText_email)
        mRegisterPhone = findViewById(R.id.register_editText_phone)
        mRegisterPassword = findViewById(R.id.register_editText_password)
        mRegisterPasswordConfirmation = findViewById(R.id.register_editText_passwordConfirmation)

        mRegisterSave = findViewById(R.id.register_button_save)

        mRegisterSave.setOnClickListener(this)
    }

    override fun onClick(view: View?){

        when(view?.id) {
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
        isFormFilled = isPasswordFilled(password)&& isFormFilled
        isFormFilled = isPasswordConfirmationFilled(passwordConfirmation) && isFormFilled

        if(isFormFilled){
            if(password == passwordConfirmation) {

                // TODO: Criar uma instância de usuário e salvar no banco -> FireBase

                GlobalScope.launch {
                    // TODO: Inserindo no Room -> Vamos inserir no Firebase.
//                    val userDAO = DatabaseUtil.getInstance(applicationContext).getUserDAO()
//                    userDAO.insert(user)


                }

                Toast.makeText(
                    applicationContext,
                    "Usuario $name inserido com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()

            } else {
                mRegisterPasswordConfirmation.error = "Senhas incompatíveis"
            }
        }

    }

    private fun isNameFilled(name: CharSequence): Boolean {
        return if(name.isBlank()) {
            mRegisterName.error = "Este campo é obrigatório!"
            false
        } else {
            true
        }
    }
    private fun isPhoneFilled(phone: String): Boolean {
        return if(phone.isBlank()) {
            mRegisterPhone.error = "Este campo é obrigatório!"
            false
        } else {
            true
        }
    }
    private fun isEmailFilled(email: String): Boolean {
        return if(email.isBlank()) {
            mRegisterEmail.error = "Este campo é obrigatório!"
            false
        } else {
            true
        }
    }
    private fun isPasswordFilled(password: String): Boolean {
        return if(password.isBlank()) {
            mRegisterPassword.error = "Este campo é obrigatório!"
            false
        } else {
            true
        }
    }
    private fun isPasswordConfirmationFilled(passwordConfirmation: String): Boolean {
        return if(passwordConfirmation.isBlank()) {
            mRegisterPasswordConfirmation.error = "Este campo é obrigatório!"
            false
        } else {
            true
        }
    }
}
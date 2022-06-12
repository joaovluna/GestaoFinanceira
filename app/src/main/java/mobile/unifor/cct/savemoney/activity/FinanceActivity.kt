package mobile.unifor.cct.savemoney.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mobile.unifor.cct.savemoney.R
import mobile.unifor.cct.savemoney.entity.Finance
import mobile.unifor.cct.savemoney.entity.User

class FinanceActivity : AppCompatActivity() {

    private lateinit var mFinanceName: EditText
    private lateinit var mFinanceDescription: EditText
    private lateinit var mFinanceValue: EditText
    private lateinit var mFinanceAction: Button

    private lateinit var mFinanceLucre: RadioButton

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase

    private var mFinanceKey = ""
    private var mUserKey = ""
    private var mFinanceRadioGroup: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)

        mFinanceLucre = findViewById(R.id.finance_radioButton_lucre)

        mUserKey = intent.getStringExtra("userKey") ?: ""
        mFinanceKey = intent.getStringExtra("financeKey") ?: ""

        mAuth = Firebase.auth
        mDatabase = Firebase.database

        mFinanceName = findViewById(R.id.finance_editText_name)
        mFinanceDescription = findViewById(R.id.finance_editText_description)
        mFinanceValue = findViewById(R.id.finance_editText_value)

        mFinanceAction = findViewById(R.id.finance_button_action)


        mFinanceAction.setOnClickListener {

            val name = mFinanceName.text.toString().trim()
            val description = mFinanceDescription.text.toString().trim()
            val value = mFinanceValue.text.toString().trim()
            val movement = mFinanceRadioGroup
            var isFinanceFilled = true
            isFinanceFilled = isNameFilled(name) && isFinanceFilled
            isFinanceFilled = isValueFilled(value) && isFinanceFilled

            if (isFinanceFilled) {

                if (mFinanceKey.isBlank()) {

                    val financeRef = mDatabase.reference
                        .child("/users")
                        .child(mUserKey)
                        .child("/finances")

                    val financeKey = financeRef.push().key ?: ""
                    val finance = Finance(financeKey, name, value, description, movement)
                    financeRef.child(financeKey).setValue(finance)

                    val dialog = AlertDialog.Builder(this@FinanceActivity)
                        .setTitle("Save Money")
                        .setMessage("Movimentação $name, cadastrada com sucesso!")
                        .setCancelable(false)
                        .setPositiveButton("Ok") { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        }.create()
                    dialog.show()

                } else {
                    val finance = Finance(mFinanceKey, name, value, description, movement)

                    val financeRef = mDatabase.reference
                        .child("/users")
                        .child(mUserKey)
                        .child("/finances")
                        .child(mFinanceKey)
                    financeRef.setValue(finance)

                    val dialog = AlertDialog.Builder(this@FinanceActivity)
                        .setTitle("Save Money")
                        .setMessage("Movimentação $name, atualizada com sucesso!")
                        .setCancelable(false)
                        .setPositiveButton("Ok") { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        }.create()
                    dialog.show()

                }

            }


        }

    }

    private fun isValueFilled(value: String): Boolean {
        return if (value.isBlank()) {
            mFinanceValue.error = "Este campo é obrigatório!"
            false
        } else {
            true
        }

    }

    private fun isNameFilled(name: String): Boolean {
        return if (name.isBlank()) {
            mFinanceName.error = "Este campo é obrigatório!"
            false
        } else {
            true
        }

    }

    override fun onResume() {
        super.onResume()
        if (mFinanceKey.isBlank()) {
            mFinanceAction.text = "Cadastrar"
        } else {
            mFinanceAction.text = "Atualizar"

            val userRef = mDatabase.getReference("/users")
            userRef
                .orderByChild("email")
                .equalTo(mAuth.currentUser?.email!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.children.first().getValue(User::class.java)
                        val finance = user?.finances?.values?.find { it.id == mFinanceKey }
                        mFinanceName.text =
                            Editable.Factory.getInstance().newEditable(finance?.name)
                        mFinanceValue.text =
                            Editable.Factory.getInstance().newEditable(finance?.value)
                        mFinanceDescription.text =
                            Editable.Factory.getInstance().newEditable(finance?.description)
                        mFinanceRadioGroup = finance?.movement == true

                        mFinanceLucre.isChecked = mFinanceRadioGroup //seta corretamente o radiobutton par atualizacao

                    }

                    override fun onCancelled(error: DatabaseError) {}

                })
        }

    }

    fun onRadioButtonClicked(view: View) {

        when (view.getId()) {
            R.id.finance_radioButton_lucre -> mFinanceRadioGroup = true
            R.id.finance_radioButton_loss -> mFinanceRadioGroup = false
        }

    }

}
package mobile.unifor.cct.savemoney.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ParcelFileDescriptor
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mobile.unifor.cct.savemoney.R
import mobile.unifor.cct.savemoney.entity.Finance

class FinanceActivity : AppCompatActivity() {

    private lateinit var mFinanceName: EditText
    private lateinit var mFinanceDescription: EditText
    private lateinit var mFinanceValue: EditText
    private lateinit var mFinanceAction: Button
    private lateinit var mFinanceRadioGroup: RadioGroup


    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)

        mAuth = Firebase.auth
        mDatabase = Firebase.database

        mFinanceName = findViewById(R.id.finance_editText_name)
        mFinanceDescription = findViewById(R.id.finance_editText_description)
        mFinanceValue = findViewById(R.id.finance_editText_value)

        mFinanceAction = findViewById(R.id.finance_button_action)

        mFinanceRadioGroup = findViewById(R.id.finance_RadioGroup_movement)

        mFinanceAction.setOnClickListener {

            val name = mFinanceName.text.toString().trim()
            val description = mFinanceDescription.text.toString().trim()
            val value = mFinanceValue.text.toString().trim() // TODO: Valor será decimal, verificar como fica a validação
            val isMovement = mFinanceRadioGroup.isActivated     // TODO: FAZER A VERIFICACAO DO RADIOGROUP QUE ESTA SELECIONADO PARA ALTERACAO DE COR

            //TODO: REALIZAR CADASTRO DE UMA NOVA RECEITA
            val userRef = mDatabase.getReference("/users")
            userRef
                .orderByChild("email")
                .equalTo("${mAuth.currentUser?.email}")
                .addChildEventListener(object: ChildEventListener{
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        val userKey = snapshot.key ?: ""
                        val financeRef = userRef.child("${userKey}").child("/finances")
                        val financeKey = financeRef.push().key
                        val finance = Finance(financeKey,name, value, description, isMovement)
                        financeRef.child("$financeKey").setValue(finance)

                        val dialog = AlertDialog.Builder(this@FinanceActivity)
                            .setTitle("Save Money")
                            .setMessage("Movimentação $name, cadastrada com sucesso!")
                            .setCancelable(false)
                            .setPositiveButton("Ok") { dialog, _ ->
                                dialog.dismiss()
                                finish()
                            }.create()
                        dialog.show()
                    }

                    override fun onChildChanged(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {
                    }

                    override fun onChildRemoved(snapshot: DataSnapshot) {
                    }

                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })

            //TODO: ATUALIZAR UMA RECEITA EXISTENTE
        }

    }

    override fun onResume() {
        super.onResume()
        mFinanceAction.text = "Cadastrar"
    }
}
package mobile.unifor.cct.savemoney.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mobile.unifor.cct.savemoney.R
import mobile.unifor.cct.savemoney.adapter.FinanceAdapter
import mobile.unifor.cct.savemoney.adapter.FinanceItemListener
import mobile.unifor.cct.savemoney.entity.User

class MainActivity : AppCompatActivity(), FinanceItemListener {

    private lateinit var mMainFinanceList: RecyclerView
    private lateinit var mMainFinanceAdd: FloatingActionButton

    private lateinit var mFinanceAdapter: FinanceAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase

    private var mUserKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = Firebase.auth
        mDatabase = Firebase.database

        mMainFinanceList = findViewById(R.id.main_recyclerview_finance)
        mMainFinanceList.layoutManager = LinearLayoutManager(this)


        mMainFinanceAdd = findViewById(R.id.main_fab_createFinance)
        mMainFinanceAdd.setOnClickListener {
            val it = Intent(this, FinanceActivity::class.java)
            it.putExtra("userKey",mUserKey)
            startActivity(it)
        }
    }

    override fun onStart() {
        super.onStart()

        val userRef = mDatabase.getReference("/users")
        userRef
            .orderByChild("email")
            .equalTo(mAuth.currentUser?.email)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.children.first().getValue(User:: class.java)
                    mUserKey = user?.id ?: ""
                    mFinanceAdapter = FinanceAdapter(user?.finances?.values?.toList()!!)
                    mFinanceAdapter.setOnFinanceItemListener(this@MainActivity)

                    mMainFinanceList.adapter = mFinanceAdapter
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

//                        financeAdapter.notifyItemInserted(position) -> para fazer a animação quando adicionado, caso fosse um pop-up poderia ver a animação
    }

    override fun onFinanceItemClick(view: View, position: Int) {
        // EDITAR
        val it = Intent(this, FinanceActivity::class.java)
        it.putExtra("userKey", mUserKey)
        it.putExtra("financeKey", mFinanceAdapter.finances[position].id)
        startActivity(it)
    }

    override fun onFinanceItemLongClick(view: View, position: Int) {
//         EXCLUIR
        val dialog = AlertDialog.Builder(this)
            .setTitle("Save Money")
            .setMessage("Você deseja realmente excluir a movimentação?")
            .setCancelable(false)
            .setPositiveButton("Sim") { dialog, _ ->
                val finance = mFinanceAdapter.finances[position]
                val financeRef = mDatabase.reference
                    .child("/users")
                    .child(mUserKey)
                    .child("/finances")
                    .child(finance.id)
                financeRef.removeValue()

                dialog.dismiss()
            }
            .setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss()

            }
            .create()
        dialog.show()
    }


}
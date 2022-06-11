package mobile.unifor.cct.savemoney.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mobile.unifor.cct.savemoney.R
import mobile.unifor.cct.savemoney.adapter.FinanceAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var mMainFinanceList: RecyclerView
    private lateinit var mMainFinanceAdd: FloatingActionButton

    private lateinit var financeAdapter: FinanceAdapter
    private var mUserId = -1

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mUserId = intent.getIntExtra("userId", -1)



        mMainFinanceList = findViewById(R.id.main_recyclerview_finance)
        mMainFinanceAdd = findViewById(R.id.main_fab_createFinance)




        mMainFinanceAdd.setOnClickListener {
            val it = Intent(this, FinanceActivity::class.java)

            it.putExtra("userId", mUserId)

            startActivity(it)
        }
    }

    override fun onStart() {
        super.onStart()
        //TODO: PREENCHER O ADAPTER COM AS TAREFAS DO USUARIO

        /**
         * override fun onFinanceItemClick -> editar
         * val it = Intent(this, FinanceActivity:: class.java)
         * startActivity(it)
         * * */

        /**
         * override fun onFinanceItemLongClick -> remover
         * val finance = financeAdapter.finances[position]
         *
         handler.post {
            val dialog = AlertDialog.Builder(this)
            .setTitle("Save Money")
            .setMessage("Deseja realmente excluir a receita ${finance.name} ?")
            .setCancelable(false)
            .setPositiveButton("Sim") { dialog, _ ->
            // TODO: REALIZAR A EXCLUSÃO DE UMA TAREFA

                dialog.dismiss()
            }
            .setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss()
            }
        }.create()

            dialog.show()
        }
         * */

//                        financeAdapter.notifyItemInserted(position) -> para fazer a animação quando adicionado, caso fosse um pop-up poderia ver a animação
    }


}
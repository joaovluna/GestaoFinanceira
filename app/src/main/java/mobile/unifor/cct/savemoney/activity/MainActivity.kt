package mobile.unifor.cct.savemoney.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import mobile.unifor.cct.savemoney.R

class MainActivity : AppCompatActivity() {

    private lateinit var mMainFinanceList: RecyclerView
    private lateinit var mMainFinanceAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMainFinanceList = findViewById(R.id.main_recyclerview_finance)
        mMainFinanceAdd = findViewById(R.id.main_fab_createFinance)
        mMainFinanceAdd.setOnClickListener {
            val it = Intent(this, FinanceActivity::class.java)
        }

    }
}
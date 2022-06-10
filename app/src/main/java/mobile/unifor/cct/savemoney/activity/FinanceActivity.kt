package mobile.unifor.cct.savemoney.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import mobile.unifor.cct.savemoney.R

class FinanceActivity : AppCompatActivity() {

    private lateinit var mFinanceName: EditText
    private lateinit var mFinanceDescripton: EditText
    private lateinit var mFinanceValue: EditText
    private lateinit var mFinanceAction: Button
    private lateinit var mFinanceRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)

        mFinanceName = findViewById(R.id.finance_editText_name)
        mFinanceDescripton = findViewById(R.id.finance_editText_description)
        mFinanceValue = findViewById(R.id.finance_editText_value)

        mFinanceAction = findViewById(R.id.finance_button_action)

        mFinanceRadioGroup = findViewById(R.id.finance_RadioGroup_movement)

        mFinanceAction.setOnClickListener {

        }



    }
}
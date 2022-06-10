package mobile.unifor.cct.savemoney.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import mobile.unifor.cct.savemoney.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mLoginRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mLoginRegister = findViewById(R.id.login_textView_register)
        mLoginRegister.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.login_textView_register -> {
                val it = Intent(this, RegisterActivity::class.java)
                startActivity(it)
            }
        }
    }
}
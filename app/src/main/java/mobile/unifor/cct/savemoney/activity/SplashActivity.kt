package mobile.unifor.cct.savemoney.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import mobile.unifor.cct.savemoney.R

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            val it = Intent(SplashActivity@ this, LoginActivity::class.java)
            startActivity(it)
            finish()
        }, 3000L)

    }
}
package com.taghavi.zaker.views.activities

import android.annotation.TargetApi
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import android.view.WindowManager
import com.taghavi.zaker.R
import com.taghavi.zaker.utils.SharedPref
import com.taghavi.zaker.utils.Utils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.first_activity_in, R.anim.second_activity_in)
        setContentView(R.layout.activity_splash)

        initializeFun()
        setStatusBarColorFun()
        fontFun()

        Handler().postDelayed({
            val intent = Intent(this, WelcomeActivity::class.java)
            val option: ActivityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.first_activity_in, R.anim.second_activity_in)
            startActivity(intent, option.toBundle())
            finish()
        }, 1000)
    }

    override fun onBackPressed() {
        overridePendingTransition(R.anim.first_activity_back, R.anim.second_activity_back)
    }

    private fun initializeFun() {
        sharedPreferences = SharedPref(this)
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun setStatusBarColorFun() {
        val window = this.window

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.dark_red)
        }
    }

    private fun fontFun() {
        splashVersion.typeface = Utils.setFont(this)
    }

}

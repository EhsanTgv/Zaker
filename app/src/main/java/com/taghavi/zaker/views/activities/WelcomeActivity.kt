package com.taghavi.zaker.views.activities

import android.annotation.TargetApi
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.WindowManager
import com.taghavi.zaker.R
import com.taghavi.zaker.utils.SharedPref
import com.taghavi.zaker.utils.Utils
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        initializeFun()
        setStatusBarColorFun()
        fontFun()
        clicksFun()
    }

    override fun onBackPressed() {
        super.onBackPressed()

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
        dailyMentionsButton.typeface = Utils.setFont(this)
        salawatCounterButton.typeface = Utils.setFont(this)
        fatimasMentionsButton.typeface = Utils.setFont(this)
        chosenMentionButton.typeface = Utils.setFont(this)
    }

    private fun clicksFun() {
        dailyMentionsButton.setOnClickListener {
            dailyMentionsButton.startAnimation(Utils.clickAnimation(this))
            val intent = Intent(this, DailyMentionsActivity::class.java)
            val option: ActivityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.first_activity_in, R.anim.second_activity_in)
            startActivity(intent, option.toBundle())
        }

        salawatCounterButton.setOnClickListener {
            salawatCounterButton.startAnimation(Utils.clickAnimation(this))
            val intent = Intent(this, SalawatCounterActivity::class.java)
            val option: ActivityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.first_activity_in, R.anim.second_activity_in)
            startActivity(intent, option.toBundle())
        }

        fatimasMentionsButton.setOnClickListener {
            fatimasMentionsButton.startAnimation(Utils.clickAnimation(this))
            val intent = Intent(this, FatimasMentionsActivity::class.java)
            val option: ActivityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.first_activity_in, R.anim.second_activity_in)
            startActivity(intent, option.toBundle())
        }

        chosenMentionButton.setOnClickListener {
            chosenMentionButton.startAnimation(Utils.clickAnimation(this))
            val intent = Intent(this, ChosenMentionActivity::class.java)
            val option: ActivityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.first_activity_in, R.anim.second_activity_in)
            startActivity(intent, option.toBundle())
        }

        settingButton.setOnClickListener {
            settingButton.startAnimation(Utils.clickAnimation(this))
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("pastActivity", "WelcomeActivity")
            val option: ActivityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.settings_first_in, R.anim.settings_second_in)
            startActivity(intent, option.toBundle())
        }
    }
}
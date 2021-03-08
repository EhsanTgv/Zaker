package com.taghavi.zaaker.views.activities

import android.annotation.TargetApi
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat
import android.view.WindowManager
import android.widget.Toast
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent
import com.taghavi.zaaker.R
import com.taghavi.zaaker.utils.SharedPref
import com.taghavi.zaaker.utils.Utils
import kotlinx.android.synthetic.main.activity_fatimas_mentions.*

class FatimasMentionsActivity : AppCompatActivity() {
    private var allahuAkbarCounter = 34
    private var alhamdulillahCounter = 33
    private var subhanallahCounter = 33
    private lateinit var sharedPreferences: SharedPref
    private lateinit var vibrate: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fatimas_mentions)

        initializeFun()
        setStatusBarColorFun()
        fontFun()
        setCountFun()
        firstSetMentionFun()
        clicksFun()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        overridePendingTransition(R.anim.first_activity_back, R.anim.second_activity_back)
    }

    private fun initializeFun() {
        sharedPreferences = SharedPref(this)
        vibrate = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun setStatusBarColorFun() {
        val window = this.window

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.navy_blue)
        }
    }

    private fun fontFun() {
        fatimasMentionsActivityTitle.typeface = Utils.setFont(this)
        fatimasMentionsMentionText.typeface = Utils.setFont(this)
        fatimasMentionsAllahuAkbarText.typeface = Utils.setFont(this)
        fatimasMentionsAllahuAkbarValue.typeface = Utils.setFont(this)
        fatimasMentionsAlhamdulillahText.typeface = Utils.setFont(this)
        fatimasMentionsAlhamdulillahValue.typeface = Utils.setFont(this)
        fatimasMentionsSubhanallahText.typeface = Utils.setFont(this)
        fatimasMentionsSubhanallahValue.typeface = Utils.setFont(this)
    }

    private fun setMentionFun() {
        if (allahuAkbarCounter > 0 && alhamdulillahCounter > 0 && subhanallahCounter > 0) {
            fatimasMentionsMentionText.text = "الله اكبر"
        } else if (allahuAkbarCounter == 0 && alhamdulillahCounter > 0 && subhanallahCounter > 0) {
            fatimasMentionsMentionText.text = "الحمدالله"
        } else if (allahuAkbarCounter == 0 && alhamdulillahCounter == 0 && subhanallahCounter > 0) {
            fatimasMentionsMentionText.text = "سبحان الله"
        }
    }

    private fun firstSetMentionFun() {
        if (allahuAkbarCounter > 0 && alhamdulillahCounter > 0 && subhanallahCounter > 0) {
            fatimasMentionsMentionText.text = "الله اكبر"
        } else if (allahuAkbarCounter == 0 && alhamdulillahCounter > 0 && subhanallahCounter > 0) {
            fatimasMentionsMentionText.text = "الحمدالله"
        } else if (allahuAkbarCounter == 0 && alhamdulillahCounter == 0 && subhanallahCounter > 0) {
            fatimasMentionsMentionText.text = "سبحان الله"
        } else if (allahuAkbarCounter == 0 && alhamdulillahCounter == 0 && subhanallahCounter == 0) {
            restartCounterFun()
        }
    }

    private fun setCountFun() {
        allahuAkbarCounter = sharedPreferences.getNumberOfFatimasMentionsAllahuAkbarFun()
        fatimasMentionsAllahuAkbarValue.text = allahuAkbarCounter.toString()

        alhamdulillahCounter = sharedPreferences.getNumberOfFatimasMentionsAlhamdulillahFun()
        fatimasMentionsAlhamdulillahValue.text = alhamdulillahCounter.toString()

        subhanallahCounter = sharedPreferences.getNumberOfFatimasMentionsSubhanallahFun()
        fatimasMentionsSubhanallahValue.text = subhanallahCounter.toString()

        fatimasMentionsDecoView.addSeries(SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0f, 100f, 100f)
                .setInitialVisibility(false)
                .setLineWidth(32f)
                .build())

        val seriesItem1 = SeriesItem.Builder(Color.argb(255, 47, 62, 70))
                .setRange(0f, 100f, 0f)
                .setLineWidth(32f)
                .setSpinDuration(1500)
                .build()

        @Suppress("UNUSED_VARIABLE")
        val series1Index = fatimasMentionsDecoView.addSeries(seriesItem1)

        fatimasMentionsDecoView.addEvent(DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDuration(0)
                .build())

        fatimasMentionsDecoView.addEvent(DecoEvent.Builder((allahuAkbarCounter + alhamdulillahCounter + subhanallahCounter).toFloat()).setIndex(1).setDelay(300).build())
    }

    private fun clicksFun() {
        fatimasMentionsDecoView.setOnClickListener {
            if (allahuAkbarCounter > 0 && alhamdulillahCounter > 0 && subhanallahCounter > 0) {
                allahuAkbarCounter--
                setMentionFun()
                animationFun()
                sharedPreferences.setNumberOfFatimasMentionsAllahuAkbarFun(allahuAkbarCounter)
                fatimasMentionsAllahuAkbarValue.startAnimation(Utils.clickAnimation(this))
                fatimasMentionsAllahuAkbarValue.text = allahuAkbarCounter.toString()
            } else if (allahuAkbarCounter == 0 && alhamdulillahCounter > 0 && subhanallahCounter > 0) {
                alhamdulillahCounter--
                setMentionFun()
                animationFun()
                sharedPreferences.setNumberOfFatimasMentionsAlhamdulillahFun(alhamdulillahCounter)
                fatimasMentionsAlhamdulillahValue.startAnimation(Utils.clickAnimation(this))
                fatimasMentionsAlhamdulillahValue.text = alhamdulillahCounter.toString()
            } else if (allahuAkbarCounter == 0 && alhamdulillahCounter == 0 && subhanallahCounter > 0) {
                subhanallahCounter--
                setMentionFun()
                animationFun()
                sharedPreferences.setNumberOfFatimasMentionsSubhanallahFun(subhanallahCounter)
                fatimasMentionsSubhanallahValue.startAnimation(Utils.clickAnimation(this))
                fatimasMentionsSubhanallahValue.text = subhanallahCounter.toString()
            } else if (allahuAkbarCounter == 0 && alhamdulillahCounter == 0 && subhanallahCounter == 0) {
                Toast.makeText(this, "تقبل الله", Toast.LENGTH_LONG).show()
                endVibrateFun()
            }

            fatimasMentionsDecoView.addEvent(DecoEvent.Builder((allahuAkbarCounter + alhamdulillahCounter + subhanallahCounter).toFloat()).setIndex(1).setDuration(300).build())
        }

        fatimasMentionsRestartCounter.setOnClickListener {
            restartCounterFun()
        }

        fatimasMentionsSettingButton.setOnClickListener {
            fatimasMentionsSettingButton.startAnimation(Utils.clickAnimation(this))
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("pastActivity", "FatimasMentionsActivity")
            val option: ActivityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.settings_first_in, R.anim.settings_second_in)
            startActivity(intent, option.toBundle())
        }

        fatimasMentionsBackButton.setOnClickListener {
            fatimasMentionsBackButton.startAnimation(Utils.clickAnimation(this))
            onBackPressed()
        }
    }

    private fun animationFun() {
        fatimasMentionsMentionText.startAnimation(Utils.clickAnimation(this))
//        fatimasMentionsDecoView.startAnimation(Utils.clickAnimation(this))
    }

    private fun endVibrateFun() {
        if (sharedPreferences.getVibrateStatusFun()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrate.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrate.vibrate(500)
            }
        }
    }

    private fun restartCounterFun() {
        sharedPreferences.setNumberOfFatimasMentionsAllahuAkbarFun(34)
        sharedPreferences.setNumberOfFatimasMentionsAlhamdulillahFun(33)
        sharedPreferences.setNumberOfFatimasMentionsSubhanallahFun(33)

//        fatimasMentionsDecoView.startAnimation(Utils.clickAnimation(this))
        fatimasMentionsMentionText.startAnimation(Utils.clickAnimation(this))
        fatimasMentionsAllahuAkbarText.startAnimation(Utils.clickAnimation(this))
        fatimasMentionsAllahuAkbarValue.startAnimation(Utils.clickAnimation(this))
        fatimasMentionsAlhamdulillahText.startAnimation(Utils.clickAnimation(this))
        fatimasMentionsAlhamdulillahValue.startAnimation(Utils.clickAnimation(this))
        fatimasMentionsSubhanallahText.startAnimation(Utils.clickAnimation(this))
        fatimasMentionsSubhanallahValue.startAnimation(Utils.clickAnimation(this))
        fatimasMentionsRestartCounter.startAnimation(Utils.clickAnimation(this))

        fatimasMentionsDecoView.deleteAll()
        setCountFun()
        setMentionFun()
    }
}

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
import kotlinx.android.synthetic.main.activity_daily_mentions.*
import java.util.*

class DailyMentionsActivity : AppCompatActivity() {
    private var counter: Int = 100
    private var calendar = Calendar.getInstance()
    private var date: String = calendar.get(Calendar.YEAR).toString() + "/" +
            (calendar.get(Calendar.MONTH) + 1).toString() + "/" +
            calendar.get(Calendar.DATE).toString()
    private lateinit var sharedPreferences: SharedPref
    private lateinit var vibrate: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_mentions)

        initializeFun()
        setStatusBarColorFun()
        fontFun()
        setMentionFun()
        setCountFun()
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
            window.statusBarColor = ContextCompat.getColor(this, R.color.yellow)
        }
    }

    private fun fontFun() {
        dailyMentionsActivityTitle.typeface = Utils.setFont(this)
        dailyMentionsMentionText.typeface = Utils.setFont(this)
        dailyMentionsRemindedMentions.typeface = Utils.setFont(this)
        dailyMentionsMentionCounter.typeface = Utils.setFont(this)
    }

    private fun setMentionFun() {
        when (weekDayFun()) {
            Calendar.SATURDAY -> {
                dailyMentionsMentionText.text = "یا رب العالمین"
                dailyMentionsActivityTitle.text = "ذکر روز شنبه"
            }
            Calendar.SUNDAY -> {
                dailyMentionsMentionText.text = "یا ذالجلال والاکرام"
                dailyMentionsActivityTitle.text = "ذکر روز یکشنبه"
            }
            Calendar.MONDAY -> {
                dailyMentionsMentionText.text = "یا قاضی الحاجات"
                dailyMentionsActivityTitle.text = "ذکر روز دوشنبه"
            }
            Calendar.TUESDAY -> {
                dailyMentionsMentionText.text = "یا ارحم الراحمین"
                dailyMentionsActivityTitle.text = "ذکر روز سه شنبه"
            }
            Calendar.WEDNESDAY -> {
                dailyMentionsMentionText.text = "یا حی یا قیوم"
                dailyMentionsActivityTitle.text = "ذکر روز چهار شنبه"
            }
            Calendar.THURSDAY -> {
                dailyMentionsMentionText.text = "لا اله الا الله الملک الحق المبین"
                dailyMentionsActivityTitle.text = "ذکر روز پنج شنبه"
            }
            Calendar.FRIDAY -> {
                dailyMentionsMentionText.text = "اللهم صل علی محمد و ال محمد"
                dailyMentionsActivityTitle.text = "ذکر روز جمعه"
            }
        }
    }

    private fun setCountFun() {
        if (date == sharedPreferences.getDateFun()) {
            counter = sharedPreferences.getNumberOfDailyMentionsFun()
            dailyMentionsMentionCounter.text = counter.toString()
        } else {
            counter = 100
            sharedPreferences.setNumberOfDailyMentionsFun(counter)
            sharedPreferences.setDateFun(date)
        }

        dailyMentionsDecoView.addSeries(SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0f, 100f, 100f)
                .setInitialVisibility(false)
                .setLineWidth(32f)
                .build())

        val seriesItem1 = SeriesItem.Builder(Color.argb(255, 253, 160, 1))
                .setRange(0f, 100f, 0f)
                .setLineWidth(32f)
                .setSpinDuration(1500)
                .build()

        @Suppress("UNUSED_VARIABLE")
        val series1Index = dailyMentionsDecoView.addSeries(seriesItem1)

        dailyMentionsDecoView.addEvent(DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDuration(0)
                .build())

        dailyMentionsDecoView.addEvent(DecoEvent.Builder(counter.toFloat()).setIndex(1).setDelay(300).build())
    }

    private fun clicksFun() {
        dailyMentionsDecoView.setOnClickListener {
            setMentionFun()
            animationFun()
            if (counter > 0) {
                counter--
                sharedPreferences.setNumberOfDailyMentionsFun(counter)
                sharedPreferences.setDateFun(date)
                dailyMentionsMentionCounter.text = counter.toString()

                dailyMentionsDecoView.addEvent(DecoEvent.Builder(counter.toFloat()).setIndex(1).setDuration(300).build())

            } else {
                Toast.makeText(this, "تقبل الله", Toast.LENGTH_LONG).show()
                endVibrateFun()
            }
        }

        dailyMentionsSettingButton.setOnClickListener {
            dailyMentionsSettingButton.startAnimation(Utils.clickAnimation(this))
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("pastActivity", "DailyMentionsActivity")
            val option: ActivityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.settings_first_in, R.anim.settings_second_in)
            startActivity(intent, option.toBundle())
        }

        dailyMentionsBackButton.setOnClickListener {
            dailyMentionsBackButton.startAnimation(Utils.clickAnimation(this))
            onBackPressed()
        }

        dailyMentionsRestartButton.setOnClickListener {
            restartCounterFun()
        }
    }

    private fun animationFun() {
//        dailyMentionsMentionText.startAnimation(Utils.clickAnimation(this))
        dailyMentionsMentionCounter.startAnimation(Utils.clickAnimation(this))
//        dailyMentionsDecoView.startAnimation(Utils.clickAnimation(this))
    }

    private fun weekDayFun(): Int {
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    private fun endVibrateFun() {
        if (sharedPreferences.getVibrateStatusFun()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrate.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrate.vibrate(500)
            }
        }
    }

    private fun restartCounterFun() {
        sharedPreferences.setNumberOfDailyMentionsFun(100)

        dailyMentionsMentionText.startAnimation(Utils.clickAnimation(this))
        dailyMentionsMentionText.startAnimation(Utils.clickAnimation(this))
        dailyMentionsRestartButton.startAnimation(Utils.clickAnimation(this))

        dailyMentionsDecoView.deleteAll()
        setCountFun()
        setMentionFun()
    }
}

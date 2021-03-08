package com.taghavi.zaaker.views.activities

import android.annotation.TargetApi
import android.app.ActivityOptions
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.taghavi.zaaker.R
import com.taghavi.zaaker.utils.SharedPref
import com.taghavi.zaaker.utils.Utils
import kotlinx.android.synthetic.main.activity_salawat_counter.*
import android.view.View.OnFocusChangeListener
import android.view.WindowManager
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent

class SalawatCounterActivity : AppCompatActivity() {
    private var counter: Int = 0
    private var maxCounter: Int = 1
    private lateinit var sharedPreferences: SharedPref
    private lateinit var vibrate: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salawat_counter)

        initializeFun()
        setStatusBarColorFun()
        fontFun()
        setCountFun()
        setCirclePositionFun(maxCounter.toFloat(), counter.toFloat())
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
            window.statusBarColor = ContextCompat.getColor(this, R.color.green)
        }
    }

    private fun fontFun() {
        salawatCounterActivityTitle.typeface = Utils.setFont(this)
        salawatCounterMentionText.typeface = Utils.setFont(this)
        salawatCounterRemindedMentions.typeface = Utils.setFont(this)
        salawatCounterMentionCounter.typeface = Utils.setFont(this)
    }

    private fun setCountFun() {
        counter = sharedPreferences.getNumberOfSalawatCounterFun()
        maxCounter = sharedPreferences.getNumberOfSalawatMaxCounterFun()
        salawatCounterMentionCounter.text = counter.toString()
    }

    private fun setCirclePositionFun(max: Float, current: Float) {
        salawatCounterDecoView.addSeries(
            SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0f, max, max)
                .setInitialVisibility(false)
                .setLineWidth(32f)
                .build())

        val seriesItem1 = SeriesItem.Builder(Color.argb(255, 0, 149, 135))
                .setRange(0f, max, 0f)
                .setLineWidth(32f)
                .setSpinDuration(1500)
                .build()

        @Suppress("UNUSED_VARIABLE")
        val series1Index = salawatCounterDecoView.addSeries(seriesItem1)

        salawatCounterDecoView.addEvent(
            DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDuration(0)
                .build())

        salawatCounterDecoView.addEvent(DecoEvent.Builder(current).setIndex(1).setDelay(300).build())
    }

    private fun clicksFun() {
        salawatCounterDecoView.setOnClickListener {
            animationFun()
            if (counter > 0) {
                counter--
                sharedPreferences.setNumberOfSalawatCounterFun(counter)
                salawatCounterMentionCounter.text = counter.toString()

                salawatCounterDecoView.addEvent(DecoEvent.Builder(counter.toFloat()).setIndex(1).setDuration(300).build())

            } else {
                Toast.makeText(this, "تقبل الله", Toast.LENGTH_LONG).show()
                endVibrateFun()
            }
        }

        salawatCounterEditButton.setOnClickListener {
            salawatCounterEditButton.startAnimation(Utils.clickAnimation(this))
            setSalawatDialogFun()
        }

        salawatCounterSettingButton.setOnClickListener {
            salawatCounterSettingButton.startAnimation(Utils.clickAnimation(this))
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("pastActivity", "SalawatCounterActivity")
            val option: ActivityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.settings_first_in, R.anim.settings_second_in)
            startActivity(intent, option.toBundle())
        }

        salawatCounterBackButton.setOnClickListener {
            salawatCounterBackButton.startAnimation(Utils.clickAnimation(this))
            onBackPressed()
        }
    }

    private fun animationFun() {
//        salawatCounterMentionText.startAnimation(Utils.clickAnimation(this))
        salawatCounterMentionCounter.startAnimation(Utils.clickAnimation(this))
//        salawatCounterDecoView.startAnimation(Utils.clickAnimation(this))
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

    private fun setSalawatDialogFun() {
        val salawatDialog = Dialog(this)
        salawatDialog.setContentView(R.layout.dialog_salawat_counter)

        val title: TextView = salawatDialog.findViewById(R.id.dialogSalawatCounterTitle)
        val editText: EditText = salawatDialog.findViewById(R.id.dialogSalawatCounterEditText)
        val submit: TextView = salawatDialog.findViewById(R.id.dialogSalawatCounterSubmit)

        title.typeface = Utils.setFont(this)
        editText.typeface = Utils.setFont(this)
        submit.typeface = Utils.setFont(this)

        //neshan dadane keyboard
        editText.setText(sharedPreferences.getNumberOfSalawatCounterFun().toString())
        editText.selectAll()
        editText.onFocusChangeListener = OnFocusChangeListener { _, _ ->
            editText.post {
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
            }
        }
        editText.requestFocus()

        submit.setOnClickListener {
            if (editText.text.isNotEmpty()) {
                if (editText.text.toString().toInt() in 1..100000) {
                    sharedPreferences.setNumberOfSalawatCounterFun(editText.text.toString().toInt())
                    sharedPreferences.setNumberOfSalawatMaxCounterFun(editText.text.toString().toInt())
                    setCountFun()

                    salawatCounterDecoView.deleteAll()
                    setCirclePositionFun(editText.text.toString().toFloat(), editText.text.toString().toFloat())
//                salawatCounterDecoView.addEvent(DecoEvent.Builder(editText.text.toString().toFloat()).setIndex(1).setDuration(500).build())

                    salawatDialog.dismiss()
                } else {
                    Toast.makeText(this, "تداد ذکر باید بین 1 تا 100000 باشد", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "لطفاً تعداد صلوات را وارد نمایید", Toast.LENGTH_SHORT).show()
            }
        }

        salawatDialog.show()
    }

}

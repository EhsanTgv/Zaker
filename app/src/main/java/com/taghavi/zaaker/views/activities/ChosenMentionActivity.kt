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
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent
import com.taghavi.zaaker.R
import com.taghavi.zaaker.utils.SharedPref
import com.taghavi.zaaker.utils.Utils
import kotlinx.android.synthetic.main.activity_chosen_mention.*
import androidx.core.content.ContextCompat
import android.view.WindowManager

class ChosenMentionActivity : AppCompatActivity() {
    private var counter = 0
    private var maxCounter = 1
    private lateinit var sharedPreferences: SharedPref
    private lateinit var vibrate: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chosen_mention)


        initializeFun()
        setStatusBarColorFun()
        fontFun()
        setMentionFun()
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
            window.statusBarColor = ContextCompat.getColor(this, R.color.red)
        }
    }

    private fun fontFun() {
        chosenMentionActivityTitle.typeface = Utils.setFont(this)
        chosenMentionMentionText.typeface = Utils.setFont(this)
        chosenMentionRemindedMentions.typeface = Utils.setFont(this)
        chosenMentionMentionCounter.typeface = Utils.setFont(this)
    }

    private fun setMentionFun() {
        chosenMentionMentionText.text = sharedPreferences.getChosenMentionTextFun()
    }

    private fun setCountFun() {
        counter = sharedPreferences.getNumberOfChosenMentionValueFun()
        maxCounter = sharedPreferences.getNumberOfChosenMentionMaxValueFun()
        chosenMentionMentionCounter.text = counter.toString()
    }

    private fun setCirclePositionFun(max: Float, current: Float) {
        chosenMentionDecoView.addSeries(SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0f, max, max)
                .setInitialVisibility(false)
                .setLineWidth(32f)
                .build())

        val seriesItem1 = SeriesItem.Builder(Color.argb(255, 218, 43, 57))
                .setRange(0f, max, 0f)
                .setLineWidth(32f)
                .setSpinDuration(1500)
                .build()

        @Suppress("UNUSED_VARIABLE")
        val series1Index = chosenMentionDecoView.addSeries(seriesItem1)

        chosenMentionDecoView.addEvent(DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDuration(0)
                .build())

        chosenMentionDecoView.addEvent(DecoEvent.Builder(current).setIndex(1).setDelay(300).build())
    }

    private fun clicksFun() {
        chosenMentionDecoView.setOnClickListener {
            setMentionFun()
            if (counter > 0) {
                animationFun()
                counter--
                sharedPreferences.setNumberOfChosenMentionValueFun(counter)
                chosenMentionMentionCounter.text = counter.toString()

                chosenMentionDecoView.addEvent(DecoEvent.Builder(counter.toFloat()).setIndex(1).setDuration(300).build())
            } else {
                Toast.makeText(this, "تقبل الله", Toast.LENGTH_LONG).show()
                endVibrateFun()
            }
        }

        chosenMentionEditButton.setOnClickListener {
            chosenMentionEditButton.startAnimation(Utils.clickAnimation(this))

            setChosenMentionDialogFun()
        }

        chosenMentionSettingButton.setOnClickListener {
            chosenMentionSettingButton.startAnimation(Utils.clickAnimation(this))
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("pastActivity", "ChosenMentionActivity")
            val option: ActivityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.settings_first_in, R.anim.settings_second_in)
            startActivity(intent, option.toBundle())
        }

        chosenMentionBackButton.setOnClickListener {
            chosenMentionBackButton.startAnimation(Utils.clickAnimation(this))
            onBackPressed()
        }
    }

    private fun animationFun() {
        chosenMentionMentionText.startAnimation(Utils.clickAnimation(this))
        chosenMentionMentionCounter.startAnimation(Utils.clickAnimation(this))
//        chosenMentionDecoView.startAnimation(Utils.clickAnimation(this))
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

    private fun setChosenMentionDialogFun() {
        val chosenMentionDialog = Dialog(this)
        chosenMentionDialog.setContentView(R.layout.dialog_chosen_mention)

        val title: TextView = chosenMentionDialog.findViewById(R.id.dialogChosenMentionTitle)
        val mentionText: EditText = chosenMentionDialog.findViewById(R.id.dialogChosenMentionText)
        val mentionValue: EditText = chosenMentionDialog.findViewById(R.id.dialogChosenMentionValue)
        val submit: TextView = chosenMentionDialog.findViewById(R.id.dialogChosenMentionSubmit)

        title.typeface = Utils.setFont(this)
        mentionText.typeface = Utils.setFont(this)
        mentionValue.typeface = Utils.setFont(this)
        submit.typeface = Utils.setFont(this)

        mentionText.setText(sharedPreferences.getChosenMentionTextFun())
        mentionValue.setText(sharedPreferences.getNumberOfChosenMentionValueFun().toString())

        submit.setOnClickListener {
            if (mentionText.text.isNotEmpty()) {
                sharedPreferences.setChosenMentionTextFun(mentionText.text.toString())
                setMentionFun()
                if (mentionValue.text.isNotEmpty()) {
                    if (mentionValue.text.toString().toInt() in 1..100000) {
                        sharedPreferences.setNumberOfChosenMentionValueFun(mentionValue.text.toString().toInt())
                        sharedPreferences.setNumberOfChosenMentionMaxValueFun(mentionValue.text.toString().toInt())
                        setCountFun()
                        chosenMentionDecoView.deleteAll()
                        setCirclePositionFun(mentionValue.text.toString().toFloat(), mentionValue.text.toString().toFloat())
                        chosenMentionDialog.dismiss()
                    } else {
                        Toast.makeText(this, "تداد ذکر باید بین 1 تا 100000 باشد", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this, "لطفاً تعداد تکرار ذکر مورد نظر را وارد کند", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "لطفاً ذکر مورد نظر خود را وارد کنید", Toast.LENGTH_SHORT).show()
            }
        }

        chosenMentionDialog.show()
    }
}

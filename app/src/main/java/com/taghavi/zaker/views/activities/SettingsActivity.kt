package com.taghavi.zaker.views.activities

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.taghavi.zaker.R
import com.taghavi.zaker.utils.SharedPref
import com.taghavi.zaker.utils.Utils
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPref
    private lateinit var pastActivity: String
    private var linkColor: Int = 0
    private var highlightColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initializeFun()
        setColorFun()
        fontFun()
        clicksFun()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        overridePendingTransition(R.anim.settings_first_back, R.anim.settings_second_back)
    }

    private fun initializeFun() {
        sharedPreferences = SharedPref(this)
        vibrateSwitch.isChecked = sharedPreferences.getVibrateStatusFun()
        notificationsSwitch.isChecked = sharedPreferences.getNotificationStatusFun()
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun setColorFun() {
        pastActivity = intent.getStringExtra("pastActivity")!!

        when (pastActivity) {
            "WelcomeActivity" -> {
                vibrateIcon.setImageResource(R.drawable.dark_red_vibrate_icon)
                notificationsIcon.setImageResource(R.drawable.dark_red_notification_icon)
                backButton.setImageResource(R.drawable.dark_red_back_icon)
                linkColor = R.color.dark_red
                highlightColor = R.color.dark_red70
                settingsAboutButton.setTextColor(resources.getColor(R.color.dark_red))

                val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
                val thumbColors = intArrayOf(Color.parseColor("#FFEAEAEA"), Color.parseColor("#3E000C"))
                val trackColors = intArrayOf(Color.parseColor("#FFB1B1B1"), Color.parseColor("#b43e000c"))
                DrawableCompat.setTintList(DrawableCompat.wrap(notificationsSwitch.thumbDrawable), ColorStateList(states, thumbColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(vibrateSwitch.thumbDrawable), ColorStateList(states, thumbColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(notificationsSwitch.trackDrawable), ColorStateList(states, trackColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(vibrateSwitch.trackDrawable), ColorStateList(states, trackColors))

                val window = this.window

                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.dark_red)
                }
            }
            "DailyMentionsActivity" -> {
                vibrateIcon.setImageResource(R.drawable.yellow_vibrate_icon)
                notificationsIcon.setImageResource(R.drawable.yellow_notification_icon)
                backButton.setImageResource(R.drawable.yellow_back_icon)
                linkColor = R.color.yellow
                highlightColor = R.color.yellow70
                settingsAboutButton.setTextColor(resources.getColor(R.color.yellow))


                val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
                val thumbColors = intArrayOf(Color.parseColor("#FFEAEAEA"), Color.parseColor("#FDA001"))
                val trackColors = intArrayOf(Color.parseColor("#FFB1B1B1"), Color.parseColor("#b4FDA001"))
                DrawableCompat.setTintList(DrawableCompat.wrap(notificationsSwitch.thumbDrawable), ColorStateList(states, thumbColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(vibrateSwitch.thumbDrawable), ColorStateList(states, thumbColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(notificationsSwitch.trackDrawable), ColorStateList(states, trackColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(vibrateSwitch.trackDrawable), ColorStateList(states, trackColors))

                val window = this.window

                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.yellow)
                }
            }
            "SalawatCounterActivity" -> {
                vibrateIcon.setImageResource(R.drawable.green_vibrate_icon)
                notificationsIcon.setImageResource(R.drawable.green_notification_icon)
                backButton.setImageResource(R.drawable.green_back_icon)
                linkColor = R.color.green
                highlightColor = R.color.green70
                settingsAboutButton.setTextColor(resources.getColor(R.color.green))

                val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
                val thumbColors = intArrayOf(Color.parseColor("#FFEAEAEA"), Color.parseColor("#009587"))
                val trackColors = intArrayOf(Color.parseColor("#FFB1B1B1"), Color.parseColor("#b4009587"))
                DrawableCompat.setTintList(DrawableCompat.wrap(notificationsSwitch.thumbDrawable), ColorStateList(states, thumbColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(vibrateSwitch.thumbDrawable), ColorStateList(states, thumbColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(notificationsSwitch.trackDrawable), ColorStateList(states, trackColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(vibrateSwitch.trackDrawable), ColorStateList(states, trackColors))

                val window = this.window

                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.green)
                }
            }
            "FatimasMentionsActivity" -> {
                vibrateIcon.setImageResource(R.drawable.navy_blue_vibrate_icon)
                notificationsIcon.setImageResource(R.drawable.navy_blue_notification_icon)
                backButton.setImageResource(R.drawable.navy_blue_back_icon)
                linkColor = R.color.navy_blue
                highlightColor = R.color.navy_blue70
                settingsAboutButton.setTextColor(resources.getColor(R.color.navy_blue))

                val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
                val thumbColors = intArrayOf(Color.parseColor("#FFEAEAEA"), Color.parseColor("#2F3E46"))
                val trackColors = intArrayOf(Color.parseColor("#FFB1B1B1"), Color.parseColor("#b42F3E46"))
                DrawableCompat.setTintList(DrawableCompat.wrap(notificationsSwitch.thumbDrawable), ColorStateList(states, thumbColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(vibrateSwitch.thumbDrawable), ColorStateList(states, thumbColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(notificationsSwitch.trackDrawable), ColorStateList(states, trackColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(vibrateSwitch.trackDrawable), ColorStateList(states, trackColors))

                val window = this.window

                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.navy_blue)
                }
            }
            "ChosenMentionActivity" -> {
                vibrateIcon.setImageResource(R.drawable.red_vibrate_icon)
                notificationsIcon.setImageResource(R.drawable.red_notification_icon)
                backButton.setImageResource(R.drawable.red_back_icon)
                linkColor = R.color.red
                highlightColor = R.color.red70
                settingsAboutButton.setTextColor(resources.getColor(R.color.red))


                val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
                val thumbColors = intArrayOf(Color.parseColor("#FFEAEAEA"), Color.parseColor("#DA2B39"))
                val trackColors = intArrayOf(Color.parseColor("#FFB1B1B1"), Color.parseColor("#b4DA2B39"))
                DrawableCompat.setTintList(DrawableCompat.wrap(notificationsSwitch.thumbDrawable), ColorStateList(states, thumbColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(vibrateSwitch.thumbDrawable), ColorStateList(states, thumbColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(notificationsSwitch.trackDrawable), ColorStateList(states, trackColors))
                DrawableCompat.setTintList(DrawableCompat.wrap(vibrateSwitch.trackDrawable), ColorStateList(states, trackColors))

                val window = this.window

                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.red)
                }
            }
        }
    }

    private fun fontFun() {
        settingsTitle.typeface = Utils.setFont(this)
        vibrateSwitch.typeface = Utils.setFont(this)
        notificationsSwitch.typeface = Utils.setFont(this)
        settingsAboutButton.typeface = Typeface.createFromAsset(this.assets, "fonts/lobster_two.otf")
    }

    private fun clicksFun() {
        vibrateSwitch.setOnCheckedChangeListener { _, isChecked ->
            vibrateSwitch.startAnimation(Utils.clickAnimation(this))
            vibrateIcon.startAnimation(Utils.clickAnimation(this))
            val vibrateStatus: Boolean = isChecked
            sharedPreferences.setVibrateStatusFun(vibrateStatus)
        }

        settingsVibrateCardView.setOnClickListener {
            if (vibrateSwitch.isChecked) {
                vibrateSwitch.isChecked = false
            } else if (!vibrateSwitch.isChecked) {
                vibrateSwitch.isChecked = true
            }
        }

        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            notificationsSwitch.startAnimation(Utils.clickAnimation(this))
            notificationsIcon.startAnimation(Utils.clickAnimation(this))
            val notificationStatus: Boolean = isChecked
            sharedPreferences.setNotificationStatusFun(notificationStatus)

            setNotificationFun()
        }

        settingsNotificationCardView.setOnClickListener {
            if (notificationsSwitch.isChecked) {
                notificationsSwitch.isChecked = false
            } else if (!notificationsSwitch.isChecked) {
                notificationsSwitch.isChecked = true
            }
        }

        settingsAboutButton.setOnClickListener {
            aboutUsDialogFun()
        }

        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    @Suppress("DEPRECATION")
    private fun aboutUsDialogFun() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_about_us)
        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val programmerName: TextView = dialog.findViewById(R.id.dialogAboutUsProgrammerName)
        val programmerEmail: TextView = dialog.findViewById(R.id.dialogAboutUsProgrammerEmail)
        val designerName: TextView = dialog.findViewById(R.id.dialogAboutUsDesignerName)
        val designerEmail: TextView = dialog.findViewById(R.id.dialogAboutUsDesignerEmail)

        programmerEmail.setLinkTextColor(resources.getColor(linkColor))
        designerEmail.setLinkTextColor(resources.getColor(linkColor))

        programmerEmail.highlightColor = resources.getColor(highlightColor)
        designerEmail.highlightColor = resources.getColor(highlightColor)

        programmerName.typeface = Utils.setFont(this)
        designerName.typeface = Utils.setFont(this)

        dialog.show()
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun setNotificationFun() {
//        val intent = Intent(this, ReminderReceiver::class.java)

        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 1234, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager: AlarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (sharedPreferences.getNotificationStatusFun()) {
            val calendar: Calendar = Calendar.getInstance()

            calendar.set(Calendar.HOUR_OF_DAY, 20)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

            Toast.makeText(this, "ذکر روزانه هر شب به شما یادآوری می شود", Toast.LENGTH_LONG).show()
        } else {
            alarmManager.cancel(pendingIntent)
        }
    }
}

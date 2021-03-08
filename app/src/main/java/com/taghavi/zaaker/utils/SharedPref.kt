package com.taghavi.zaaker.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    private val sp: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val sharedPreferencesName = "data"
    private val dailyMentions = "dailyMentions"
    private val salawatCounter = "salawatCounter"
    private val salawatMaxCounter = "salawatMaxCounter"
    private val fatimasMentionsAllahuAkbar = "FatimasMentionsAllahuAkbar"
    private val fatimasMentionsAlhamdulillah = "FatimasMentionsAlhamdulillah"
    private val fatimasMentionsSubhanallah = "FatimasMentionsSubḥanallah"
    private val chosenMentionValue = "chosenMentionValue"
    private val chosenMentionMaxValue = "chosenMentionMaxValue"
    private val chosenMentionText = "chosenMentionText"
    private val vibrate = "vibrate"
    private val notification = "notification"
    private val date = "date"
    private val isFirst = "isFirst"

    init {
        sp = context.getSharedPreferences(sharedPreferencesName, 0)
        editor = sp.edit()
        editor.apply()
    }

    fun setNumberOfDailyMentionsFun(mention: Int) {
        editor.putInt(dailyMentions, mention).apply()
    }

    fun setNumberOfSalawatCounterFun(mention: Int) {
        editor.putInt(salawatCounter, mention).apply()
    }

    fun setNumberOfSalawatMaxCounterFun(mention: Int) {
        editor.putInt(salawatMaxCounter, mention).apply()
    }

    fun setNumberOfFatimasMentionsAllahuAkbarFun(mention: Int) {
        editor.putInt(fatimasMentionsAllahuAkbar, mention).apply()
    }

    fun setNumberOfFatimasMentionsAlhamdulillahFun(mention: Int) {
        editor.putInt(fatimasMentionsAlhamdulillah, mention).apply()
    }

    fun setNumberOfFatimasMentionsSubhanallahFun(mention: Int) {
        editor.putInt(fatimasMentionsSubhanallah, mention).apply()
    }

    fun setNumberOfChosenMentionValueFun(mention: Int) {
        editor.putInt(chosenMentionValue, mention).apply()
    }

    fun setNumberOfChosenMentionMaxValueFun(mention: Int) {
        editor.putInt(chosenMentionMaxValue, mention).apply()
    }

    fun setChosenMentionTextFun(mention: String) {
        editor.putString(chosenMentionText, mention).apply()
    }

    fun setNotificationStatusFun(notificationStatus: Boolean) {
        editor.putBoolean(notification, notificationStatus).apply()
    }

    fun setVibrateStatusFun(vibrateStatus: Boolean) {
        editor.putBoolean(vibrate, vibrateStatus).apply()
    }

    fun setDateFun(d: String) {
        editor.putString(date, d).apply()
    }

    fun setIsFirstFun(d: Boolean) {
        editor.putBoolean(isFirst, d).apply()
    }

////////////////////////////////////////////////////////////////////////////

    fun getNumberOfDailyMentionsFun(): Int {
        return sp.getInt(dailyMentions, 100)
    }

    fun getNumberOfSalawatCounterFun(): Int {
        return sp.getInt(salawatCounter, 0)
    }

    fun getNumberOfSalawatMaxCounterFun(): Int {
        if (sp.getInt(salawatMaxCounter, 1) < 1) {
            return 1
        }
        return sp.getInt(salawatMaxCounter, 1)
    }

    fun getNumberOfFatimasMentionsAllahuAkbarFun(): Int {
        return sp.getInt(fatimasMentionsAllahuAkbar, 34)
    }

    fun getNumberOfFatimasMentionsAlhamdulillahFun(): Int {
        return sp.getInt(fatimasMentionsAlhamdulillah, 33)
    }

    fun getNumberOfFatimasMentionsSubhanallahFun(): Int {
        return sp.getInt(fatimasMentionsSubhanallah, 33)
    }

    fun getNumberOfChosenMentionValueFun(): Int {
        return sp.getInt(chosenMentionValue, 0)
    }

    fun getNumberOfChosenMentionMaxValueFun(): Int {
        if (sp.getInt(chosenMentionMaxValue, 1) < 1) {
            return 1
        }
        return sp.getInt(chosenMentionMaxValue, 1)
    }

    fun getChosenMentionTextFun(): String {
        return sp.getString(chosenMentionText, "سبحان الله")!!
    }

    fun getNotificationStatusFun(): Boolean {
        return sp.getBoolean(notification, false)
    }

    fun getVibrateStatusFun(): Boolean {
        return sp.getBoolean(vibrate, true)
    }

    fun getDateFun(): String {
        return sp.getString(date, "0")!!
    }

    fun getIsFirst(): Boolean {
        return sp.getBoolean(isFirst, true)
    }
}
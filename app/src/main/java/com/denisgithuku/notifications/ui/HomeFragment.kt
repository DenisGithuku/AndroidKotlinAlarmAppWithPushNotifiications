package com.denisgithuku.notifications.ui

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.app.AlarmManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denisgithuku.notifications.databinding.FragmentHomeBinding
import com.denisgithuku.notifications.utils.AlarmReceiver
import com.denisgithuku.notifications.viewmodels.MainViewModel
import com.denisgithuku.notifications.viewmodels.MainViewModelFactory
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import com.google.android.material.timepicker.TimeFormat.CLOCK_12H
import java.util.*


private const val channelId = "testchannelid"
private const val channelName = "testchannelname"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val application = requireActivity().application
        val viewModelFactory = MainViewModelFactory(application)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        createNotificationsChannel(channelId, channelName)

        binding.timePickerBtn.setOnClickListener {
            selectTime()
        }
        binding.setAlarmBrn.setOnClickListener {
            setAlarm()
        }
        return binding.root
    }

    private fun selectTime() {
        //show a material timepicker to select time
        val isSystem24HourFormat = is24HourFormat(requireActivity())
        val timeFormat = if(isSystem24HourFormat) TimeFormat.CLOCK_24H else CLOCK_12H

        val picker = MaterialTimePicker.Builder()
            .setHour(Date().hours)
            .setMinute(Date().minutes)
            .setTitleText("Select time")
            .setTimeFormat(timeFormat)
            .setInputMode(INPUT_MODE_KEYBOARD)
            .build()

        picker.addOnPositiveButtonClickListener {
            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
        }

        picker.show(parentFragmentManager, "timealarmpicker")
    }

    private fun setAlarm() {
        //set the alarm using a pending intent
        val context = requireContext()
        val alarmManager = context.getSystemService(
            Context.ALARM_SERVICE
        ) as AlarmManager


        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        Toast.makeText(requireContext(), "Alarm scheduled for: ${calendar.time}", Toast.LENGTH_SHORT).show()
    }




    private fun createNotificationsChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "denisgithukuappnotifications"
            channel.setShowBadge(true)
            channel.enableVibration(true)
            channel.enableLights(true)

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            ) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}
package com.mili.workmanagerandpendingnotification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.*
import com.mili.workmanagerandpendingnotification.Constants.LAST_PERIODIC_TIME
import com.mili.workmanagerandpendingnotification.Constants.ONETIME_WORK_DESCRIPTION
import com.mili.workmanagerandpendingnotification.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val constraints = Constraints.Builder()
        .setRequiresBatteryNotLow(true)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observeChanges()
    }

    private fun observeChanges() {
        viewModel.setPeriodWork().observe(this , Observer {
            val periodWork = PeriodicWorkRequest.Builder(PeriodicBackgroundNotification::class.java,it.toLong(),TimeUnit.MINUTES)
                .addTag("periodic-pending-notification")
                .setConstraints(constraints)
                .build()
            SharedPrefHelpers.writeToSharedPreferences(this, LAST_PERIODIC_TIME, System.currentTimeMillis().toString())
            WorkManager.getInstance(this).enqueueUniquePeriodicWork("periodic-pending-notification", ExistingPeriodicWorkPolicy.KEEP, periodWork)
        })

        viewModel.setOneTimeWork().observe(this, Observer {
            val onetimeWork = OneTimeWorkRequest.Builder(OnetimeBackgroundNotification::class.java)
            onetimeWork.setConstraints(constraints)
            val data = Data.Builder()
            data.putString(ONETIME_WORK_DESCRIPTION,it)
            onetimeWork.setInputData(data.build())

            WorkManager.getInstance(this).enqueue(onetimeWork.build())
        })
    }
}

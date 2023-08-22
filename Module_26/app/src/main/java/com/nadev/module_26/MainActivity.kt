package com.nadev.module_26

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.firebase.messaging.FirebaseMessaging
import com.nadev.module_26.backend.retrofit
import com.nadev.module_26.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US) // указываем формат подходящий для АПИ
    private val currentDate = dateFormat.format(Date()) //получаем текущую дату
    private val cancellationTokenSource = CancellationTokenSource()
    private var longitude = 0F // создаем координаты
    private var latitude = 0F // потом им будут присвоены значения текущего местоположения
    private var minutes = 0 //будем хранить время для отсчета
    private var hours = 0
    private val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf( //необходимые разрешения - нам достаточно неточных координат
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions(permissions, 1) // запрашиваем разрешение
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fusedLocation = LocationServices.getFusedLocationProviderClient(this@MainActivity)
        binding.timePicker.setIs24HourView(true)
        FirebaseMessaging.getInstance().token.addOnCompleteListener {  }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocation.getCurrentLocation(
                Priority.PRIORITY_LOW_POWER,
                cancellationTokenSource.token
            ).addOnFailureListener {
                Toast.makeText(this@MainActivity, "Ошибка получения локации", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener { location ->
                longitude = location.longitude.toFloat() // присваиваем долготу и широту
                latitude = location.latitude.toFloat()
                lifecycleScope.launch {
                    Log.d("COORDINATES", "$longitude, $latitude")
                    val sunrise = retrofit.getSunsetTime(latitude, longitude, currentDate) // будем использовать апи, вместо фоновой работы - так мы сэкономим ресурсы и упростим приложение (+ более точные данные)
                    binding.sunriseTime.text = sunrise.results.sunrise //сюда же входит получение часового пояса из координат, ведь я его не указал (пришлите мою звездочку по почте:)
                    Log.d("RESPONSE", "$sunrise")
                }
            }
        } else {
            Toast.makeText(this, "We can not work without permissions", Toast.LENGTH_SHORT).show()
        }

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            binding.setTime.text ="$hourOfDay:$minute"
            minutes = minute
            hours = hourOfDay
        }

        binding.set.setOnClickListener {
            val millis = convertTime(hours, minutes)
            Log.d("MLIS", millis.toString())

            backgroundTimer(millis)
            Log.d("TIME", "${ System.currentTimeMillis()}")
        }
    }

    private fun convertTime(hours:Int, mins:Int):Long{
        var minutes = mins.toLong()
        minutes += hours * 60
        minutes = (minutes * 60) * 1000L
        return minutes
    }

    private fun backgroundTimer(timeInMillis:Long){

        val sysServices = getSystemService(Context.ALARM_SERVICE) as AlarmManager
  //      Log.d("SYS", "$sysServices")
        val alarmType = AlarmManager.RTC
   //     Log.d("alarm", "$alarmType")
        val time = System.currentTimeMillis() + (timeInMillis / 2)
        //проверка версии андроид не нужна, ведь минимально допустимая у меня стоит 8.0 (таковы современные требования)
  //      Log.d("TIME", time.toString())
        val intent = Intent(this, Broadcast::class.java)
  //      Log.d("intent", "$intent")
        val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)
//        Log.d("pend", "$pendingIntent")
        sysServices.setExactAndAllowWhileIdle(
            alarmType,
            time,
            pendingIntent
        ).apply { Log.d("tag", "sys serv") }

    }
}
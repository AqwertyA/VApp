package com.v.getdeviceinfo

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import androidx.work.*

class MainActivity : AppCompatActivity() {
    //    private val receiver = DownloadReceiver()
    private val filter = IntentFilter()
    private val observer = MyObserver(this)

    init {

        filter.addAction("android.intent.action.DOWNLOAD_COMPLETE")
        filter.addAction("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED")

        lifecycle.addObserver(observer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val liveData = MutableLiveData<String>()
        liveData.observe(this, Observer { data -> Log.d("testTag", "dataChanged-->$data") })

        findViewById<View>(R.id.btn).setOnClickListener {
            //            liveData.postValue(System.currentTimeMillis().toString())
            startActivity(Intent(this@MainActivity, NextActivity::class.java))
        }
        findViewById<View>(R.id.btn2).setOnClickListener {
        }
    }

    private fun workerTest() {
        //OneTimeWorkRequest
//            val request = OneTimeWorkRequest.Builder(TestWorker::class.java)
        //PeriodicWorkRequest
//            val request = PeriodicWorkRequest.Builder(TestWorker::class.java, 2, TimeUnit.SECONDS)
//                    .addTag("testWorkerTag")
//                    .setConstraints(Constraints.Builder()
//                            .build())
//                    .setInputData(Data.Builder()
//                            .putString("dataKey", "work a")
//                            .build())
//                    .build()
        val requestA = newWork("workA", 1000)
        val requestB = newWork("workB", 2000)
        val requestC = newWork("workC", 3000)

        WorkManager.getInstance()
                .beginWith(requestA, requestB)
                .then(requestC)
                .enqueue()
                .addListener({ Log.d("testTag", "work listener executed") }, { command -> command.run() })
        WorkManager.getInstance().getStatusesByTagLiveData("workC")
                .observe(this@MainActivity, Observer { list ->
                    run {
                        Log.d("testTag", "list.size-->${list?.size}")
                        list?.forEach { status ->
                            if (status != null) {
                                val double = status.outputData.getDouble("outKey", 0.0)
                                Log.d("testTag", "tags-->${status.tags}, state-->${status.state.name}, data-->$double")
                            }
                        }
                    }
                })
    }

    private fun newWork(nameTag: String, duration: Long): OneTimeWorkRequest {
        return OneTimeWorkRequest.Builder(TestWorker::class.java)
                .addTag(nameTag)
                .setConstraints(Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build())
                .setInputData(Data.Builder()
                        .putString("dataKey", nameTag)
                        .putLong("duration", duration)
                        .build())
                .build()
    }

//    private fun clipDeviceInfo() {
//        val tv = findViewById<TextView>(R.id.tv)
//        tv.text = getDeviceInfo(this@MainActivity)
//        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//        clipboardManager.primaryClip = ClipData.newPlainText(null, getDeviceInfo(this@MainActivity))
//    }
//
//    private fun getDeviceInfo(context: Context): String? {
//        try {
//            val json = org.json.JSONObject()
//            val tm = context
//                    .getSystemService(Context.TELEPHONY_SERVICE) as android.telephony.TelephonyManager
//            var deviceId: String? = null
//            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
//                deviceId = tm.deviceId
//            }
//            val mac = getMac(context)
//
//            json.put("mac", mac)
//            if (TextUtils.isEmpty(deviceId)) {
//                deviceId = mac
//            }
//            if (TextUtils.isEmpty(deviceId)) {
//                deviceId = android.provider.Settings.Secure.getString(context.contentResolver, android.provider.Settings.Secure.ANDROID_ID)
//            }
//            json.put("device_id", deviceId)
//            return json.toString()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return null
//    }
//
//    private fun getMac(context: Context?): String? {
//        var mac: String? = ""
//        if (context == null) {
//            return mac
//        }
//        if (Build.VERSION.SDK_INT < 23) {
//            mac = getMacBySystemInterface(context)
//        } else {
//            mac = macByJavaAPI
//            if (TextUtils.isEmpty(mac)) {
//                mac = getMacBySystemInterface(context)
//            }
//        }
//        return mac
//
//    }
//
//    private val macByJavaAPI: String?
//        @TargetApi(9)
//        get() {
//            try {
//                val interfaces = NetworkInterface.getNetworkInterfaces()
//                while (interfaces.hasMoreElements()) {
//                    val netInterface = interfaces.nextElement()
//                    if ("wlan0" == netInterface.name || "eth0" == netInterface.name) {
//                        val addr = netInterface.hardwareAddress
//                        if (addr == null || addr.size == 0) {
//                            return null
//                        }
//                        val buf = StringBuilder()
//                        for (b in addr) {
//                            buf.append(String.format("%02X:", b))
//                        }
//                        if (buf.length > 0) {
//                            buf.deleteCharAt(buf.length - 1)
//                        }
//                        return buf.toString().toLowerCase(Locale.getDefault())
//                    }
//                }
//            } catch (e: Throwable) {
//            }
//
//            return null
//        }
//
//    private fun getMacBySystemInterface(context: Context?): String {
//        if (context == null) {
//            return ""
//        }
//        try {
//            val wifi = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//            if (checkPermission(context, Manifest.permission.ACCESS_WIFI_STATE)) {
//                val info = wifi.connectionInfo
//                return info.macAddress
//            } else {
//                return ""
//            }
//        } catch (e: Throwable) {
//            return ""
//        }
//
//    }
//
//    fun checkPermission(context: Context?, permission: String): Boolean {
//        var result = false
//        if (context == null) {
//            return result
//        }
//        if (Build.VERSION.SDK_INT >= 23) {
//            try {
//                val clazz = Class.forName("android.content.Context")
//                val method = clazz.getMethod("checkSelfPermission", String::class.java)
//                val rest = method.invoke(context, permission) as Int
//                result = rest == PackageManager.PERMISSION_GRANTED
//            } catch (e: Throwable) {
//                result = false
//            }
//
//        } else {
//            val pm = context.packageManager
//            if (pm.checkPermission(permission, context.packageName) == PackageManager.PERMISSION_GRANTED) {
//                result = true
//            }
//        }
//        return result
//    }
}

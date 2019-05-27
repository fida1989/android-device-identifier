package com.deviceidentifier

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import android.provider.Settings
import android.provider.Settings.System


class MainActivity : AppCompatActivity() {

    val MY_PERMISSIONS_REQUEST = 111


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Permissions.check(this/*context*/, Manifest.permission.READ_PHONE_STATE, null, object : PermissionHandler() {
            override fun onGranted() {
                // do your task.
                getIMEI()
                getAndroidId()
            }

            override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                super.onDenied(context, deniedPermissions)
                textView1.text = "Permission Error!"
            }
        })
    }


    @SuppressLint("MissingPermission")
    fun getIMEI() {
        try {
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val IMEI = telephonyManager.deviceId
            if (IMEI != null) {
                textView1.text = "IMEI: $IMEI"
            } else {
                println("IMEI:::Null")
            }


        } catch (ex: Exception) {
            println("IMEI:::${ex.message}")
        }
    }


    fun getAndroidId(){
        try {
            val androidID = System.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
            textView2.text = "ID: $androidID"
        } catch (e: Exception) {
            println("ID:::Null")
        }
    }
}
package com.kakao.android.kakaomaptest.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.*
import com.kakao.android.kakaomaptest.R

class SplashActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "KM/SplashActivity"

        private const val PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (!checkPermission()) {
            requestPermission()
        } else {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        Handler().postDelayed({
            intent = Intent(this@SplashActivity, MainActivity::class.java)
            this@SplashActivity.startActivity(intent)
            finish()
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)

        }, 1000)
    }

    private fun checkPermission(): Boolean {
        return checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        // 사용자가 이전에 한번 거부한 경우, 설명과 함께 권한을 요청
        if (shouldShowRequestPermissionRationale(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            var builder = AlertDialog.Builder(this)
            builder.setMessage("권한이 없어 해당 기능을 사용하실 수 없습니다.\n권한을 허용하시면 사용하실 수 있습니다")
            builder.setCancelable(false)
            builder.setPositiveButton("허용") { _, _ ->
                requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)

            }
            builder.setNegativeButton("거부") { _, _ ->
                Toast.makeText(
                    this,
                    "위치 정보 엑세스 권한이 없어 해당 기능을 사용하실 수 없습니다.",
                    Toast.LENGTH_SHORT
                )
                    .show()
                finish()
            }
            builder.create().show()
        } else {
            // 최초 권한 요청시
            requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    Log.d(TAG, "permission_granted" + grantResults[0])
                    startMainActivity()
                } else {
                    // 사용자가 첫번째로 거부한 경우 => 다음번 앱에서 권한 설정 가능
                    if (shouldShowRequestPermissionRationale(
                            this, Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        Toast.makeText(
                            this,
                            "위치 정보 엑세스 권한이 없어 해당 기능을 사용하실 수 없습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // 2번 이상 사용자가 거부한 경우 => 설정에서 권한 변경 필요
                        Toast.makeText(
                            this,
                            "권한이 거부되었습니다.\n권한을 허용하시려면 설정을 눌러주세요\n\n필요권한 : 위치 정보 액세스",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                }
                return
            }
        }
    }
}
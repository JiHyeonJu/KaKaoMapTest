package com.kakao.android.kakaomaptest.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.*
import com.kakao.android.kakaomaptest.R

class SplashActivity : Activity() {
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
            Toast.makeText(this, "앱 실행을 위해서 권한이 필요합니다", Toast.LENGTH_LONG).show()
            requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
            // todo:Snackbar로 변경
            /* Snackbar.make(
                 mLayout, "이 앱을 실행하려면 카메라와 외부 저장소 접근 권한이 필요합니다.",
                 Snackbar.LENGTH_INDEFINITE
             ).setAction("확인", View.OnClickListener() {
                 requestPermissions(this@SplashActivity, permissions, PERMISSION_REQUEST_CODE)
             }).show()*/
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
                            "위치 권한이 거부되었습니다",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        // 사용자가 거부한 경우 => 설정에서 권한 변경 필요
                        Toast.makeText(this, "권한이 거부되었습니다\n설정에서 위치 권한을 허용해주세요", Toast.LENGTH_LONG)
                            .show()
                    }
                    finish()
                }
                return
            }
        }
    }
}
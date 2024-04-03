package com.example.elderlycare.user.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.elderlycare.R
import com.example.elderlycare.utils.Constants
import org.json.JSONObject

class UserLoginActivity : AppCompatActivity() {
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity_user_login)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // 로그인 요청 보내기
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        val url = "${Constants.BASE_URL}/m/user/login"

        val requestBody = JSONObject()
        requestBody.put("email", email)
        requestBody.put("password", password)

        val request = JsonObjectRequest(
            Request.Method.POST, url, requestBody,
            { response ->
                // 로그인 성공 처리
                val email = response.getString("email")
                val role = response.getString("role")
                // TODO: 로그인 성공 후 처리 로직 추가
                Toast.makeText(this@UserLoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
            },
            { error ->
                // 로그인 실패 처리
                // TODO: 로그인 실패 처리 로직 추가
                Toast.makeText(this@UserLoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        )

        Volley.newRequestQueue(this).add(request)
    }
}
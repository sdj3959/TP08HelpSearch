package com.sdj2022.tp08helpsearch.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sdj2022.tp08helpsearch.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    val binding:ActivityLoginBinding by lazy{ActivityLoginBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        setContentView(binding.root)

        // 둘러보기 글씨 클릭으로 로그인없이 Main 화면으로 이동
        binding.tvGo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // 회원가입 버튼 클릭
        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        // 이메일 로그인 버튼 클릭
        binding.layoutEmail.setOnClickListener{
            startActivity(Intent(this, EmailLoginActivity::class.java))
        }

        // 간편로그인 버튼들
        binding.btnLoginKakao.setOnClickListener { clickedLoginKakao() }
        binding.btnLoginGoogle.setOnClickListener { clickedLoginGoogle() }
        binding.btnLoginNaver.setOnClickListener { clickedLoginNaver() }
    }//onCreate

    private fun clickedLoginKakao(){
        Toast.makeText(this, "kakao", Toast.LENGTH_SHORT).show()
    }

    private fun clickedLoginGoogle(){
        Toast.makeText(this, "google", Toast.LENGTH_SHORT).show()
    }

    private fun clickedLoginNaver(){
        Toast.makeText(this, "naver", Toast.LENGTH_SHORT).show()
    }
}
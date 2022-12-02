package com.sdj2022.tp08helpsearch.activities

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.sdj2022.tp08helpsearch.R
import com.sdj2022.tp08helpsearch.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    val binding:ActivitySignupBinding by lazy{ActivitySignupBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_signup)
        setContentView(binding.root)

        // 툴바를 액션바로 설정
        setSupportActionBar(binding.toolbar)
        // 액션바에 업버튼 설정 및 제목글씨 제거
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)

        binding.btnSignup.setOnClickListener { clickSignUp() }
    }

    // 업버튼 클릭할때 자동 발동하는 콜백메소드
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun clickSignUp(){
        // Firebase Firestore DB에 사용자 정보 저장하기 [앱과 firebase 플랫폼 연동]

        var email:String = binding.etEmail.text.toString()
        var password:String = binding.etPassword.text.toString()
        var passwordConfirm:String = binding.etPasswordConfirm.text.toString()

        // 원래는 정규표현식(RegExp)을 이용하여 유효성 검사함. 시간상 pass

        // 패스워드가 올바른지 확인 (코틀린은 자동 equals)
        if(password != passwordConfirm){
            AlertDialog.Builder(this).setMessage("비밀번호가 서로 다릅니다\n정확히 입력해주세요").show()
            binding.etPasswordConfirm.selectAll() //써있는 글씨를 모두 선택상태로 하여 손쉽게 새로 입력이 가능함
            return
        }
        
        // Firebase Firestore DB에 저장하기 위해 Firestore DB 관리자객체 소환
        var db:FirebaseFirestore = FirebaseFirestore.getInstance()

        // 이미 가입한 적이 있는 email 인지 검사
        // 필드값 중에 'email' 의 값이 EditText에 입력한 email과 같은 것이 있는지 찾아달라고 요청
        db.collection("emailUsers")
            .whereEqualTo("email", email)
            .get().addOnSuccessListener {
                // 같은 값을 가진 Document 가 있다면.. 기존에 같은 email 이 있다는 것임
                if(it.documents.size>0){
                    AlertDialog.Builder(this).setMessage("중복된 이메일이 존재합니다\n다시 입력해주세요").show()
                    binding.etEmail.requestFocus() //selectAll() 하려면 focus가 있어야 함.
                    binding.etEmail.selectAll()
                }else{
                    // 신규 email

                    // 저장할 데이터들을 하나로 묶기위해 HashMap
                    var user:MutableMap<String, String> = mutableMapOf()
                    user.put("email", email)
                    user.put("password", password)

                    // DB 안에 Collection 명은 "emailUsers"로 지정 [RDBMS 의 테이블 이름 같은 역할]
                    // 별도의 Document 명을 주지 않으면 Random 값으로 설정됨. 이 랜덤값을 회원번호의 역할로 사용함.
                    db.collection("emailUsers").add(user).addOnSuccessListener {
                        AlertDialog.Builder(this)
                            .setMessage("축하합니다\n회원가입이 완료되었습니다")
                            .setCancelable(false)
                            .setPositiveButton("확인", object : OnClickListener{
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    finish()
                                }
                            }).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, "회원가입 실패 : ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }


    }

}
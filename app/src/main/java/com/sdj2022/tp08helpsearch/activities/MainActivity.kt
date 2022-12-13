package com.sdj2022.tp08helpsearch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.sdj2022.tp08helpsearch.R
import com.sdj2022.tp08helpsearch.databinding.ActivityMainBinding
import com.sdj2022.tp08helpsearch.fragments.SearchListFragment
import com.sdj2022.tp08helpsearch.fragments.SearchMapFragment

class MainActivity : AppCompatActivity() {

    val binding:ActivityMainBinding by lazy{ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)

        // 툴바를 제목줄로 대체하여 설정
        setSupportActionBar(binding.toolbar)

        // 첫 실행될 프레그먼트를 동적 추가
        supportFragmentManager.beginTransaction().add(R.id.container_fragment, SearchListFragment()).commit()

        // 탭 레이아웃의 탭 버튼 클릭시에 보여줄 프레그먼트 변경
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.text == "리스트"){
                    supportFragmentManager.beginTransaction().replace(R.id.container_fragment, SearchListFragment()).commit()
                }else if(tab?.text == "지도"){
                    supportFragmentManager.beginTransaction().replace(R.id.container_fragment, SearchMapFragment()).commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        } )

    }

    // 툴바에 옵션메뉴 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_help-> Toast.makeText(this, "도움말", Toast.LENGTH_SHORT).show()
            R.id.menu_logout-> Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
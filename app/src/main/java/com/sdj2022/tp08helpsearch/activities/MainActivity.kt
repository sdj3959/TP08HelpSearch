package com.sdj2022.tp08helpsearch.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.sdj2022.tp08helpsearch.R
import com.sdj2022.tp08helpsearch.databinding.ActivityMainBinding
import com.sdj2022.tp08helpsearch.fragments.SearchListFragment
import com.sdj2022.tp08helpsearch.fragments.SearchMapFragment

class MainActivity : AppCompatActivity() {

    val binding:ActivityMainBinding by lazy{ActivityMainBinding.inflate(layoutInflater)}

    // 카카오 지역검색(로컬) API -- 카카오개발자 사이트 참고
    //1. 검색 장소명
    var searchQuery:String = "화장실" //앱 초기 키워드 - 내 주변 개방 화장실
    //2. 현재 내 위치 정보 객체 (위도, 경도 정보를 멤버로 보유)
    var mylocation:Location? = null

    // [ Google Fused API 사용 - 위치정보 라이브러리 play-services-location ]
    val providerClient : FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

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
        })
       
       // 내 위치정보 제공에 대한 사용자 동적 퍼미션
       val permissions:Array<String> = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
       if(checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED){
           // 허가 되어 있지 않으므로 권한을 요청하는 다이얼로그 보이기
           requestPermissions(permissions, 10)
           
       }else{
           // 허가 되어 있으므로 곧바로 내 위치값을 요구하기
           requestMyLocation()
       }

    }//onCreate method..

    // 퍼미션요청 다이얼로그의 [허가/거부] 선택이 되었을때 자동 발동하는 콜백메소드
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == 10 && grantResults[0] == PackageManager.PERMISSION_GRANTED) requestMyLocation()
        else Toast.makeText(this, "내 위치정보를 제공하지 않아 검색기능을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
    }
    
    // 내 위치정보를 가져오는 기능 메소드
    private fun requestMyLocation(){
        
        // 위치검색을 위한 기준(요청)객체가 필요함.
        val request:LocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        // 실시간 위치정보 갱신 요청
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        providerClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())

    }

    // 위치정보 갱신될때 마다 발동하는 Callback 객체 생성
    private val locationCallback:LocationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)

            mylocation = p0.lastLocation

            // 위치탐색이 끝났으니 내 위치정보 업데이트는 종료
            providerClient.removeLocationUpdates(this) //this : locationCallback

            // 내 위치정보도 있으니.. 카카오 로컬 API 키워드 검색작업 시작
            searchPlaces()
        }
    }

    // 카카오 키워드 로컬 장소 검색 API 작업 기능 메소드
    private fun searchPlaces(){
        // 검색에 필요한 요청변수들.. 확인 [검색어, 내 위치 좌표]
        Toast.makeText(this, "$searchQuery : ${mylocation?.latitude}, ${mylocation?.longitude}", Toast.LENGTH_SHORT).show()
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
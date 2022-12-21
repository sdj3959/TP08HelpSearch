package com.sdj2022.tp08helpsearch.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sdj2022.tp08helpsearch.activities.MainActivity
import com.sdj2022.tp08helpsearch.activities.PlaceUrlActivity
import com.sdj2022.tp08helpsearch.databinding.FragmentSearchMapBinding
import com.sdj2022.tp08helpsearch.model.Place
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.POIItemEventListener

class SearchMapFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    val binding:FragmentSearchMapBinding by lazy { FragmentSearchMapBinding.inflate(layoutInflater) }

    val mapView:MapView by lazy { MapView(context) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 맵뷰를 뷰그룹에 추가하여 화면에 배치하도록. .. 여기까지만 해도 지도는 일단 보여짐.
        binding.containerMapview.addView(mapView)

        // 마커 or 말풍선을 클릭했을때 반응하기 - 반드시 마커 설정보다 먼저 맵뷰에 설정해 놓아야 동작함
        mapView.setPOIItemEventListener(markerEventListener)

        // 지도 관련 설정들..[ 지도위치, 마커 추가등 ]
        setMapAndMarkers()
    }

    private fun setMapAndMarkers(){
        // 맵 중심점을 내 위치로 변경
        // 현재 내위치 정보..는 MainActivity 의 멤버변수로 저장되어 있음.
        var latitude:Double = (activity as MainActivity).mylocation?.latitude ?: 37.566805
        var longitude:Double = (activity as MainActivity).mylocation?.longitude ?: 126.9784147

        var myMapPoint:MapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        mapView.setMapCenterPointAndZoomLevel(myMapPoint, 5, true)
        mapView.zoomIn(true)
        mapView.zoomOut(true)

        // 내 위치에 마커 표시
        var marker = MapPOIItem()
        marker.apply {
            this.itemName  = "ME"
            mapPoint = myMapPoint
            markerType = MapPOIItem.MarkerType.BluePin
            selectedMarkerType = MapPOIItem.MarkerType.YellowPin
        }
        mapView.addPOIItem(marker)

        // 검색결과 장소들.. 마커들을 추가하기
        val documents:MutableList<Place>? = (activity as MainActivity).searchPlaceResponse?.documents
        documents?.forEach {
            val point:MapPoint = MapPoint.mapPointWithGeoCoord(it.y.toDouble(), it.x.toDouble())

            // 마커옵션 객체를 만들어서 기본 설정들
            val marker:MapPOIItem = MapPOIItem().apply {
                itemName = it.place_name
                mapPoint = point
                markerType = MapPOIItem.MarkerType.RedPin
                selectedMarkerType = MapPOIItem.MarkerType.YellowPin

                // 마커에 표시되지는 않지만 저장하고 싶은 사용자정보가 있다면..
                userObject = it
            }
            mapView.addPOIItem(marker)
        }
    }

    // 마커나 말풍선이 클릭되는 이벤트에 반응하는 리스너
    private val markerEventListener:POIItemEventListener = object:POIItemEventListener{
        override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {

        }

        override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {

        }

        // 말풍선을 클릭했을때 반응하는 콜백메소드
        override fun onCalloutBalloonOfPOIItemTouched(
            p0: MapView?,
            p1: MapPOIItem?,
            p2: MapPOIItem.CalloutBalloonButtonType?
        ) {
            // 두번째 파라미터 p1 : 클릭한 말풍선의 마커객체
            if(p1?.userObject == null) return

            var place: Place = p1?.userObject as Place

            // 장소의 상세정보 URL 을 가지고 상세정보 웹페이지를 보여주는 화면으로 전환
            val intent:Intent = Intent(context, PlaceUrlActivity::class.java)
            intent.putExtra("place_url", place.place_url)
            startActivity(intent)

        }

        override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {

        }
    }
}
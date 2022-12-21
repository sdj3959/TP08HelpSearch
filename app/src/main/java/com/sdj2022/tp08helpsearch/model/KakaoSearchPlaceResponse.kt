package com.sdj2022.tp08helpsearch.model

data class KakaoSearchPlaceResponse(var meta:PlaceMeta, var documents:MutableList<Place>)

data class PlaceMeta(var total_count:Int, var pagealbe_count:Int, var is_end:Boolean)

data class Place(
    var id:String,
    var place_name:String,
    var category_name:String,
    var phone:String,
    var address_name:String,
    var road_address_name:String,
    var x:String,  //경도 - longitude
    var y:String,   //위도 - latitude
    var place_url:String, //장소에 대한 정보 웹사이트
    var distance:String //중심좌표(내 위치)로부터의 거리. 단위 - meter [단, 요청파라미터로 x,y 를 줬을때만 값이 존재함]
)
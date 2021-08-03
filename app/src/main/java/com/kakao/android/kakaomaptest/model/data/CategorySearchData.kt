package com.kakao.android.kakaomaptest.model.data

data class CategorySearchData(
    var meta: PlaceMeta,
    var documents: ArrayList<Place>     // 검색 결과
)

data class PlaceMeta(
    var total_count: Int,               // 문서 수
    var pageable_count: Int,            // total_count 중 노출 가능 문서 수, 최대 45
    var is_end: Boolean                 // 현재 페이지가 마지막 페이지인지 여부
)

data class Place(
    var place_name: String,             // 장소명
    var address_name: String,           // 전체 지번 주소
    var road_address_name: String,      // 전체 도로명 주소
    var category_name: String,          // 카테고리 이름
    val category_group_code: String,    // 카테고리 코드
    val place_url: String,              // 장소 url (unique 값)
    var x: String,                      // X 좌표값 혹은 longitude
    var y: String                       // Y 좌표값 혹은 latitude
)
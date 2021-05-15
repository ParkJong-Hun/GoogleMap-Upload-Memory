package pjh.mjc.project_gimal_2017081066;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    //선언
    MapFragment mapFrag;
    GoogleMap gMap;
    //+ 그라운드오버레이(투명한 레이어. 기타 맵 기능을 넣음.(마커 등)) 선언
    GroundOverlayOptions videoMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, MODE_PRIVATE);

        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);//XML로 만든 것을 FragmentManager로 바인딩해서 MapFragment 객체 생성.
        mapFrag.getMapAsync(this);//onMapReady을 비동기 호출해서 불러오기
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {//지도 초기화
        gMap = googleMap;//생성자를 통해 생성된 구글맵 객체를 가져옴.
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng location = new LatLng(37.568256, 126.897240);//월드컵 경기장 좌표
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.568256, 126.897240), 15));//카메라 초기화할 때 좌표 지정.(명지전문대학)
        gMap.getUiSettings().setZoomControlsEnabled(true);//줌 컨트롤 모드 키기.

        //동적으로 사용자에게 퍼미션을 요청하여 수략받아야 함(with requestPermission)
        //현재 위치
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 때 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MODE_PRIVATE);
        } else {
            //권한이 이미 있음.
            gMap.setMyLocationEnabled(true);//현재 위치 설정
            gMap.getUiSettings().setMyLocationButtonEnabled(true);//내 위치 버튼 사용
        }

/*        //월드컵 경기장 좌표에 마킹, 스니펫
        MarkerOptions markerOptions = new MarkerOptions();//마커 객체 생성
        markerOptions.position(location);//위치 설정
        markerOptions.title("월드컵경기장");//스니펫 제목
        markerOptions.snippet("2012년 월드컵 게최");//스니펫 본문
        markerOptions.alpha(0.7f);//투명도
        gMap.addMarker(markerOptions);//지도에 마커 생성*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//오버플로우 메뉴 만들기
        super.onCreateOptionsMenu(menu);

        menu.add(0, 1, 0, "목록으로 보기");
        menu.add(0, 2, 0, "다른 사람의 지도 보기");//if 현재 보는 map이 상대방의 map이라면 내 지도 보기
        menu.add(0, 3, 0, "로그아웃");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//오버플로우 메뉴 아이템 클릭 이벤트 처리
        switch (item.getItemId()) {
            case 1:
                //목록으로 보기
                return true;
            case 2:
                //다른 사람의 지도 보기
            case 3:
                //로그아웃
                return true;
        }
        return false;//case에 없는 것 클릭하거나, 아예 클릭 안하면 처리 안함 알림.
    }

    private void getLocationPermission() {

    }
}
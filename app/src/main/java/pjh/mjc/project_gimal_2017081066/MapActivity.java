package pjh.mjc.project_gimal_2017081066;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    //선언
    MapFragment mapFrag;
    GoogleMap map;
    //+ 그라운드오버레이(투명한 레이어. 기타 맵 기능을 넣음.(마커 등)) 선언
    GroundOverlayOptions videoMark;
    ImageButton post_here;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("id"));

        ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, MODE_PRIVATE);

        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);//XML로 만든 것을 FragmentManager로 바인딩해서 MapFragment 객체 생성.
        mapFrag.getMapAsync(this);//onMapReady을 비동기 호출해서 불러오기
        post_here = findViewById(R.id.post_here);
        //TODO: 버튼 클릭 이벤트 리스너
        post_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocation();
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {//지도 초기화
        map = googleMap;//생성자를 통해 생성된 구글맵 객체를 가져옴.
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.568256, 126.897240), 15));//카메라 초기화할 때 좌표 지정.(명지전문대학)
        map.getUiSettings().setZoomControlsEnabled(true);//줌 컨트롤 모드 키기.

        //현재 위치
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 때 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MODE_PRIVATE);
        } else {
            //권한이 이미 있음.
            map.setMyLocationEnabled(true);//현재 위치 설정
            map.getUiSettings().setMyLocationButtonEnabled(true);//내 위치 버튼 사용
        }
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Intent intent = new Intent(MapActivity.this, PostingActivity.class);
                intent.putExtra("Lat", latLng.latitude);
                intent.putExtra("Lng", latLng.longitude);
                startActivityForResult(intent, 0);
            }
        });
    }

    //현재 위치 좌표 요청, 카메라 전환
    private void requestLocation() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            //현재 위치 좌표 출력
            Location lastlocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastlocation != null) {
                Intent intent = new Intent(this, PostingActivity.class);
                startActivityForResult(intent, 0);
                Toast.makeText(MapActivity.this, "최근 위치: " + lastlocation.getLatitude() + "," + lastlocation.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//오버플로우 메뉴 만들기
        super.onCreateOptionsMenu(menu);

        menu.add(0, 1, 0, "목록으로 보기");
        menu.add(0, 2, 0, "로그아웃");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//오버플로우 메뉴 아이템 클릭 이벤트 처리
        switch (item.getItemId()) {
            case 1:
                //목록으로 보기
                return true;
            case 2:
                //로그아웃
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
        }
        return false;//case에 없는 것 클릭하거나, 아예 클릭 안하면 처리 안함 알림.
    }

    //TODO: 포스트 완료 하고 돌아왔을 때 마커 생성. 좌표 상 반올림했을 때 똑같은 위치에 마커가 있으면, 한 마커에 여러 게시글 표시하게
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1, 1), 17));//포스트한 곳으로 이동
            /*onActivityResult에서
          MarkerOptions markerOptions = new MarkerOptions();
          markerOptions.position(latLng);
          markerOptions.title("제목");
          markerOptions.snippet("스니펫");
          markerOptions.alpha(0.9f);
          gMap.addMarker(markerOptions);
       */
        }
    }
}
package pjh.mjc.Project_GIMAL_2017081066;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//지도 창
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    //선언
    MapFragment mapFrag;
    GoogleMap map;
    //+ 그라운드오버레이(투명한 레이어. 기타 맵 기능을 넣음.(마커 등)) 선언
    GroundOverlayOptions videoMark;
    ImageButton post_here;
    DBHelper dbHelper;
    SQLiteDatabase db;
    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        setTitle(id);

        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);//XML로 만든 것을 FragmentManager로 바인딩해서 MapFragment 객체 생성.
        mapFrag.getMapAsync(this);//onMapReady을 비동기 호출해서 불러오기

        post_here = findViewById(R.id.post_here);
        post_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLocationPost();
            }
        });

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {//지도 초기화
        map = googleMap;//생성자를 통해 생성된 구글맵 객체를 가져옴.
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37,126), 1));//카메라 초기 설정
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
        
        //지도에 자신의 계정의 게시글을 마커로 표시
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM Post WHERE poster='"+ id + "';", null);
        while(cursor.moveToNext()){
            LatLng newLatLng = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(newLatLng);
            markerOptions.alpha(0.9f);
            map.addMarker(markerOptions);
        }
        db.close();
        
        //마커 클릭 이벤트
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //좌표가 일치하고 아이디가 동일한 게시글을 검색
                //게시글 화면으로 이동하는 인텐트
                Intent intent = new Intent(MapActivity.this, PostActivity.class);
                db = dbHelper.getReadableDatabase();
                Cursor cursor;
                cursor = db.rawQuery("SELECT * FROM Post WHERE latitude='" + marker.getPosition().latitude + "' and longitude='" + marker.getPosition().longitude + "' and poster='" + id + "';", null);
                if(cursor.moveToNext()) {
                    //일치하는 하나의 튜플에서 값 가져오기
                    intent.putExtra("code", cursor.getInt(0));
                    intent.putExtra("title", cursor.getString(1));
                    intent.putExtra("article", cursor.getString(2));
                    intent.putExtra("url", cursor.getString(3));
                    intent.putExtra("lat", cursor.getDouble(4));
                    intent.putExtra("lng", cursor.getDouble(5));
                    intent.putExtra("date", cursor.getString(6));
                    intent.putExtra("poster", cursor.getString(7));
                    intent.putExtra("map_click", true);
                    //이동
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        
        //위치 정보 구하기
    }

    //현재 위치 좌표 요청, 카메라 전환하고 포스트 작성
    private void currentLocationPost() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            //현재 위치 좌표 출력
            Location lastlocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastlocation != null) {
                //현재 위치로 바꾸면서, 글 작성 액티비티로 이동
                Intent intent = new Intent(this, PostingActivity.class);
                intent.putExtra("Lat", lastlocation.getLatitude());
                intent.putExtra("Lng", lastlocation.getLongitude());
                startActivityForResult(intent, 0);
            } else {
                Toast.makeText(getApplicationContext(), "현재 위치에 대한 정보가 없습니다. 현재 위치 버튼을 클릭해주세요.", Toast.LENGTH_SHORT).show();
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
                Intent intent_list = new Intent(MapActivity.this, PostListActivity.class);
                intent_list.putExtra("id", id);
                startActivity(intent_list);
                return true;
            case 2:
                //로그아웃
                Intent intent_logout = new Intent(this, LoginActivity.class);
                startActivity(intent_logout);
                return true;
        }
        return false;//case에 없는 것 클릭하거나, 아예 클릭 안하면 처리 안함 알림.
    }

    //포스트 완료 하고 돌아왔을 때 갱신해서 마커 생성. 좌표 상 반올림했을 때 똑같은 위치에 마커가 있으면, 한 마커에 여러 게시글 표시하게
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {//PostingActivity
            if(resultCode == RESULT_OK) {
                String out_title = data.getStringExtra("out_title");
                String out_article = data.getStringExtra("out_article");
                Double out_latitude = data.getDoubleExtra("out_latitude", 0);
                Double out_longitude = data.getDoubleExtra("out_longitude", 0);
                String out_date = data.getStringExtra("out_date");
                String out_url = data.getStringExtra("out_url");
                //포스트한 곳으로 이동
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(out_latitude, out_longitude), 17));
                //DB에 추가
                db = dbHelper.getReadableDatabase();
                Cursor cursor;
                cursor = db.rawQuery("SELECT code FROM Post ORDER BY code DESC;", null);
                int num = 1;
                //데이터가 아무것도 없으면 1로, 데이터가 테이블에 하나라도 있으면 코드를 그 값의 + 1로 만듦.
                if(cursor.moveToNext()) {
                    try {
                        num = cursor.getInt(0) + 1;
                    } catch (Exception e) {
                    }
                }
                db = dbHelper.getWritableDatabase();
                db.execSQL("INSERT INTO Post VALUES(" + num + ", '" + out_title + "', '" + out_article + "', '" + out_url + "', '" + out_latitude + "', '" + out_longitude + "', '" + out_date + "', '" + id + "');");
                db.close();
                //db 업데이트한 것 반영.
                LatLng newLatLng = new LatLng(out_latitude, out_longitude);
                MarkerOptions new_markerOptions = new MarkerOptions();
                new_markerOptions.position(newLatLng);
                new_markerOptions.alpha(0.9f);
                map.addMarker(new_markerOptions);
            }
        }
    }
}
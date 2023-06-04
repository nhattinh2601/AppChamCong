package org.tensorflow.lite.examples.detection.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.tensorflow.lite.examples.detection.CameraActivity;
import org.tensorflow.lite.examples.detection.DetectorActivity;
import org.tensorflow.lite.examples.detection.R;
import org.tensorflow.lite.examples.detection.model.LoaiSp;
import org.tensorflow.lite.examples.detection.model.LoaiSpAdapter;
import org.tensorflow.lite.examples.detection.retrofit.ApiBanHang;
import org.tensorflow.lite.examples.detection.retrofit.RetrofitClient;
import org.tensorflow.lite.examples.detection.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {


    ListView listViewManHinhChinh;
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangloaisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
        ActionBar();
        if(isConnected(this)){
            getLoaiSanPham();
            getEventClick();
        }else{
            Toast.makeText(getApplicationContext(), "Không có internet, vui lòng kết nối", Toast.LENGTH_LONG).show();

        }

//        Log.d("UserHomeActivity",Utils.user_current.getUsername());
    }

    private void initView() {

        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        listViewManHinhChinh =findViewById(R.id.listviewmanhinhchinh);
        navigationView =findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);

        // khoi tao list
        mangloaisp = new ArrayList<>();
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }

        });
    }
    private void getEventClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> apdaterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), DetectorActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent checkin = new Intent(getApplicationContext(), CheckInActivity.class);
                        startActivity(checkin);
                        break;
                    case 2:
                        Intent checkout = new Intent(getApplicationContext(), CheckOutActivity.class);
                        startActivity(checkout);
                        break;
                    case 3:
                        Intent thongtin = new Intent(getApplicationContext(), CheckOutActivity.class);
                        startActivity(thongtin);
                        break;
                    case 4:
                        //xoa key user
                        FirebaseAuth.getInstance().signOut();
                        Intent dangnhap = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(dangnhap);
                        finish();
                        break;
                }
            }
        });
    }


    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected()) ){
            return  true;}
        else { return  false;}
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess()){
                                mangloaisp =loaiSpModel.getResult();
                                mangloaisp.add(new LoaiSp("Đăng xuất",""));
                                loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(),mangloaisp);
                                listViewManHinhChinh.setAdapter(loaiSpAdapter);
                            }

                        }

                ));


    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
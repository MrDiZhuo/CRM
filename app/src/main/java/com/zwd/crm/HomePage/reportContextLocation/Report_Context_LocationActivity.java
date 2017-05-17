package com.zwd.crm.HomePage.reportContextLocation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.zwd.crm.R;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.ToastUtils;

public class Report_Context_LocationActivity extends Activity implements LocationSource, AMapLocationListener,
        AMap.OnMapLoadedListener, AMap.OnCameraChangeListener,
        Animation.AnimationListener, GeocodeSearch.OnGeocodeSearchListener{
    private layout_action_bar actionBar;
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private UiSettings mUiSettings;
    private AMapLocation aLocation;
    private Animation centerMarker;
    private ImageView centerImageView;
    private LinearLayout centerView;
    private Marker currentMarker;
    private boolean isFirst = true;
    private MarkerOptions markerOption;
    private LatLng point;
    private GeocodeSearch geocoderSearch;
    private ProgressDialog progDialog = null;
    private Button button;
    private String addressName;/////////返回数据
    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                if (isFirst) {
                    isFirst = false;
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(
                            amapLocation.getLatitude(), amapLocation.getLongitude())));
                    CameraUpdateFactory.zoomTo(16);
                }
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report__context__location);
        actionBar = (layout_action_bar)findViewById(R.id.report__context__location_action_bar);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        centerMarker = AnimationUtils.loadAnimation(this,
                R.anim.bounce_interpolator);
        centerImageView = (ImageView) findViewById(R.id.centerMarkerImg);
        centerView = (LinearLayout) findViewById(R.id.centerView);
        initActionBar(actionBar);
        initMap();
    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =getIntent();
                intent.putExtra("address",addressName);
                Report_Context_LocationActivity.this.setResult(3,intent);
                Report_Context_LocationActivity.this.finish();

            }
        });
        actionBar.setTitle("选择位置");
        actionBar.setImage(R.mipmap.finish);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(Report_Context_LocationActivity.this,"选择的地点是："+addressName);
            }
        });
    }

    /**
     * 初始化AMap对象
     */
    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationRotateAngle(180);
        aMap.setLocationSource(this);// 设置定位监听
        mUiSettings.setMyLocationButtonEnabled(true); // 是否显示默认的定位按钮
        aMap.setMyLocationEnabled(true);// 是否可触发定位并显示定位层
//		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
//		mUiSettings.setTiltGesturesEnabled(false);// 设置地图是否可以倾斜
        mUiSettings.setScaleControlsEnabled(false);// 设置地图默认的比例尺是否显示
        mUiSettings.setZoomControlsEnabled(false);
        initMapListener();
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        progDialog = new ProgressDialog(this);
    }

    private void initMapListener() {
        aMap.setOnMapLoadedListener(this);
        aMap.setOnCameraChangeListener(this);
        centerMarker.setAnimationListener(this);
//		locate.setOnClickListener(this);
    }


    @Override
    public void onMapLoaded() {
        centerView.startAnimation(centerMarker);
        CameraUpdateFactory.zoomTo(16);
        Log.e("load", "onMapLoaded");
    }

    @Override
    public void onCameraChange(CameraPosition arg0) {
    }


    @Override
    public void onCameraChangeFinish(CameraPosition arg0) {
        point = arg0.target;
        getAddress(point);
        centerView.startAnimation(centerMarker);
        Log.e("load", "onCameraChangeFinish+获取后台数据");
    }



    @Override
    public void onAnimationStart(Animation animation) {
        centerImageView.setImageResource(R.mipmap.bubble_end);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        centerImageView.setImageResource(R.mipmap.bubble_end);
    }



    /**
     * 把LatLng对象转化为LatLonPoint对象
     */
    public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
        return new LatLonPoint(latlon.latitude, latlon.longitude);
    }

    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

    /**
     * 根据经纬度得到地址
     */
    public void getAddress(final LatLng latLonPoint) {
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(convertToLatLonPoint(latLonPoint), 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {

    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                addressName = result.getRegeocodeAddress().getFormatAddress(); // 逆转地里编码不是每次都可以得到对应地图上的opi
                ToastUtils.showShort(this,addressName);
            } else {
                ToastUtils.showShort(Report_Context_LocationActivity.this, getString(R.string.no_result));
            }
        } else if (rCode == 27) {
            ToastUtils.showShort(this, getString(R.string.error_network));
        } else if (rCode == 32) {
            ToastUtils.showShort(this,getString( R.string.error_key));
        } else {
            ToastUtils.showShort(this,
                    getString(R.string.error_other) + rCode);
        }
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    /**
     *键盘返回
     */
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent =getIntent();
            intent.putExtra("address",addressName);
            Report_Context_LocationActivity.this.setResult(3,intent);
            Report_Context_LocationActivity.this.finish();
        }
        return true;
    }


}

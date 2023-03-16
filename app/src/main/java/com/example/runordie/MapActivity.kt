package com.example.runordie


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.*
import com.amap.api.maps2d.LocationSource.OnLocationChangedListener
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.MyLocationStyle
import java.text.SimpleDateFormat
import java.util.*


//TODO: 自定义锚点（不同阵营颜色不同）
//TODO: 多人共享位置
class MapActivity:AppCompatActivity(),LocationSource,AMapLocationListener{
    // 显示地图控件
    lateinit var mMapView:MapView //地图控件
    lateinit var aMap:AMap // 地图对象

    // 定位需要的声明
    var mLocationClient: AMapLocationClient? = null //定位发起端
    var mLocationOption: AMapLocationClientOption? = null //定位参数
    var mListener: OnLocationChangedListener?= null  //定位监听器

    private var isFirstLoc = true

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.amap)

        Log.i("Jump Information","In Map")

        AMapLocationClient.updatePrivacyShow(this,true,true)
        AMapLocationClient.updatePrivacyAgree(this,true)

        //获取地图控件引用
        mMapView = findViewById(R.id.map)
        //在activity执行onCreate时执行mMapView的onCreate，创建地图
        mMapView.onCreate(savedInstanceState) //有一个非空保护


        aMap = mMapView.map
        //设置显示定位按钮
        val settings = aMap.uiSettings
        aMap.setLocationSource(this) //设置定位监听
        settings.isMyLocationButtonEnabled = true  // 是否显示定位按钮
        aMap.isMyLocationEnabled = true //是否可以触发定位并显示定位层

        // 定位图标
        val myLocationStyle = MyLocationStyle()
        // myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.location))
        myLocationStyle.radiusFillColor(android.R.color.transparent)
        myLocationStyle.strokeColor(android.R.color.transparent)
        aMap.setMyLocationStyle(myLocationStyle)

        initLoc();
    }


    fun initLoc(){
        //初始化定位
        val mLocationClient = AMapLocationClient(applicationContext)
        mLocationClient.setLocationListener(this) //设置定位回调监听

        val mLocationOption = AMapLocationClientOption()
        //定位精度为高精度
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationOption.isNeedAddress = true
        mLocationOption.isOnceLocation = false  //不止定位一次
        mLocationOption.isWifiScan = true   //强制刷新Wifi
        mLocationOption.isMockEnable = false // 设置不允许模拟位置
        mLocationOption.interval = 2000 // 定位间隔 2000ms
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption)
        mLocationClient.startLocation() //启动定位

    }

    override fun activate(listener: OnLocationChangedListener?) {
        mListener = listener
    }

    override fun deactivate() {
        mListener = null
    }

    //定位回调函数
    override fun onLocationChanged(amapLocation: AMapLocation?) {
       if(mListener != null && amapLocation != null){
           if(amapLocation.errorCode == 0){
               Log.i("Map","定位成功")
               // 定位成功回调信息，设置相关信息
               // amapLocation.locationType
               val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
               val date = Date(amapLocation.time)
               df.format(date)


               if(isFirstLoc) {
                   // 设置缩放级别
                   aMap.moveCamera(CameraUpdateFactory.zoomTo(17F))
                   // 将地图移到定位点
                   aMap.moveCamera(CameraUpdateFactory.changeLatLng(LatLng(amapLocation.latitude, amapLocation.longitude)))
                   mListener?.onLocationChanged(amapLocation)  // 定位按钮移动地图中心
                   // Map.addMarker(getMark)a
                   //获取定位信息
                   val buffer = amapLocation.country + "" + amapLocation.province + "" + amapLocation.city + "" +
                           amapLocation.province + "" + amapLocation.district + "" + amapLocation.street + "" +
                           amapLocation.streetNum
                   Toast.makeText(applicationContext,buffer,Toast.LENGTH_LONG).show()
                   isFirstLoc = false
               }
           }else{
               //TODO: 添加Log系统
               val errText = "定位失败," + amapLocation.errorCode.toString() + ": " + amapLocation.errorInfo
               Log.e("AmapErr", errText)
           }
       }
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy() //销毁地图
        mLocationClient?.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()  //重新绘制加载地图
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause() // 暂停地图绘制
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState) //保存地图当前状态
    }

}
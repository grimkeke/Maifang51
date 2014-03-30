package com.djmedia.maifang51.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.djmedia.maifang51.R;
import com.djmedia.maifang51.tools.BMapUtil;

/**
 * Created by rd on 14-3-25.
 */
public class MapFragment extends Fragment {
    private static final String TAG = MapFragment.class.getSimpleName();

    private BMapManager mapManager;
    private MapView mMapView;

    private LocationClient mLocationClient;
    private LocationData locData;
    private MapMyLocationOverlay myLocationOverlay; //定位图层
    boolean isFirstLoc = true;
    boolean isRequest;

    private MKSearch mkSearch = null;	// 搜索模块，也可去掉地图模块独立使用
    private RouteOverlay routeOverlay = null;
    private TransitOverlay transitOverlay = null;//保存公交路线图层数据的变量，供浏览节点时使用
    int searchType = -1;//记录搜索的类型，区分驾车/步行和公交

    private PopupOverlay pop  = null;//弹出泡泡图层，浏览节点时使用
    private TextView  popupText = null;//泡泡view
    private View viewCache = null;
    private boolean showPopup = false;

    //浏览路线节点相关
    Button mBtnPre = null;//上一个节点
    Button mBtnNext = null;//下一个节点
    int nodeIndex = -2;//节点索引,供浏览节点时使用
    MKRoute route = null;//保存驾车/步行路线数据的变量，供浏览节点时使用

    private GeoPoint destPoint;
    private String currentCity = "上海";

    public MapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注意：请在试用setContentView前初始化BMapManager对象，否则会报错
        mapManager = new BMapManager(getActivity().getApplication());
        mapManager.init("hU5VEvTMibvRimDxyGiyjiSN", new MapGeneralListener());

        mkSearch = new MKSearch();
        mkSearch.init(mapManager, new MapSearchListener());

        mLocationClient = new LocationClient(getActivity());
        mLocationClient.registerLocationListener(new MapLocationListenner());

//        destPoint = (GeoPoint) getArguments().get(Constants.DEST_GEOPOINT);
        destPoint = new GeoPoint((int)(31.213997 * 1E6), (int)(121.521866 * 1E6));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        viewCache = getActivity().getLayoutInflater().inflate(R.layout.custom_text_view, null);
        popupText =(TextView) viewCache.findViewById(R.id.textcache);

        view.findViewById(R.id.id_locate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRequest = true;
                mLocationClient.requestLocation();
                Toast.makeText(getActivity(), "正在定位……", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.id_public_trafic_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MKPlanNode start = new MKPlanNode();
                start.pt = new GeoPoint((int) (locData.latitude * 1E6), (int) (locData.longitude * 1E6));

                MKPlanNode end = new MKPlanNode();
                end.pt = destPoint;

                mkSearch.setTransitPolicy(MKSearch.EBUS_TIME_FIRST);
                mkSearch.transitSearch(currentCity, start, end);
            }
        });

        view.findViewById(R.id.id_drive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MKPlanNode start = new MKPlanNode();
                start.pt = new GeoPoint((int) (locData.latitude * 1E6), (int) (locData.longitude * 1E6));

                MKPlanNode end = new MKPlanNode();
                end.pt = destPoint;

                mkSearch.setDrivingPolicy(MKSearch.ECAR_TIME_FIRST); // 设置驾车路线搜索策略，时间优先、费用最少或距离最短
                mkSearch.drivingSearch(currentCity, start, currentCity, end);
            }
        });

        view.findViewById(R.id.id_walk_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MKPlanNode start = new MKPlanNode();
                start.pt = new GeoPoint((int) (locData.latitude * 1E6), (int) (locData.longitude * 1E6));

                MKPlanNode end = new MKPlanNode();
                end.pt = destPoint;

                mkSearch.walkingSearch(currentCity, start, currentCity, end);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) getActivity().findViewById(R.id.id_map_view);
        mMapView.setBuiltInZoomControls(true);
        mMapView.getController().setZoom(14);
        mMapView.getController().enableClick(true);

        mBtnPre = (Button)getActivity().findViewById(R.id.pre);
        mBtnNext = (Button)getActivity().findViewById(R.id.next);
        mBtnPre.setVisibility(View.INVISIBLE);
        mBtnNext.setVisibility(View.INVISIBLE);
        mBtnPre.setOnClickListener(nodeClickListener);
        mBtnNext.setOnClickListener(nodeClickListener);

        pop = new PopupOverlay(mMapView, new PopupClickListener() {
            @Override
            public void onClickedPopup(int i) {
                Log.v("click", "clickapoapo");
            }
        });

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);

        mLocationClient.setLocOption(option);
        mLocationClient.start();

        locData = new LocationData();
        myLocationOverlay = new MapMyLocationOverlay(mMapView); //定位图层初始化
        myLocationOverlay.setData(locData);
        myLocationOverlay.enableCompass();
        mMapView.getOverlays().add(myLocationOverlay);     //添加定位图层
        mMapView.refresh();
    }

    //继承MyLocationOverlay重写dispatchTap实现点击处理
    public class MapMyLocationOverlay extends MyLocationOverlay{
        public MapMyLocationOverlay(MapView mapView) {
            super(mapView);
        }

        @Override
        protected boolean dispatchTap() {
            //处理点击事件,弹出泡泡
            popupText.setBackgroundResource(R.drawable.popup);
            popupText.setText("我的位置");
            showPopup = !showPopup;
            if (showPopup) {
                pop.showPopup(BMapUtil.getBitmapFromView(popupText),
                        new GeoPoint((int)(locData.latitude*1e6), (int)(locData.longitude*1e6)),
                        12);
            } else {
                pop.hidePop();
            }
            return true;
        }
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    private class MapGeneralListener implements MKGeneralListener {
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(MapFragment.this.getActivity(), "您的网络出错啦！", Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(MapFragment.this.getActivity(), "输入正确的检索条件！", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onGetPermissionState(int iError) {
            //非零值表示key验证未通过
            if (iError != 0) {
                //授权Key错误：
                Log.e(TAG, "Key error: " + iError);
                Toast.makeText(MapFragment.this.getActivity(), "授权Key错误！error: "+iError, Toast.LENGTH_LONG).show();
            }
        }
    }

    // 定位监听
    public class MapLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return ;
            }

            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            locData.accuracy = location.getRadius();   //如果不显示定位精度圈，将accuracy赋值为0即可
            locData.direction = location.getDerect(); // 可以设置 locData的方向信息, 如果定位SDK未返回方向
            myLocationOverlay.setData(locData);  //更新定位数据
            mMapView.refresh();     //更新图层数据执行刷新后生效

            if (isRequest || isFirstLoc){
                mMapView.getController().animateTo(new GeoPoint((int) (locData.latitude * 1e6), (int) (locData.longitude * 1e6)));
//                myLocationOverlay.setLocationMode(MyLocationOverlay.LocationMode.FOLLOWING);
                isRequest = false;
                mMapView.refresh();
            }
            isFirstLoc = false;  //首次定位完成
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }

    private class MapSearchListener implements MKSearchListener {
        @Override
        public void onGetPoiResult(MKPoiResult result, int type, int error) {
            // 错误号可参考MKEvent中的定义
            if ( error == MKEvent.ERROR_RESULT_NOT_FOUND){
                Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_LONG).show();
                return ;
            } else if (error != 0 || result == null) {
                Toast.makeText(getActivity(), "搜索出错啦..", Toast.LENGTH_LONG).show();
                return;
            }

            // 将poi结果显示到地图上
            PoiOverlay poiOverlay = new PoiOverlay(getActivity(), mMapView);
            poiOverlay.setData(result.getAllPoi());
            mMapView.getOverlays().clear();
            mMapView.getOverlays().add(poiOverlay);
            mMapView.refresh();
            //当ePoiType为2（公交线路）或4（地铁线路）时， poi坐标为空
            for(MKPoiInfo info : result.getAllPoi() ){
                if ( info.pt != null ){
                    mMapView.getController().animateTo(info.pt);
                    break;
                }
            }
        }

        @Override
        public void onGetTransitRouteResult(MKTransitRouteResult result, int error) {
            Log.d(TAG, "transitroute result" + error);
            //起点或终点有歧义，需要选择具体的城市列表或地址列表
            if (error == MKEvent.ERROR_ROUTE_ADDR) {
                //遍历所有地址
//					ArrayList<MKPoiInfo> stPois = result.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = result.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = result.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = result.getAddrResult().mEndCityList;
                return;
            }
            if (error != 0 || result == null) {
                Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                return;
            }

            searchType = 1;
            transitOverlay = new TransitOverlay(getActivity(), mMapView);
            // 此处仅展示一个方案作为示例
            transitOverlay.setData(result.getPlan(0));
            //清除其他图层
            mMapView.getOverlays().clear();
            //添加路线图层
            mMapView.getOverlays().add(transitOverlay);
            //执行刷新使生效
            mMapView.refresh();
            // 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
            mMapView.getController().zoomToSpan(transitOverlay.getLatSpanE6(), transitOverlay.getLonSpanE6());
            //移动地图到起点
            mMapView.getController().animateTo(result.getStart().pt);
            //重置路线节点索引，节点浏览时使用
            nodeIndex = 0;
            mBtnPre.setVisibility(View.VISIBLE);
            mBtnNext.setVisibility(View.VISIBLE);
        }

        @Override
        public void onGetDrivingRouteResult(MKDrivingRouteResult result, int error) {
            //起点或终点有歧义，需要选择具体的城市列表或地址列表
            if (error == MKEvent.ERROR_ROUTE_ADDR) {
                //遍历所有地址
//					ArrayList<MKPoiInfo> stPois = result.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = result.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = result.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = result.getAddrResult().mEndCityList;
                return;
            }
            // 错误号可参考MKEvent中的定义
            if (error != 0 || result == null) {
                Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                return;
            }

            searchType = 0;
            routeOverlay = new RouteOverlay(getActivity(), mMapView);
            routeOverlay.setData(result.getPlan(0).getRoute(0)); // 此处仅展示一个方案作为示例
            mMapView.getOverlays().clear(); //清除其他图层
            mMapView.getOverlays().add(routeOverlay); //添加路线图层
            mMapView.refresh(); //执行刷新使生效
            mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6()); // 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
            mMapView.getController().animateTo(result.getStart().pt); //移动地图到起点
            route = result.getPlan(0).getRoute(0); //将路线数据保存给全局变量
            nodeIndex = -1; //重置路线节点索引，节点浏览时使用
            mBtnPre.setVisibility(View.VISIBLE);
            mBtnNext.setVisibility(View.VISIBLE);
        }

        @Override
        public void onGetWalkingRouteResult(MKWalkingRouteResult result, int error) {
            //起点或终点有歧义，需要选择具体的城市列表或地址列表
            if (error == MKEvent.ERROR_ROUTE_ADDR) {
                //遍历所有地址
//					ArrayList<MKPoiInfo> stPois = result.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = result.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = result.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = result.getAddrResult().mEndCityList;
                return;
            }
            if (error != 0 || result == null) {
                Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                return;
            }

            searchType = 2;
            routeOverlay = new RouteOverlay(getActivity(), mMapView);
            // 此处仅展示一个方案作为示例
            routeOverlay.setData(result.getPlan(0).getRoute(0));
            //清除其他图层
            mMapView.getOverlays().clear();
            //添加路线图层
            mMapView.getOverlays().add(routeOverlay);
            //执行刷新使生效
            mMapView.refresh();
            // 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
            mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
            //移动地图到起点
            mMapView.getController().animateTo(result.getStart().pt);
            //将路线数据保存给全局变量
            route = result.getPlan(0).getRoute(0);
            //重置路线节点索引，节点浏览时使用
            nodeIndex = -1;
            mBtnPre.setVisibility(View.VISIBLE);
            mBtnNext.setVisibility(View.VISIBLE);

        }

        @Override
        public void onGetAddrResult(MKAddrInfo result, int error) {
            if (error != 0) {
                String str = String.format("错误号：%d", error);
                Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
                return;
            }
            //地图移动到该点
            mMapView.getController().animateTo(result.geoPt);
            if (result.type == MKAddrInfo.MK_GEOCODE) {
                //地理编码：通过地址检索坐标点
                String strInfo = String.format("纬度：%f 经度：%f", result.geoPt.getLatitudeE6()/1e6, result.geoPt.getLongitudeE6()/1e6);
                Log.d(TAG, strInfo);
                Toast.makeText(getActivity(), strInfo, Toast.LENGTH_LONG).show();
            }
            if (result.type == MKAddrInfo.MK_REVERSEGEOCODE) {
                //反地理编码：通过坐标点检索详细地址及周边poi
                String strInfo = result.strAddr;
                Toast.makeText(getActivity(), strInfo, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onGetBusDetailResult(MKBusLineResult mkBusLineResult, int i) {

        }

        @Override
        public void onGetSuggestionResult(MKSuggestionResult mkSuggestionResult, int i) {

        }

        @Override
        public void onGetPoiDetailSearchResult(int i, int i2) {

        }

        @Override
        public void onGetShareUrlResult(MKShareUrlResult mkShareUrlResult, int i, int i2) {

        }
    }

    View.OnClickListener nodeClickListener = new View.OnClickListener(){
        public void onClick(View v) {
            //浏览路线节点
            if (searchType == 0 || searchType == 2){
                //驾车、步行使用的数据结构相同，因此类型为驾车或步行，节点浏览方法相同
                if (nodeIndex < -1 || route == null || nodeIndex >= route.getNumSteps())
                    return;

                //上一个节点
                if (mBtnPre.equals(v) && nodeIndex > 0){
                    //索引减
                    nodeIndex--;
                    //移动到指定索引的坐标
                    mMapView.getController().animateTo(route.getStep(nodeIndex).getPoint());
                    //弹出泡泡
                    popupText.setBackgroundResource(R.drawable.popup);
                    popupText.setText(route.getStep(nodeIndex).getContent());
                    pop.showPopup(BMapUtil.getBitmapFromView(popupText),
                            route.getStep(nodeIndex).getPoint(),
                            5);
                }
                //下一个节点
                if (mBtnNext.equals(v) && nodeIndex < (route.getNumSteps()-1)){
                    //索引加
                    nodeIndex++;
                    //移动到指定索引的坐标
                    mMapView.getController().animateTo(route.getStep(nodeIndex).getPoint());
                    //弹出泡泡
                    popupText.setBackgroundResource(R.drawable.popup);
                    popupText.setText(route.getStep(nodeIndex).getContent());
                    pop.showPopup(BMapUtil.getBitmapFromView(popupText),
                            route.getStep(nodeIndex).getPoint(),
                            5);
                }
            }
            if (searchType == 1){
                //公交换乘使用的数据结构与其他不同，因此单独处理节点浏览
                if (nodeIndex < -1 || transitOverlay == null || nodeIndex >= transitOverlay.getAllItem().size())
                    return;

                //上一个节点
                if (mBtnPre.equals(v) && nodeIndex > 1){
                    //索引减
                    nodeIndex--;
                    //移动到指定索引的坐标
                    mMapView.getController().animateTo(transitOverlay.getItem(nodeIndex).getPoint());
                    //弹出泡泡
                    popupText.setBackgroundResource(R.drawable.popup);
                    popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
                    pop.showPopup(BMapUtil.getBitmapFromView(popupText),
                            transitOverlay.getItem(nodeIndex).getPoint(),
                            5);
                }
                //下一个节点
                if (mBtnNext.equals(v) && nodeIndex < (transitOverlay.getAllItem().size()-2)){
                    //索引加
                    nodeIndex++;
                    //移动到指定索引的坐标
                    mMapView.getController().animateTo(transitOverlay.getItem(nodeIndex).getPoint());
                    //弹出泡泡
                    popupText.setBackgroundResource(R.drawable.popup);
                    popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
                    pop.showPopup(BMapUtil.getBitmapFromView(popupText),
                            transitOverlay.getItem(nodeIndex).getPoint(),
                            5);
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        if(mapManager!=null){
            mapManager.start();
        }
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        if(mapManager!=null){
            mapManager.stop();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.destroy();
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        if(mapManager!=null){
            mapManager.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mMapView.onRestoreInstanceState(savedInstanceState);
    }
}

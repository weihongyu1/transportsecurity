package com.why.transportsecurity_finally.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/**
 * @Description TODO 百度地图逆地址解析
 * @Author why
 * @Date 2021/6/5 16:32
 * Version 1.0
 **/
@Slf4j
public class BaiduMapUtils {

    //应用ak
    private static final String MAP_AK = "pEW1fFdsLBZdKFhUUtmtSgtL4cyAdcxe";
    //接口地址
    public static String MAP_URL = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=" + MAP_AK+"&output=json&coordtype=bd09ll$addressComponent=street_number&level=门址";
    /**
     * 将经纬度获取解析成详细地址
     *
     * @param lng
     *            经度
     * @param lat
     *            纬度
     * @return
     */
    public static String getAddress(double lng, double lat) {
        String address = "";
        String location = lat + "," + lng;
        BufferedReader in = null;
        URL url = null;
        URLConnection connection = null;
        try {
            url = new URL(MAP_URL + "&location=" + location);
            connection = url.openConnection();
            connection.setDoOutput(true);
            in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String line;
            StringBuilder text = new StringBuilder("");
            while ((line = in.readLine()) != null) {
                text.append(line.trim());
            }
            JSONObject result = JSONObject.parseObject(text.toString());
            if (result != null && result.getIntValue("status") == 0) {
                log.debug(String.valueOf(result.getJSONObject("result")));
                address = result.getJSONObject("result").getString("formatted_address") + "-" + result.getJSONObject("result").getString("business");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    /**
     * 将地址解析成经纬度
     *
     * @param address
     *            地址，例：浙江省杭州市西湖区
     * @return 返回经纬度数据。例：{"lng":120.08899292561351,"lat":30.207036169515438}
     */
    public static JSONObject getPosition(String address) {
        BufferedReader in = null;
        URL url = null;
        URLConnection connection = null;
        try {
            url = new URL("http://api.map.baidu.com/geocoding/v3/?address="+address+"&output=json&ak="+MAP_AK+"&callback=showLocation&confidence=100&comprehension=100&level=道路");
            connection = url.openConnection();
            connection.setDoOutput(true);
            in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String line;
            StringBuilder text = new StringBuilder("");
            while ((line = in.readLine()) != null) {
                text.append(line.trim());
            }
            JSONObject result = JSONObject.parseObject(text.toString());
            if (result != null && result.getIntValue("status") == 0) {
                return result.getJSONObject("result").getJSONObject("location");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
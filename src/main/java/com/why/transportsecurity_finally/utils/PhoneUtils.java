package com.why.transportsecurity_finally.utils;


import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import java.io.IOException;

/**
 * @Description TODO 发送短信
 * @Author why
 * @Date 2021/7/28 9:43
 * Version 1.0
 **/
@Slf4j
public class PhoneUtils {

    /**
     * 返送短信
     * @param phoneNumbers 电话号码
     * @param params 短信内容
     */
    public static void sendPhoneMail(String[] phoneNumbers,String[] params){
        // 短信应用 SDK AppID
        int appid = 1400553539; // SDK AppID 以1400开头
// 短信应用 SDK AppKey
        String appkey = "9ae23a3768079d6c637375eb9351a57d";
// 短信模板 ID，需要在短信应用中申请
        int templateId = 1053503; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
// 签名
        String smsSign = "风雪踏梦行"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请

        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                    templateId, params, smsSign, "", "");
            System.out.println(result);
        }  catch (JSONException e) {
            // JSON 解析错误
            log.warn("json解析错误");
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            log.warn("网络IO错误");
            e.printStackTrace();
        } catch (HTTPException e) {
            log.warn("错误");
            e.printStackTrace();
        }
    }
}

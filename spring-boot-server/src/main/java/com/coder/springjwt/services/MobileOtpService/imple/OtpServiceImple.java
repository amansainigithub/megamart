package com.coder.springjwt.services.MobileOtpService.imple;

import com.coder.springjwt.services.MobileOtpService.MobileOtpService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class OtpServiceImple implements MobileOtpService {
    Logger logger  = LoggerFactory.getLogger(OtpServiceImple.class);
    private static String API_KEY = "a4TmdJ0BDoIUu7e5VkzNRiZYbs3rQW928lcyM6vfhg1jwpOCHGvsS6tPlA04H15bIrjpKF8gqdQ2uc7J";
    private static String LANGUAGE = "english";
    private static String ROUTE = "q";

    public  void sendSMS(String otp , String number , String messageContent)
    {
        logger.info("Starting Messaging Service");

        String encodeMessage = OtpServiceImple.encodeMessage(messageContent);
            String smsUrl = "https://www.fast2sms.com/dev/bulkV2?authorization="+API_KEY+"&message="+encodeMessage+"&language="+LANGUAGE+"&route="+ROUTE+"&numbers="+number+"";
        try {
            URL url = new URL(smsUrl);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setRequestProperty("User-Agent","Mozilla/5.0");
            int code = httpsURLConnection.getResponseCode();

            logger.info("Status Code:: " + code );

            StringBuffer  response = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));

            while (true)
            {
                String line = bufferedReader.readLine();
                if(line == null)
                {
                    break;
                }
                else{
                    response.append(line);
                }
            }
            JSONObject jsonObject =  new JSONObject(response.toString());
            String returnResult = jsonObject.get("return").toString();
            String requestId = jsonObject.get("request_id").toString();
            JSONArray jsonArray = jsonObject.getJSONArray ("message");

            logger.info("OTP send Successfully");

        } catch (MalformedURLException e) {
            throw new RuntimeException("Message Not Sent ! Error in FastSMS API " + this.getClass().getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String encodeMessage(String message)
    {
        try {
           return  URLEncoder.encode(message,"UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

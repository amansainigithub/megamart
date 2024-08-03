package com.coder.springjwt.services.MobileOtpService.imple;

import com.coder.springjwt.exception.customerException.PropsNotFoundException;
import com.coder.springjwt.models.props.Api_Props;
import com.coder.springjwt.repository.apiProps.ApiPropsRepository;
import com.coder.springjwt.services.MobileOtpService.MobileOtpService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ApiPropsRepository apiPropsRepository;
    private static String LANGUAGE = "english";
    private static String ROUTE = "q";

    public  void sendSMS(String otp , String number , String messageContent)
    {
        logger.info("Starting Messaging Service");

        Api_Props props = this.apiPropsRepository.findByProvider("FAST2SMS");
        if(props == null)
        {
            logger.error("Props Not Found :" +OtpServiceImple.class.getName());
            throw new PropsNotFoundException("Props Not Found " + "`FAST2SMS`");
        }

        String encodeMessage = OtpServiceImple.encodeMessage(messageContent);
        String smsUrl = "https://www.fast2sms.com/dev/bulkV2?authorization="+ props.getApiKey() +"&message="+encodeMessage+"&language="+LANGUAGE+"&route="+ROUTE+"&numbers="+number+"";

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

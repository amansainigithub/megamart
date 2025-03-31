package com.coder.springjwt.constants.sellerConstants.sellerEmailConstants;

import org.springframework.stereotype.Component;

@Component
public class SellerEmailConstants {


    public static String registrationCompleted()
    {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Registration Successful</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .email-container {\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "            max-width: 600px;\n" +
                "            margin: auto;\n" +
                "        }\n" +
                "        .email-header {\n" +
                "            text-align: center;\n" +
                "            border-bottom: 1px solid #dddddd;\n" +
                "            padding-bottom: 20px;\n" +
                "        }\n" +
                "        .email-header h1 {\n" +
                "            color: #4CAF50;\n" +
                "        }\n" +
                "        .email-body {\n" +
                "            padding-top: 20px;\n" +
                "        }\n" +
                "        .email-body p {\n" +
                "            font-size: 16px;\n" +
                "            color: #333333;\n" +
                "        }\n" +
                "        .email-footer {\n" +
                "            text-align: center;\n" +
                "            font-size: 12px;\n" +
                "            color: #aaaaaa;\n" +
                "            padding-top: 20px;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            margin-top: 20px;\n" +
                "            font-size: 16px;\n" +
                "            color: #ffffff;\n" +
                "            background-color: #4CAF50;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"email-container\">\n" +
                "        <div class=\"email-header\">\n" +
                "            <h1>Registration Successful</h1>\n" +
                "        </div>\n" +
                "        <div class=\"email-body\">\n" +
                "            <p>Dear {{name}},</p>\n" +
                "            <p>Thank you for registering on our platform. We are excited to have you onboard!</p>\n" +
                "            <p>Your registration was successful, and you can now log in to your account using the following link:</p>\n" +
                "            <a href=\"{{loginUrl}}\" class=\"button\">Log In to Your Account</a>\n" +
                "        </div>\n" +
                "        <div class=\"email-footer\">\n" +
                "            <p>If you did not sign up for this account, please ignore this email or contact support.</p>\n" +
                "            <p>&copy; 2024 Your Company. All rights reserved.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }


//    ORDER SHIPPED

    public static String generateOrderShippedEmail(
            String customerName, String orderNumber, String trackingNumber,
            String courierName, String estimatedDeliveryDate, String companyName, String trackingLink) {

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<title>Your Order is On Its Way!</title>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
                ".container { background: #ffffff; padding: 20px; border-radius: 10px; max-width: 600px; margin: auto; }" +
                "h2 { color: #333; }" +
                "p { font-size: 16px; color: #555; }" +
                "a { display: inline-block; padding: 10px 15px; background: #007bff; color: #fff; text-decoration: none; border-radius: 5px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<h2>Your Order is On Its Way! 🎉</h2>" +
                "<p>Hey <strong>" + customerName + "</strong>,</p>" +
                "<p>Your order <strong>#" + orderNumber + "</strong> has officially shipped! We know you're excited, and so are we!</p>" +
                "<p>📦 <strong>Tracking Number:</strong> " + trackingNumber + "</p>" +
                "<p>🚛 <strong>Shipped via:</strong> " + courierName + "</p>" +
                "<p>📍 <strong>Track it here:</strong> <a href='" + trackingLink + "' target='_blank'>Track My Order</a></p>" +
                "<p>Your package is headed your way and should arrive by <strong>" + estimatedDeliveryDate + "</strong>.</p>" +
                "<p>Got any questions? We’re happy to help! Just reply to this email.</p>" +
                "<p><strong>Happy Shopping!<br>" + companyName + "</strong></p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }



}

package com.coder.springjwt.constants.sellerConstants.sellerEmailConstants;

import org.springframework.stereotype.Component;

import java.util.Map;

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

    public static String generateOrderShippedEmail(Map<String, String> data) {
        String template = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Shipped Items Order Delivery</title>\n" +
                "    <style>\n" +
                "        body { \n" +
                "            font-family: Arial, sans-serif; \n" +
                "            margin: 0; \n" +
                "            padding: 20px; \n" +
                "            background: linear-gradient(135deg, #f3f4f6, #e5e7eb); \n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .container { \n" +
                "            max-width: 600px; \n" +
                "            margin: 30px auto; \n" +
                "            background: #fff; \n" +
                "            padding: 20px; \n" +
                "            border-radius: 10px; \n" +
                "            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .text-center { \n" +
                "            text-align: center; \n" +
                "        }\n" +
                "        img { \n" +
                "            border-radius: 50%; \n" +
                "        }\n" +
                "        .table-responsive {\n" +
                "            width: 100%;\n" +
                "            overflow-x: auto;\n" +
                "        }\n" +
                "        table { \n" +
                "            width: 100%; \n" +
                "            border-collapse: collapse; \n" +
                "            margin-top: 20px; \n" +
                "        }\n" +
                "        table, th, td { \n" +
                "            border: 1px solid #ddd; \n" +
                "            padding: 12px; \n" +
                "            text-align: left; \n" +
                "        }\n" +
                "        th { \n" +
                "            background-color: #0029f8; \n" +
                "            color: #fff; \n" +
                "        }\n" +
                "        td { \n" +
                "            background-color: #f9f9f9; \n" +
                "        }\n" +
                "        .btn { \n" +
                "            display: inline-block; \n" +
                "            padding: 12px 18px; \n" +
                "            text-decoration: none; \n" +
                "            background: #0029f8; \n" +
                "            color: #fff; \n" +
                "            border-radius: 6px; \n" +
                "            transition: all 0.3s ease-in-out;\n" +
                "        }\n" +
                "        .btn:hover {\n" +
                "            background: #0029f8;\n" +
                "            box-shadow: 0 4px 10px rgba(0, 41, 248, 0.5); /* Adds a blue shadow */\n" +
                "        }\n" +
                "        @media screen and (max-width: 600px) {\n" +
                "            .container {\n" +
                "                padding: 15px;\n" +
                "            }\n" +
                "            table, th, td {\n" +
                "                font-size: 14px;\n" +
                "            }\n" +
                "            .btn {\n" +
                "                width: 100%;\n" +
                "                text-align: center;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div>\n" +
                "            <img src=\"https://images.unsplash.com/photo-1615915468538-0fbd857888ca?q=80&w=1968&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D\" \n" +
                "            alt=\"Company Logo\" height=\"80\" width=\"80\">\n" +
                "        </div>\n" +
                "        <h2  style=\"color: #0029f8;\">Hello, {customerName}</h2>\n" +
                "        <p>Your order <strong>#{orderNumber}</strong> has officially shipped! \uD83D\uDE9A\uD83C\uDF89</p>\n" +
                "        \n" +
                "        <div class=\"table-responsive\">\n" +
                "            <table>\n" +
                "                <thead style=\"font-size: 14px;background-color: #2a4cf6;color: white;\">\n" +
                "                    <tr>\n" +
                "                        <th>Product</th>\n" +
                "                        <th>Quantity</th>\n" +
                "                        <th>Tracking No</th>\n" +
                "                        <th>Price</th>\n" +
                "                    </tr>\n" +
                "                </thead>\n" +
                "                <tbody>\n" +
                "                    <tr>\n" +
                "                        <td>{productName}</td>\n" +
                "                        <td>{quantity}</td>\n" +
                "                        <td>{trackingNumber}</td>\n" +
                "                        <td>$ {productPrice}</td>\n" +
                "                    </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "\n" +
                "        <p><strong>Estimated Delivery:</strong> {estimatedDeliveryDate} \uD83D\uDCE6</p>\n" +
                "        <p><strong>Courier Partner:</strong> {courierPartner}</p>\n" +
                "        <p><strong>Tracking Number:</strong> {trackingNumber}</p>\n" +
                "        \n" +
                "        <p class=\"text-center\">\n" +
                "            <a href=\"{trackingLink}\" class=\"btn\">Track Your Order</a>\n" +
                "        </p>\n" +
                "\n" +
                "        <p>Thank you for shopping with us! We appreciate your business. \uD83D\uDC99</p>\n" +
                "\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Best Regards,<br><strong>{companyName}</strong></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";

        for (Map.Entry<String, String> entry : data.entrySet()) {
            template = template.replace("{" + entry.getKey() + "}", entry.getValue());
        }

        return template;
    }



}

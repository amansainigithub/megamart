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

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Shipped Items Order Delivery</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 20px;\n" +
                "            background-color: #f4f4f4;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background: #fff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .text-center {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        img {\n" +
                "            border-radius: 10px;\n" +
                "        }\n" +
                "        table {\n" +
                "            width: 100%;\n" +
                "            border-collapse: collapse;\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "        table, th, td {\n" +
                "            border: 1px solid #ddd;\n" +
                "            padding: 8px;\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "        th {\n" +
                "            background-color: #f2f2f2;\n" +
                "        }\n" +
                "        .btn {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 15px;\n" +
                "            text-decoration: none;\n" +
                "            background: #007bff;\n" +
                "            color: #fff;\n" +
                "            border-radius: 5px;\n" +
                "            margin-top: 10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"text-center\">\n" +
                "            <img src=\"https://images.unsplash.com/photo-1518841252147-00cc0a43dcaf?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D\" \n" +
                "            alt=\"Company Logo\" height=\"60\" width=\"60\">\n" +
                "        </div>\n" +
                "        \n" +
                "        <h4 class=\"text-center\">Hello, {customerName}</h4>\n" +
                "        <p>Your order #{orderNumber} has officially shipped! We know you're excited, and so are we!</p>\n" +
                "        \n" +
                "        <table>\n" +
                "            <caption>Order Details</caption>\n" +
                "            <thead>\n" +
                "                <tr>\n" +
                "                    <th>Name</th>\n" +
                "                    <th>Quantity</th>\n" +
                "                    <th>Price</th>\n" +
                "                    <th>Tracking No</th>\n" +
                "                    <th>Total Price</th>\n" +
                "                </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td>{customerName}</td>\n" +
                "                    <td>1</td>\n" +
                "                    <td>1500</td>\n" +
                "                    <td>{trackingNumber}</td>\n" +
                "                    <td>50000</td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "        \n" +
                "        <p>Your package is headed your way and should arrive by {estimatedDeliveryDate}. If you have any questions, just reply to this email.</p>\n" +
                "        \n" +
                "        <p><strong>Shipping Address:</strong><br>\n" +
                "        894/25 Old Chilkana Road Near Shiv Mandir Saharanpur 247001 Uttar Pradesh</p>\n" +
                "        \n" +
                "        <p><strong>Courier Partner:</strong> {companyName}</p>\n" +
                "        \n" +
                "        <p><strong>Tracking Number:</strong> {trackingNumber}</p>\n" +
                "        \n" +
                "        <p>You can track your shipment using the link below:</p>\n" +
                "        <p><a href=\"{trackingLink}\" class=\"btn\">Track Your Order</a></p>\n" +
                "        \n" +
                "        <p>Thank you for shopping with us!</p>\n" +
                "        <p>Best Regards,<br>{companyName}</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
//        return "<!DOCTYPE html>" +
//                "<html>" +
//                "<head>" +
//                "<meta charset='UTF-8'>" +
//                "<title>Your Order is On Its Way!</title>" +
//                "<style>" +
//                "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
//                ".container { background: #ffffff; padding: 20px; border-radius: 10px; max-width: 600px; margin: auto; }" +
//                "h2 { color: #333; }" +
//                "p { font-size: 16px; color: #555; }" +
//                "a { display: inline-block; padding: 10px 15px; background: #007bff; color: #fff; text-decoration: none; border-radius: 5px; }" +
//                "</style>" +
//                "</head>" +
//                "<body>" +
//                "<div class='container'>" +
//                "<h2>Your Order is On Its Way! 🎉</h2>" +
//                "<p>Hey <strong>" + customerName + "</strong>,</p>" +
//                "<p>Your order <strong>#" + orderNumber + "</strong> has officially shipped! We know you're excited, and so are we!</p>" +
//                "<p>📦 <strong>Tracking Number:</strong> " + trackingNumber + "</p>" +
//                "<p>🚛 <strong>Shipped via:</strong> " + courierName + "</p>" +
//                "<p>📍 <strong>Track it here:</strong> <a href='" + trackingLink + "' target='_blank'>Track My Order</a></p>" +
//                "<p>Your package is headed your way and should arrive by <strong>" + estimatedDeliveryDate + "</strong>.</p>" +
//                "<p>Got any questions? We’re happy to help! Just reply to this email.</p>" +
//                "<p><strong>Happy Shopping!<br>" + companyName + "</strong></p>" +
//                "</div>" +
//                "</body>" +
//                "</html>";
    }



    public static String getTestEmail(){
        return "<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <!-- Required meta tags -->\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "\n" +
                "    <!-- Bootstrap CSS -->\n" +
                "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">\n" +
                "\n" +
                "    <title>Hello, world!</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <h1>Hello, world!</h1>\n" +
                "\n" +
                "    <!-- Optional JavaScript; choose one of the two! -->\n" +
                "\n" +
                "    <!-- Option 1: Bootstrap Bundle with Popper -->\n" +
                "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM\" crossorigin=\"anonymous\"></script>\n" +
                "\n" +
                "    <!-- Option 2: Separate Popper and Bootstrap JS -->\n" +
                "    <!--\n" +
                "    <script src=\"https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js\" integrity=\"sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p\" crossorigin=\"anonymous\"></script>\n" +
                "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js\" integrity=\"sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF\" crossorigin=\"anonymous\"></script>\n" +
                "    -->\n" +
                "  </body>\n" +
                "</html>";
    }



}

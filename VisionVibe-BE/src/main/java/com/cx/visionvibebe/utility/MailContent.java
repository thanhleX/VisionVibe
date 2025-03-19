package com.cx.visionvibebe.utility;

public class MailContent {
    public static final String PENDING_SUBJECT = "Order Pending";
    public static final String CONFIRMED_SUBJECT = "Order Confirmed";
    public static final String DENIED_SUBJECT = "Order Denied";

    // Confirmed Email Template
    public static final String HTML_CONFIRMED_CONTENT = """
            <html>
            <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;">
                <div style="display: flex; align-items: center; justify-content: center; min-height: 100vh;">
                    <div style="width: 600px; padding: 20px; border: 1px solid #dddddd; 
                                border-radius: 8px; background-color: #ffffff; 
                                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center;">
                        <h2>Thank you for your purchase!</h2>
                        <h3>Your order has been confirmed.</h3>
                        <p>You will receive your order soon. Please be patient!</p>
                        <p>Your order ID is <strong>%s</strong>. Contact us if you don't receive your order.</p>
                        <p>Click below to view your order:</p>
                        <a href="http://localhost:4200/order/%s" 
                           style="display: inline-block; padding: 10px 20px; background-color: #4CAF50; 
                                  color: #ffffff; text-decoration: none; border-radius: 4px;">
                            View Order
                        </a>
                    </div>
                </div>
            </body>
            </html>
            """;

    // Denied Email Template
    public static final String HTML_DENIED_CONTENT = """
            <html>
            <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;">
                <div style="display: flex; align-items: center; justify-content: center; min-height: 100vh;">
                    <div style="width: 600px; padding: 20px; border: 1px solid #dddddd; 
                                border-radius: 8px; background-color: #ffffff; 
                                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center;">
                        <h2>Thank you for your purchase!</h2>
                        <h3>Your order has been denied.</h3>
                        <p>Something went wrong. Please try again or contact us for more details.</p>
                        <p>Your order ID is <strong>%s</strong>. Contact us if you need assistance.</p>
                        <p>Click below to view your order:</p>
                        <a href="http://localhost:4200/order/%s" 
                           style="display: inline-block; padding: 10px 20px; background-color: #4CAF50; 
                                  color: #ffffff; text-decoration: none; border-radius: 4px;">
                            View Order
                        </a>
                    </div>
                </div>
            </body>
            </html>
            """;

    // Pending Email Template
    public static final String HTML_PENDING_CONTENT = """
            <html>
            <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;">
                <div style="display: flex; align-items: center; justify-content: center; min-height: 100vh;">
                    <div style="width: 600px; padding: 20px; border: 1px solid #dddddd; 
                                border-radius: 8px; background-color: #ffffff; 
                                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center;">
                        <h2>Thank you for your purchase!</h2>
                        <h3>Your order is being processed.</h3>
                        <p>Your order has been sent to our staff. We will contact you shortly.</p>
                        <p>Your order ID is <strong>%s</strong>. Contact us if you need assistance.</p>
                        <p>Click below to view your order:</p>
                        <a href="http://localhost:4200/order/%s" 
                           style="display: inline-block; padding: 10px 20px; background-color: #4CAF50; 
                                  color: #ffffff; text-decoration: none; border-radius: 4px;">
                            View Order
                        </a>
                    </div>
                </div>
            </body>
            </html>
            """;
}
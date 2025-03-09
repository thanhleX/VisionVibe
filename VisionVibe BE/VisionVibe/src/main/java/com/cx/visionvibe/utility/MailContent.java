package com.cx.visionvibe.utility;

public class MailContent {
    public static String pendingSubject = "Order pending";
    public static String confirmedSubject = "Order confirmed";
    public static String deniedSubject = "Order denied";
    public static String htmlConfirmContent = """
            <html>
            <body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;'>
                <div style='display: flex; align-items: center; justify-content: center; min-height: 100vh;'>
                    <div style='width: 600px; padding: 20px; border: 1px solid #dddddd; border-radius: 8px; background-color: #ffffff; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center;'>
                        <h2>THANKS FOR BUY OUR PRODUCTS</h2>
                        <h3>Your order has been confirmed</h3>
                        <p>You will receive your order in the afterlife, please be patient!!!!!</p>
                        <p>Your order ID is <span style='font-weight: bold;'> %s </span>, contact us if you don't receive your order.</p>
                        <p>Click here to see your order:</p>
                        <a href='http://localhost:4200/order/%s' style='display: inline-block; padding: 10px 20px; background-color: #4CAF50; color: #ffffff; text-decoration: none; border-radius: 4px;'>Click here</a>
                    </div>
                </div>
            </body>
            </html>
            """;

    public static String htmlDeniedContent = """
            <html>
            <body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;'>
                <div style='display: flex; align-items: center; justify-content: center; min-height: 100vh;'>
                    <div style='width: 600px; padding: 20px; border: 1px solid #dddddd; border-radius: 8px; background-color: #ffffff; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center;'>
                        <h2>THANK YOU FOR PURCHASING OUR PRODUCTS</h2>
                        <h3>Your order has been denied</h3>
                        <p>Something went wrong. Please place your order again or contact us for details.</p>
                        <p>Your order ID is <span style='font-weight: bold;'> %s </span>. Contact us if you don't receive your order.</p>
                        <p>Click the button below to view your order:</p>
                        <a href='http://localhost:4200/order/%s' 
                            style='display: inline-block; padding: 10px 20px; background-color: #4CAF50; 
                            color: #ffffff; text-decoration: none; border-radius: 4px;'>View Order</a>
                    </div>
                </div>
            </body>
            </html>
            """;

    public static String htmlPendingContent = """
            <html>
            <body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;'>
                <div style='display: flex; align-items: center; justify-content: center; min-height: 100vh;'>
                    <div style='width: 600px; padding: 20px; border: 1px solid #dddddd; border-radius: 8px; background-color: #ffffff; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center;'>
                        <h2>THANK YOU FOR PURCHASING OUR PRODUCTS</h2>
                        <h3>Your order has been sent to our staff. We will contact you soon.</h3>
                        <p>Your order ID is <span style='font-weight: bold;'> %s </span>. Contact us if you don't receive your order.</p>
                        <p>Click the button below to view your order:</p>
                        <a href='http://localhost:4200/order/%s' 
                            style='display: inline-block; padding: 10px 20px; background-color: #4CAF50; 
                            color: #ffffff; text-decoration: none; border-radius: 4px;'>View Order</a>
                    </div>
                </div>
            </body>
            </html>
            """;
}

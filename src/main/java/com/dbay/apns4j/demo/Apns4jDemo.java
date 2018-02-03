package com.dbay.apns4j.demo;

import com.dbay.apns4j.IApnsService;
import com.dbay.apns4j.impl.ApnsServiceImpl;
import com.dbay.apns4j.model.ApnsConfig;
import com.dbay.apns4j.model.Feedback;
import com.dbay.apns4j.model.Payload;

import java.util.List;

/**
 * @author RamosLi
 */
public class Apns4jDemo {
    private static IApnsService apnsService;

    private static IApnsService getApnsService() {
        if (apnsService == null) {
            ApnsConfig config = new ApnsConfig();
//			InputStream is = Apns4jDemo.class.getClassLoader().getResourceAsStream("Certificate.p12");
            config.setKeyStore("/media/alan/Data/ehu社区/推送/ios推送证书/最新/push_station.p12");
            config.setProEnv(true);
            config.setPassword("123456");
            config.setPoolSize(3);
            // 假如需要在同个java进程里给不同APP发送通知，那就需要设置为不同的name
            config.setName("merchant");
            apnsService = ApnsServiceImpl.createInstance(config);
        }
        return apnsService;
    }

    public static void main(String[] args) {
        IApnsService service = getApnsService();
        List<Feedback> feedbacks = service.getFeedbacks();
        if (feedbacks != null && feedbacks.size() > 0) {
            for (Feedback feedback : feedbacks) {
                System.out.println(feedback.getDate() + " " + feedback.getToken());
            }
        }
        // send notification
        String token = "318dd0159d51922fdaeb6bc98efb88cd7b9ff054afbd57bcb808b7673dbfb584";

        Payload payload = new Payload();
        payload.setAlert("How are you?");
        // If this property is absent, the badge is not changed. To remove the badge, set the value of this property to 0
        payload.setBadge(1);
        // set sound null, the music won't be played
//		payload.setSound(null);
//        payload.setSound("orderComing.caf");
        payload.addParam("uid", 123456);
        payload.addParam("type", 12);
        service.sendNotification(token, payload);

        // payload, use loc string
//		Payload payload2 = new Payload();
//		payload2.setBadge(1);
//		payload2.setAlertLocKey("GAME_PLAY_REQUEST_FORMAT");
//		payload2.setAlertLocArgs(new String[]{"Jenna", "Frank"});
//		service.sendNotification(token, payload2);

        // get feedback
        List<Feedback> list = service.getFeedbacks();
        if (list != null && list.size() > 0) {
            for (Feedback feedback : list) {
                System.out.println(feedback.getDate() + " " + feedback.getToken());
            }
        }

//		try {
//			Thread.sleep(5000);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

        // It's a good habit to shutdown what you never use
//		service.shutdown();

//		System.exit(0);
    }
}

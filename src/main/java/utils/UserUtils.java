package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author wang.gs
 * @Date 2019/04/21
 */
public class UserUtils {

    //private static final String PHOTO_URL = "https://linker-pub.oss-cn-hangzhou.aliyuncs.com";

    public static List<String> malePhotoList = new ArrayList<>(8);
    public static List<String> femalePhotoList = new ArrayList<>(4);


    static {
        malePhotoList.add("/photo_def/photo_m_1.png");
        malePhotoList.add("/photo_def/photo_m_2.png");
        malePhotoList.add("/photo_def/photo_m_3.png");
        malePhotoList.add("/photo_def/photo_m_4.png");
        malePhotoList.add("/photo_def/photo_m_5.png");
        malePhotoList.add("/photo_def/photo_m_6.png");
        malePhotoList.add("/photo_def/photo_m_7.png");
        malePhotoList.add("/photo_def/photo_m_8.png");
        malePhotoList.add("/photo_def/photo_m_9.png");

        femalePhotoList.add("/photo_def/photo_f_1.png");
        femalePhotoList.add("/photo_def/photo_f_2.png");
        femalePhotoList.add("/photo_def/photo_f_3.png");
        femalePhotoList.add("/photo_def/photo_f_4.png");
    }

    public static String getRandomPhoto(String gender) {
        String photo;
        if ("女".equals(gender)) {
            photo = femalePhotoList.get(new Random().nextInt(3) + 1);
        } else {
            photo = malePhotoList.get(new Random().nextInt(8) + 1);
        }
        return photo;
    }

    public static String getRandomNickName() {
        return "L_" + getRandonChar() + System.currentTimeMillis() / 1000;
    }

    private static char getRandonChar() {
        Random random = new Random();
        int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
        return (char) (choice + random.nextInt(26));
    }
}

import com.mrli.passwordmanager.dao.UserDao;
import com.mrli.passwordmanager.domain.User;
import com.mrli.passwordmanager.utils.EncryptUtils;
import com.mrli.passwordmanager.utils.MD5Utils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/6/30.
 */
public class TestProject {

    public static ApplicationContext context;

    static {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Autowired
    private UserDao userDao;

    @Test
    public void testDao() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(MD5Utils.md5("123456"));
        userDao.save(user);
    }

    @Test
    public void testAes() throws Exception {
        EncryptUtils encryptUtils = new EncryptUtils();
        encryptUtils.aes();
//        AES aes = new AES();
////   加解密 密钥
////        byte[] keybytes = {0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38};  b59227d86200d7fedfb8418a59a8eea9
//        byte[] keybytes = "b59227d86200d7fee".getBytes();
//        String content = "1";
//        // 加密字符串
//        System.out.println("加密前的：" + content);
//        System.out.println("加密密钥：" + new String(keybytes));
//        // 加密方法
//        byte[] enc = aes.encrypt(content.getBytes(), keybytes);
//        System.out.println("加密后的内容：" + new String(Hex.encode(enc)));
//        // 解密方法
//        byte[] dec = aes.decrypt(enc, keybytes);
//        System.out.println("解密后的内容：" + new String(dec));
    }

}

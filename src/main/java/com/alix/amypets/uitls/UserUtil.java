package com.alix.amypets.uitls;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.service.ex.base.CaptchaException;
import com.alix.amypets.service.ex.user.UserIdentityException;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Component
@Controller
public class UserUtil {

//    public static boolean admin(HttpServletRequest request) throws IOException {
//        // 管理员登录
//        String url = request.getRequestURL().toString();
//        String enc = "123456"; // 管理员身份识别码
//        url = request.getRequestURL().toString().substring(url.lastIndexOf("/") + 1);
//        return url.matches("^(admin)(" + enc + ")*");
//    }


    /**
     * 获取唯一身份编码
     * @param salt 加密盐
     * @param code 附加条件（用户id，也可以不要）
     * @param digit 编码位数（最好64位以内）
     * @return 身份编码
     */
    public static String uniqueCode(String salt,String code,Integer digit) {
        StringBuilder sb = new StringBuilder();
        for (char aChar : salt.toCharArray()) {
            sb.append((int) aChar);
        }
        return sb.delete(0,sb.append(code).length()-digit).toString();
    }

    /**
     * 通过Request获取session中登录的user
     * @param request request请求
     * @return 登录中的用户
     */
    public static User getUserByRequest(HttpServletRequest request){
        return (User) request.getSession().getAttribute("user");
    }

    /**
     * 用户身份校验
     * @param from 被校验元素
     * @param ref 参照物
     */
    public static void idCheck(Object from,Object ref) {
        if (!from.equals(ref))
            throw new UserIdentityException("用户身份异常");
    }

    /**
     * 获取验证码
     * @param request 请求
     * @param response 响应
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            CaptchaUtil.out(request, response);
        } catch (IOException e) {
            // 将原生异常捕捉并重新抛一个自定义的异常
            throw new CaptchaException("验证码获取异常");
        }
    }

    /**
     * 验证码判断
     * @param code 前端用户输入的验证码
     * @param session req请求
     * @return y / n
     */
    public static boolean verify(String code, HttpSession session){
        if (UserUtil.empty(code)) return false;
        // 获取session中的验证码图形中的字符
        String captcha = (String) session.getAttribute("captcha");
        if (UserUtil.empty(captcha)) return false;
        // 进行验证
        return captcha.equals(code);
    }

    /**
     * 初始化新用户
     * @param username 用户名
     * @param password 密码
     * @return 初始化后的user
     */
    public static User init(String username, String password){
        User user = new User();
        user.setUsername(username); // 设置用户名
        user.setPassword(password); // 设置密码
        user.setIsDelete(0); // 设置user为未删除状态
        user.setType(3); // 设置user级别
        user.setAvatar("url-def-avatar"); // 设置默认用户头像
        Date date = new Date();
        user.setUsername(username);
        user.setCreatedTime(date);
        user.setModifier(username);
        user.setModifiedTime(date);
        // 加密 返回
        return black(user);
    }

    /**
     * user pwd初始化加密
     * @param user 要加密pwd的user
     * @return 加密后的user
     */
    private static User black(User user){
        String pwd = user.getPassword();
        // 随机生成一个盐值
        String salt = UUID.randomUUID().toString().toUpperCase(); // 获取一个32位盐值
        user.setSalt(salt);
        user.setPassword(getMd5(pwd,salt));
        return user;
    }

    /**
     * 获取md5密文（32位）
     * @param pwd 原密码明文
     * @param salt 盐值
     * @return 加密后密文
     */
    public static String getMd5(String pwd ,String salt){
        String password = null;
        for (int i = 0; i < 3; i++) { // 加密3次
            password = DigestUtils.md5DigestAsHex((salt + pwd + salt).getBytes());
        }
        return password;
    }

    /**
     * 验证字符非空
     * @param str 要验证的字符
     * @return y / n
     */
    public static boolean empty(String...str){
        for (String s : str) {
            if (s == null || s.equals("")) {
                return true;
            }
        }
        return false;
    }


}

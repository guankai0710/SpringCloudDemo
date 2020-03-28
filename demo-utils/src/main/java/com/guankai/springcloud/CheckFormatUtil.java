package com.guankai.springcloud;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

/**
 * 格式校验工具类
 *
 * @author: guan.kai
 * @date: 2019/8/28 10:45
 **/
public class CheckFormatUtil {

    private CheckFormatUtil(){}

    /**
     * 手机号码正则表达式
     **/
    public static final String PATTERN_MOBILE = "^1[3|4|5|6|7|8|9][0-9]{9}$";
    /**
     * 邮箱号码正则表达式
     **/
    public static final String PATTERN_EMAIL = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 身份证号码正则表达式（18位）
     **/
    public static final String PATTERN_IDCARD = "^[0-9]{17}[0-9Xx]{1}$";

    /**
     * 身份证校验码
     */
    private static final int[] COEFFICIENT_ARRAY = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 身份证号的尾数规则
     */
    private static final String[] IDENTITY_MANTISSA = {"X", "9", "8", "7", "6", "5", "4", "3", "2", "1", "0"};


    /**
     * 密码正则表达式（8-18位字母加数字）
     **/
    public static final String PATTERN_PASSWORD = "^[a-zA-Z0-9]{8,18}$";

    /**
     * 手机号码校验
     *
     * @param mobileNo 手机号
     * @return 是否匹配
     */
    public static boolean isMobile(String mobileNo){
        return (StringUtils.isNotBlank(mobileNo) && mobileNo.matches(PATTERN_MOBILE));
    }

    /**
     * 邮箱地址校验
     *
     * @param email 邮箱地址
     * @return 是否匹配
     */
    public static boolean isEmail(String email){
        return (StringUtils.isNotBlank(email) && email.matches(PATTERN_EMAIL));
    }

    /**
     * 身份证号码校验（仅对18位新居民身份证）
     *
     * @param idCard 身份证号码
     * @return 是否匹配
     */
    public static boolean isIdCard(String idCard){
        if (StringUtils.isBlank(idCard)) {
            return false;
        }
        if (!idCard.matches(PATTERN_IDCARD)) {
            return false;
        }
        char[] chars = idCard.toCharArray();
        long sum = IntStream.range(0, 17).map(index -> {
            char ch = chars[index];
            int digit = Character.digit(ch, 10);
            int coefficient = COEFFICIENT_ARRAY[index];
            return digit * coefficient;
        }).summaryStatistics().getSum();
        // 计算出的尾数索引
        int mantissaIndex = (int) (sum % 11);
        String mantissa = IDENTITY_MANTISSA[mantissaIndex];
        String lastChar = idCard.substring(17);

        return lastChar.equalsIgnoreCase(mantissa);
    }

    /**
     * 密码校验
     *
     * @param password 密码
     * @return 是否匹配
     */
    public static boolean isAccount(String password){
        return (StringUtils.isNotBlank(password) && password.matches(PATTERN_PASSWORD));
    }
}

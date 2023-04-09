package com.elephant.common.constants;

/**
 * @author Tower
 * @description
 * @date 2022/12/1
 */
public interface TokenConstants {
    /**
     * jwt
     */
    public static String JWT_ISS = "cunw";

    /**
     * jwt密钥
     */
    String JWT_SECRET = "dcw#zc#hht#qyj#wh#cb#yl#czz#zzy#dmj#rc#yzh#yc#zyy#nhb#yc#sz#lb@cunw.com.cn&$(YHK!UGHJGS&^%!^&!JBMN!(*^#(*Y!KJ#GH!JGS&^!$%R@TYFVGHVDYTR!&^#RHNVDKAL:!())(123IUH#IU!(*&!&^HK!^*^*()(&*F$$#^^FLKLHFIUEHK)_APQY^#!";

    /**
     * Token使用start
     */
    String AUTH_BEARER_START = "Bearer ";

    /**
     * token策略，-1表示不限，1表示限制一个
     */
    String TOKEN_NO_LIMIT = "-1";

    String TOKEN_ONE_LIMIT = "1";

    String JET_CACHE_REMOTE = "remote";
}

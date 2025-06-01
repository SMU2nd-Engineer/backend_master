package com.culturemoa.cultureMoaProject.common.jwt;

import java.util.List;

/**
 * 환경에 맞춰서 필터 통과할 경로를 추가하기 위한 클래스
 */
public class PrefixFilterPassPaths {

    public static List<String> getPermitPaths(String prefix) {
        return List.of(
                prefix + "/user/login",
                prefix + "/refresh",
                prefix + "/user/logout",
                prefix + "/user/kakaoAuth",
                prefix + "/user/naverAuth",
                prefix + "/user/googleAuth",
                prefix + "/user/registration",
                prefix + "/user/duplicatecheck",
                prefix + "/user/idFind",
                prefix + "/user/passwordFind",
                prefix + "/user/passwordChange",
                prefix + "/logout",
                prefix + "/ws"

        );
    }
}
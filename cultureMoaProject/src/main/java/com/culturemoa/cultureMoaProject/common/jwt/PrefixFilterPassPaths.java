package com.culturemoa.cultureMoaProject.common.jwt;

import java.util.List;

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
                prefix + "/product/upload_img",
                prefix + "/board/board_upload_img",
                "/ws"

        );
    }
}
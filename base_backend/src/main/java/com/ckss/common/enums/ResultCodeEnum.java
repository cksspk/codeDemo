package com.ckss.common.enums;


import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(true,20000,"成功"),
    NOT_ALLOW(false,21004,"不允许操作管理员用户"),
    INVALID_FILE_FORMAT(false,21004,"不允许操作管理员用户"),

    FILE_UPLOAD_ERROR(false, 21004, "文件格式错误"),
    UPLOAD_IMAGE_EXCEPTION(false, 21005, "文件上传异常"),

    CATEGORY_TITLE_NOT_UNIQUE(false, 21005, "分类已存在"),
    TAG_TITLE_NOT_UNIQUE(false, 21005, "分类已存在"),


    UNKNOWN_REASON(false,21004,"失败");

    private Boolean success;
    private Integer code;
    private String message;


    private ResultCodeEnum(Boolean success,Integer code, String message){
        this.success=success;
        this.code = code;
        this.message = message;
    }


}

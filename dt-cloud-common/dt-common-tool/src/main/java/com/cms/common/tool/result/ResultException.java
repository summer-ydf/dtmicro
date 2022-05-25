package com.cms.common.tool.result;

/**
 * @author DT辰白 Created by 2022/1/22 15:16
 */
public class ResultException extends Exception {

    private static final long serialVersionUID = -238091758285157331L;

    private int errCode = 50000;

    private String errMsg;

    public ResultException() {
    }

    public ResultException(String message, Throwable cause) {
        super(message, cause);
        this.errMsg=message;
    }

    public ResultException(String message) {
        super(message);
        this.errMsg=message;
    }

    public ResultException(Throwable cause) {
        super(cause);
    }

    public ResultException(int errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ResultException(int errCode, String errMsg, Throwable cause) {
        super(errCode + ":" + errMsg, cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ResultException(ResultEnum cmsError) {
        super(cmsError.getCode() + ":" + cmsError.getMessage());
        this.errCode = cmsError.getCode();
        this.errMsg = cmsError.getMessage();
    }

    public ResultException(ResultEnum cmsError, String message) {
        super(cmsError.getCode() + ":" + message);
        this.errCode = cmsError.getCode();
        this.errMsg = cmsError.getMessage()+":"+message;
    }

    public ResultException(ResultEnum cmsError, String message, Throwable cause) {
        super(cmsError.getCode() + ":" + message,cause);
        this.errCode = cmsError.getCode();
        this.errMsg = cmsError.getMessage()+":"+message;
    }

    public ResultException(ResultEnum cmsError, Throwable cause) {
        super(cmsError.getCode() + ":" + cmsError.getMessage(), cause);
        this.errCode = cmsError.getCode();
        this.errMsg = cmsError.getMessage();
    }

    public int getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}

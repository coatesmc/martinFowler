package com.coates.tools.excpetion;

public class AssetServiceException extends RuntimeException {

	private static final long serialVersionUID = -1537005231017316882L;
	private String errCode;
	private String errMsg;

	public AssetServiceException() {
		super();
	}

	public AssetServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public AssetServiceException(Throwable cause) {
		super(cause);
	}

	public AssetServiceException(String errCode, String errMsg) {
		super(errCode + ":" + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return this.errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}
}

package org.hyq.hovel.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hyq.hovel.constants.Constants;
import org.hyq.hovel.enums.CodeEnum;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultBean {

	private String code;
	private String msg;
	private Object result;
	private boolean status;
	/**
     * 总的页数
     */
    private Integer total;
    /**
     *  总记录数
     */
    private Long totalCount;

	private ResultBean(String code, String msg, Object result,
                       boolean status) {
		super();
		this.code = code;
		this.msg = msg;
		this.result = result;
		this.status = status;
	}

	public ResultBean(String code, String msg, Object result,
                      boolean status, Integer total, Long totalCount) {
		super();
		this.code = code;
		this.msg = msg;
		this.result = result;
		this.status = status;
		this.total = total;
		this.totalCount = totalCount;
	}

	public ResultBean() {

	}

	public static ResultBean ok() {
		return new ResultBean(CodeEnum.OK.getCode(), Constants.SUCCESS, null, true);
	}
	public static ResultBean ok(Object data){
		return new ResultBean(CodeEnum.OK.getCode(), Constants.SUCCESS, data, true);
	}

	public static ResultBean ok(Object data, Integer total, Long totalCount){
		return new ResultBean(CodeEnum.OK.getCode(), Constants.SUCCESS, data, true, total, totalCount);
	}

	public static ResultBean error(CodeEnum codeEnum, String msg){
		return new ResultBean(codeEnum.getCode(), msg, null, false);
	}

	public static ResultBean error(CodeEnum codeEnum){
		return new ResultBean(codeEnum.getCode(), codeEnum.getMsg(), null, false);
	}

	public static ResultBean error(String msg){
        return new ResultBean(CodeEnum.CUSTOM_ERROR.getCode(), msg, null, false);
    }

	/**
	 * 未登录返回结果
	 */
	public static ResultBean unauthorized() {
		return new ResultBean(CodeEnum.UNAUTHORIZED.getCode(), CodeEnum.UNAUTHORIZED.getMsg(), null, false);
	}
	/**
	 * 未登录返回结果
	 */
	public static ResultBean unauthorized(String msg) {
		return new ResultBean(CodeEnum.UNAUTHORIZED.getCode(), msg, null, false);
	}

	/**
	 * 未授权返回结果
	 */
	public static ResultBean forbidden() {
		return new ResultBean(CodeEnum.FORBIDDEN.getCode(), CodeEnum.FORBIDDEN.getMsg(), null, false);
	}
	/**
	 * 未授权返回结果
	 */
	public static ResultBean forbidden(String msg) {
		return new ResultBean(CodeEnum.FORBIDDEN.getCode(), msg, null, false);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

    @Override
    public String toString() {
        if(result instanceof List){
            return "ResultBean [code=" + code + ", msg=" + msg + ", status=" + status
                    + ", total=" + total + ", totalCount=" + totalCount + "]";
        }
        return "ResultBean [code=" + code + ", msg=" + msg + ", result=" + result + ", status=" + status
                + ", total=" + total + ", totalCount=" + totalCount + "]";
    }


}

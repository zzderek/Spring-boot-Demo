package com.y2t.akeso.common.api;

 /**
 * @author ZiTung
 * @description: 封装API的错误码
 * @date 2020/4/2 14:54
 */
public interface IErrorCode {
    String SUCCESS ="SUCCESSS";

    String FAIL ="FAIL";

    String OK ="OK";

    String SERVER_ERROR = "500";

    /**
     * 返回”返回信息“

     * @return 返回信息
     */
    String getReturnMessage();

    /**
     * 返回错误码
     *
     * @return 错误码
     */
    String getErrorCode();



}

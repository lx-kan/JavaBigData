package cn.highedu.nybike.common.pojo;


import cn.highedu.nybike.common.exception.ServiceCode;
import cn.highedu.nybike.common.exception.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果类型
 */
@Data
public class JsonResult<T> implements Serializable {

    private Integer code;
    private String message;
    private T data; // E > Element / K > Key / V > Value / T > Type

    public static JsonResult<Void> ok() {
        return ok(null);
    }

    public static <T> JsonResult<T> ok(T data) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(ServiceCode.OK.getValue());
        jsonResult.setData(data);
        jsonResult.message = "OK";
        return jsonResult;
    }

    public static JsonResult<Void> fail(ServiceException e) {
        return fail(e.getServiceCode(), e.getMessage());
    }

    public static JsonResult<Void> fail(ServiceCode serviceCode, String message) {
        JsonResult<Void> jsonResult = new JsonResult<>();
        jsonResult.setCode(serviceCode.getValue());
        jsonResult.setMessage(message);
        return jsonResult;
    }

    /**
     * 将当前对象转换为JSON字符串
     * @return 当前对象的JSON字符串表示
     */
    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

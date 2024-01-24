package cn.highedu.nybike.common.exception;
import lombok.Getter;
/**
 * 业务异常
 * 是异常的基础类
 * @author HighEDU
 * @version 0.0.1
 */
public class ServiceException extends RuntimeException{
    /**
     * 业务状态码
     */
    @Getter
    private ServiceCode serviceCode;

    public ServiceException(ServiceCode serviceCode, String message) {
        super(message);
        this.serviceCode = serviceCode;
    }

}

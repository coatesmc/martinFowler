package com.coates.helloservice.exception;

/**
 *
 * 自定义接口业务繁忙异常
 * @author huang_kangjie
 * @create 2018-06-09 10:31
 **/
public class InterfaceBusyException extends RuntimeException
{

     public InterfaceBusyException(String message)
     {
          super(message);
     }

     public InterfaceBusyException(Throwable cause)
     {
          super(cause);
     }


     public InterfaceBusyException(String message, Throwable cause)
     {
          super(message, cause);
     }
}

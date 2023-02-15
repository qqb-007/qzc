package info.batcloud.wxc.core.entity;/*
一个常见的做法是自定义一个BaseException作为“根异常”，然后，派生出各种业务类型的异常。
BaseException需要从一个适合的Exception派生，通常建议从RuntimeException派生：
*/

//自定义的BaseException应该提供多个构造方法：
public class BaseException extends RuntimeException {
    public BaseException() {
        super();
    }
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
    public BaseException(String message) {
        super(message);
    }
    public BaseException(Throwable cause) {
        super(cause);
    }
}
//上述构造方法实际上都是原样照抄RuntimeException。这样，抛出异常的时候，就可以选择合适的构造方法。通过IDE可以根据父类快速生成子类的构造方法。
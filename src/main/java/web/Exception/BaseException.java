package web.Exception;

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BaseException() {
        // TODO Auto-generated constructor stub
    }

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, Exception ex) {
        super(msg, ex);
    }

}

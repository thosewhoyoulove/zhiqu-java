package web.Utils;

import web.Entry.Result;

public class ResultUtils<T> {

    private Result<T> result;

    public ResultUtils(){
        result = new Result<>();
        result.setSuccess(true);
        result.setMessage("success");
        result.setCode(200);
    }

    public Result<T> setData(T t){
        this.result.setData(t);
        this.result.setCode(200);
        return this.result;
    }

    public Result<T> setSuccessMsg(String msg){
        this.result.setSuccess(true);
        this.result.setMessage(msg);
        this.result.setCode(200);
        this.result.setData(null);
        return this.result;
    }

    public Result<T> setData(T t, String msg){
        this.result.setData(t);
        this.result.setCode(200);
        this.result.setMessage(msg);
        return this.result;
    }

    public Result<T> setData(T t, String token, String msg){
        this.result.setData(t);
        this.result.setCode(200);
        this.result.setMessage(msg);
        this.result.setToken(token);
        return this.result;
    }


    public Result<T> setErrorMsg(String msg){
        this.result.setSuccess(false);
        this.result.setMessage(msg);
        this.result.setCode(500);
        return this.result;
    }

    public Result<T> setErrorMsg(Integer code, String msg){
        this.result.setSuccess(false);
        this.result.setMessage(msg);
        this.result.setCode(code);
        return this.result;
    }

    public static <T> Result<T> data(T t){
        return new ResultUtils<T>().setData(t);
    }

    public static <T> Result<T> data(T t, String msg){
        return new ResultUtils<T>().setData(t, msg);
    }
    public static <T> Result<T> data(T t, String token, String msg){
        return new ResultUtils<T>().setData(t, token, msg);
    }

    public static <T> Result<T> success(String msg){
        return new ResultUtils<T>().setSuccessMsg(msg);
    }

    public static <T> Result<T> error(String msg){
        return new ResultUtils<T>().setErrorMsg(msg);
    }

    public static <T> Result<T> error(Integer code, String msg){
        return new ResultUtils<T>().setErrorMsg(code, msg);
    }

}

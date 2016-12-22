package app.com.hawker.Models;

/**
 * Created by Administrator on 6/4/2016.
 */
public class BaseResponse<T> {
    private T data;
    private boolean isSuccess;
    private String message;

    public boolean getStatus() {
        return isSuccess;
    }

    public void setStatus(boolean status) {
        this.isSuccess = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

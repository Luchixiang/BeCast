package library.common.http.result;

import java.io.Serializable;

public class BaseResult<T> implements Serializable {
    //统一结果请求
    private int requestCode;
    private T data;
    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

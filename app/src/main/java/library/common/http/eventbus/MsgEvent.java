package library.common.http.eventbus;

import android.os.Bundle;

public class MsgEvent {
    private String msg;
    private int code;
    private Bundle bundle = new Bundle();

    public MsgEvent() {
    }

    public MsgEvent(int code,String msg) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}

package library.common.http;

public class Statues {
    //网络状况参数
    public static final int NET_CODE_SUCCESS = 0;
    public static final int NET_CODE_ERROR = 1;

    public static final int NET_CODE_CONNECT = 400;
    public static final int NET_CODE_UNKNOWN_HOST = 401;
    public static final int NET_CODE_SOCKET_TIMEOUT = 402;

    public static final String CONNECT_EXCEPTION = "网络连接异常，请检查您的网络状态";
    public static final String SOCKET_TIMEOUT_EXCEPTION = "网络连接超时，请检查您的网络状态，稍后重试";
    public static final String UNKNOWN_HOST_EXCEPTION = "与服务器连接失败";
    public static final String EMPTY_RESPONSE_EXCEPTION = "无效的返回";
}

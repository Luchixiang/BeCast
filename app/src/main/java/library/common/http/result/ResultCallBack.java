package library.common.http.result;

import library.common.http.Statues;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ResultCallBack<T> implements Callback<Result<T>> {
    @Override
    public void onResponse(Call<Result<T>> call, Response<Result<T>> response) {
        if (response!=null&&response.body()!=null)
        {
            if (response.body().getCode()== Statues.NET_CODE_SUCCESS)
            {handleResult(true,new Throwable(response.body().getMsg()),null);}
            else
                handleResult(false,new Throwable(response.body().getMsg()),null);
        }
    else handleResult(false,new Throwable(Statues.EMPTY_RESPONSE_EXCEPTION),null);
    }

    @Override
    public void onFailure(Call<Result<T>> call, Throwable t) {
        handleResult(false, t, null);

    }
    public abstract void handleResult(boolean success,Throwable throwable,T T);
}
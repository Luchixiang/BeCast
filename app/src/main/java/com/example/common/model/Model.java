package com.example.common.model;

import android.app.Application;

import com.example.common.single.Single;

public class Model {
    private static Model INSTANCE = null;
    private static BecastResoprity becastResoprity;

    private Model(Application application) {
    }
    public static Model getInstance(Application application)
    {
        if (INSTANCE!=null)
        {
            return INSTANCE;
        }else {
            INSTANCE = new Model(application);
            becastResoprity = new BecastResoprity(application);
            return INSTANCE;
        }
    }
    public void addHistory( Single single)
    {
        becastResoprity.insertSingle(single);
    }

    public void getSingleList(BecastResoprity.HistoryCallBack historyCallBack)
    {
        becastResoprity.getHistoryList(historyCallBack);
    }
    public void stopModel()
    {
        becastResoprity.stopRespority();
    }
}

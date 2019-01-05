package com.example.common.model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.common.single.Single;
import com.example.common.single.SingleDao;

import java.util.ArrayList;
import java.util.List;

public class BecastResoprity {
    private final SingleDao singleDao;
    private final List<AsyncTask> asyncTasks = new ArrayList<>();
    private static BecastResoprity INSTANCE = null;
    private static final String TAG = "luchixiang";

    //得到仓库实例
   public BecastResoprity(Application application) {
        BecastDataBase becastDataBase = BecastDataBase.getDatabase(application);
        singleDao = becastDataBase.singleDao();
    }

    public static BecastResoprity getINSTANCE(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new BecastResoprity(application);
        }
        return INSTANCE;
    }

    //添加历史
    public void insertSingle(Single single) {
        InsertFavoriteAsynTask insertFavoriteAsynTask = new InsertFavoriteAsynTask(singleDao);
        asyncTasks.add(insertFavoriteAsynTask);
        insertFavoriteAsynTask.execute(single);
    }

    private static class InsertFavoriteAsynTask extends AsyncTask<Single, Void, Void> {
        private final SingleDao singleDao;

        InsertFavoriteAsynTask(SingleDao singleDao) {
            this.singleDao = singleDao;
        }

        @Override
        protected Void doInBackground(Single... singles) {
            singleDao.addHistory(singles[0]);
            Log.d(TAG, "add "+singles[0].getTitle());
            return null;
        }
    }

    //得到历史
    public void getHistoryList(HistoryCallBack historyCallBack) {
        getHistoryTask getHistoryTask = new getHistoryTask(singleDao,historyCallBack);
        asyncTasks.add(getHistoryTask);
        getHistoryTask.execute();
    }

    private static class getHistoryTask extends AsyncTask<Void, Void, List<Single>> {
        private final SingleDao singleDao;
        private List<Single> singles = new ArrayList<>();
        private final HistoryCallBack historyCallBack;

        getHistoryTask(SingleDao singleDao,HistoryCallBack historyCallBack) {
            this.singleDao = singleDao;
            this.historyCallBack = historyCallBack;
        }

        @Override
        protected List<Single> doInBackground(Void... voids) {
            this.singles = singleDao.getAllHistory();
            return singles;
        }

        @Override
        protected void onPostExecute(List<Single> singleList) {
            historyCallBack.getHistoryList(singleList);
            super.onPostExecute(singleList);
        }
    }
    public void stopRespority()
    {
        for (AsyncTask asyncTask: asyncTasks)
        {
            asyncTask.cancel(true);
        }
    }
    public interface HistoryCallBack{
       void getHistoryList(List<Single> singleList);
    }
}

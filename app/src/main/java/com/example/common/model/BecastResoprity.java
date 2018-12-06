//package com.example.common.model;
//
//import android.app.Application;
//import android.os.AsyncTask;
//
//import com.example.common.tour.Classify;
//import com.example.common.tour.ClassifyDao;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BecastResoprity {
//    private ClassifyDao classifyDao;
//    private List<AsyncTask> asyncTasks;
//    private static BecastResoprity INSTANCE = null;
//    private static List<Classify> classifyList = new ArrayList<>();
//
//    //得到仓库实例
//    private BecastResoprity(Application application) {
//        BecastDataBase becastDataBase = BecastDataBase.getDatabase(application);
//        classifyDao = becastDataBase.classifyDao();
//    }
//
//    public static BecastResoprity getINSTANCE(Application application) {
//        if (INSTANCE == null) {
//            INSTANCE = new BecastResoprity(application);
//        }
//        return INSTANCE;
//    }
//
//    //收藏专集
//    public void insertClassify(Classify classify) {
//        InsertFavoriteAsynTask insertFavoriteAsynTask = new InsertFavoriteAsynTask(classifyDao);
//        asyncTasks.add(insertFavoriteAsynTask);
//        insertFavoriteAsynTask.execute(classify);
//    }
//
//    private static class InsertFavoriteAsynTask extends AsyncTask<Classify, Void, Void> {
//        private ClassifyDao classifyDao;
//
//        InsertFavoriteAsynTask(ClassifyDao classifyDao) {
//            this.classifyDao = classifyDao;
//        }
//
//        @Override
//        protected Void doInBackground(Classify... classifies) {
//            classifyDao.insertClass(classifies[0]);
//            return null;
//        }
//    }
//
//    //得到收藏的专辑
//    public List<Classify> getFavoriteClassify() {
//        List<Classify> list = new ArrayList<>();
//        GetFavoriteClassifyAsy getFavoriteClassifyAsy = new GetFavoriteClassifyAsy(classifyDao);
//        asyncTasks.add(getFavoriteClassifyAsy);
//        getFavoriteClassifyAsy.execute();
//        return classifyList;
//    }
//
//    private static class GetFavoriteClassifyAsy extends AsyncTask<Void, Void, List<Classify>> {
//        private ClassifyDao classifyDao;
//        private List<Classify> classifies = new ArrayList<>();
//
//        GetFavoriteClassifyAsy(ClassifyDao classifyDao) {
//            this.classifyDao = classifyDao;
//        }
//
//        @Override
//        protected List<Classify> doInBackground(Void... voids) {
//            classifies = classifyDao.getFavorite();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(List<Classify> classifies) {
//            classifyList = classifies;
//            super.onPostExecute(classifies);
//        }
//    }
//}

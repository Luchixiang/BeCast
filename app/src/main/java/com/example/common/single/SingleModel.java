package com.example.common.single;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SingleModel {
    private SingleView singleView;
    private List<Single> singleList = new ArrayList<>();
    private String albumTitle;
    private String albumDescription;
    private static final int ALBUMTITLE = 0x0000001;
    private SingleHandler singleHandler;
    private Element channelElement = null;
    private ThreadPoolExecutor poolExecutor;
    private Runnable runnable;
    SingleModel(SingleView singleView) {
        this.singleView = singleView;
        singleHandler = new SingleHandler(Looper.getMainLooper(),this);
        poolExecutor = new ThreadPoolExecutor(3, 5,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10));
    }
    public void startXml(String feedUrl,int end, int start) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(feedUrl).build();
        runnable = () -> {
            Response response;
            singleList.clear();
            try {
                if (start==0){
                    response = client.newCall(request).execute();
                    assert response != null;
                    assert response.body() != null;
                    Log.d("luchixiangg", "startXml: "+feedUrl);
                    String data = Objects.requireNonNull(response.body()).string();
                    SAXReader reader = new SAXReader();
                    Document document;
                    document = reader.read(new ByteArrayInputStream(data.getBytes("UTF-8")));
                    Element rootElement = document.getRootElement();
                    channelElement = rootElement.element("channel");
                    albumTitle = channelElement.element("title").getText();
                    albumDescription = channelElement.element("description").getText();}
                List itemElement = channelElement.elements("item");
                int size = itemElement.size()>=end? end:itemElement.size();
                for (int i = start; i < size; i++) {
                    Element element = (Element) itemElement.get(i);
                    String songTitle = element.element("title").getText();
                    String time = element.element("duration").getText();
                    String updataTime = element.element("pubDate").getText();
                    String voiceUrl = element.element("enclosure").attributeValue("url");
                    Single single = new Single(songTitle, updataTime, voiceUrl,"");
                    single.setTime(time);
                    singleList.add(single);
                }
                Message message = new Message();
                message.what = ALBUMTITLE;
                singleHandler.sendMessage(message);
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        };
        poolExecutor.execute(runnable);
    }
    public static class SingleHandler extends Handler {
        private WeakReference<SingleModel> singleViewWeakReference;
        SingleHandler(Looper looper, SingleModel singleModel)
        {
            super(looper);
            singleViewWeakReference = new WeakReference<>(singleModel);
        }
        @Override
        public void handleMessage( Message msg) {
            SingleModel singleModel = singleViewWeakReference.get();
            switch (msg.what)
            {
                case ALBUMTITLE:
                singleModel.singleView.setTitle(singleModel.albumTitle);
                singleModel.singleView.setDescription(singleModel.albumDescription);
                singleModel.singleView.getList(singleModel.singleList);
                break;
            }
        }
    }
    public void stop()
    {
        singleHandler.removeMessages(ALBUMTITLE);
        poolExecutor.remove(runnable);
    }
}

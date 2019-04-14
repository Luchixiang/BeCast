package com.example.common.single;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.common.utils.ChangeTime;
import com.lzx.starrysky.model.SongInfo;

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

class SingleModel {
    private final SingleView singleView;
    private final List<Single> singleList = new ArrayList<>();
    private String albumTitle = "";
    private String albumDescription = "";
    private static final int ERROR = 0x000002;
    private static final int ALBUMTITLE = 0x0000001;
    private final SingleHandler singleHandler;
    private Element channelElement = null;
    private final ThreadPoolExecutor poolExecutor;
    private Runnable runnable;
    private int id = 0;

    SingleModel(SingleView singleView) {
        this.singleView = singleView;
        singleHandler = new SingleHandler(Looper.getMainLooper(), this);
        poolExecutor = new ThreadPoolExecutor(3, 5,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10));
    }

    public void startXml(String feedUrl, int end, int start) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(feedUrl).build();
        runnable = () -> {
            Response response;
            singleList.clear();
            try {
                if (start == 0) {
                    response = client.newCall(request).execute();
                    Log.d("luchixiangg", "startXml: " + feedUrl);
                    String data = Objects.requireNonNull(response.body()).string();
                    SAXReader reader = new SAXReader();
                    Document document;
                    document = reader.read(new ByteArrayInputStream(data.getBytes("UTF-8")));
                    Element rootElement = document.getRootElement();
                    channelElement = rootElement.element("channel");
                }
                if (channelElement != null) {
                    Element titleElement = channelElement.element("title");
                    if (titleElement != null) albumTitle = titleElement.getText();
                    titleElement = channelElement.element("description");
                    if (titleElement != null) albumDescription = titleElement.getText();
                    List itemElement = channelElement.elements("item");
                    int size = itemElement.size() >= end ? end : itemElement.size();
                    for (int i = start; i < size; i++) {
                        id = start + i;
                        Element element = (Element) itemElement.get(i);
                        Element subElement;
                        subElement = element.element("title");
                        String songTitle = "";
                        String time = "";
                        String updataTime;
                        String realTime = "";
                        String voiceUrl = "";
                        if (subElement != null) songTitle = subElement.getText();
                        subElement = element.element("duration");
                        if (subElement != null) time = subElement.getText();
                        subElement = element.element("pubDate");
                        if (subElement != null) {
                            updataTime = subElement.getText();
                            realTime = updataTime.substring(0, updataTime.indexOf("201") + 4);
                        }
                        subElement = element.element("enclosure");
                        if (subElement != null)
                            voiceUrl = element.element("enclosure").attributeValue("url");
                        subElement = element.element("image");
//                        Single single = new Single(songTitle, realTime, voiceUrl, "");
                        SongInfo single = new SongInfo();
                        single.setSongName(songTitle);
                        single.setPublishTime(realTime);
                        single.setSongUrl(voiceUrl);
                        if (subElement != null) {
                            String imgUrl = subElement.attributeValue("href");
                            single.setSongCover(imgUrl);
                        }
                        single.setDuration(ChangeTime.changTimeintoLong(time));
                        single.setSongId(String.valueOf(id));
                        Single single1 = new Single();
                        single1.changeFromSongInfo(single);
                        singleList.add(single1);
                    }
                    Message message = new Message();
                    message.what = ALBUMTITLE;
                    singleHandler.sendMessage(message);
                }
            } catch (IOException | DocumentException e) {
                Message message = new Message();
                message.what = ERROR;
                singleHandler.sendMessage(message);
            }
        };
        poolExecutor.execute(runnable);
    }

    static class SingleHandler extends Handler {
        private final WeakReference<SingleModel> singleViewWeakReference;

        SingleHandler(Looper looper, SingleModel singleModel) {
            super(looper);
            singleViewWeakReference = new WeakReference<>(singleModel);
        }

        @Override
        public void handleMessage(Message msg) {
            SingleModel singleModel = singleViewWeakReference.get();
            switch (msg.what) {
                case ALBUMTITLE:
                    singleModel.singleView.setTitle(singleModel.albumTitle);
                    singleModel.singleView.setDescription(singleModel.albumDescription);
                    singleModel.singleView.getList(singleModel.singleList);
                    break;
                case ERROR:
                    singleModel.singleView.Error();
                    break;
            }
        }
    }

    public void stop() {
        singleHandler.removeMessages(ALBUMTITLE);
        singleHandler.removeMessages(ERROR);
        poolExecutor.remove(runnable);
    }
}

package com.example.common.single;

import java.util.List;

public interface SingleView {
    void getList(List<Single> singleList);
    void initView();
    void setTitle(String title);
    void setDescription(String description);
    void setImage(String url);
}

package com.example.common.single;

import java.util.List;

interface SingleView {
    void getList(List<Single> singleList);
    void initView();
    void setTitle(String title);
    void setDescription(String description);
    void Error();
}

package com.example.team_project_final;

public interface item {

    void setitem(int year, int month, int index, int page);

    void setyear(int year);

    void setmonth(int month);

    void setpremonth(int premonth);

    void setsize(int size);

    void setdate(int date);

    void settime(int time);

    void setprepage(int prepage);

    void setkey(int key);

    int getyear();

    int getmonth();

    int getindex();

    int getsize();

    int getprepage();

    int getpremonth();

}

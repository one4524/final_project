package com.example.team_project_final;

public interface item {

    void setitem(int year, int month, int index, int page);

    void setindex(int index);

    void setyear(int year);

    void setMonth(int month);

    void setpage(int page);

    void setsize(int size);

    void setdate(int date);

    void settime(int time);

    void setprepage(int prepage);

    void setkey(int key);

    int getyear();

    int getmonth();

    int getindex();

    int getpage();

    int getsize();

    int getprepage();

    int getdate();

    int gettime();

    int getkey();
}

package com.taobao.geek.demo.unixtime;

import java.util.Date;

/**
 * User: kyle
 * Date: 13-11-20
 * Time: PM3:33
 */
public class UnixTime {


    private  final int value;

    public  UnixTime() {
        this((int)(System.currentTimeMillis()/1000L + 2208988800L));

    }

    public  UnixTime(int value) {
        this.value =value;
    }


    public  int value() {
        return value;
    }


    @Override
    public String toString()  {

        return  new Date((value() - 2208988800L)*1000L).toString();
    }

}

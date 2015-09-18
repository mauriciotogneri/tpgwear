package com.mauriciotogneri.common.model;

import android.text.TextUtils;

import com.mauriciotogneri.common.utils.JsonUtils;

import java.util.ArrayList;

public class BusStopList extends ArrayList<BusStop>
{
    public static BusStopList fromString(String input)
    {
        return JsonUtils.fromJson(input, BusStopList.class);
    }

    public BusStop getByName(String name)
    {
        for (BusStop busStop : this)
        {
            if (TextUtils.equals(busStop.getName(), name))
            {
                return busStop;
            }
        }

        return null;
    }

    @Override
    public String toString()
    {
        return JsonUtils.toJson(this);
    }
}
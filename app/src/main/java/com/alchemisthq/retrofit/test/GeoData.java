package com.alchemisthq.retrofit.test;

import com.google.gson.annotations.SerializedName;

/**
 * Created by laaptu on 3/23/15.
 */

public class GeoData {
    @SerializedName("longitude")
    public double longitude;
    @SerializedName("latitude")
    public double latitude;
    @SerializedName("asn")
    public String asn;
    @SerializedName("ip")
    public String ip;
    @SerializedName("isp")
    public String isp;
}

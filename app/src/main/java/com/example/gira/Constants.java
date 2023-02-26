package com.example.gira;

public class Constants {
    private static boolean mapaGeneral = false;
    private static double lat;
    private static double lng;
    private static String nombreGps;

    public static double getLat() {
        return lat;
    }

    public static void setLat(double lat) {
        Constants.lat = lat;
    }

    public static double getLng() {
        return lng;
    }

    public static void setLng(double lng) {
        Constants.lng = lng;
    }

    public static boolean isMapaGeneral() {
        return mapaGeneral;
    }

    public static void setMapaGeneral(boolean mapaGeneral) {
        Constants.mapaGeneral = mapaGeneral;
    }

    public static String getNombreGps() {
        return nombreGps;
    }

    public static void setNombreGps(String nombreGps) {
        Constants.nombreGps = nombreGps;
    }
}

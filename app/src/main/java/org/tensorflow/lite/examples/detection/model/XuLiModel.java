package org.tensorflow.lite.examples.detection.model;

import org.tensorflow.lite.examples.detection.DetectorActivity;

public class XuLiModel {
    //User Check Out
    public static String formattedTimeCheckInOut="";


    //User Check In
    public static String formattedTimeCheckOutIn="";

//    public static String nameUser="";

    public static String namel="";



    public static String getNamel() {
        return namel;
    }

    public static void setNamel(String namel) {
        XuLiModel.namel = namel;
    }

//    public static String getNameUser() {
//        return nameUser;
//    }
//
//    public static void setNameUser(String nameUser) {
//        XuLiModel.nameUser = nameUser;
//    }

    public static String getFormattedTimeCheckInOut() {
        return formattedTimeCheckInOut;
    }

    public static void setFormattedTimeCheckInOut(String formattedTimeCheckInOut) {
        XuLiModel.formattedTimeCheckInOut = formattedTimeCheckInOut;
    }

    public static String getFormattedTimeCheckOutIn() {
        return formattedTimeCheckOutIn;
    }

    public static void setFormattedTimeCheckOutIn(String formattedTimeCheckOutIn) {
        XuLiModel.formattedTimeCheckOutIn = formattedTimeCheckOutIn;
    }
}

package com.zlcdgroup.camera;



public class CameraPreferences {
    
    public static final String KEY_AUTO_FOCUS = "preferences_auto_focus";

    public static final String KEY_VOLUME="preferences_volume";

    public static final String KEY_SCREEN ="preferences_screen";

    public static final String KEY_GUIDE="preferences_gride";

    public static final String KEY_GUIDE_TOP="preferences_gride_top";

    public static final String KEY_GUIDE_LEFT="preferences_gride_left";
    
    public static final String KEY_DISABLE_AUTO_ORIENTATION = "preferences_orientation";
    
    public static final String KEY_DISABLE_CONTINUOUS_FOCUS = "preferences_disable_continuous_focus";
    
    public static final String KEY_INVERT_SCAN = "preferences_invert_scan"; 
    
    public static final String KEY_DISABLE_BARCODE_SCENE_MODE = "preferences_disable_barcode_scene_mode";
    
    public static final String KEY_DISABLE_METERING = "preferences_disable_metering";
    
    public static final String KEY_DISABLE_EXPOSURE = "preferences_disable_exposure";
    
    public static final String KEY_FRONT_LIGHT_MODE = "preferences_front_light_mode";


    
    public static final String KEY_VIBRATE = "preferences_vibrate";
    
    public static final String KEY_PLAY_BEEP = "preferences_play_beep";
    
    public static final String KEY_DECODE_1D_PRODUCT = "preferences_decode_1D_product";
    public static final String KEY_DECODE_1D_INDUSTRIAL = "preferences_decode_1D_industrial";
    public static final String KEY_DECODE_QR = "preferences_decode_QR";
    public static final String KEY_DECODE_DATA_MATRIX = "preferences_decode_Data_Matrix";
    public static final String KEY_DECODE_AZTEC = "preferences_decode_Aztec";
    public static final String KEY_DECODE_PDF417 = "preferences_decode_PDF417";
    
    public static final String KEY_SEARCH_COUNTRY = "preferences_search_country";
    
    
    //拍照成功
    public   static final  int  TACKPIC_RESULT_DATA_OK = 1;
    //拍照失败
    public   static final  int  TACKPIC_RESULT_DATA_NO = 2;
    
    //拍照原始图片保存成功
    public   static final  int  TACKPIC_RESULT_VIEWBASEIMG_OK=3;
    //拍照原始图片转换失败
    public   static final  int  TACKPIC_RESULT_VIEWBASEIMG_ERROR = 4;
    
    //拍照16：9 图片转换成功
    public   static final  int  TACKPIC_RESULT_VIEWIMG_OK = 5;
    
    //拍照16：9 图片转换失败
    public   static final  int  TACKPIC_RESULT_VIEWIMG_ERROR = 6;
    
    public   static final  int  RESET_CAMERA=7;

    public   static final  int  TACKPIC_RESULT_TEMP=8;
}

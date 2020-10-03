package app.kurosaki.developer.vegidel.interfaces;

public interface Constants {

    String baseURL = "http://3.129.114.201:3000/api/";



    String ANDROID = "ANDROID";
    String APP = "VEGIDEL";
    String DEVICE_TYPE = ANDROID;

    String MALE = "Male";
    String FEMALE = "Female";
    String DLAT = "DLAT";
    String DLONG = "DLONG";
    String RESTAURANTID = "RESTAURANT_ID";
    String OFFERID = "OFFERID";
    int PASSWORD_LENGTH = 8;
    //shared preferences variable
    String IS_USER_LOGGED_IN = "is_user_logged_in";
    String SAFTEY_PIN = "safety_pin";
    String USER_PUSH_TOKEN = "user_push_token";
    String USER_MODEL = "user_model";
    String ROLE = "role";
    String COUNTRY = "country";
    String DASHBOARD_MODEL = "dashborad_model";
    String VERIFY_OTP_MODEL= "verify_otp_model";
    String TIMELIMIT= "TIMELIMIT";
    String CHECK= "CHECK";
    String BADGECOUNT = "BADGECOUNT";


    /* variables used for crop type*/
    int SQUARE = 0;
    int RECTANGLE_10_BY_6 = 1;
    int DO_NOT_CROP_PHOTO = 2;

    /* variables used for intents*/
    int RC_CROP_INTENT = 201;
    int PERMISSION_CAMERA = 101;
    int PERMISSION_GALLERY = 102;

    int PERMISSION_REQUEST_CODE_CG = 100;
    int PERMISSION_REQUEST_CODE_LOCATION = 44;

    /* variables used for loaders*/
    enum LOADER_TYPE {
        NORMAL, SWIPE, PAGINATION
    }



    String DATE = "yyyyMMdd_HHmmss";
    String DATE2 = "dd-MM-yyyy";
    String DATE3 = "dd MMM yyyy , hh:mm a";
    String DATE4 = "dd MMM yyyy";
    String DATE5 = "dd MM yyyy";
    String DATE6 = "yyyy-MM-dd";
    String DATE7 = "dd/MM/yyyy";
    String DATE8 = "MMMM";
    String DATE9 = "yyyy";
    String DATE10= "MM";
    String DATE11 = "yyyy-MM-dd,HH:mm";
    String DATE12 = "yyyy/MM/dd";
    String DATE13 = "yyyy-MM-dd HH:mm:ss";
    String DATE14 = "MMM dd yyyy, hh:mm a";
    String DATE15 = "E dd MMM yyyy";
    String DATE16 = "MMM dd, yyyy";
    String DATE17 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    String DATE18 = "MMM yyyy";
    String DATE19 = "MMM dd yyyy HH:mm:ss";
    String DATE20 = "EEE MMM dd HH:mm:ss Z yyyy";
    String DATE21 = "dd/MM/yy, HH:mm";


    String TIME = "HH:mm:ss";
    String TIME2 = "hh:mm a";
    String TIME3 = "HH:mm";

    //SHARED PREFRENCE KEYS
    String ATTENDANCESTATUS = "Attendance Status";
    String LOCATIONADDRESS = "Current Address";

    int SPLASH_TIMER = 3000;
    String SUCCESS = "success";
    String ERROR = "error";


}
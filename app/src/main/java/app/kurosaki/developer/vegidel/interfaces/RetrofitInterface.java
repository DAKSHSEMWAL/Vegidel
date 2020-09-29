package app.kurosaki.developer.vegidel.interfaces;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {

    //Authentication Apis

    @FormUrlEncoded
    @POST("forgotpassword")
    Call<ResponseBody> forgotpassword(@Field("email") String email);

    //Upload Profile Image
    @Multipart
    @POST("uploadprofileimage")
    Call<ResponseBody> updateprofile(@Part MultipartBody.Part image);

    //Update Profile Image
    @Multipart
    @POST("updateprofileimage")
    Call<ResponseBody> updateprofile(@Header("Authorization") String token, @Part MultipartBody.Part image);

    //Upload Restaurant Image
    @Multipart
    @POST("restaurant/addrestaurantimages")
    Call<ResponseBody> updateRestaurantImage(
            @Header("Authorization") String token,
            @Part("profile_image") RequestBody ProfileImage,
            @Part MultipartBody.Part image);

    //LOGIN
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_id") String device_id,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type);

    @FormUrlEncoded
    @POST("updateuserprofile")
    Call<ResponseBody> updateProfile(
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("date_of_birth") String date_of_birth,
            @Field("address") String address,
            @Field("postcode") String postcode);

    //REGISTER USER
    @FormUrlEncoded
    @POST("signup")
    Call<ResponseBody> registerUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("date_of_birth") String date_of_birth,
            @Field("address") String address,
            @Field("postcode") String postcode,
            @Field("role") String role,
            @Field("image") String image);

    //REGISTER BUSINESS
    @FormUrlEncoded
    @POST("signup")
    Call<ResponseBody> registerUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("role") String role,
            @Field("business_address") String business_address,
            @Field("business_name") String business_name,
            @Field("business_type") String business_type,
            @Field("website_address") String website_address,
            @Field("postcode") String postcode,
            @Field("business_description") String business_description,
            @Field("business_lat") Double business_lat,
            @Field("business_lng") Double business_lng);

    //STRIPE SETUP INTENT
    @POST("payment/createsetupintent")
    Call<ResponseBody> createsetupintent(@Header("Authorization") String token);

    //SURVEY
    @FormUrlEncoded
    @POST("surveyanswers")
    Call<ResponseBody> submitsurvey(
            @Header("Authorization") String token,
            @Field("answer1") String answer1,
            @Field("answer2") String answer2,
            @Field("answer3") String answer3,
            @Field("answer4") String answer4,
            @Field("answer5") String answer5);

    //GET POST
    @FormUrlEncoded
    @POST("community/createpost")
    Call<ResponseBody> createPost(
            @Header("Authorization") String token,
            @Field("post") String post);

    //ALL POST
    @FormUrlEncoded
    @POST("community/getallposts")
    Call<ResponseBody> getAllPosts(
            @Header("Authorization") String token,
            @Field("page") String page);

    //EDIT POST
    @FormUrlEncoded
    @POST("community/editpost")
    Call<ResponseBody> editpost(
            @Header("Authorization") String token,
            @Field("post_id") String postid,
            @Field("post") String post);

    //DELETE POST
    @FormUrlEncoded
    @POST("community/deletepost")
    Call<ResponseBody> deletepost(
            @Header("Authorization") String token,
            @Field("post_id") String postid);

    //ADD COMMENT
    @FormUrlEncoded
    @POST("community/addcommentonpost")
    Call<ResponseBody> addcommentonpost(
            @Header("Authorization") String token,
            @Field("post_id") String postid,
            @Field("comment") String comment);

    //GET COMMENT
    @FormUrlEncoded
    @POST("community/getpostcomments")
    Call<ResponseBody> getpostcomments(
            @Header("Authorization") String token,
            @Field("page") String page,
            @Field("limit") String limit,
            @Field("post_id") String post_id);

    //EDIT COMMENT
    @FormUrlEncoded
    @POST("community/editcomment")
    Call<ResponseBody> editcomment(
            @Header("Authorization") String token,
            @Field("comment_id") String comment_id,
            @Field("comment") String comment);

    //DELETE COMMENT
    @FormUrlEncoded
    @POST("community/deletecomment")
    Call<ResponseBody> deletecomment(
            @Header("Authorization") String token,
            @Field("comment_id") String postid);

    //Get News
    @FormUrlEncoded
    @POST("news/getall")
    Call<ResponseBody> getNews(
            @Header("Authorization") String token,
            @Field("page") String page,
            @Field("limit") String limit);

    //Get Restaurant
    @FormUrlEncoded
    @POST("restaurant/getrestaurantlist")
    Call<ResponseBody> getRestaurant(
            @Header("Authorization") String token,
            @Field("user_lat") Double user_lat,
            @Field("user_lng") Double user_lng);

    //Favourite Toggle
    @FormUrlEncoded
    @POST("togglefavourite")
    Call<ResponseBody> togglefavourite(
            @Header("Authorization") String token,
            @Field("r_id") Integer r_id);

    //Get My Review
    @POST("reviews/getmyreviews")
    Call<ResponseBody> getReviews(@Header("Authorization") String token);

    //Get Coins
    @POST("reviews/getcoins")
    Call<ResponseBody> getcoins(@Header("Authorization") String token);

    //Get My Review
    @FormUrlEncoded
    @POST("reviews/getrestaurantreviews")
    Call<ResponseBody> getrestaurantreviews(
            @Header("Authorization") String token,
            @Field("page") String page,
            @Field("limit") String limit,
            @Field("restaurant_id") String restaurant_id);

    //Upload Review Image
    @Multipart
    @POST("reviews/uploadreviewimage")
    Call<ResponseBody> updateReviewImage(
            @Header("Authorization") String token,
            @Part("review_id") RequestBody review_id,
            @Part MultipartBody.Part image);

    //Add Review
    @FormUrlEncoded
    @POST("reviews/addreview")
    Call<ResponseBody> addreview(
            @Header("Authorization") String token,
            @Field("restaurant_id") String restaurant_id,
            @Field("offer_id") String offer_id,
            @Field("rating") String rating,
            @Field("spent") String spent,
            @Field("description") String description);

    //Add Restaurant Offers
    @Multipart
    @POST("offers/addoffers")
    Call<ResponseBody> addoffers(
            @Header("Authorization") String token,
            @Part("percentage") RequestBody percentage,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part image);

    //Offer List
    @POST("offers/getrestaurantoffers")
    Call<ResponseBody> getrestaurantoffers(@Header("Authorization") String token);

    //Get Qr
    @FormUrlEncoded
    @POST("offers/redeemoffer")
    Call<ResponseBody> redeemoffer(@Header("Authorization") String token, @Field("offer_id") String offer_id);


    //Send Qr Data
    @FormUrlEncoded
    @POST("offers/redeemofferrestaurant")
    Call<ResponseBody> redeemofferrestaurant(@Header("Authorization") String token, @Field("qrdata") String qrdata);

}

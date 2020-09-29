package app.kurosaki.developer.vegidel.services;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import app.kurosaki.developer.vegidel.interfaces.Constants;
import app.kurosaki.developer.vegidel.utils.SharedPref;

public class FirebaseMessagingServices extends FirebaseMessagingService implements Constants {

    private SharedPref sp;
    private String message;
    private Intent notificationIntent;
    String GROUP_KEY_TENANT_NOTIFICATION = "";

    @Override
    public void onNewToken(@NotNull String token) {
        Log.e("OnNewToken", "Refreshed token: " + token);

        sp = new SharedPref(this);
        sp.setString(USER_PUSH_TOKEN, token);
    }

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        //setNotification(remoteMessage);
    }

  /*  public void setNotification(@NotNull RemoteMessage remoteMessage) {
        sp = new SharedPref(this);
        String title = getString(R.string.app_name);
        int action = 0;
        String fromuid = "";
        String fromImage = "";
        String fromname = "";
        String toUserId = "";
        Log.e("push Received", remoteMessage.getData().toString());
        JSONObject jsonObject = new JSONObject(remoteMessage.getData());
        if (jsonObject.has("message")) {
            try {
                message = jsonObject.getString("message");
                action = jsonObject.getInt("action");
                fromuid = jsonObject.getString("fromuserid");
                fromImage = jsonObject.getString("from_image");
                fromname = jsonObject.getString("from_name");
                toUserId = jsonObject.getString("toUserId");
                GROUP_KEY_TENANT_NOTIFICATION = jsonObject.getString("from_name") + " " + jsonObject.getString("fromuserid");
                ;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (sp.getBoolean(IS_USER_LOGGED_IN)) {
            if (action == 1) {
                notificationIntent = new Intent(this, MainChatScreen.class)
                        .putExtra("NAME", fromname)
                        .putExtra("USER_ID", fromuid);
                if (Common.validateEditText(sp.getString(CURRENT_USER_ID_CHAT))) {
                    try {
                        if (!sp.getString(CURRENT_USER_ID_CHAT).equals(fromuid)) {
                            sendNotification(title, message, fromImage);
                        }
                    } catch (Exception e) {
                        sendNotification(title, message, fromImage);
                    }
                } else {
                    sendNotification(title, message, fromImage);
                }
            } else if (action == 2) {
                if (sp.getBoolean(Constants.IS_USER_LOGGED_IN)) {
                    if (getUserModelFromSharedPreference(sp).getRole() == 2) {
                        notificationIntent = new Intent(this, HomeActivity.class);
                        notificationIntent.putExtra("MESSAGE", "Owner Accepted you request");
                        sendNotification(title, message, "");
                    } else {
                        notificationIntent = new Intent(this, SplashActivity.class);
                        sendNotification(title, message, "");
                    }
                } else {
                    notificationIntent = new Intent(this, SplashActivity.class);
                    sendNotification(title, message, "");
                }
            } else if (action == 3) {
                if (sp.getBoolean(Constants.IS_USER_LOGGED_IN)) {
                    if (getUserModelFromSharedPreference(sp).getRole() == 2) {
                        notificationIntent = new Intent(this, HomeActivity.class);
                        notificationIntent.putExtra("MESSAGE", "Owner rejected your request");
                        sendNotification(title, message, "");
                    } else {
                        notificationIntent = new Intent(this, SplashActivity.class);
                        sendNotification(title, message, "");
                    }
                } else {
                    notificationIntent = new Intent(this, SplashActivity.class);
                    sendNotification(title, message, "");
                }
            } else if (action == 4) {
                if (sp.getBoolean(Constants.IS_USER_LOGGED_IN)) {
                    if (getUserModelFromSharedPreference(sp).getRole() == 2) {
                        notificationIntent = new Intent(this, HomeActivity.class);
                        notificationIntent.putExtra("MESSAGE", "Owner assigned a property to you");
                        sendNotification(title, message, "");
                    } else {
                        notificationIntent = new Intent(this, SplashActivity.class);
                        sendNotification(title, message, "");
                    }
                } else {
                    notificationIntent = new Intent(this, SplashActivity.class);
                    sendNotification(title, message, "");
                }
            } else if (action == 5) {
                if (sp.getBoolean(Constants.IS_USER_LOGGED_IN)) {
                    if (getUserModelFromSharedPreference(sp).getRole() == 1) {
                        notificationIntent = new Intent(this, MyTicketsActivity.class);
                        notificationIntent.putExtra("MESSAGE", "Tentant raised a ticket");
                        sendNotification(title, message, "");
                    } else {
                        notificationIntent = new Intent(this, SplashActivity.class);
                        sendNotification(title, message, "");
                    }
                } else {
                    notificationIntent = new Intent(this, SplashActivity.class);
                    sendNotification(title, message, "");
                }
            }else if (action == 6) {
                if (sp.getBoolean(Constants.IS_USER_LOGGED_IN)) {
                    if (getUserModelFromSharedPreference(sp).getRole() == 2) {
                        notificationIntent = new Intent(this, RaiseTicketActivity.class);
                        notificationIntent.putExtra("MESSAGE", "Owner accepted the ticket");
                        sendNotification(title, message, "");
                    } else {
                        notificationIntent = new Intent(this, SplashActivity.class);
                        sendNotification(title, message, "");
                    }
                } else {
                    notificationIntent = new Intent(this, SplashActivity.class);
                    sendNotification(title, message, "");
                }
            }else if (action == 7) {
                if (sp.getBoolean(Constants.IS_USER_LOGGED_IN)) {
                    if (getUserModelFromSharedPreference(sp).getRole() == 2) {
                        notificationIntent = new Intent(this, RaiseTicketActivity.class);
                        notificationIntent.putExtra("MESSAGE", "Owner rejected the ticket");
                        sendNotification(title, message, "");
                    } else {
                        notificationIntent = new Intent(this, SplashActivity.class);
                        sendNotification(title, message, "");
                    }
                } else {
                    notificationIntent = new Intent(this, SplashActivity.class);
                    sendNotification(title, message, "");
                }
            }else if (action == 8) {
                if (sp.getBoolean(Constants.IS_USER_LOGGED_IN)) {
                    if (getUserModelFromSharedPreference(sp).getRole() == 1) {
                        notificationIntent = new Intent(this, BidsActivity.class);
                        notificationIntent.putExtra("MESSAGE", "You have a new bid");
                        sendNotification(title, message, "");
                    } else {
                        notificationIntent = new Intent(this, SplashActivity.class);
                        sendNotification(title, message, "");
                    }
                } else {
                    notificationIntent = new Intent(this, SplashActivity.class);
                    sendNotification(title, message, "");
                }
            }else if (action == 9) {
                if (sp.getBoolean(Constants.IS_USER_LOGGED_IN)) {
                    if (getUserModelFromSharedPreference(sp).getRole() == 3) {
                        notificationIntent = new Intent(this, HomeActivity.class);
                        notificationIntent.putExtra("MESSAGE", "Owner accepted your bid");
                        sendNotification(title, message, "");
                    } else {
                        notificationIntent = new Intent(this, SplashActivity.class);
                        sendNotification(title, message, "");
                    }
                } else {
                    notificationIntent = new Intent(this, SplashActivity.class);
                    sendNotification(title, message, "");
                }
            }else if (action == 10) {
                if (sp.getBoolean(Constants.IS_USER_LOGGED_IN)) {
                    if (getUserModelFromSharedPreference(sp).getRole() == 3) {
                        notificationIntent = new Intent(this, BidsActivity.class);
                        notificationIntent.putExtra("MESSAGE", "Owner rejected your bid");
                        sendNotification(title, message, "");
                    } else {
                        notificationIntent = new Intent(this, SplashActivity.class);
                        sendNotification(title, message, "");
                    }
                } else {
                    notificationIntent = new Intent(this, SplashActivity.class);
                    sendNotification(title, message, "");
                }
            }else if (action == 11) {
                if (sp.getBoolean(Constants.IS_USER_LOGGED_IN)) {
                    if (getUserModelFromSharedPreference(sp).getRole() == 1) {
                        notificationIntent = new Intent(this, MyTenantsActivity.class);
                        notificationIntent.putExtra("MESSAGE", "Tenant send you the request");
                        sendNotification(title, message, "");
                    } else {
                        notificationIntent = new Intent(this, SplashActivity.class);
                        sendNotification(title, message, "");
                    }
                } else {
                    notificationIntent = new Intent(this, SplashActivity.class);
                    sendNotification(title, message, "");
                }
            }
        }

    }

    private void sendNotification(String title, String messageBody, String image) {
        Bitmap bitmap = null;
        if (image.isEmpty()||image!=null) {
            bitmap = null;
        }
        else {
            bitmap = getCircleBitmap(getBitmapFromURL(image));
        }
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        int pushIcon = R.drawable.splash_logo;
        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";


        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setContentIntent(intent)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setStyle(new NotificationCompat.InboxStyle().addLine(message))
                .setSmallIcon(pushIcon)
                .setLargeIcon(bitmap)
                .setAutoCancel(true)
                .setTicker(messageBody)
                .setGroup(GROUP_KEY_TENANT_NOTIFICATION)
                .setWhen(System.currentTimeMillis())
                .setOngoing(false);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        if (mNotificationManager != null) {
            mNotificationManager.notify(notificationId, notification);
        }
    }*/

    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap getCircleBitmap(@NotNull Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

}
package com.example.ikand.notificacionestrabajo;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int NOTIFICACION_ID = 1;
    Button notificacionSimple;
    private boolean segundaVez = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificacionSimple = findViewById(R.id.btnNotificacionSimple);


    }

    public void notificacionSimple(View view) {
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this);
        notificacion
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notificacion simple")
                .setContentText("Primera notificacion");


        Notification notification = notificacion.build();
        NotificationManagerCompat.from(this).notify(NOTIFICACION_ID, notification);
    }

    public void notificacionAccion(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/index.html"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("Notificacion + Accion");
        builder.setContentText("Ir a la pagina web de los desarrolladores de Android");
        builder.setSubText("Toca para ver la documentacion de android");

        NotificationManager notifitacionManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifitacionManager.notify(2, builder.build());
    }

    public void notificacionActualizacion(View view) {
        //en caso de haber mas de un aviso sobre la misma aplicacion agruparlos y cambiar el diseño
        String GRUPO_NOTIFICACIONES = "notificaciones de la misma app";
        Notification notificacion;
        if(!segundaVez){
            notificacion=new NotificationCompat.Builder(this)
                    .setContentTitle("Mensaje nuevo")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("El señor ¿Donde estas?")
                    .setColor(getResources().getColor(R.color.colorPrimaryDark))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .build();

            segundaVez=true;
        }else{
            notificacion=new NotificationCompat.Builder(this)
                    .setContentTitle("2 mensajes nuevos")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setNumber(2)
                    .setColor(getResources().getColor(R.color.colorPrimaryDark))
                    .setStyle(
                            new NotificationCompat.InboxStyle()
                            .addLine("El señor ¿Si lo viste?")
                            .addLine("Ximena Claus nuevo diseño del logo")
                            .setBigContentTitle("2 mensajes nuevos")
                    )
                    .setGroup(GRUPO_NOTIFICACIONES)
                    .setGroupSummary(true)
                    .build();
        }
        final NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifyMgr.notify(3,notificacion);

    }
    public void notificacionProgreso(View view) {

        final NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this);
        notificacion
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notificacion simple")
                .setContentText("Primera notificacion").setNumber(4);
        //NotificationManagerCompat notificationManagerCompat=new NotificationManagerCompat().notify();
        final NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int i;
                        for (i=0; i<=100;i++){
                            notificacion.setProgress(100,i,false);
                            notifyMgr.notify(4,notificacion.build());

                            try{
                                Thread.sleep(1000);
                            }catch (InterruptedException e){
                                Log.d("TAG","Fallo sleep");
                            }
                        }
                        notificacion.setContentText("Sincronizacion completa")
                                .setProgress(0,0,false);
                        notifyMgr.notify(4,notificacion.build());

                    }
                }
        ).start();

    }
    public void notificacionAviso(View view) {
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this);
        notificacion
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("Notificacion aviso")
                .setContentText("Primera notificacion")
        .setNumber(2);

        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        notificacion.setFullScreenIntent(fullScreenPendingIntent,true);

        final NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifyMgr.notify(5,notificacion.build());

        /*Notification notification = notificacion.build();
        NotificationManagerCompat.from(this).notify(NOTIFICACION_ID, notification);*/

    }
    public void notificacionBigView(View view) {
        //Creamos los Intents y correspondientes PendingIntents que responderan a los botones que aparecen en la notificación.
        Intent _intentAbrir = new Intent(this, MainActivity.class);
        PendingIntent _pendingAbrir = PendingIntent.getActivity(this, 0, _intentAbrir, 0);
        String direccion="http://developer.android.com/index.html";
        Intent _intentIniciar = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/index.html"));
        //Intent _intentIniciar = new Intent(this, LoginActivity.class);
        PendingIntent _pendingIniciar = PendingIntent.getActivity(this, 0, _intentIniciar, 0);


        NotificationCompat.Action accion_abrir = new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_delete, "Abrir App", _pendingAbrir).build();
        NotificationCompat.Action accion_iniciar = new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_edit, "Iniciar Sesión", _pendingIniciar).build();


        /*
            En este caso, omitiremos el intent principal que se lanza al pulsar sobre la notificacion,
                    dejando así solo las acciones añadidas

            //Definimos el intent para la acción principal
            Intent intentFinal = new Intent(this, LoginActivity.class);

            //Definimos la nueva actividad como nueva tarea
            intentFinal.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);


            //Encapsulamos el intenFinal anteriormente creado dentro de un nuevo PendingIntent.
            PendingIntent resultPendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    intentFinal,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
        */
        try{
            Notification NotiBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Aplicación actualizada")
                            .setContentText("Vuelve a iniciar sesión")
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setDefaults(Notification.DEFAULT_ALL)//Requiere permisos de Vibración.
//                            .setContentIntent(resultPendingIntent)



                            //Métodos exclusivos para definir una notificacion como Big View.
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText("La aplicación se ha actualizado a una nueva versión, es necesario que vuelvas a iniciar sesión."))
                            .addAction (accion_abrir)
                            .addAction (accion_iniciar).build();


            //Obtenemos una instancia del servicio de NotificactionManager
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            //Creamos y mostramos la notificación
            mNotifyMgr.notify(7, NotiBuilder);


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}

package com.lucasf.aula4app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final int SELECIONAR_CONTATO = 0;
    private final int TIRAR_FOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickWeb( View view )
    {
        Uri uri = Uri.parse( "http://www.unisc.br");
        Intent it = new Intent(Intent.ACTION_VIEW, uri );
        startActivity( it );
    }


    public void onClickContacts( View view )
    {
        Uri uri = Uri.parse( "content://com.android.contacts/contacts");
        Intent it = new Intent(Intent.ACTION_PICK, uri );
        startActivityForResult( it, SELECIONAR_CONTATO );

    }

    public void onClickMaps1( View view )
    {
        Uri uriGeo = Uri.parse("geo:0,0?q=Sete+de+Setembro,Curitiba");
        Intent it = new Intent( Intent.ACTION_VIEW, uriGeo );
        startActivity( it );
    }
    public void onClickMaps2( View view )
    {
        Uri localizacao = Uri.parse("geo:-25.443195,-49.280977");
        Intent it = new Intent( Intent.ACTION_VIEW, localizacao );
        startActivity( it );
    }

    public void onClickMaps3( View view )
    {
        String partida = "-25.443195,-49.280977";
        String destino = "-25.442207,-49.278403";
        String url = "http://maps.google.com/maps?f=d&saddr="+partida+"&daddr="+destino+"&hl=pt";
        Intent it = new Intent( Intent.ACTION_VIEW, Uri.parse(url) );
        startActivity( it );
    }

    public void onClickPicture( View view )
    {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE );
        startActivityForResult( it, TIRAR_FOTO );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK ) {
            switch (requestCode) {
                case SELECIONAR_CONTATO:
                    Uri uri = data.getData();
                    Cursor c = getContentResolver().query( uri, null, null, null, null );
                    c.moveToNext();

                    int nameCol = c.getColumnIndexOrThrow( ContactsContract.Contacts.DISPLAY_NAME );
                    int idCol = c.getColumnIndexOrThrow( ContactsContract.Contacts._ID );

                    String name = c.getString( nameCol );
                    String id = c.getString( idCol );

                    c.close();

                    TextView tvNome = (TextView)findViewById(R.id.nameView );
                    tvNome.setText( name );
                    break;
                case TIRAR_FOTO:
                    ImageView imgView = findViewById(R.id.imageView );
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imgView.setImageBitmap( photo );
                    break;
            }
        }
    }
}
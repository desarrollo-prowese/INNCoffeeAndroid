
	 
	/*
     *	This content is generated from the API File Info.
     *	(Alt+Shift+Ctrl+I).
     *
     *	@desc
     *	@file 		pag_inicial
     *	@date 		0
     *	@title 		PAG INICIAL
     *	@author
     *	@keywords
     *	@generator 	Export Kit v1.2.8.xd
     *
     */


    package com.example.inncoffee.RegistroLogin;

    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.example.inncoffee.R;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.database.DatabaseReference;

    public class RegistroVerifica extends Activity {


        private View _bg__pantalla_inn_coffee___3_ek2;
        private ImageView whatsapp_image_2019_07_23_at_15_12_24_2__ek3;
        private ImageView rectangle_ek13;
        private ImageView color_mode_inncoffe_ek19;
        private ImageButton rect_ngulo_1461_ek12;
        private TextView iniciar_sesi_n_ek2;
        private FirebaseAuth mAuth;
        private DatabaseReference mDatabase;
        private TextView para_terminar_tu_registro_verifica_tu_correo_electr_nico_mediante_el_enlace_que_acabamos_de_enviarte_por_mail_;


        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.registro_verifica);

            _bg__pantalla_inn_coffee___3_ek2 = findViewById(R.id._bg__pantalla_inn_coffee___3_ek2);
            whatsapp_image_2019_07_23_at_15_12_24_2__ek3 = findViewById(R.id.whatsapp_image_2019_07_23_at_15_12_24_2__ek3);
            rectangle_ek13 = findViewById(R.id.rectangle_ek13);
            color_mode_inncoffe_ek19 = findViewById(R.id.color_mode_inncoffe_ek19);
            rect_ngulo_1461_ek12 = findViewById(R.id.rect_ngulo_1461_ek12);
            iniciar_sesi_n_ek2 = findViewById(R.id.iniciar_sesi_n_ek2);
            para_terminar_tu_registro_verifica_tu_correo_electr_nico_mediante_el_enlace_que_acabamos_de_enviarte_por_mail_ = findViewById(R.id.para_terminar_tu_registro_verifica_tu_correo_electr_nico_mediante_el_enlace_que_acabamos_de_enviarte_por_mail_);





            //custom code goes here
            rect_ngulo_1461_ek12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      startActivity(new Intent(RegistroVerifica.this, Login.class));
                      finish();
                }
            });
        }
    }
	
	
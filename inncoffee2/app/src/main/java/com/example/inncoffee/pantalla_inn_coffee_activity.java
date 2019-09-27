
	 
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


    package com.example.inncoffee;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import com.facebook.AccessToken;
    import com.facebook.CallbackManager;
    import com.facebook.FacebookCallback;
    import com.facebook.FacebookException;
    import com.facebook.FacebookSdk;
    import com.facebook.login.LoginResult;
    import com.facebook.login.widget.LoginButton;
    import com.google.android.gms.auth.api.Auth;
    import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
    import com.google.android.gms.auth.api.signin.GoogleSignInResult;
    import com.google.android.gms.common.ConnectionResult;
    import com.google.android.gms.common.SignInButton;
    import com.google.android.gms.common.api.GoogleApiClient;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthCredential;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FacebookAuthProvider;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.auth.GoogleAuthProvider;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;

    import java.util.HashMap;
    import java.util.Map;

    public class pantalla_inn_coffee_activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
            View.OnClickListener {


        private static final String TAG = "SignInActivity";
        private static final int RC_SIGN_IN = 9001;
        private View _bg__pantalla_inn_coffee_ek2;
        private ImageView whatsapp_image_2019_07_23_at_15_12_24_2_;
        private ImageView rectangle;
        private ImageView color_mode_inncoffe_ek3;
        private View rectangle_ek1;
        private ImageView path_ek1;
        private View rect_ngulo_160;
        private View rect_ngulo_1458;
        private ImageView trazado_1_ek1;
        private TextView facebook_ek1;
        private View rect_ngulo_1459;
        private TextView google;
        private ImageView _246x0w;
        private View rect_ngulo_1460;
        private TextView registrarse_con_correo;
        private View rect_ngulo_117;
        private ImageView trazado_50;
        private View rect_ngulo_1461;
        private TextView iniciar_sesi_n;
        private TextView __a_n_no_tienes_cuenta_;
        private GoogleApiClient mGoogleApiClient;
        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListener;

        private CallbackManager mCallbackManager;

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.pantalla_inn_coffee);


            _bg__pantalla_inn_coffee_ek2 = findViewById(R.id._bg__pantalla_inn_coffee_ek2);
            whatsapp_image_2019_07_23_at_15_12_24_2_ = findViewById(R.id.whatsapp_image_2019_07_23_at_15_12_24_2_);
            rectangle = findViewById(R.id.rectangle);
            color_mode_inncoffe_ek3 = findViewById(R.id.color_mode_inncoffe_ek3);
            rectangle_ek1 = findViewById(R.id.rectangle_ek1);
            path_ek1 = findViewById(R.id.path_ek1);
            rect_ngulo_1460 = findViewById(R.id.rect_ngulo_1460);
            registrarse_con_correo = findViewById(R.id.registrarse_con_correo);
            rect_ngulo_117 = findViewById(R.id.rect_ngulo_117);
            trazado_50 = findViewById(R.id.trazado_50);
            rect_ngulo_1461 = findViewById(R.id.rect_ngulo_1461);
            iniciar_sesi_n = findViewById(R.id.iniciar_sesi_n);
            __a_n_no_tienes_cuenta_ = findViewById(R.id.__a_n_no_tienes_cuenta_);

            //setContentView(R.layout.activity_login);
            //Email Register
            rect_ngulo_1460.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(pantalla_inn_coffee_activity.this, pantalla_inn_coffee___1_activity.class));
                    finish();
                }
            });
            //Login
            rect_ngulo_1461.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(pantalla_inn_coffee_activity.this, pantalla_inn_coffee___2_activity.class));
                    finish();
                }
            });
            // Facebook Login
            FacebookSdk.sdkInitialize(getApplicationContext());
            mCallbackManager = CallbackManager.Factory.create();

            LoginButton mFacebookSignInButton = findViewById(R.id.facebook_button);
            mFacebookSignInButton.setReadPermissions("email", "public_profile", "user_birthday", "user_friends");

            mFacebookSignInButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.d(TAG, "facebook:onSuccess:" + loginResult);
                    firebaseAuthWithFacebook(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    Log.d(TAG, "facebook:onCancel");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d(TAG, "facebook:onError", error);
                }
            });

            // Google Sign-In
            // Assign fields
            SignInButton mGoogleSignInButton = findViewById(R.id.google_button);

            // Set click listeners
            mGoogleSignInButton.setOnClickListener(this);

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            // Initialize FirebaseAuth
            mAuth = FirebaseAuth.getInstance();

            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        // User is signed in
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    } else {
                        // User is signed out
                        Log.d(TAG, "onAuthStateChanged:signed_out");
                    }
                }
            };

        }

        @Override
        public void onStart() {
            super.onStart();
            mAuth.addAuthStateListener(mAuthListener);
        }

        @Override
        public void onStop() {
            super.onStop();
            if (mAuthListener != null) {
                mAuth.removeAuthStateListener(mAuthListener);
            }
        }

        private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
            Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithCredential", task.getException());
                                Toast.makeText(pantalla_inn_coffee_activity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseUser user = mAuth.getCurrentUser();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

                                Map newPost = new HashMap();
                                newPost.put("FullName", user.getDisplayName());
                                newPost.put("Telefono", user.getPhoneNumber());
                                String nombre = user.getDisplayName();
                                current_user_db.setValue(newPost);
                                startActivity(new Intent(pantalla_inn_coffee_activity.this, Main2Activity.class));
                                Intent Intent = new Intent(pantalla_inn_coffee_activity.this, Main2Activity.class);
                                Intent.putExtra("valor", nombre);
                                startActivity(Intent);
                                finish();
                            }
                        }
                    });
        }

        private void firebaseAuthWithFacebook(AccessToken token) {
            Log.d(TAG, "handleFacebookAccessToken:" + token);

            final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithCredential", task.getException());
                                Toast.makeText(pantalla_inn_coffee_activity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseUser user = mAuth.getCurrentUser();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

                                Map newPost = new HashMap();
                                newPost.put("FullName", user.getDisplayName());
                                newPost.put("Telefono", user.getPhoneNumber());
                                startActivity(new Intent(pantalla_inn_coffee_activity.this, menu_parallax_activity.class));
                                finish();

                            }
                        }
                    });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.google_button:
                    signIn();
                    break;
                default:
                    return;
            }
        }

        private void signIn() {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            mCallbackManager.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                } else {
                    // Google Sign In failed
                    Log.e(TAG, "Google Sign In failed.");
                }
            }
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            // An unresolvable error has occurred and Google APIs (including Sign-In) will not
            // be available.
            Log.d(TAG, "onConnectionFailed:" + connectionResult);
            Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
        }
    }
	
	
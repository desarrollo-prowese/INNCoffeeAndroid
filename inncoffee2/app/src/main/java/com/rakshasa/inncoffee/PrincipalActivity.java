package com.rakshasa.inncoffee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.rakshasa.inncoffee.RegistroLogin.Login;
import com.rakshasa.inncoffee.RegistroLogin.Registro;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginFragment;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import io.paperdb.Paper;


public class PrincipalActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private LoginButton loginButton;
    private GoogleSignInClient mgoogle;

    private ConstraintLayout signInButton,fb;
    private CallbackManager callbackManager;
    private String Emails;
    private WebView webView;
    public static final int GOOGLE_SIGN_IN = 123;
    public static final int FACE_SIGN_IN = 123;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    private void inicialize() {
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    String uid = mAuth.getCurrentUser().getUid();
                    intent.putExtra("user_id", uid);
                    startActivity(intent);
                    finish();
                    Log.w("TAG", "onAuthStateChanged - Logueado");

                } else {

                    Log.w("TAG", "onAuthStateChanged - Cerro sesion");

                }
            }
        };

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Button register = findViewById(R.id.btnLoginRegister);
        Button login = findViewById(R.id.btnLogin);
        inicialize();
        Facebook();
        Google();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        //Launch Registration screen when Register Button is clicked
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrincipalActivity.this, Registro.class);
                startActivity(i);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrincipalActivity.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

    }




   private void Google(){
       Paper.init(this);
       GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken(getString(R.string.default_web_client_id))
               .requestEmail()
               .build();


       signInButton = (ConstraintLayout) findViewById(R.id.sing_in_button1);

       mgoogle = GoogleSignIn.getClient(PrincipalActivity.this,gso);

       signInButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent sing = mgoogle.getSignInIntent();
               startActivityForResult(sing, GOOGLE_SIGN_IN);
           }
       });


   }
    public void onClickFacebookButton(View view) {
        if (view == fb) {
            loginButton.performClick();
        }
    }
   private void Facebook(){

       Paper.init(this);
       callbackManager = CallbackManager.Factory.create();
       fb = (ConstraintLayout) findViewById(R.id.login_button1);
       loginButton = (LoginButton) findViewById(R.id.login_button);
       loginButton.setPermissions("email", "public_profile");
       loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

           @Override
           public void onSuccess(LoginResult loginResult) {
               firebaseAuthWithFacebook(loginResult.getAccessToken());
               requestEmail(AccessToken.getCurrentAccessToken());
           }

           @Override
           public void onCancel() {
               LoginManager.getInstance().logOut();
               mAuth.removeAuthStateListener(firebaseAuthListener);
               Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onError(FacebookException error) {
               Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
           }
       });

   }


    private void requestEmail(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (response.getError() != null) {
                    Toast.makeText(getApplicationContext(), response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    Emails = object.getString("email");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("TAG", "Google sign in failed", e);
            }
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }



    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(PrincipalActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete (@NonNull final Task<AuthResult> task) {
                if (task.isSuccessful()){
                    mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            Log.d("TAG", "signInWithCredential:onComplete:" + dataSnapshot.child(mAuth.getUid()).exists());

                            if(dataSnapshot.child(mAuth.getUid()).exists()){
                                Log.d("TAG", "NONNONONONONONONONONO");
                                Toast.makeText(PrincipalActivity.this, "Ya estas registrado ",Toast.LENGTH_SHORT).show();
                            }else{

                                Log.d("TAG", "SISISISISISISISISISI");
                                Map<String, Object> newPost = new HashMap<>();
                                Log.w("TAG", "Se Registro Correctamente ", task.getException());
                                // Sign in success, update UI with the signed-in user's information
                                String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                String FullName = acct.getDisplayName();
                                String email = acct.getEmail();
                                Object obj = new Object();
                                String Alergia = "0";
                                obj = Alergia;
                                Object obj1 = new Object();
                                String Phone = "";
                                obj1 = Phone;
                                Object obj2 = new Object();
                                String Center = "";
                                obj2 = Center;
                                Object obj3 = new Object();
                                String PhotoURL = String.valueOf(acct.getPhotoUrl());
                                obj3 = PhotoURL;
                                Object obj4 = new Object();
                                String FechaNac = "";
                                obj4 = FechaNac;
                                Object obj5 = new Object();
                                String Google = "Google";
                                obj5 = Google;
                                Object obj6 = new Object();
                                obj6 = "0.00€";
                                newPost.put("DineroAcumulados", obj6);
                                newPost.put("FullName", FullName);
                                newPost.put("Alergias", obj);
                                newPost.put("Phone", obj1);
                                newPost.put("Center", obj2);
                                newPost.put("PhotoURL", obj3);
                                newPost.put("FechaNac", obj4);
                                newPost.put("Google", obj5);
                                newPost.put("Email", email);
                                mDatabase.child("Users").child(user_id).setValue(newPost).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete (@NonNull Task<Void> task2) {
                                        if (!task2.isSuccessful()) {
                                            if (mAuth.getCurrentUser() != null) {
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                updateUI(user);
                                                Toast.makeText(PrincipalActivity.this, "Se Registro " + task2.isSuccessful(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }
        });
    }

    private void firebaseAuthWithFacebook(@NonNull AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(PrincipalActivity.this, new OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete (@NonNull final Task<AuthResult> task) {
                if (task.isSuccessful()){
                    mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            Log.d("TAG", "signInWithCredential:onComplete:" + dataSnapshot.child(mAuth.getUid()).exists());

                            if(dataSnapshot.child(mAuth.getUid()).exists()){
                                Log.d("TAG", "NONNONONONONONONONONO");
                                Toast.makeText(PrincipalActivity.this, "Ya estas registrado ",Toast.LENGTH_SHORT).show();
                            }else{

                                Log.d("TAG", "SISISISISISISISISISI");
                                Map<String, Object> newPost = new HashMap<>();
                                Log.w("TAG", "Se Registro Correctamente ", task.getException());
                                // Sign in success, update UI with the signed-in user's information
                                String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                String FullName = Profile.getCurrentProfile().getName();
                                String email = Emails;
                                Object obj = new Object();
                                String Alergia = "0";
                                obj = Alergia;
                                Object obj1 = new Object();
                                String Phone = "";
                                obj1 = Phone;
                                Object obj2 = new Object();
                                String Center = "";
                                obj2 = Center;
                                Object obj3 = new Object();
                                String PhotoURL = Profile.getCurrentProfile().getProfilePictureUri(25, 25).toString();
                                obj3 = PhotoURL;
                                Object obj4 = new Object();
                                String FechaNac = "";
                                obj4 = FechaNac;
                                Object obj5 = new Object();

                                String Facebook = "Facebook";
                                obj5 = Facebook;
                                Object obj6 = new Object();
                                obj6 = "0.00€";
                                newPost.put("DineroAcumulados", obj6);
                                newPost.put("FullName", FullName);
                                newPost.put("Alergias", obj);
                                newPost.put("Phone", obj1);
                                newPost.put("Center", obj2);
                                newPost.put("PhotoURL", obj3);
                                newPost.put("FechaNac", obj4);
                                newPost.put("Facebook", obj5);
                                newPost.put("Email", email);
                                mDatabase.child("Users").child(user_id).setValue(newPost).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete (@NonNull Task<Void> task2) {
                                        if (!task2.isSuccessful()) {
                                            if (mAuth.getCurrentUser() != null) {
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                updateUI(user);
                                                Toast.makeText(PrincipalActivity.this, "Se Registro " + task2.isSuccessful(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }
                                });

                                    }
                                }

                                @Override
                                public void onCancelled (@NonNull DatabaseError databaseError) {

                                }
                    });

                }

            }
        });


    }

    public void updateUI(FirebaseUser currentUser) {
        String keyid = mDatabase.push().getKey();
        assert keyid != null;
        mDatabase.child(keyid).setValue(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
      /*  if (firebaseAuthListener != null) {
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            mAuth.removeAuthStateListener(firebaseAuthListener);
        }*/
    }
}

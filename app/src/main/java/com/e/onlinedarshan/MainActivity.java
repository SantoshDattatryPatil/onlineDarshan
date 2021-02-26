package com.e.onlinedarshan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
private RecyclerView firestorelist;
RecyclerView.LayoutManager layoutManager;

private FirebaseFirestore firebaseFirestore;
   private FirestoreRecyclerAdapter adapter;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
ConnectivityManager connectivityManager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
if(networkInfo==null || !networkInfo.isConnected() || !networkInfo.isAvailable())
{
    Dialog dialog=new Dialog(this);
    dialog.setContentView(R.layout.internetconnection);
dialog.setCancelable(false);
dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
dialog.getWindow().getAttributes().windowAnimations=R.style.Animation_Design_BottomSheetDialog;

Button button=dialog.findViewById(R.id.btn);
button.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                  recreate();                              }
                          }
);

}

    firebaseFirestore= FirebaseFirestore.getInstance();
    firestorelist=findViewById(R.id.firestore_list);
    layoutManager=new GridLayoutManager(this,2);
    firestorelist.setLayoutManager(layoutManager);
        Query query=firebaseFirestore.collection("Visit");;
        FirestoreRecyclerOptions<GodModel> options=new FirestoreRecyclerOptions.Builder<GodModel>()
                .setQuery(query,GodModel.class)
                .build();
         adapter= new FirestoreRecyclerAdapter<GodModel, ProductViewHolder>(options) {
            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.god,parent,false);
                return new ProductViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final GodModel model) {

            holder.name.setText(model.getName().toUpperCase());
                Glide.with(holder.img.getContext()).load(model.getImg()).into(holder.img);
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent= new Intent(MainActivity.this,DarshanActivity.class);

                   intent.putExtra("title",model.getLink());
                   startActivity(intent);
               }
           });
            /*    */
            }
        };
firestorelist.setHasFixedSize(false);
firestorelist.setLayoutManager(new LinearLayoutManager(this));
firestorelist.setAdapter(adapter);
    }

    private class ProductViewHolder  extends  RecyclerView.ViewHolder{
       private TextView name;
private CircleImageView img;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
        name=itemView.findViewById(R.id.name);
        img=itemView.findViewById(R.id.image);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
}

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
}
}
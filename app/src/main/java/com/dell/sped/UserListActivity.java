package com.dell.sped;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListActivity extends AppCompatActivity {
    private DatabaseReference databaseReferenceUsers;
    private DatabaseReference onlineReference;
    private RecyclerView mUsersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        mUsersList = (RecyclerView)findViewById(R.id.recycleView);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        Bundle bundle = getIntent().getExtras();
        Toast.makeText(UserListActivity.this,bundle.getString("uid"),Toast.LENGTH_LONG).show();
        onlineReference = FirebaseDatabase.getInstance().getReference().child("Users").child(bundle.getString("uid"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        onlineReference.child("online").setValue("offline");
    }

    @Override
    protected void onStart() {
        super.onStart();
        onlineReference.child("online").setValue("online");
        FirebaseRecyclerAdapter<User, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UsersViewHolder>(
                User.class,
                R.layout.user_row_layout,
                UsersViewHolder.class,
                databaseReferenceUsers
        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, User model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setDist(model.getLat(),model.getLo(),getApplicationContext());
                viewHolder.setEmail(model.getEmail());
                viewHolder.setImage(model.getImage(), getApplicationContext());
                viewHolder.setOnline(model.getOnline());
            }
        };

        mUsersList.setAdapter(firebaseRecyclerAdapter);


    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name){
            TextView mUserNameView = (TextView) mView.findViewById(R.id.textViewNameRow);
            mUserNameView.setText(name);
        }
        public void setEmail(String email){
            TextView mUserEmailView = (TextView) mView.findViewById(R.id.textViewEmailRow);
            mUserEmailView.setText(email);
        }
        public void setImage(String image, Context context){
            CircleImageView mUserImageView = (CircleImageView) mView.findViewById(R.id.imageViewProfileRow);
            Picasso.with(context).load(image).into(mUserImageView);
        }
        public void setOnline(String online){
            TextView mUserOnlineView = (TextView) mView.findViewById(R.id.textViewStatusRow);
            if(online.equals("online")){
                mUserOnlineView.setTextColor(Color.parseColor("#085912"));
            }
            if(online.equals("offline")){
                mUserOnlineView.setTextColor(Color.parseColor("#979997"));
            }
            mUserOnlineView.setText(online);
            mUserOnlineView.setAllCaps(true);
        }

        public void setDist(String lat, String lo, Context c){
            GPSTrack gpsTrack = new GPSTrack(c);
            Location my = gpsTrack.getLocation();
            GPSDist gpsDist = new GPSDist(my.getLatitude(),my.getLongitude(),Double.parseDouble(lat),Double.parseDouble(lo));
            TextView mUserDistView = (TextView) mView.findViewById(R.id.textViewDistRow);
            double dist = gpsDist.getDist();
            int dist1 = (int) dist;
            if(dist1==0){
                mUserDistView.setText("");
            }else {
                mUserDistView.setText("~"+dist1+ " km");
            }

        }
    }


}

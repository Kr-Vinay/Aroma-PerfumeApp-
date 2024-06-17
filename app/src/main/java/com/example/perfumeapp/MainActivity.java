package com.example.perfumeapp;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView card2RecyclerView;
    private RecyclerView cardRecyclerView;
    private CardAdapter cardAdapter;
    private CardAdapter2 cardAdapter2;
    private adapter adapter;
    private List<Card> cards;
    private List<Card2> cards2;
    private List<res> resourceList;
    private DatabaseReference imagesRef;
    private DatabaseReference cardsImagesRef;
    private DatabaseReference card2ImagesRef;
    private ProgressBar progressBar;
    private ProgressBar pagerprogress;
    private ViewPager2 viewPager2;
    private ImagePagerAdapter viewpageradapter;
    private List<ImageModel> imageList;
    private DatabaseReference viewpagerref;

    // Auto-slide variables
    private Handler autoSlideHandler;
    private Runnable autoSlideRunnable;
    private final long AUTO_SLIDE_DELAY = 3000; // 3 seconds
    private SpringDotsIndicator dotsIndicator;
    private ImageView kartImage,Liked,you;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // Initialize RecyclerViews and ProgressBar
        Liked=findViewById(R.id.liked);
        kartImage=findViewById(R.id.kart);
        you=findViewById(R.id.you);
        dotsIndicator=findViewById(R.id.dots_indicator);
        viewPager2 = findViewById(R.id.view_pager);
        recyclerView = findViewById(R.id.recycler_view);
        cardRecyclerView = findViewById(R.id.card_recycler_view);
        card2RecyclerView = findViewById(R.id.card_recycler_view2);
        progressBar = findViewById(R.id.progressBar);
        pagerprogress=findViewById(R.id.pagerProgress);

        recyclerView.setHasFixedSize(true);
        cardRecyclerView.setHasFixedSize(true);
        card2RecyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        card2RecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        // Initialize data lists
        imageList=new ArrayList<>();
        resourceList = new ArrayList<>();
        cards = new ArrayList<>();
        cards2 = new ArrayList<>();

        // Initialize adapters
        viewpageradapter=new ImagePagerAdapter(MainActivity.this, imageList);
        adapter = new adapter(resourceList, MainActivity.this);
        cardAdapter = new CardAdapter(cards, MainActivity.this);
        cardAdapter2 = new CardAdapter2(cards2, MainActivity.this);

        // Set adapters to RecyclerViews
        viewPager2.setAdapter(viewpageradapter);

        recyclerView.setAdapter(adapter);
        cardRecyclerView.setAdapter(cardAdapter);
        card2RecyclerView.setAdapter(cardAdapter2);

        // Initialize database references
        viewpagerref= FirebaseDatabase.getInstance().getReference("SlideImages");
        imagesRef = FirebaseDatabase.getInstance().getReference("images");
        cardsImagesRef = FirebaseDatabase.getInstance().getReference("cardsImages");
        card2ImagesRef = FirebaseDatabase.getInstance().getReference("card2images");

        kartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kart=new Intent(MainActivity.this,Cart.class);
                startActivity(kart);
            }
        });
        Liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Liked=new Intent(MainActivity.this,  Liked.class);
                startActivity(Liked);
            }
        });
        you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent You=new Intent(MainActivity.this, you.class);
                startActivity(You);

            }
        });

        // Initialize auto-slide handler and runnable
        autoSlideHandler = new Handler();
        autoSlideRunnable = new Runnable() {
            @Override
            public void run() {
                int nextItem = viewPager2.getCurrentItem() + 1;
                if (nextItem >= viewpageradapter.getItemCount()) {
                    nextItem = 0;
                }
                viewPager2.setCurrentItem(nextItem, true);
                autoSlideHandler.postDelayed(this, AUTO_SLIDE_DELAY);
            }
        };

        // Load data from Firebase
        loadviewPager();
        loadImagesData();
        loadCardsData();
        loadCard2Data();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startAutoSlide();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopAutoSlide();
    }

    // Start auto-slide
    private void startAutoSlide() {
        autoSlideHandler.postDelayed(autoSlideRunnable, AUTO_SLIDE_DELAY);
    }

    // Stop auto-slide
    private void stopAutoSlide() {
        autoSlideHandler.removeCallbacks(autoSlideRunnable);
    }

    private void loadviewPager() {
        pagerprogress.setVisibility(View.VISIBLE);
        viewpagerref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imageList.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    ImageModel imageModel = datasnapshot.getValue(ImageModel.class);
                    if (imageModel != null) {
                        imageList.add(imageModel);
                    }
                }

                if (viewpageradapter == null) {
                    viewpageradapter = new ImagePagerAdapter(MainActivity.this, imageList);
                    viewPager2.setAdapter(viewpageradapter);
                } else {
                    viewpageradapter.notifyDataSetChanged();
                }

                pagerprogress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "Failed to read data", error.toException());
                pagerprogress.setVisibility(View.GONE);
            }
        });
        dotsIndicator.setViewPager2(viewPager2);
    }

    private void loadImagesData() {
        progressBar.setVisibility(View.VISIBLE);
        imagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resourceList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    res item = snapshot.getValue(res.class);
                    resourceList.add(item);
                    Log.e("MainActivity", "Image data loaded");
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "Database Error: " + error.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadCardsData() {
        progressBar.setVisibility(View.VISIBLE);
        cardsImagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cards.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Card card = snapshot.getValue(Card.class);
                    cards.add(card);
                }
                cardAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "Database Error: " + error.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadCard2Data() {
        progressBar.setVisibility(View.VISIBLE);
        card2ImagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cards2.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Card2 card = snapshot.getValue(Card2.class);
                    cards2.add(card);
                }
                cardAdapter2.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "Database Error: " + error.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}

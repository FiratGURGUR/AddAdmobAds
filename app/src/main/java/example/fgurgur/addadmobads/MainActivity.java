package example.fgurgur.addadmobads;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener {
    private AdView mAdView;//Banner reklamı için tanımladık
    Button btnGecis,btnOdul;
    InterstitialAd mInterstitialAd;//Geçiş Reklamı için ranımladık.
    private RewardedVideoAd mRewardedVideoAd;//Ödüllü reklam için tanımladık.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGecis = findViewById(R.id.btnGecis);
        btnGecis.setOnClickListener(this);
        btnOdul = findViewById(R.id.btnOdul);
        btnOdul.setOnClickListener(this);

        mAdView = findViewById(R.id.adView);//MainActivity'de ki adView'i değişkenimize atadık.
        AdRequest adRequest = new AdRequest.Builder().build(); //Reklam isteği oluşturuyor.
        mAdView.loadAd(adRequest); //Reklamı yüklüyor.




        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGecis:
                GecisReklam();
                break;
            case R.id.btnOdul:
                loadRewardedVideoAd();
                break;

             default:
                 break;
        }
    }



    public void GecisReklam(){
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Toast.makeText(MainActivity.this, "Kapatıldı.", Toast.LENGTH_SHORT).show();
                /*
                //Reklamı kapattığınız zaman 20 saniye sonra reklamı tekrar gösterir.
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reklamiYukle();
                    }
                }, 20000);
                super.onAdClosed();
                */
            }

            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                super.onAdLoaded();

            }
        });
        reklamiYukle();
    }

    private void reklamiYukle() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }


    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
        Toast.makeText(this, "Video Başlatılıyor.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRewardedVideoAdLoaded() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }else{
            Toast.makeText(this, "Hata Oluştu. Kapatıp tekrar deneyiniz", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(this, "Reklam açıldı.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        Toast.makeText(this, "Reklam başladı.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(this, "Reklamı kapattınız.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(this, "Uygulamadan ayrıldı.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Toast.makeText(this, "Reklam Yüklenemedi.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {
        Toast.makeText(this, "Reklam Tamamen izlendi.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }
}

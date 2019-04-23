package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.ads.MobileAds;

import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBAdditionalInformationDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBAddressDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBClientDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBLegalPersonDatabase;
import br.com.newoutsourcing.walletofclients.Repository.Database.Tables.TBPhysicalPersonDatabase;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash);
        this.onLoadDatabaseSession();
        this.onStartActivity();
    }

    private void onLoadDatabaseSession(){
        try{
            SessionDatabase.TB_CLIENT = TBClientDatabase.newInstance(SplashActivity.this);
            SessionDatabase.TB_PHYSICAL_PERSON = TBPhysicalPersonDatabase.newInstance(SplashActivity.this);
            SessionDatabase.TB_LEGAL_PERSON = TBLegalPersonDatabase.newInstance(SplashActivity.this);
            SessionDatabase.TB_ADDITIONAL_INFORMATION = TBAdditionalInformationDatabase.newInstance(SplashActivity.this);
            SessionDatabase.TB_ADDRESS = TBAddressDatabase.newInstance(SplashActivity.this);
        }catch (Exception ex){
            FunctionsApp.showMessageError(SplashActivity.this,"Erro","Ocorreu um erro ao inicializar a aplicação. Tente novamente!");
            FunctionsApp.closeActivity(SplashActivity.this);
        }
    }

    private void onStartActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FunctionsApp.startActivity(SplashActivity.this,ListClientActivity.class,null);
                FunctionsApp.closeActivity(SplashActivity.this);
            }
        },1000);
    }
}

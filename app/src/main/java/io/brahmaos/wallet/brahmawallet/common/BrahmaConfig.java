package io.brahmaos.wallet.brahmawallet.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.brahmaos.wallet.brahmawallet.R;
import io.brahmaos.wallet.brahmawallet.db.entity.TokenEntity;

/**
 * the project common config
 */

public class BrahmaConfig {

    private static BrahmaConfig instance = new BrahmaConfig();
    public static BrahmaConfig getInstance() {
        return instance;
    }

    private Context context;
    private SharedPreferences sharedPref = null;
    private static final String FIRST_OPEN_APP_FLAG = "new.first.open.app.flag";
    private static final String KEY_ASSETS_VISIBLE = "assets.visible";
    private static final String KEY_TOKEN_LIST_HASH = "token.list.hash";

    // first user app, show the guide
    private boolean firstOpenAppFlag = true;
    private String networkUrl;
    private String languageLocale;
    private boolean assetsVisible = true;
    private String tokenListHash;

    private String localKeystorePath;
    private List<TokenEntity> tokenEntities = new ArrayList<>();

    public void init(Context context) {
        this.context = context;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        localKeystorePath = context.getFilesDir().toString();
        networkUrl = sharedPref.getString(context.getString(R.string.key_network_url), BrahmaConst.MAINNET_URL);
        languageLocale = sharedPref.getString(context.getString(R.string.key_wallet_language), null);
        assetsVisible = sharedPref.getBoolean(KEY_ASSETS_VISIBLE, true);
        tokenListHash = sharedPref.getString(KEY_TOKEN_LIST_HASH, "");
        initLocale();
    }

    private void initTokens() {
        tokenEntities.add(new TokenEntity(0, "BrahmaOS", "BRM",
                "0xd7732e3783b0047aa251928960063f863ad022d8", R.drawable.icon_brm, String.valueOf(R.drawable.icon_brm)));
        tokenEntities.add(new TokenEntity(0, "Ethereum", "ETH",
                "", R.drawable.icon_eth, String.valueOf(R.drawable.icon_eth)));
        tokenEntities.add(new TokenEntity(0, "EOS", "EOS",
                "0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0", R.drawable.icon_eos, String.valueOf(R.drawable.icon_eos)));
        tokenEntities.add(new TokenEntity(0, "Tronix", "TRX",
                "0xf230b790e05390fc8295f4d3f60332c93bed42e2", R.drawable.icon_trx, String.valueOf(R.drawable.icon_trx)));
        tokenEntities.add(new TokenEntity(0, "VeChain", "VEN",
                "0xd850942ef8811f2a866692a623011bde52a462c1", R.drawable.icon_ven, String.valueOf(R.drawable.icon_ven)));
        tokenEntities.add(new TokenEntity(0, "OmiseGO", "OMG",
                "0xd26114cd6EE289AccF82350c8d8487fedB8A0C07", R.drawable.icon_omg, String.valueOf(R.drawable.icon_omg)));
        tokenEntities.add(new TokenEntity(0, "BNB", "BNB",
                "0xB8c77482e45F1F44dE1745F52C74426C631bDD52", R.drawable.icon_bnb, String.valueOf(R.drawable.icon_bnb)));
        tokenEntities.add(new TokenEntity(0, "ICON", "ICX",
                "0xb5a5f22694352c15b00323844ad545abb2b11028", R.drawable.icon_icx, String.valueOf(R.drawable.icon_icx)));
        tokenEntities.add(new TokenEntity(0, "Bytom", "BTM",
                "0xcb97e65f07da24d46bcdd078ebebd7c6e6e3d750", R.drawable.icon_bytom, String.valueOf(R.drawable.icon_bytom)));
        tokenEntities.add(new TokenEntity(0, "Populous", "PPT",
                "0xd4fa1460f537bb9085d22c7bccb5dd450ef28e3a", R.drawable.icon_ppt, String.valueOf(R.drawable.icon_ppt)));
        tokenEntities.add(new TokenEntity(0, "ZRX", "ZRX",
                "0xe41d2489571d322189246dafa5ebde1f4699f498", R.drawable.icon_zrx, String.valueOf(R.drawable.icon_zrx)));
        tokenEntities.add(new TokenEntity(0, "DGD", "DGD",
                "0xe0b7927c4af23765cb51314a0e0521a9645f0e2a", R.drawable.icon_dgd, String.valueOf(R.drawable.icon_dgd)));
        tokenEntities.add(new TokenEntity(0, "Zilliqa", "ZIL",
                "0x05f4a42e251f2d52b8ed15e9fedaacfcef1fad27", R.drawable.icon_zil, String.valueOf(R.drawable.icon_zil)));
        tokenEntities.add(new TokenEntity(0, "Maker", "MKR",
                "0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2", R.drawable.icon_mkr, String.valueOf(R.drawable.icon_mkr)));
        tokenEntities.add(new TokenEntity(0, "StatusNetwork", "SNT",
                "0x744d70fdbe2ba4cf95131626614a1763df805b9e", R.drawable.icon_snt, String.valueOf(R.drawable.icon_snt)));
        tokenEntities.add(new TokenEntity(0, "RHOC", "RHOC",
                "0x168296bb09e24a88805cb9c33356536b980d3fc5", R.drawable.icon_rhoc, String.valueOf(R.drawable.icon_rhoc)));
        tokenEntities.add(new TokenEntity(0, "Aeternity", "AE",
                "0x5ca9a71b1d01849c0a95490cc00559717fcf0d1d", R.drawable.icon_ae, String.valueOf(R.drawable.icon_ae)));
        tokenEntities.add(new TokenEntity(0, "AION", "AION",
                "0x4CEdA7906a5Ed2179785Cd3A40A69ee8bc99C466", R.drawable.icon_aion, String.valueOf(R.drawable.icon_aion)));
        tokenEntities.add(new TokenEntity(0, "Loopring", "LRC",
                "0xef68e7c694f40c8202821edf525de3782458639f", R.drawable.icon_lrc, String.valueOf(R.drawable.icon_lrc)));
        tokenEntities.add(new TokenEntity(0, "Golem", "GNT",
                "0xa74476443119A942dE498590Fe1f2454d7D4aC0d", R.drawable.icon_gnt, String.valueOf(R.drawable.icon_gnt)));
        tokenEntities.add(new TokenEntity(0, "Walton", "WTC",
                "0xb7cb1c96db6b22b0d3d9536e0108d062bd488f74", R.drawable.icon_wtc, String.valueOf(R.drawable.icon_wtc)));
        tokenEntities.add(new TokenEntity(0, "REP", "REP",
                "0xe94327d07fc17907b4db788e5adf2ed424addff6", R.drawable.icon_rep, String.valueOf(R.drawable.icon_rep)));
        /*tokenEntities.add(new TokenEntity(0, "BrahmaOS", "BRM(TEST)",
                "0xb958c57d1896823b8f4178a21e1bf6796371eac4", R.drawable.icon_eth));*/
    }

    public String getLocalKeystorePath() {
        return localKeystorePath;
    }

    public List<TokenEntity> getTokenEntities() {
        return tokenEntities;
    }

    public String getNetworkUrl() {
        return networkUrl;
    }

    public void setNetworkUrl(String networkUrl) {
        this.networkUrl = networkUrl;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.key_network_url), networkUrl);
        editor.apply();
    }

    public String getLanguageLocale() {
        return languageLocale;
    }

    public void setLanguageLocale(String languageLocale) {
        this.languageLocale = languageLocale;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.key_wallet_language), languageLocale);
        editor.apply();
    }

    public void initLocale() {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale newLocale = Locale.getDefault();
        if (languageLocale == null) {
            if (newLocale.getLanguage().equals("zh")) {
                languageLocale = BrahmaConst.LANGUAGE_CHINESE;
            } else {
                languageLocale = BrahmaConst.LANGUAGE_ENGLISH;
            }
        }
        if (languageLocale.equals(BrahmaConst.LANGUAGE_CHINESE)) {
            newLocale = Locale.CHINESE;
        } else {
            newLocale = Locale.ENGLISH;
        }
        config.setLocale(newLocale);
        resources.updateConfiguration(config, dm);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.key_wallet_language), languageLocale);
        editor.apply();
    }

    public void setLocale() {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale newLocale = Locale.getDefault();
        if (languageLocale == null) {
            if (newLocale.getLanguage().equals("zh")) {
                languageLocale = BrahmaConst.LANGUAGE_CHINESE;
            } else {
                languageLocale = BrahmaConst.LANGUAGE_ENGLISH;
            }
        }
        if (languageLocale.equals(BrahmaConst.LANGUAGE_CHINESE)) {
            newLocale = Locale.CHINESE;
        } else {
            newLocale = Locale.ENGLISH;
        }
        config.setLocale(newLocale);
        resources.updateConfiguration(config, dm);
    }

    public boolean isAssetsVisible() {
        return assetsVisible;
    }

    public void setAssetsVisible(boolean assetsVisible) {
        this.assetsVisible = assetsVisible;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(KEY_ASSETS_VISIBLE, assetsVisible);
        editor.apply();
    }

    public String getTokenListHash() {
        return tokenListHash;
    }

    public void setTokenListHash(String tokenListHash) {
        this.tokenListHash = tokenListHash;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_TOKEN_LIST_HASH, tokenListHash);
        editor.apply();
    }
}

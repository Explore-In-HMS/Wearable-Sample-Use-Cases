package com.myapps.wearenginepractices.slice;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.huawei.watch.kit.hiwear.HiWear;
import com.huawei.watch.kit.hiwear.p2p.HiWearMessage;
import com.huawei.watch.kit.hiwear.p2p.P2pClient;
import com.huawei.watch.kit.hiwear.p2p.SendCallback;
import com.myapps.wearenginepractices.MyApplication;
import com.myapps.wearenginepractices.ResourceTable;
import com.myapps.wearenginepractices.article.ArticleClickedListener;
import com.myapps.wearenginepractices.article.ArticleItemProvider;
import com.myapps.wearenginepractices.model.Article;
import com.myapps.wearenginepractices.utils.LogUtils;
import com.myapps.wearenginepractices.utils.UIUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;
import ohos.app.dispatcher.task.Revocable;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static ohos.agp.components.Component.AXIS_Y;

public class MainAbilitySlice extends AbilitySlice implements ArticleClickedListener {

    private final static String TAG = "MainAbilitySlice";
    private static final int MY_MODULE = 0x00201;
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, MY_MODULE, "MainAbilitySlice");

    private ListContainer listContainer;
    private CircularProgressView progressView;
    private Component errorContainer;
    private Text errorText;

    private Revocable revocable;

    private P2pClient mP2pClient;

    // Set the package name of your app on the phone
    private static final String TEST_PEER_PACKAGE = "com.myapps.wearenginepractice";
    // Set the fingerprint information for the phone app
    private static final String TEST_FINGERPRINT = "28D47989C2AAC2857CCC86AD73AAB5B48E68EA99DA48F64878741AF3978B5735";

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        listContainer = (ListContainer) findComponentById(ResourceTable.Id_lc_ability_main);
        listContainer.setMode(Component.OVAL_MODE);
        final int contentOffset = UIUtils.vpTpPixel(this, 80);
        listContainer.setContentOffSet(contentOffset, contentOffset);
        listContainer.setLongClickable(false);
        listContainer.setTouchFocusable(true);

        progressView = (CircularProgressView) findComponentById(ResourceTable.Id_cpv_ability_main);
        errorContainer = findComponentById(ResourceTable.Id_dl_ability_main_error_container);
        errorText = (Text) findComponentById(ResourceTable.Id_t_ability_main_error);

        // Step 1: Obtain a point-to-point communication object
        mP2pClient = HiWear.getP2pClient(this);

        // Step 2: Set the package name of your app on the phone
        mP2pClient.setPeerPkgName(TEST_PEER_PACKAGE);

        // Step 3: Set the fingerprint information for the phone app
        mP2pClient.setPeerFingerPrint(TEST_FINGERPRINT);

        findComponentById(ResourceTable.Id_t_ability_main_btn_retry).setClickedListener(component -> reload());

        reload();
    }

    private void reload() {
        if (revocable != null) {
            revocable.revoke();
        }

        progressView.setVisibility(Component.VISIBLE);
        errorContainer.setVisibility(Component.HIDE);
        listContainer.setVisibility(Component.HIDE);
        revocable = getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
            try {
                initListOnUIThread(MyApplication.getApiInteractor().loadArticles());
            } catch (Exception e) {
                LogUtils.e(TAG, "Failed to load articles: " + e.getMessage());
                showErrorUI(e);
            }
        });
    }

    private void showErrorUI(final Exception exception) {
        getUITaskDispatcher().asyncDispatch(() -> {
            if (exception instanceof UnknownHostException) {
                errorText.setText(ResourceTable.String_error_offline);
            } else {
                errorText.setText(ResourceTable.String_error_generic);
            }
            progressView.setVisibility(Component.HIDE);
            errorContainer.setVisibility(Component.VISIBLE);
            listContainer.setVisibility(Component.HIDE);
        });
    }

    private void initListOnUIThread(final List<Article> articleItems) {
        getUITaskDispatcher().asyncDispatch(() -> {
            progressView.setVisibility(Component.HIDE);
            errorContainer.setVisibility(Component.HIDE);
            listContainer.setVisibility(Component.VISIBLE);
            listContainer.setItemProvider(new ArticleItemProvider(this, articleItems, this));
            listContainer.requestFocus();
            listContainer.enableScrollBar(AXIS_Y, true);
        });
    }

    @Override
    public void onArticleItemClicked(final Article article) {

        startAbility(ArticleDetailAbility.getIntent(this, article));
        sendP2pMessage(article.getUrl());

        // Wear Engine Test Page
        //present(new WearEngineAbility(), new Intent());
    }

    @Override
    protected void onStop() {
        super.onStop();
        revocable.revoke();
    }

    /**
     * Send a short message to your app on the phone
     */
    private void sendP2pMessage(String newsUrl) {
        // Build a Message object
        HiWearMessage.Builder builder = new HiWearMessage.Builder();
        builder.setPayload(newsUrl.getBytes(StandardCharsets.UTF_8));
        HiWearMessage sendMessage = builder.build();

        // Create the callback method
        SendCallback sendCallback = new SendCallback() {
            @Override
            public void onSendResult(int resultCode) {
                HiLog.info(LABEL, "sendP2PMessage, resultCode:  %{public}d", resultCode);
            }

            @Override
            public void onSendProgress(long progress) {
                HiLog.info(LABEL, "sendP2PMessage, progress is:  %{public}d", progress);
            }
        };

        mP2pClient.send(sendMessage, sendCallback);
    }
}

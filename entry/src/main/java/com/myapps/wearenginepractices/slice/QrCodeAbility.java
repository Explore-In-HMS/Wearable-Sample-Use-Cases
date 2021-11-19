package com.myapps.wearenginepractices.slice;

import com.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import com.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import com.huawei.watch.kit.hiwear.HiWear;
import com.huawei.watch.kit.hiwear.p2p.HiWearMessage;
import com.huawei.watch.kit.hiwear.p2p.P2pClient;
import com.huawei.watch.kit.hiwear.p2p.Receiver;
import com.myapps.wearenginepractices.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Image;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.PixelMap;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class QrCodeAbility extends AbilitySlice {

    private P2pClient mP2pClient;
    private Image mQrCode;
    private Receiver mReceiver;

    private static final int MY_MODULE = 0x00201;

    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, MY_MODULE, "QrCodeAbilitySlice");

    // Set the package name of your app on the phone
    private static final String TEST_PEER_PACKAGE = "com.myapps.wearenginepractice";
    // Set the fingerprint information for the phone app
    private static final String TEST_FINGERPRINT = "28D47989C2AAC2857CCC86AD73AAB5B48E68EA99DA48F64878741AF3978B5735";

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);

        super.setUIContent(ResourceTable.Layout_ability_qr_code);

        mQrCode = (Image) findComponentById(ResourceTable.Id_qr_code);

        // Step 1: Obtain a point-to-point communication object
        mP2pClient = HiWear.getP2pClient(this);

        // Step 2: Set the package name of your app on the phone
        mP2pClient.setPeerPkgName(TEST_PEER_PACKAGE);

        // Step 3: Set the fingerprint information for the phone app
        mP2pClient.setPeerFingerPrint(TEST_FINGERPRINT);

        // Register Receiver
        registerReceiver();

    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver();
    }

    private void createEnglishQRCode(String qrCodeString) {
        new EventHandler(EventRunner.create("createEnglishQRCode")).postTask(() -> {
            PixelMap pixelMap = QRCodeEncoder.syncEncodeQRCode(qrCodeString,
                    BGAQRCodeUtil.dp2px(QrCodeAbility.this, 160));
            getUITaskDispatcher().asyncDispatch(() -> mQrCode.setPixelMap(pixelMap));
        });
    }

    private void registerReceiver() {
        mReceiver = message -> {
            switch (message.getType()) {
                // Receive the short message
                case HiWearMessage.MESSAGE_TYPE_DATA: {
                    HiLog.info(LABEL, "receive P2P message");
                    if (message.getData() != null) {
                        String msg = new String(message.getData(), StandardCharsets.UTF_8);
                        // ZXing
                        createEnglishQRCode(msg);

                    }
                    break;
                }
                // Receive the file
                case HiWearMessage.MESSAGE_TYPE_FILE: {
                    HiLog.info(LABEL, "receive file");
                    File file = message.getFile();
                    if (file != null && file.exists()) {
                        // receive file
                        HiLog.info(LABEL, "receive file");
                    }
                    break;
                }
                default:
                    HiLog.error(LABEL, "unsupported message type");
            }
        };
        mP2pClient.registerReceiver(mReceiver);
    }

    private void unregisterReceiver() {
        if (mP2pClient != null && mReceiver != null) {
            mP2pClient.unregisterReceiver(mReceiver);
        }
    }
}

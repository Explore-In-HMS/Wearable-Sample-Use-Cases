package com.myapps.wearenginepractices;

import com.myapps.wearenginepractices.slice.QrCodeAbility;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(QrCodeAbility.class.getName());

        setSwipeToDismiss(true);
    }
}

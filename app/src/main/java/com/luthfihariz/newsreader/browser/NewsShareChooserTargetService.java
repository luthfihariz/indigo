package com.luthfihariz.newsreader.browser;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.os.Build;
import android.service.chooser.ChooserTarget;
import android.service.chooser.ChooserTargetService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luthfihariz on 8/25/17.
 */

@TargetApi(Build.VERSION_CODES.M)
public class NewsShareChooserTargetService extends ChooserTargetService {
    @Override
    public List<ChooserTarget> onGetChooserTargets(ComponentName targetActivityName,
                                                   IntentFilter matchedFilter) {
        return new ArrayList<>();
    }
}

package io.github.emotionbug.openheadunitshortcut;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/** A NAV-button entry point that opens Open Headunit without acquiring audio focus. */
public final class MainActivity extends Activity {
    // The installed application ID differs from Open Headunit's Java namespace.
    private static final String OPEN_HEADUNIT_PACKAGE = "com.andrerinas.headunitrevived";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // Resolve the app's launcher entry instead of depending on an internal activity
            // name. Use a fresh intent so OEM navigation extras never leak into the app.
            Intent launch = getPackageManager().getLaunchIntentForPackage(OPEN_HEADUNIT_PACKAGE);
            if (launch == null) {
                Toast.makeText(this, R.string.open_headunit_missing, Toast.LENGTH_LONG).show();
                return;
            }
            // Keep Open Headunit in its own task. Its launcher decides how to resume an
            // existing projection session; this shortcut never clears or restarts that task.
            launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launch);
        } catch (ActivityNotFoundException | SecurityException e) {
            // The app may be disabled, removed or blocked between lookup and launch.
            Toast.makeText(this, R.string.open_headunit_unavailable, Toast.LENGTH_LONG).show();
        } finally {
            // Theme.NoDisplay requires finishing before onResume, including error paths.
            finish();
        }
    }
}

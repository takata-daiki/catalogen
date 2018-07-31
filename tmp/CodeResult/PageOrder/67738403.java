package cgeo.geocaching;

import cgeo.calendar.ICalendar;
import cgeo.geocaching.cgData.StorageLocation;
import cgeo.geocaching.activity.AbstractActivity;
import cgeo.geocaching.activity.Progress;
import cgeo.geocaching.apps.cache.GeneralAppsFactory;
import cgeo.geocaching.apps.cache.navi.NavigationAppFactory;
import cgeo.geocaching.connector.ConnectorFactory;
import cgeo.geocaching.connector.IConnector;
import cgeo.geocaching.enumerations.LoadFlags;
import cgeo.geocaching.enumerations.LoadFlags.RemoveFlag;
import cgeo.geocaching.enumerations.LogType;
import cgeo.geocaching.enumerations.WaypointType;
import cgeo.geocaching.geopoint.GeopointFormatter;
import cgeo.geocaching.network.HtmlImage;
import cgeo.geocaching.network.Parameters;
import cgeo.geocaching.utils.BaseUtils;
import cgeo.geocaching.utils.CancellableHandler;
import cgeo.geocaching.utils.ClipboardUtils;
import cgeo.geocaching.utils.CryptUtils;
import cgeo.geocaching.utils.TranslationUtils;
import cgeo.geocaching.utils.UnknownTagsHandler;

import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitleProvider;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import android.R.color;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Activity to handle all single-cache-stuff.
 *
 * e.g. details, description, logs, waypoints, inventory...
 */
public class CacheDetailActivity extends AbstractActivity {

    private static final int MENU_SHARE = 12;
    private static final int MENU_CALENDAR = 11;
    private static final int MENU_CACHES_AROUND = 10;
    private static final int MENU_BROWSER = 7;
    private static final int MENU_DEFAULT_NAVIGATION = 13;

    private static final int CONTEXT_MENU_WAYPOINT_EDIT = 1234;
    private static final int CONTEXT_MENU_WAYPOINT_DUPLICATE = 1235;
    private static final int CONTEXT_MENU_WAYPOINT_DELETE = 1236;
    private static final int CONTEXT_MENU_WAYPOINT_NAVIGATE = 1238;
    private static final int CONTEXT_MENU_WAYPOINT_CACHES_AROUND = 1239;
    private static final int CONTEXT_MENU_WAYPOINT_DEFAULT_NAVIGATION = 1240;

    private cgGeo geolocation;
    private cgCache cache;
    private final Progress progress = new Progress();
    private SearchResult search;
    private final LocationUpdater locationUpdater = new LocationUpdater();
    private CharSequence clickedItemText = null;
    private int contextMenuWPIndex = -1;

    /**
     * A {@link List} of all available pages.
     *
     * @todo Move to adapter
     */
    private final List<Page> pageOrder = new ArrayList<Page>();

    /**
     * Instances of all {@link PageViewCreator}.
     */
    private final Map<Page, PageViewCreator> viewCreators = new HashMap<Page, PageViewCreator>();

    /**
     * The {@link ViewPager} for this activity.
     */
    private ViewPager viewPager;

    /**
     * The {@link ViewPagerAdapter} for this activity.
     */
    private ViewPagerAdapter viewPagerAdapter;

    /**
     * The {@link TitlePageIndicator} for this activity.
     */
    private TitlePageIndicator titleIndicator;

    /**
     * If another activity is called and can modify the data of this activity, we refresh it on resume.
     */
    private boolean refreshOnResume = false;

    // some views that must be available from everywhere // TODO: Reference can block GC?
    private TextView cacheDistanceView;

    public CacheDetailActivity() {
        // identifier for manual
        super("c:geolocation-cache-details");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize the main view and set a default title
        setTheme();
        setContentView(R.layout.cacheview);
        setTitle(res.getString(R.string.cache));

        if (geolocation == null) {
            geolocation = app.startGeo(locationUpdater);
        }

        String geocode = null;
        String guid = null;
        String name = null;

        // TODO Why can it happen that search is not null? onCreate should be called only once and it is not set before.
        if (search != null) {
            cache = search.getFirstCacheFromResult(LoadFlags.LOADALLDBONLY);
            if (cache != null && cache.getGeocode() != null) {
                geocode = cache.getGeocode();
            }
        }

        // get parameters
        final Bundle extras = getIntent().getExtras();
        final Uri uri = getIntent().getData();

        // try to get data from extras
        if (geocode == null && extras != null) {
            geocode = extras.getString("geocode");
            name = extras.getString("name");
            guid = extras.getString("guid");
        }

        // try to get data from URI
        if (geocode == null && guid == null && uri != null) {
            String uriHost = uri.getHost().toLowerCase();
            String uriPath = uri.getPath().toLowerCase();
            String uriQuery = uri.getQuery();

            if (uriQuery != null) {
                Log.i(Settings.tag, "Opening URI: " + uriHost + uriPath + "?" + uriQuery);
            } else {
                Log.i(Settings.tag, "Opening URI: " + uriHost + uriPath);
            }

            if (uriHost.contains("geocaching.com")) {
                geocode = uri.getQueryParameter("wp");
                guid = uri.getQueryParameter("guid");

                if (StringUtils.isNotBlank(geocode)) {
                    geocode = geocode.toUpperCase();
                    guid = null;
                } else if (StringUtils.isNotBlank(guid)) {
                    geocode = null;
                    guid = guid.toLowerCase();
                } else {
                    showToast(res.getString(R.string.err_detail_open));
                    finish();
                    return;
                }
            } else if (uriHost.contains("coord.info")) {
                if (uriPath != null && uriPath.startsWith("/gc")) {
                    geocode = uriPath.substring(1).toUpperCase();
                } else {
                    showToast(res.getString(R.string.err_detail_open));
                    finish();
                    return;
                }
            }
        }

        // no given data
        if (geocode == null && guid == null) {
            showToast(res.getString(R.string.err_detail_cache));
            finish();
            return;
        }

        // Go4Cache
        if (StringUtils.isNotBlank(geocode)) {
            app.setAction(geocode);
        }

        final LoadCacheHandler loadCacheHandler = new LoadCacheHandler();

        try {
            String title = res.getString(R.string.cache);
            if (StringUtils.isNotBlank(name)) {
                title = name;
            } else if (null != geocode && StringUtils.isNotBlank(geocode)) { // can't be null, but the compiler doesn't understand StringUtils.isNotBlank()
                title = geocode.toUpperCase();
            }
            progress.show(this, title, res.getString(R.string.cache_dialog_loading_details), true, loadCacheHandler.cancelMessage());
        } catch (Exception e) {
            // nothing, we lost the window
        }

        // initialize ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);

        titleIndicator = (TitlePageIndicator) findViewById(R.id.pager_indicator);
        titleIndicator.setViewPager(viewPager);
        titleIndicator.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (Settings.isOpenLastDetailsPage()) {
                    Settings.setLastDetailsPage(position);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // switch to entry page (last used or 2)
        final int entryPageIndex = Settings.isOpenLastDetailsPage() ? Settings.getLastDetailsPage() : 1;
        if (viewPagerAdapter.getCount() < entryPageIndex) {
            for (int i = 0; i <= entryPageIndex; i++) {
                // we can't switch to a page that is out of bounds, so we add null-pages
                pageOrder.add(null);
            }
        }
        viewPager.setCurrentItem(entryPageIndex, false);

        // Initialization done. Let's load the data with the given information.
        new LoadCacheThread(geocode, guid, loadCacheHandler).start();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Maybe keyboard hidden or display orientation changed. No need for update the UI or something else.
    }

    @Override
    public void onResume() {
        super.onResume();

        if (geolocation == null) {
            geolocation = app.startGeo(locationUpdater);
        }

        if (refreshOnResume) {
            notifyDataSetChanged();
            refreshOnResume = false;
        }
    }

    @Override
    public void onDestroy() {
        if (geolocation != null) {
            geolocation = app.removeGeo();
        }

        super.onDestroy();
    }

    @Override
    public void onStop() {
        if (geolocation != null) {
            geolocation = app.removeGeo();
        }

        super.onStop();
    }

    @Override
    public void onPause() {
        if (geolocation != null) {
            geolocation = app.removeGeo();
        }

        super.onPause();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, view, info);
        final int viewId = view.getId();
        contextMenuWPIndex = -1;
        switch (viewId) {
            case R.id.value: // coordinates
                clickedItemText = ((TextView) view).getText();
                buildOptionsContextmenu(menu, viewId, res.getString(R.string.copy_coords), true);
                break;
            case R.id.shortdesc:
                clickedItemText = ((TextView) view).getText();
                buildOptionsContextmenu(menu, viewId, res.getString(R.string.copy_desc), false);
                break;
            case R.id.longdesc:
                // combine short and long description
                String shortDesc = cache.getShortDescription();
                if (shortDesc.compareTo("") == 0) {
                    clickedItemText = ((TextView) view).getText();
                } else {
                    clickedItemText = shortDesc + "\n\n" + ((TextView) view).getText();
                }
                buildOptionsContextmenu(menu, viewId, res.getString(R.string.copy_desc), false);
                break;
            case R.id.personalnote:
                clickedItemText = ((TextView) view).getText();
                buildOptionsContextmenu(menu, viewId, res.getString(R.string.copy_personalnote), true);
                break;
            case R.id.hint:
                clickedItemText = ((TextView) view).getText();
                buildOptionsContextmenu(menu, viewId, res.getString(R.string.copy_hint), false);
                break;
            case R.id.log:
                clickedItemText = ((TextView) view).getText();
                buildOptionsContextmenu(menu, viewId, res.getString(R.string.copy_log), false);
                break;
            case -1:
                if (null != cache.getWaypoints()) {
                    try {
                        final ViewGroup parent = ((ViewGroup) view.getParent());
                        for (int i = 0; i < parent.getChildCount(); i++) {
                            if (parent.getChildAt(i) == view) {
                                final List<cgWaypoint> sortedWaypoints = new ArrayList<cgWaypoint>(cache.getWaypoints());
                                Collections.sort(sortedWaypoints);
                                final cgWaypoint waypoint = sortedWaypoints.get(i);
                                final int index = cache.getWaypoints().indexOf(waypoint);
                                menu.setHeaderTitle(res.getString(R.string.waypoint));
                                menu.add(CONTEXT_MENU_WAYPOINT_EDIT, index, 0, R.string.waypoint_edit);
                                menu.add(CONTEXT_MENU_WAYPOINT_DUPLICATE, index, 0, R.string.waypoint_duplicate);
                                contextMenuWPIndex = index;
                                if (waypoint.isUserDefined()) {
                                    menu.add(CONTEXT_MENU_WAYPOINT_DELETE, index, 0, R.string.waypoint_delete);
                                }
                                if (waypoint.getCoords() != null) {
                                    menu.add(CONTEXT_MENU_WAYPOINT_DEFAULT_NAVIGATION, index, 0, NavigationAppFactory.getDefaultNavigationApplication(this).getName());
                                    menu.add(CONTEXT_MENU_WAYPOINT_NAVIGATE, index, 0, R.string.cache_menu_navigate).setIcon(android.R.drawable.ic_menu_mapmode);
                                    menu.add(CONTEXT_MENU_WAYPOINT_CACHES_AROUND, index, 0, R.string.cache_menu_around);
                                }
                                break;
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                break;
            default:
                break;
        }
    }

    private void buildOptionsContextmenu(ContextMenu menu, int viewId, String copyPrompt, boolean copyOnly) {
        menu.setHeaderTitle(res.getString(R.string.options_context_menu_title));
        menu.add(viewId, 1, 0, copyPrompt);
        if (!copyOnly) {
            if (clickedItemText.length() > TranslationUtils.translationTextLengthToWarn) {
                showToast(res.getString(R.string.translate_length_warning));
            }
            menu.add(viewId, 2, 0, res.getString(R.string.translate_to_sys_lang, Locale.getDefault().getDisplayLanguage()));
            if (Settings.isUseEnglish() && Locale.getDefault() != Locale.ENGLISH) {
                menu.add(viewId, 3, 0, res.getString(R.string.translate_to_english));
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final int groupId = item.getGroupId();
        final int index = item.getItemId();
        switch (groupId) {
            case R.id.value:
            case R.id.shortdesc:
            case R.id.longdesc:
            case R.id.personalnote:
            case R.id.hint:
            case R.id.log:
                switch (index) {
                    case 1: // copy
                        ClipboardUtils.copyToClipboard(clickedItemText);
                        return true;
                    case 2: // translate to system language
                        TranslationUtils.startActivityTranslate(this, Locale.getDefault().getLanguage(), clickedItemText.toString());
                        return true;
                    case 3: // translate to English
                        TranslationUtils.startActivityTranslate(this, Locale.ENGLISH.getLanguage(), clickedItemText.toString());
                        return true;
                    default:
                        break;
                }

                break;
            case CONTEXT_MENU_WAYPOINT_EDIT:
                if (cache.hasWaypoints() && index < cache.getWaypoints().size()) {
                    final cgWaypoint waypoint = cache.getWaypoints().get(index);
                    Intent editIntent = new Intent(this, cgeowaypointadd.class);
                    editIntent.putExtra("waypoint", waypoint.getId());
                    startActivity(editIntent);
                    refreshOnResume = true;
                }
                break;
            case CONTEXT_MENU_WAYPOINT_DUPLICATE:
                if (cache.duplicateWaypoint(index)) {
                    notifyDataSetChanged();
                }
                break;
            case CONTEXT_MENU_WAYPOINT_DELETE:
                if (cache.deleteWaypoint(index)) {
                    notifyDataSetChanged();
                }
                break;
            case CONTEXT_MENU_WAYPOINT_DEFAULT_NAVIGATION:
 {
                final cgWaypoint waypoint = cache.getWaypoint(index);
                if (waypoint != null) {
                    NavigationAppFactory.startDefaultNavigationApplication(geolocation, this, null, null, waypoint, null);
                }
                }
                break;
            case CONTEXT_MENU_WAYPOINT_NAVIGATE:
 {
                final cgWaypoint waypoint = cache.getWaypoint(contextMenuWPIndex);
                if (waypoint != null) {
                    NavigationAppFactory.showNavigationMenu(geolocation, this, null, null, waypoint, null);
                }
            }
                break;
            case CONTEXT_MENU_WAYPOINT_CACHES_AROUND:
 {
                final cgWaypoint waypoint = cache.getWaypoint(index);
                if (waypoint != null) {
                    cgeocaches.startActivityCachesAround(this, waypoint.getCoords());
                }
            }
                break;
            default:
                return onOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (null != cache) {
            menu.add(0, MENU_DEFAULT_NAVIGATION, 0, NavigationAppFactory.getDefaultNavigationApplication(this).getName()).setIcon(android.R.drawable.ic_menu_compass); // default navigation tool

            final SubMenu subMenu = menu.addSubMenu(1, 0, 0, res.getString(R.string.cache_menu_navigate)).setIcon(android.R.drawable.ic_menu_mapmode);
            NavigationAppFactory.addMenuItems(subMenu, this);
            GeneralAppsFactory.addMenuItems(subMenu, this, cache);

            menu.add(1, MENU_CALENDAR, 0, res.getString(R.string.cache_menu_event)).setIcon(android.R.drawable.ic_menu_agenda); // add event to calendar
            addVisitMenu(menu, cache);
            menu.add(0, MENU_CACHES_AROUND, 0, res.getString(R.string.cache_menu_around)).setIcon(android.R.drawable.ic_menu_rotate); // caches around
            menu.add(1, MENU_BROWSER, 0, res.getString(R.string.cache_menu_browser)).setIcon(R.drawable.ic_menu_globe); // browser
            menu.add(0, MENU_SHARE, 0, res.getString(R.string.cache_menu_share)).setIcon(android.R.drawable.ic_menu_share); // share cache
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(MENU_DEFAULT_NAVIGATION).setVisible(null != cache.getCoords());
        menu.findItem(MENU_CALENDAR).setVisible(cache.canBeAddedToCalendar());
        menu.findItem(MENU_CACHES_AROUND).setVisible(null != cache.getCoords() && cache.supportsCachesAround());
        menu.findItem(MENU_BROWSER).setVisible(cache.canOpenInBrowser());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int menuItem = item.getItemId();

        // no menu selected, but a new sub menu shown
        if (menuItem == 0) {
            return false;
        }

        if (menuItem == MENU_DEFAULT_NAVIGATION) {
            startDefaultNavigation();
            return true;
        } else if (menuItem == MENU_LOG_VISIT) {
            refreshOnResume = true;
            cache.logVisit(this);
            return true;
        } else if (menuItem == MENU_BROWSER) {
            cache.openInBrowser(this);
            return true;
        } else if (menuItem == MENU_CACHES_AROUND) {
            cachesAround();
            return true;
        } else if (menuItem == MENU_CALENDAR) {
            addToCalendarWithIntent();
            return true;
        } else if (menuItem == MENU_SHARE) {
            if (cache != null) {
                cache.shareCache(this, res);
                return true;
            }
            return false;
        }
        if (NavigationAppFactory.onMenuItemSelected(item, geolocation, this, cache, search, null, null)) {
            return true;
        }
        if (GeneralAppsFactory.onMenuItemSelected(item, this, cache)) {
            return true;
        }

        int logType = menuItem - MENU_LOG_VISIT_OFFLINE;
        cache.logOffline(this, LogType.getById(logType));
        return true;
    }

    private class LoadCacheHandler extends CancellableHandler {
        @Override
        public void handleRegularMessage(final Message msg) {
            if (cgBase.UPDATE_LOAD_PROGRESS_DETAIL == msg.what && msg.obj instanceof String) {
                updateStatusMsg((String) msg.obj);
            } else {
                if (search == null) {
                    showToast(res.getString(R.string.err_dwld_details_failed));

                    finish();
                    return;
                }

                if (search.getError() != null) {
                    showToast(res.getString(R.string.err_dwld_details_failed) + " " + search.getError().getErrorString(res) + ".");

                    finish();
                    return;
                }

                updateStatusMsg(res.getString(R.string.cache_dialog_loading_details_status_render));

                // Data loaded, we're ready to show it!
                notifyDataSetChanged();
            }
        }

        private void updateStatusMsg(final String msg) {
            progress.setMessage(res.getString(R.string.cache_dialog_loading_details)
                    + "\n\n"
                    + msg);
        }

        @Override
        public void handleCancel(final Object extra) {
            finish();
        }

    }

    private void notifyDataSetChanged() {
        if (search == null) {
            return;
        }

        cache = search.getFirstCacheFromResult(LoadFlags.LOADALLDBONLY);

        if (cache == null) {
            progress.dismiss();
            showToast(res.getString(R.string.err_detail_cache_find_some));
            finish();
            return;
        }

        // notify all creators that the data has changed
        for (PageViewCreator creator : viewCreators.values()) {
            creator.notifyDataSetChanged();
        }

        // actionbar: title and icon (default: mystery-icon)
        if (StringUtils.isNotBlank(cache.getName())) {
            setTitle(cache.getName() + " (" + cache.getGeocode().toUpperCase() + ")");
        } else {
            setTitle(cache.getGeocode().toUpperCase());
        }
        ((TextView) findViewById(R.id.actionbar_title)).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(cache.getType().markerId), null, null, null);

        // add available pages (remove old pages first)
        pageOrder.clear();

        pageOrder.add(Page.WAYPOINTS);
        pageOrder.add(Page.DETAILS);
        pageOrder.add(Page.DESCRIPTION);
        if (CollectionUtils.isNotEmpty(cache.getLogs(true))) {
            pageOrder.add(Page.LOGS);
        }
        if (CollectionUtils.isNotEmpty(cache.getLogs(false))) {
            pageOrder.add(Page.LOGSFRIENDS);
        }
        if (CollectionUtils.isNotEmpty(cache.getInventory())) {
            pageOrder.add(Page.INVENTORY);
        }

        // switch to page 2 (index 1) if we're out of bounds
        if (viewPager.getCurrentItem() < 0 || viewPager.getCurrentItem() >= viewPagerAdapter.getCount()) {
            viewPager.setCurrentItem(1, false);
        }

        // notify the adapter that the data has changed
        viewPagerAdapter.notifyDataSetChanged();

        // notify the indicator that the data has changed
        titleIndicator.notifyDataSetChanged();

        // rendering done! remove progress-popup if any there
        progress.dismiss();
    }

    private class LocationUpdater implements UpdateLocationCallback {
        @Override
        public void updateLocation(cgGeo geo) {
            if (geo == null) {
                return;
            }
            if (cacheDistanceView == null) {
                return;
            }

            try {
                StringBuilder dist = new StringBuilder();

                if (geo.coordsNow != null && cache != null && cache.getCoords() != null) {
                    dist.append(cgBase.getHumanDistance(geo.coordsNow.distanceTo(cache.getCoords())));
                }

                if (cache != null && cache.getElevation() != null) {
                    if (geo.altitudeNow != null) {
                        double diff = (cache.getElevation() - geo.altitudeNow);
                        if (diff >= 0) {
                            dist.append(" ↗");
                        } else if (diff < 0) {
                            dist.append(" ↘");
                        }
                        if (Settings.isUseMetricUnits()) {
                            dist.append(Math.abs((int) diff));
                            dist.append(" m");
                        } else {
                            dist.append(Math.abs((int) (diff * 3.2808399)));
                            dist.append(" ft");
                        }
                    }
                }

                cacheDistanceView.setText(dist.toString());
                cacheDistanceView.bringToFront();
            } catch (Exception e) {
                Log.w(Settings.tag, "Failed to update location.");
            }
        }
    }

    /**
     * Loads the cache with the given geocode or guid.
     */
    private class LoadCacheThread extends Thread {

        private CancellableHandler handler = null;
        private String geocode;
        private String guid;

        public LoadCacheThread(final String geocode, final String guid, final CancellableHandler handlerIn) {
            handler = handlerIn;

            if (StringUtils.isBlank(geocode) && StringUtils.isBlank(guid)) {
                showToast(res.getString(R.string.err_detail_cache_forgot));

                finish();
                return;
            }

            this.geocode = geocode;
            this.guid = guid;
        }

        @Override
        public void run() {
            search = cgBase.searchByGeocode(geocode, StringUtils.isBlank(geocode) ? guid : null, 0, false, handler);
            handler.sendMessage(new Message());
        }
    }

    /**
     * Starts activity to search for caches near this cache.
     *
     * Also finishes this activity.
     */
    private void cachesAround() {
        cgeocaches.startActivityCachesAround(this, cache.getCoords());

        finish();
    }

    private void addToCalendarWithIntent() {

        final boolean calendarAddOnAvailable = cgBase.isIntentAvailable(this, ICalendar.INTENT);

        if (calendarAddOnAvailable) {
            final Parameters params = new Parameters(
                    ICalendar.PARAM_NAME, cache.getName(),
                    ICalendar.PARAM_NOTE, StringUtils.defaultString(cache.getPersonalNote()),
                    ICalendar.PARAM_HIDDEN_DATE, String.valueOf(cache.getHiddenDate().getTime()),
                    ICalendar.PARAM_URL, StringUtils.defaultString(cache.getUrl()),
                    ICalendar.PARAM_COORDS, cache.getCoords() == null ? "" : cache.getCoords().format(GeopointFormatter.Format.LAT_LON_DECMINUTE_RAW),
                    ICalendar.PARAM_LOCATION, StringUtils.defaultString(cache.getLocation()),
                    ICalendar.PARAM_SHORT_DESC, StringUtils.defaultString(cache.getShortDescription())
                    );

            startActivity(new Intent(ICalendar.INTENT,
                    Uri.parse(ICalendar.URI_SCHEME + "://" + ICalendar.URI_HOST + "?" + params.toString())));
        } else {
            showToast(res.getString(R.string.helper_calendar_missing));
        }
    }

    /**
     * Creates a {@link List} of all coordinates (cache and waypoints) for the current cache.
     *
     * @return A {@link List} of all coordinates
     */
    public List<cgCoord> getCoordinates() {
        List<cgCoord> coordinates = new ArrayList<cgCoord>();

        // cache
        try {
            final cgCoord coords = new cgCoord();
            coords.setCoordType("cache");
            if (StringUtils.isNotBlank(cache.getName())) {
                coords.setName(cache.getName());
            } else {
                coords.setName(cache.getGeocode().toUpperCase());
            }
            coords.setCoords(cache.getCoords());
            coordinates.add(coords);
        } catch (Exception e) {
            Log.e(Settings.tag, "CacheDetailActivity.getCoordinates (cache)", e);
        }

        // waypoints
        try {
            if (cache.hasWaypoints()) {
                for (cgWaypoint waypoint : cache.getWaypoints()) {
                    if (null != waypoint.getCoords()) {
                        final cgCoord coords = new cgCoord();
                        coords.setCoordType("waypoint");
                        coords.setName(waypoint.getName());
                        coords.setCoords(waypoint.getCoords());
                        coordinates.add(coords);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(Settings.tag, "CacheDetailActivity.getCoordinates (waypoint)", e);
        }

        return coordinates;
    }

    /**
     * Tries to navigate to the {@link cgCache} of this activity.
     */
    private void startDefaultNavigation() {
        if (cache == null || cache.getCoords() == null) {
            showToast(res.getString(R.string.err_location_unknown));
            return;
        }

        NavigationAppFactory.startDefaultNavigationApplication(geolocation, this, cache, search, null, null);
    }

    /**
     * Wrapper for the referenced method in the xml-layout.
     */
    public void startDefaultNavigation(@SuppressWarnings("unused") View view) {
        startDefaultNavigation();
    }

    /**
     * referenced from XML view
     */
    public void showNavigationMenu(@SuppressWarnings("unused") View view) {
        showNavigationMenu();
    }

    private void showNavigationMenu() {
        NavigationAppFactory.showNavigationMenu(geolocation, this, cache, search, null, null);
    }

    /**
     * Opens a dialog to do actions on an username
     */
    private class UserActionsClickListener implements View.OnClickListener {

        public void onClick(View view) {
            if (view == null) {
                return;
            }
            if (!cache.supportsUserActions()) {
                return;
            }

            clickedItemText = ((TextView) view).getText().toString();

            final CharSequence[] items = {res.getString(R.string.user_menu_view_hidden),
                    res.getString(R.string.user_menu_view_found),
                    res.getString(R.string.user_menu_open_browser)
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(CacheDetailActivity.this);
            builder.setTitle(res.getString(R.string.user_menu_title) + " " + clickedItemText);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    switch (item) {
                        case 0:
                            cgeocaches.startActivityOwner(CacheDetailActivity.this, clickedItemText.toString());
                            return;
                        case 1:
                            cgeocaches.startActivityUserName(CacheDetailActivity.this, clickedItemText.toString());
                            return;
                        case 2:
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.geocaching.com/profile/?u=" + URLEncoder.encode(clickedItemText.toString()))));
                            return;
                        default:
                            break;
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public static void startActivity(final Context context, final String geocode) {
        final Intent detailIntent = new Intent(context, CacheDetailActivity.class);
        detailIntent.putExtra("geocode", geocode.toUpperCase());
        context.startActivity(detailIntent);
    }

    /**
     * The ViewPagerAdapter for scrolling through pages of the CacheDetailActivity.
     */
    private class ViewPagerAdapter extends PagerAdapter implements TitleProvider {

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public void finishUpdate(View container) {
        }

        @Override
        public int getCount() {
            return pageOrder.size();
        }

        @Override
        public Object instantiateItem(View container, int position) {
            final Page page = pageOrder.get(position);

            PageViewCreator creator = viewCreators.get(page);

            if (null == creator && null != page) {
                // The creator is not instantiated yet, let's do it.
                switch (page) {
                    case DETAILS:
                        creator = new DetailsViewCreator();
                        break;

                    case DESCRIPTION:
                        creator = new DescriptionViewCreator();
                        break;

                    case LOGS:
                        creator = new LogsViewCreator(true);
                        break;

                    case LOGSFRIENDS:
                        creator = new LogsViewCreator(false);
                        break;

                    case WAYPOINTS:
                        creator = new WaypointsViewCreator();
                        break;

                    case INVENTORY:
                        creator = new InventoryViewCreator();
                        break;
                }
                viewCreators.put(page, creator);
            }

            View view = null;

            try {
                if (null != creator) {
                    // Result from getView() is maybe cached, but it should be valid because the
                    // creator should be informed about data-changes with notifyDataSetChanged()
                    view = creator.getView();
                    ((ViewPager) container).addView(view, 0);
                }
            } catch (Exception e) {
                Log.e(Settings.tag, "ViewPagerAdapter.instantiateItem ", e);
            }

            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public int getItemPosition(Object object) {
            // We are doing the caching. So pretend that the view is gone.
            // The ViewPager will get it back in instantiateItem()
            return POSITION_NONE;
        }

        @Override
        public String getTitle(int position) {
            final Page page = pageOrder.get(position);
            if (null == page) {
                return "";
            }
            // show number of waypoints directly in waypoint title
            if (page == Page.WAYPOINTS) {
                int waypointCount = (cache.hasWaypoints() ? cache.getWaypoints().size() : 0);
                return res.getQuantityString(R.plurals.waypoints, waypointCount, waypointCount);
            }
            return res.getString(page.titleStringId);
        }
    }

    /**
     * Enum of all possible pages with methods to get the view and a title.
     */
    private enum Page {
        DETAILS(R.string.detail),
        DESCRIPTION(R.string.cache_description),
        LOGS(R.string.cache_logs),
        LOGSFRIENDS(R.string.cache_logsfriends),
        WAYPOINTS(R.string.cache_waypoints),
        INVENTORY(R.string.cache_inventory);

        final private int titleStringId;

        private Page(final int titleStringId) {
            this.titleStringId = titleStringId;
        }
    }

    private class AttributeViewBuilder {
        private ViewGroup attributeIconsLayout; // layout for attribute icons
        private ViewGroup attributeDescriptionsLayout; // layout for attribute descriptions
        private boolean attributesShowAsIcons = true; // default: show icons
        /**
         * True, if the cache was imported with an older version of c:geo.
         * These older versions parsed the attribute description from the tooltip in the web
         * page and put them into the DB. No icons can be matched for these.
         */
        private boolean noAttributeIconsFound = false;
        private int attributeBoxMaxWidth;

        public void fillView(final LinearLayout attributeBox) {
            // first ensure that the view is empty
            attributeBox.removeAllViews();

            // maximum width for attribute icons is screen width - paddings of parents
            attributeBoxMaxWidth = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getWidth();
            ViewParent child = attributeBox;
            do {
                if (child instanceof View) {
                    attributeBoxMaxWidth -= ((View) child).getPaddingLeft() + ((View) child).getPaddingRight();
                }
                child = child.getParent();
            } while (child != null);

            // delete views holding description / icons
            attributeDescriptionsLayout = null;
            attributeIconsLayout = null;

            attributeBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // toggle between attribute icons and descriptions
                    toggleAttributeDisplay(attributeBox, attributeBoxMaxWidth);
                }
            });

            // icons or text?
            //
            // also show icons when noAttributeImagesFound == true. Explanation:
            //  1. no icons could be found in the first invocation of this method
            //  2. user refreshes cache from web
            //  3. now this method is called again
            //  4. attributeShowAsIcons is false but noAttributeImagesFound is true
            //     => try to show them now
            if (attributesShowAsIcons || noAttributeIconsFound) {
                showAttributeIcons(attributeBox, attributeBoxMaxWidth);
            } else {
                showAttributeDescriptions(attributeBox);
            }
        }

        /**
         * lazy-creates the layout holding the icons of the chaches attributes
         * and makes it visible
         */
        private void showAttributeIcons(LinearLayout attribBox, int parentWidth) {
            if (attributeIconsLayout == null) {
                attributeIconsLayout = createAttributeIconsLayout(parentWidth);
                // no matching icons found? show text
                if (noAttributeIconsFound) {
                    showAttributeDescriptions(attribBox);
                    return;
                }
            }
            attribBox.removeAllViews();
            attribBox.addView(attributeIconsLayout);
            attributesShowAsIcons = true;
        }

        /**
         * lazy-creates the layout holding the discriptions of the chaches attributes
         * and makes it visible
         */
        private void showAttributeDescriptions(LinearLayout attribBox) {
            if (attributeDescriptionsLayout == null) {
                attributeDescriptionsLayout = createAttributeDescriptionsLayout();
            }
            attribBox.removeAllViews();
            attribBox.addView(attributeDescriptionsLayout);
            attributesShowAsIcons = false;
        }

        /**
         * toggle attribute descriptions and icons
         */
        private void toggleAttributeDisplay(LinearLayout attribBox, int parentWidth) {
            // Don't toggle when there are no icons to show.
            if (noAttributeIconsFound) {
                return;
            }

            // toggle
            if (attributesShowAsIcons) {
                showAttributeDescriptions(attribBox);
            } else {
                showAttributeIcons(attribBox, parentWidth);
            }
        }

        private ViewGroup createAttributeIconsLayout(int parentWidth) {
            LinearLayout rows = new LinearLayout(CacheDetailActivity.this);
            rows.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            rows.setOrientation(LinearLayout.VERTICAL);

            LinearLayout attributeRow = newAttributeIconsRow();
            rows.addView(attributeRow);

            noAttributeIconsFound = true;

            final String packageName = cgeoapplication.getInstance().getBaseContext().getPackageName();
            for (String attributeName : cache.getAttributes()) {
                boolean strikethru = attributeName.endsWith("_no");
                // cut off _yes / _no
                if (attributeName.endsWith("_no") || attributeName.endsWith("_yes")) {
                    attributeName = attributeName.substring(0, attributeName.lastIndexOf("_"));
                }
                // check if another attribute icon fits in this row
                attributeRow.measure(0, 0);
                int rowWidth = attributeRow.getMeasuredWidth();
                FrameLayout fl = (FrameLayout) getLayoutInflater().inflate(R.layout.attribute_image, null);
                ImageView iv = (ImageView) fl.getChildAt(0);
                if ((parentWidth - rowWidth) < iv.getLayoutParams().width) {
                    // make a new row
                    attributeRow = newAttributeIconsRow();
                    rows.addView(attributeRow);
                }

                // dynamically search icon of the attribute
                Drawable d = null;
                int id = res.getIdentifier("attribute_" + attributeName, "drawable", packageName);
                if (id > 0) {
                    noAttributeIconsFound = false;
                    d = res.getDrawable(id);
                    iv.setImageDrawable(d);
                    // strike through?
                    if (strikethru) {
                        // generate strikethru image with same properties as attribute image
                        ImageView strikethruImage = new ImageView(CacheDetailActivity.this);
                        strikethruImage.setLayoutParams(iv.getLayoutParams());
                        d = res.getDrawable(R.drawable.attribute__strikethru);
                        strikethruImage.setImageDrawable(d);
                        fl.addView(strikethruImage);
                    }
                } else {
                    d = res.getDrawable(R.drawable.attribute_icon_not_found);
                    iv.setImageDrawable(d);
                }

                attributeRow.addView(fl);
            }

            return rows;
        }

        private LinearLayout newAttributeIconsRow() {
            LinearLayout rowLayout = new LinearLayout(CacheDetailActivity.this);
            rowLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            return rowLayout;
        }

        private ViewGroup createAttributeDescriptionsLayout() {
            final LinearLayout descriptions = (LinearLayout) getLayoutInflater().inflate(
                    R.layout.attribute_descriptions, null);
            TextView attribView = (TextView) descriptions.getChildAt(0);

            StringBuilder buffer = new StringBuilder();
            final String packageName = cgeoapplication.getInstance().getBaseContext().getPackageName();
            final List<String> attributes = cache.getAttributes();

            for (String attribute : attributes) {
                // dynamically search for a translation of the attribute
                int id = res.getIdentifier("attribute_" + attribute, "string", packageName);
                if (id > 0) {
                    String translated = res.getString(id);
                    if (StringUtils.isNotBlank(translated)) {
                        attribute = translated;
                    }
                }
                if (buffer.length() > 0) {
                    buffer.append('\n');
                }
                buffer.append(attribute);
            }

            if (noAttributeIconsFound) {
                buffer.append("\n\n").append(res.getString(R.string.cache_attributes_no_icons));
            }

            attribView.setText(buffer);

            return descriptions;
        }
    }

    private interface PageViewCreator {
        /**
         * Returns a validated view.
         *
         * @return
         */
        public View getDispatchedView();

        /**
         * Returns a (maybe cached) view.
         *
         * @return
         */
        public View getView();

        /**
         * Handles changed data-sets.
         */
        public void notifyDataSetChanged();
    }

    /**
     * Creator for details-view.
     */
    private class DetailsViewCreator implements PageViewCreator {
        /**
         * The main view for this creator
         */
        private ScrollView view;

        /**
         * Reference to the details list, so that the helper-method can access it without an additional argument
         */
        private LinearLayout detailsList;

        // TODO Do we need this thread-references?
        private StoreCacheThread storeThread;
        private RefreshCacheThread refreshThread;
        private Thread watchlistThread;

        @Override
        public void notifyDataSetChanged() {
            // There is a lot of data in this view, let's update everything
            view = null;
        }

        @Override
        public View getView() {
            if (view == null) {
                view = (ScrollView) getDispatchedView();
            }

            return view;
        }

        @Override
        public View getDispatchedView() {
            if (cache == null) {
                // something is really wrong
                return null;
            }

            view = (ScrollView) getLayoutInflater().inflate(R.layout.cacheview_details, null);

            // Start loading preview map
            if (Settings.isStoreOfflineMaps()) {
                new PreviewMapTask().execute((Void) null);
            }

            detailsList = (LinearLayout) view.findViewById(R.id.details_list);

            // cache name (full name)
            Spannable span = (new Spannable.Factory()).newSpannable(Html.fromHtml(cache.getName()).toString());
            if (cache.isDisabled() || cache.isArchived()) { // strike
                span.setSpan(new StrikethroughSpan(), 0, span.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            addCacheDetail(R.string.cache_name, span);

            // cache type
            addCacheDetail(R.string.cache_type, cache.getType().getL10n());

            // size
            if (null != cache.getSize() && cache.showSize()) {
                addCacheDetail(R.string.cache_size, cache.getSize().getL10n());
            }

            // gc-code
            addCacheDetail(R.string.cache_geocode, cache.getGeocode().toUpperCase());

            // cache state
            if (cache.isLogOffline() || cache.isArchived() || cache.isDisabled() || cache.isPremiumMembersOnly() || cache.isFound()) {
                final StringBuilder state = new StringBuilder();
                if (cache.isLogOffline()) {
                    state.append(res.getString(R.string.cache_status_offline_log));
                }
                if (cache.isFound()) {
                    if (state.length() > 0) {
                        state.append(", ");
                    }
                    state.append(res.getString(R.string.cache_status_found));
                }
                if (cache.isArchived()) {
                    if (state.length() > 0) {
                        state.append(", ");
                    }
                    state.append(res.getString(R.string.cache_status_archived));
                }
                if (cache.isDisabled()) {
                    if (state.length() > 0) {
                        state.append(", ");
                    }
                    state.append(res.getString(R.string.cache_status_disabled));
                }
                if (cache.isPremiumMembersOnly()) {
                    if (state.length() > 0) {
                        state.append(", ");
                    }
                    state.append(res.getString(R.string.cache_status_premium));
                }

                addCacheDetail(R.string.cache_status, state.toString());
            }

            // distance
            cacheDistanceView = addCacheDetail(R.string.cache_distance, cache.getDistance() != null ? "~" + cgBase.getHumanDistance(cache.getDistance()) : "--");

            // difficulty
            if (cache.getDifficulty() > 0) {
                addStarRating(R.string.cache_difficulty, cache.getDifficulty());
            }

            // terrain
            if (cache.getTerrain() > 0) {
                addStarRating(R.string.cache_terrain, cache.getTerrain());
            }

            // rating
            if (cache.getRating() > 0) {
                final RelativeLayout itemLayout = addStarRating(R.string.cache_rating, cache.getRating());
                if (cache.getVotes() > 0) {
                    final TextView itemAddition = (TextView) itemLayout.findViewById(R.id.addition);
                    itemAddition.setText("(" + cache.getVotes() + ")");
                    itemAddition.setVisibility(View.VISIBLE);
                }
            }

            // favourite count
            addCacheDetail(R.string.cache_favourite, cache.getFavoritePoints() + "×");

            // own rating
            if (cache.getMyVote() > 0) {
                addStarRating(R.string.cache_own_rating, cache.getMyVote());
            }

            // cache author
            if (StringUtils.isNotBlank(cache.getOwner()) || StringUtils.isNotBlank(cache.getOwnerReal())) {
                TextView ownerView = addCacheDetail(R.string.cache_owner, "");
                if (StringUtils.isNotBlank(cache.getOwner())) {
                    ownerView.setText(Html.fromHtml(cache.getOwner()), TextView.BufferType.SPANNABLE);
                } else if (StringUtils.isNotBlank(cache.getOwnerReal())) {
                    ownerView.setText(Html.fromHtml(cache.getOwnerReal()), TextView.BufferType.SPANNABLE);
                }
                ownerView.setOnClickListener(new UserActionsClickListener());
            }

            // cache hidden
            if (cache.getHiddenDate() != null) {
                long time = cache.getHiddenDate().getTime();
                if (time > 0) {
                    String dateString = cgBase.formatFullDate(time);
                    if (cache.isEventCache()) {
                        dateString = DateUtils.formatDateTime(cgeoapplication.getInstance().getBaseContext(), time, DateUtils.FORMAT_SHOW_WEEKDAY) + ", " + dateString;
                    }
                    addCacheDetail(cache.isEventCache() ? R.string.cache_event : R.string.cache_hidden, dateString);
                }
            }

            // cache location
            if (StringUtils.isNotBlank(cache.getLocation())) {
                addCacheDetail(R.string.cache_location, cache.getLocation());
            }

            // cache coordinates
            if (cache.getCoords() != null) {
                TextView valueView = addCacheDetail(R.string.cache_coordinates, cache.getCoords().toString());
                valueView.setOnClickListener(new View.OnClickListener() {
                            private int position = 0;
                            private GeopointFormatter.Format[] availableFormats = new GeopointFormatter.Format[] {
                                    GeopointFormatter.Format.LAT_LON_DECMINUTE,
                                    GeopointFormatter.Format.LAT_LON_DECSECOND,
                                    GeopointFormatter.Format.LAT_LON_DECDEGREE
                            };

                            // rotate coordinate formats on click
                            @Override
                            public void onClick(View view) {
                                position = (position + 1) % availableFormats.length;

                                final TextView valueView = (TextView) view.findViewById(R.id.value);
                                valueView.setText(cache.getCoords().format(availableFormats[position]));
                            }
                        });
                registerForContextMenu(valueView);
            }

            // cache attributes
            if (cache.hasAttributes()) {
                new AttributeViewBuilder().fillView((LinearLayout) view.findViewById(R.id.attributes_innerbox));
                view.findViewById(R.id.attributes_box).setVisibility(View.VISIBLE);
            }

            updateOfflineBox();

            // watchlist
            Button buttonWatchlistAdd = (Button) view.findViewById(R.id.add_to_watchlist);
            Button buttonWatchlistRemove = (Button) view.findViewById(R.id.remove_from_watchlist);
            buttonWatchlistAdd.setOnClickListener(new AddToWatchlistClickListener());
            buttonWatchlistRemove.setOnClickListener(new RemoveFromWatchlistClickListener());
            updateWatchlistBox();

            updateDebugInfos();

            // data license
            IConnector connector = ConnectorFactory.getConnector(cache);
            if (connector != null) {
                String license = connector.getLicenseText(cache);
                if (StringUtils.isNotBlank(license)) {
                    ((LinearLayout) view.findViewById(R.id.license_box)).setVisibility(View.VISIBLE);
                    TextView licenseView = ((TextView) view.findViewById(R.id.license));
                    licenseView.setText(Html.fromHtml(license), BufferType.SPANNABLE);
                    licenseView.setClickable(true);
                    licenseView.setMovementMethod(LinkMovementMethod.getInstance());
                } else {
                    ((LinearLayout) view.findViewById(R.id.license_box)).setVisibility(View.GONE);
                }
            }

            if (geolocation != null) {
                locationUpdater.updateLocation(geolocation);
            }

            return view;
        }

        private TextView addCacheDetail(final int nameId, final CharSequence value) {
            final RelativeLayout layout = (RelativeLayout) getLayoutInflater().inflate(R.layout.cache_item, null);
            ((TextView) layout.findViewById(R.id.name)).setText(res.getString(nameId));
            final TextView valueView = (TextView) layout.findViewById(R.id.value);
            valueView.setText(value);
            detailsList.addView(layout);
            return valueView;
        }

        private RelativeLayout addStarRating(final int nameId, final float value) {
            final RelativeLayout layout = (RelativeLayout) getLayoutInflater().inflate(R.layout.cache_layout, null);
            TextView viewName = (TextView) layout.findViewById(R.id.name);
            TextView viewValue = (TextView) layout.findViewById(R.id.value);
            LinearLayout layoutStars = (LinearLayout) layout.findViewById(R.id.stars);

            viewName.setText(res.getString(nameId));
            viewValue.setText(String.format("%.1f", value) + ' ' + res.getString(R.string.cache_rating_of) + " 5");
            layoutStars.addView(cgBase.createStarRating(value, 5, CacheDetailActivity.this), 1);

            detailsList.addView(layout);
            return layout;
        }

        private class StoreCacheHandler extends CancellableHandler {
            @Override
            public void handleRegularMessage(Message msg) {
                if (cgBase.UPDATE_LOAD_PROGRESS_DETAIL == msg.what && msg.obj instanceof String) {
                    updateStatusMsg((String) msg.obj);
                } else {
                    storeThread = null;

                    try {
                        cache = search.getFirstCacheFromResult(LoadFlags.LOADALLDBONLY); // reload cache details
                    } catch (Exception e) {
                        showToast(res.getString(R.string.err_store_failed));

                        Log.e(Settings.tag, "CacheDetailActivity.storeCacheHandler: " + e.toString());
                    }

                    CacheDetailActivity.this.notifyDataSetChanged();
                }
            }

            private void updateStatusMsg(final String msg) {
                progress.setMessage(res.getString(R.string.cache_dialog_offline_save_message)
                        + "\n\n"
                        + msg);
            }
        }

        private class RefreshCacheHandler extends CancellableHandler {
            @Override
            public void handleRegularMessage(Message msg) {
                if (cgBase.UPDATE_LOAD_PROGRESS_DETAIL == msg.what && msg.obj instanceof String) {
                    updateStatusMsg((String) msg.obj);
                } else {
                    refreshThread = null;

                    try {
                        cache = search.getFirstCacheFromResult(LoadFlags.LOADALLDBONLY); // reload cache details
                    } catch (Exception e) {
                        showToast(res.getString(R.string.err_refresh_failed));

                        Log.e(Settings.tag, "CacheDetailActivity.refreshCacheHandler: " + e.toString());
                    }

                    CacheDetailActivity.this.notifyDataSetChanged();
                }
            }

            private void updateStatusMsg(final String msg) {
                progress.setMessage(res.getString(R.string.cache_dialog_refresh_message)
                        + "\n\n"
                        + msg);
            }
        }

        private class DropCacheHandler extends Handler {
            @Override
            public void handleMessage(Message msg) {
                CacheDetailActivity.this.notifyDataSetChanged();
            }
        }

        private class StoreCacheClickListener implements View.OnClickListener {
            public void onClick(View arg0) {
                if (progress.isShowing()) {
                    showToast(res.getString(R.string.err_detail_still_working));
                    return;
                }

                final StoreCacheHandler storeCacheHandler = new StoreCacheHandler();

                progress.show(CacheDetailActivity.this, res.getString(R.string.cache_dialog_offline_save_title), res.getString(R.string.cache_dialog_offline_save_message), true, storeCacheHandler.cancelMessage());

                if (storeThread != null) {
                    storeThread.interrupt();
                }

                storeThread = new StoreCacheThread(storeCacheHandler);
                storeThread.start();
            }
        }

        private class RefreshCacheClickListener implements View.OnClickListener {
            public void onClick(View arg0) {
                if (progress.isShowing()) {
                    showToast(res.getString(R.string.err_detail_still_working));
                    return;
                }

                final RefreshCacheHandler refreshCacheHandler = new RefreshCacheHandler();

                progress.show(CacheDetailActivity.this, res.getString(R.string.cache_dialog_refresh_title), res.getString(R.string.cache_dialog_refresh_message), true, refreshCacheHandler.cancelMessage());

                if (refreshThread != null) {
                    refreshThread.interrupt();
                }

                refreshThread = new RefreshCacheThread(refreshCacheHandler);
                refreshThread.start();
            }
        }

        private class StoreCacheThread extends Thread {
            final private CancellableHandler handler;

            public StoreCacheThread(final CancellableHandler handler) {
                this.handler = handler;
            }

            @Override
            public void run() {
                cache.store(CacheDetailActivity.this, handler);
            }
        }

        private class RefreshCacheThread extends Thread {
            final private CancellableHandler handler;

            public RefreshCacheThread(final CancellableHandler handler) {
                this.handler = handler;
            }

            @Override
            public void run() {
                app.removeCache(cache.getGeocode(), EnumSet.of(RemoveFlag.REMOVECACHE));
                search = cgBase.searchByGeocode(cache.getGeocode(), null, 0, true, handler);

                handler.sendEmptyMessage(0);
            }
        }

        private class DropCacheClickListener implements View.OnClickListener {
            public void onClick(View arg0) {
                if (progress.isShowing()) {
                    showToast(res.getString(R.string.err_detail_still_working));
                    return;
                }

                final DropCacheHandler dropCacheHandler = new DropCacheHandler();

                progress.show(CacheDetailActivity.this, res.getString(R.string.cache_dialog_offline_drop_title), res.getString(R.string.cache_dialog_offline_drop_message), true, null);
                new DropCacheThread(dropCacheHandler).start();
            }
        }

        private class DropCacheThread extends Thread {

            private Handler handler = null;

            public DropCacheThread(Handler handlerIn) {
                handler = handlerIn;
            }

            @Override
            public void run() {
                cgBase.dropCache(cache, handler);
            }
        }

        /**
         * Abstract Listener for add / remove buttons for watchlist
         */
        private abstract class AbstractWatchlistClickListener implements View.OnClickListener {
            public void doExecute(int titleId, int messageId, Thread thread) {
                if (progress.isShowing()) {
                    showToast(res.getString(R.string.err_watchlist_still_managing));
                    return;
                }
                progress.show(CacheDetailActivity.this, res.getString(titleId), res.getString(messageId), true, null);

                if (watchlistThread != null) {
                    watchlistThread.interrupt();
                }

                watchlistThread = thread;
                watchlistThread.start();
            }
        }

        /**
         * Listener for "add to watchlist" button
         */
        private class AddToWatchlistClickListener extends AbstractWatchlistClickListener {
            public void onClick(View arg0) {
                doExecute(R.string.cache_dialog_watchlist_add_title,
                        R.string.cache_dialog_watchlist_add_message,
                        new WatchlistAddThread(new WatchlistHandler()));
            }
        }

        /**
         * Listener for "remove from watchlist" button
         */
        private class RemoveFromWatchlistClickListener extends AbstractWatchlistClickListener {
            public void onClick(View arg0) {
                doExecute(R.string.cache_dialog_watchlist_remove_title,
                        R.string.cache_dialog_watchlist_remove_message,
                        new WatchlistRemoveThread(new WatchlistHandler()));
            }
        }

        /** Thread to add this cache to the watchlist of the user */
        private class WatchlistAddThread extends Thread {
            private final Handler handler;

            public WatchlistAddThread(Handler handler) {
                this.handler = handler;
            }

            @Override
            public void run() {
                handler.sendEmptyMessage(cgBase.addToWatchlist(cache));
            }
        }

        /** Thread to remove this cache from the watchlist of the user */
        private class WatchlistRemoveThread extends Thread {
            private final Handler handler;

            public WatchlistRemoveThread(Handler handler) {
                this.handler = handler;
            }

            @Override
            public void run() {
                handler.sendEmptyMessage(cgBase.removeFromWatchlist(cache));
            }
        }

        /**
         * shows/hides buttons, sets text in watchlist box
         */
        private void updateWatchlistBox() {
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.watchlist_box);
            boolean supportsWatchList = cache.supportsWatchList();
            layout.setVisibility(supportsWatchList ? View.VISIBLE : View.GONE);
            if (!supportsWatchList) {
                return;
            }
            Button buttonAdd = (Button) view.findViewById(R.id.add_to_watchlist);
            Button buttonRemove = (Button) view.findViewById(R.id.remove_from_watchlist);
            TextView text = (TextView) view.findViewById(R.id.watchlist_text);

            if (cache.isOnWatchlist()) {
                buttonAdd.setVisibility(View.GONE);
                buttonRemove.setVisibility(View.VISIBLE);
                text.setText(R.string.cache_watchlist_on);
            } else {
                buttonAdd.setVisibility(View.VISIBLE);
                buttonRemove.setVisibility(View.GONE);
                text.setText(R.string.cache_watchlist_not_on);
            }
        }

        /**
         * Handler, called when watchlist add or remove is done
         */
        private class WatchlistHandler extends Handler {
            @Override
            public void handleMessage(Message msg) {
                watchlistThread = null;
                progress.dismiss();
                if (msg.what == -1) {
                    showToast(res.getString(R.string.err_watchlist_failed));
                } else {
                    updateWatchlistBox();
                }
            }
        }

        private void updateOfflineBox() {
            // offline use
            final TextView offlineText = (TextView) view.findViewById(R.id.offline_text);
            final Button offlineRefresh = (Button) view.findViewById(R.id.offline_refresh);
            final Button offlineStore = (Button) view.findViewById(R.id.offline_store);

            if (cache.getListId() >= StoredList.STANDARD_LIST_ID) {
                long diff = (System.currentTimeMillis() / (60 * 1000)) - (cache.getDetailedUpdate() / (60 * 1000)); // minutes

                String ago = "";
                if (diff < 15) {
                    ago = res.getString(R.string.cache_offline_time_mins_few);
                } else if (diff < 50) {
                    ago = res.getString(R.string.cache_offline_time_about) + " " + diff + " " + res.getString(R.string.cache_offline_time_mins);
                } else if (diff < 90) {
                    ago = res.getString(R.string.cache_offline_time_about) + " " + res.getString(R.string.cache_offline_time_hour);
                } else if (diff < (48 * 60)) {
                    ago = res.getString(R.string.cache_offline_time_about) + " " + (diff / 60) + " " + res.getString(R.string.cache_offline_time_hours);
                } else {
                    ago = res.getString(R.string.cache_offline_time_about) + " " + (diff / (24 * 60)) + " " + res.getString(R.string.cache_offline_time_days);
                }

                offlineText.setText(res.getString(R.string.cache_offline_stored) + "\n" + ago);
                offlineRefresh.setOnClickListener(new StoreCacheClickListener());

                offlineStore.setText(res.getString(R.string.cache_offline_drop));
                offlineStore.setClickable(true);
                offlineStore.setOnClickListener(new DropCacheClickListener());
            } else {
                offlineText.setText(res.getString(R.string.cache_offline_not_ready));
                offlineRefresh.setOnClickListener(new RefreshCacheClickListener());

                offlineStore.setText(res.getString(R.string.cache_offline_store));
                offlineStore.setClickable(true);
                offlineStore.setOnClickListener(new StoreCacheClickListener());
            }
            offlineRefresh.setVisibility(cache.supportsRefresh() ? View.VISIBLE : View.GONE);
            offlineRefresh.setClickable(true);
        }

        private void updateDebugInfos() {

            if (Settings.isDebugInfos()) {
                ((LinearLayout) view.findViewById(R.id.debug_box)).setVisibility(View.VISIBLE);
                final TextView internalsText = (TextView) view.findViewById(R.id.debug_text);

                String sl = "Storage location: ";
                if (cache.getStorageLocation().contains(StorageLocation.CACHE)) {
                    sl += "Cache ";
                }
                if (cache.getStorageLocation().contains(StorageLocation.DATABASE)) {
                    sl += "Database (" + cache.getListId() + ")";
                }

                internalsText.setText(sl);
            } else {
                ((LinearLayout) view.findViewById(R.id.debug_box)).setVisibility(View.GONE);
            }
        }

        private class PreviewMapTask extends AsyncTask<Void, Void, BitmapDrawable> {
            @Override
            protected BitmapDrawable doInBackground(Void... params) {
                BitmapDrawable image = null;

                try {
                    final String latlonMap = cache.getCoords().format(GeopointFormatter.Format.LAT_LON_DECDEGREE_COMMA);

                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);

                    final int width = metrics.widthPixels;
                    final int height = (int) (110 * metrics.density);

                    // TODO move this code to StaticMapProvider and use its constant values
                    final String markerUrl = cgBase.urlencode_rfc3986("http://cgeo.carnero.cc/_markers/my_location_mdpi.png");

                    final HtmlImage mapGetter = new HtmlImage(CacheDetailActivity.this, cache.getGeocode(), false, 0, false);
                    image = mapGetter.getDrawable("http://maps.google.com/maps/api/staticmap?zoom=15&size=" + width + "x" + height + "&maptype=roadmap&markers=icon%3A" + markerUrl + "%7Cshadow:false%7C" + latlonMap + "&sensor=false");
                } catch (Exception e) {
                    Log.w(Settings.tag, "CacheDetailActivity.PreviewMapTask", e);
                }

                return image;
            }

            @Override
            protected void onPostExecute(BitmapDrawable image) {
                if (image == null) {
                    return;
                }

                final Bitmap bitmap = image.getBitmap();
                if (bitmap == null || bitmap.getWidth() <= 10) {
                    return;
                }

                ((ImageView) view.findViewById(R.id.map_preview)).setImageDrawable(image);
                view.findViewById(R.id.map_preview_box).setVisibility(View.VISIBLE);
            }
        }

    }

    private class DescriptionViewCreator implements PageViewCreator {

        ScrollView view;

        @Override
        public void notifyDataSetChanged() {
            view = null;
        }

        @Override
        public View getView() {
            if (view == null) {
                view = (ScrollView) getDispatchedView();
            }

            return view;
        }

        @Override
        public View getDispatchedView() {
            if (cache == null) {
                // something is really wrong
                return null;
            }

            view = (ScrollView) getLayoutInflater().inflate(R.layout.cacheview_description, null);

            // cache short description
            if (StringUtils.isNotBlank(cache.getShortDescription())) {
                new LoadDescriptionTask().execute(cache.getShortDescription(), view.findViewById(R.id.shortdesc), null);
                registerForContextMenu(view.findViewById(R.id.shortdesc));
            }

            // long description
            if (StringUtils.isNotBlank(cache.getDescription())) {
                if (Settings.isAutoLoadDescription()) {
                    loadLongDescription();
                } else {
                    Button showDesc = (Button) view.findViewById(R.id.show_description);
                    showDesc.setVisibility(View.VISIBLE);
                    showDesc.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            loadLongDescription();
                        }
                    });
                }
            }

            // cache personal note
            if (StringUtils.isNotBlank(cache.getPersonalNote())) {
                ((LinearLayout) view.findViewById(R.id.personalnote_box)).setVisibility(View.VISIBLE);

                TextView personalNoteText = (TextView) view.findViewById(R.id.personalnote);
                personalNoteText.setVisibility(View.VISIBLE);
                personalNoteText.setText(cache.getPersonalNote(), TextView.BufferType.SPANNABLE);
                personalNoteText.setMovementMethod(LinkMovementMethod.getInstance());
                registerForContextMenu(personalNoteText);
            }
            else {
                ((LinearLayout) view.findViewById(R.id.personalnote_box)).setVisibility(View.GONE);
            }

            // cache hint and spoiler images
            if (StringUtils.isNotBlank(cache.getHint()) || CollectionUtils.isNotEmpty(cache.getSpoilers())) {
                ((LinearLayout) view.findViewById(R.id.hint_box)).setVisibility(View.VISIBLE);
            } else {
                ((LinearLayout) view.findViewById(R.id.hint_box)).setVisibility(View.GONE);
            }

            if (StringUtils.isNotBlank(cache.getHint())) {
                TextView hintView = ((TextView) view.findViewById(R.id.hint));
                hintView.setText(CryptUtils.rot13(cache.getHint().trim()));
                hintView.setVisibility(View.VISIBLE);
                hintView.setClickable(true);
                hintView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // code hint
                        TextView hintView = (TextView) view;
                        hintView.setText(CryptUtils.rot13(hintView.getText().toString()));
                    }
                });
                registerForContextMenu(hintView);
            } else {
                TextView hintView = ((TextView) view.findViewById(R.id.hint));
                hintView.setVisibility(View.GONE);
                hintView.setClickable(false);
                hintView.setOnClickListener(null);
            }

            if (CollectionUtils.isNotEmpty(cache.getSpoilers())) {
                TextView spoilerlinkView = ((TextView) view.findViewById(R.id.hint_spoilerlink));
                spoilerlinkView.setVisibility(View.VISIBLE);
                spoilerlinkView.setClickable(true);
                spoilerlinkView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if (cache == null || CollectionUtils.isEmpty(cache.getSpoilers())) {
                            showToast(res.getString(R.string.err_detail_no_spoiler));
                            return;
                        }

                        cgeoimages.startActivitySpoilerImages(CacheDetailActivity.this, cache.getGeocode(), cache.getSpoilers());
                    }
                });
            } else {
                TextView spoilerlinkView = ((TextView) view.findViewById(R.id.hint_spoilerlink));
                spoilerlinkView.setVisibility(View.GONE);
                spoilerlinkView.setClickable(true);
                spoilerlinkView.setOnClickListener(null);
            }

            return view;
        }

        private void loadLongDescription() {
            Button showDesc = (Button) view.findViewById(R.id.show_description);
            showDesc.setVisibility(View.GONE);
            showDesc.setOnClickListener(null);
            view.findViewById(R.id.loading).setVisibility(View.VISIBLE);

            new LoadDescriptionTask().execute(cache.getDescription(), view.findViewById(R.id.longdesc), view.findViewById(R.id.loading));
            registerForContextMenu(view.findViewById(R.id.longdesc));
        }

        /**
         * Loads the description in background. <br />
         * <br />
         * Params:
         * <ol>
         * <li>description string (String)</li>
         * <li>target description view (TextView)</li>
         * <li>loading indicator view (View, may be null)</li>
         * </ol>
         */
        private class LoadDescriptionTask extends AsyncTask<Object, Void, Void> {
            private View loadingIndicatorView;
            private TextView descriptionView;
            private String descriptionString;
            private Spanned description;

            private class HtmlImageCounter implements Html.ImageGetter {

                private int imageCount = 0;

                @Override
                public Drawable getDrawable(String url) {
                    imageCount++;
                    return null;
                }

                public int getImageCount() {
                    return imageCount;
                }
            }

            @Override
            protected Void doInBackground(Object... params) {
                try {
                    descriptionString = ((String) params[0]).trim();
                    descriptionView = (TextView) params[1];
                    loadingIndicatorView = (View) params[2];
                } catch (Exception e) {
                }

                // Fast preview: parse only HTML without loading any images
                HtmlImageCounter imageCounter = new HtmlImageCounter();
                final UnknownTagsHandler unknownTagsHandler = new UnknownTagsHandler();
                description = Html.fromHtml(descriptionString, imageCounter, unknownTagsHandler);
                publishProgress();
                if (imageCounter.getImageCount() > 0) {
                    // Complete view: parse again with loading images - if necessary ! If there are any images causing problems the user can see at least the preview
                    description = Html.fromHtml(descriptionString, new HtmlImage(CacheDetailActivity.this, cache.getGeocode(), true, cache.getListId(), false), unknownTagsHandler);
                    publishProgress();
                }

                // if description has HTML table, add a note at the end of the long description
                if (unknownTagsHandler.isTableDetected() && descriptionView == view.findViewById(R.id.longdesc)) {
                    final int startPos = description.length();
                    ((Editable) description).append("\n\n" + res.getString(R.string.cache_description_table_note));
                    ((Editable) description).setSpan(new StyleSpan(Typeface.ITALIC), startPos, description.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    publishProgress();
                }
                return null;
            }

            /*
             * (non-Javadoc)
             *
             * @see android.os.AsyncTask#onProgressUpdate(Progress[])
             */
            @Override
            protected void onProgressUpdate(Void... values) {
                if (description != null) {
                    if (StringUtils.isNotBlank(descriptionString)) {
                        descriptionView.setText(description, TextView.BufferType.SPANNABLE);
                        descriptionView.setMovementMethod(LinkMovementMethod.getInstance());
                        fixBlackTextColor(descriptionView, descriptionString);
                    }

                    descriptionView.setVisibility(View.VISIBLE);
                } else {
                    showToast(res.getString(R.string.err_load_descr_failed));
                }

                if (null != loadingIndicatorView) {
                    loadingIndicatorView.setVisibility(View.GONE);
                }
            }
        }

        /**
         * handle caches with black font color
         *
         * @param view
         * @param text
         */
        private void fixBlackTextColor(final TextView view, final String text) {
            if (!Settings.isLightSkin()) {
                if (-1 != StringUtils.indexOfAny(text, new String[] { "color=\"#000000", "color=\"black" })) {
                    view.setBackgroundResource(color.darker_gray);
                }
                else {
                    view.setBackgroundResource(color.black);
                }
            }
        }
    }

    private class LogsViewCreator implements PageViewCreator {
        ScrollView view;
        boolean allLogs;

        LogsViewCreator(boolean allLogs) {
            super();
            this.allLogs = allLogs;
        }

        @Override
        public void notifyDataSetChanged() {
            view = null;
        }

        @Override
        public View getView() {
            if (view == null) {
                view = (ScrollView) getDispatchedView();
            }

            return view;
        }

        @Override
        public View getDispatchedView() {
            if (cache == null) {
                // something is really wrong
                return null;
            }

            view = (ScrollView) getLayoutInflater().inflate(R.layout.cacheview_logs, null);

            new LogInflaterTask().execute((Void) null);

            return view;
        }

        private class LogInflaterTask extends AsyncTask<Void, Void, Void> {
            private List<RelativeLayout> loglist = new LinkedList<RelativeLayout>();
            private String logCounter;
            private boolean showLogCounter = false;

            @Override
            protected Void doInBackground(Void... params) {
                // log count
                if (cache != null && cache.getLogCounts() != null) {
                    final StringBuilder text = new StringBuilder(200);
                    text.append(res.getString(R.string.cache_log_types));
                    text.append(": ");

                    // sort the log counts by type id ascending. that way the FOUND, DNF log types are the first and most visible ones
                    List<Entry<LogType, Integer>> sortedLogCounts = new ArrayList<Entry<LogType, Integer>>();
                    for (Entry<LogType, Integer> entry : cache.getLogCounts().entrySet()) {
                        sortedLogCounts.add(entry); // don't add these entries using addAll(), the iterator in the EntrySet can go wrong (see Findbugs)
                    }
                    Collections.sort(sortedLogCounts, new Comparator<Entry<LogType, Integer>>() {

                        @Override
                        public int compare(Entry<LogType, Integer> logCountItem1,
                                Entry<LogType, Integer> logCountItem2) {
                            return logCountItem1.getKey().compareTo(logCountItem2.getKey());
                        }
                    });
                    for (Entry<LogType, Integer> pair : sortedLogCounts) {
                        String logTypeLabel = pair.getKey().getL10n();
                        // it may happen that the label is unknown -> then avoid any output for this type
                        if (logTypeLabel != null) {
                            if (showLogCounter) {
                                text.append(", ");
                            }
                            text.append(pair.getValue().intValue());
                            text.append("× ");
                            text.append(logTypeLabel);
                        }
                        showLogCounter = true;
                    }
                    logCounter = text.toString();
                }

                // cache logs
                RelativeLayout rowView;

                if (cache != null) {
                    for (cgLog log : cache.getLogs(allLogs)) {
                        rowView = (RelativeLayout) getLayoutInflater().inflate(R.layout.cacheview_logs_item, null);

                        if (log.date > 0) {
                            ((TextView) rowView.findViewById(R.id.added)).setText(cgBase.formatShortDate(log.date));
                        } else {
                            ((TextView) rowView.findViewById(R.id.added)).setVisibility(View.GONE);
                        }

                        ((TextView) rowView.findViewById(R.id.type)).setText(log.type.getL10n());
                        ((TextView) rowView.findViewById(R.id.author)).setText(StringEscapeUtils.unescapeHtml4(log.author));

                        if (log.found == -1) {
                            ((TextView) rowView.findViewById(R.id.count)).setVisibility(View.GONE);
                        } else if (log.found == 0) {
                            ((TextView) rowView.findViewById(R.id.count)).setText(res.getString(R.string.cache_count_no));
                        } else if (log.found == 1) {
                            ((TextView) rowView.findViewById(R.id.count)).setText(res.getString(R.string.cache_count_one));
                        } else {
                            ((TextView) rowView.findViewById(R.id.count)).setText(log.found + " " + res.getString(R.string.cache_count_more));
                        }
                        // avoid parsing HTML if not necessary
                        if (BaseUtils.containsHtml(log.log)) {
                            ((TextView) rowView.findViewById(R.id.log)).setText(Html.fromHtml(log.log, new HtmlImage(CacheDetailActivity.this, null, false, cache.getListId(), false), null), TextView.BufferType.SPANNABLE);
                        }
                        else {
                            ((TextView) rowView.findViewById(R.id.log)).setText(log.log);
                        }
                        // add LogImages
                        LinearLayout logLayout = (LinearLayout) rowView.findViewById(R.id.log_layout);

                        if (CollectionUtils.isNotEmpty(log.logImages)) {

                            final ArrayList<cgImage> logImages = new ArrayList<cgImage>(log.logImages);

                            final View.OnClickListener listener = new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    cgeoimages.startActivityLogImages(CacheDetailActivity.this, cache.getGeocode(), logImages);
                                }
                            };

                            ArrayList<String> titles = new ArrayList<String>();
                            for (int i_img_cnt = 0; i_img_cnt < log.logImages.size(); i_img_cnt++) {
                                String img_title = log.logImages.get(i_img_cnt).getTitle();
                                if (!StringUtils.isBlank(img_title)) {
                                    titles.add(img_title);
                                }
                            }
                            if (titles.isEmpty()) {
                                titles.add(res.getString(R.string.cache_log_image_default_title));
                            }

                            LinearLayout log_imgView = (LinearLayout) getLayoutInflater().inflate(R.layout.cacheview_logs_img, null);
                            TextView log_img_title = (TextView) log_imgView.findViewById(R.id.title);
                            log_img_title.setText(StringUtils.join(titles.toArray(new String[titles.size()]), ", "));
                            log_img_title.setOnClickListener(listener);
                            logLayout.addView(log_imgView);
                        }

                        // Add colored mark
                        final ImageView logMark = (ImageView) rowView.findViewById(R.id.log_mark);
                        if (log.type == LogType.LOG_FOUND_IT
                                || log.type == LogType.LOG_WEBCAM_PHOTO_TAKEN
                                || log.type == LogType.LOG_ATTENDED) {
                            logMark.setImageResource(R.drawable.mark_green);
                        } else if (log.type == LogType.LOG_PUBLISH_LISTING
                                || log.type == LogType.LOG_ENABLE_LISTING
                                || log.type == LogType.LOG_OWNER_MAINTENANCE) {
                            logMark.setImageResource(R.drawable.mark_green_more);
                        } else if (log.type == LogType.LOG_DIDNT_FIND_IT
                                || log.type == LogType.LOG_NEEDS_MAINTENANCE
                                || log.type == LogType.LOG_NEEDS_ARCHIVE) {
                            logMark.setImageResource(R.drawable.mark_red);
                        } else if (log.type == LogType.LOG_TEMP_DISABLE_LISTING
                                || log.type == LogType.LOG_ARCHIVE) {
                            logMark.setImageResource(R.drawable.mark_red_more);
                        } else {
                            logMark.setVisibility(View.GONE);
                        }

                        ((TextView) rowView.findViewById(R.id.author)).setOnClickListener(new UserActionsClickListener());
                        TextView logView = (TextView) logLayout.findViewById(R.id.log);
                        logView.setMovementMethod(LinkMovementMethod.getInstance());
                        logView.setOnClickListener(new DecryptLogClickListener());
                        registerForContextMenu(logView);

                        loglist.add(rowView);
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void param) {
                // hide loader
                view.findViewById(R.id.loading).setVisibility(View.GONE);

                // show log count
                TextView logCounterView = (TextView) view.findViewById(R.id.log_count);
                if (showLogCounter) {
                    logCounterView.setText(logCounter);
                    logCounterView.setVisibility(View.VISIBLE);
                }

                // show logs
                LinearLayout loglistView = (LinearLayout) view.findViewById(R.id.log_list);
                for (RelativeLayout log : loglist) {
                    loglistView.addView(log);
                }
                loglistView.setVisibility(View.VISIBLE);
            }
        }

        private class DecryptLogClickListener implements View.OnClickListener {

            public void onClick(View view) {
                if (view == null) {
                    return;
                }

                try {
                    final TextView logView = (TextView) view;
                    CharSequence text = logView.getText();
                    if (text instanceof Spannable) {
                        Spannable span = (Spannable) text;
                        logView.setText(CryptUtils.rot13(span));
                    }
                    else {
                        String string = (String) text;
                        logView.setText(CryptUtils.rot13(string));
                    }
                } catch (Exception e) {
                    // nothing
                }
            }
        }
    }

    private class WaypointsViewCreator implements PageViewCreator {

        ScrollView view;

        @Override
        public void notifyDataSetChanged() {
            view = null;
        }

        @Override
        public View getView() {
            if (view == null) {
                view = (ScrollView) getDispatchedView();
            }

            return view;
        }

        @Override
        public View getDispatchedView() {
            if (cache == null) {
                // something is really wrong
                return null;
            }

            view = (ScrollView) getLayoutInflater().inflate(R.layout.cacheview_waypoints, null);

            LinearLayout waypoints = (LinearLayout) view.findViewById(R.id.waypoints);

            if (cache.hasWaypoints()) {
                LinearLayout waypointView;

                // sort waypoints: PP, Sx, FI, OWN
                List<cgWaypoint> sortedWaypoints = new ArrayList<cgWaypoint>(cache.getWaypoints());
                Collections.sort(sortedWaypoints);

                for (cgWaypoint wpt : sortedWaypoints) {
                    waypointView = (LinearLayout) getLayoutInflater().inflate(R.layout.waypoint_item, null);

                    // coordinates
                    if (null != wpt.getCoords()) {
                        final TextView coordinatesView = (TextView) waypointView.findViewById(R.id.coordinates);
                        coordinatesView.setText(wpt.getCoords().toString());
                        coordinatesView.setVisibility(View.VISIBLE);
                    }

                    // info
                    final List<String> infoTextList = new ArrayList<String>(3);
                    if (WaypointType.ALL_TYPES_EXCEPT_OWN.containsKey(wpt.getWaypointType())) {
                        infoTextList.add(wpt.getWaypointType().getL10n());
                    }
                    if (cgWaypoint.PREFIX_OWN.equalsIgnoreCase(wpt.getPrefix())) {
                        infoTextList.add(res.getString(R.string.waypoint_custom));
                    } else {
                        if (StringUtils.isNotBlank(wpt.getPrefix())) {
                            infoTextList.add(wpt.getPrefix());
                        }
                        if (StringUtils.isNotBlank(wpt.getLookup())) {
                            infoTextList.add(wpt.getLookup());
                        }
                    }
                    if (CollectionUtils.isNotEmpty(infoTextList)) {
                        final TextView infoView = (TextView) waypointView.findViewById(R.id.info);
                        infoView.setText(StringUtils.join(infoTextList, " · "));
                        infoView.setVisibility(View.VISIBLE);
                    }

                    // title
                    TextView nameView = (TextView) waypointView.findViewById(R.id.name);
                    if (StringUtils.isNotBlank(wpt.getName())) {
                        nameView.setText(StringEscapeUtils.unescapeHtml4(wpt.getName()));
                    } else if (null != wpt.getCoords()) {
                        nameView.setText(wpt.getCoords().toString());
                    } else {
                        nameView.setText(res.getString(R.string.waypoint));
                    }
                    wpt.setIcon(res, nameView);

                    // note
                    if (StringUtils.isNotBlank(wpt.getNote())) {
                        final TextView noteView = (TextView) waypointView.findViewById(R.id.note);
                        noteView.setVisibility(View.VISIBLE);
                        if (BaseUtils.containsHtml(wpt.getNote())) {
                            noteView.setText(Html.fromHtml(wpt.getNote().trim()), TextView.BufferType.SPANNABLE);
                        }
                        else {
                            noteView.setText(wpt.getNote().trim());
                        }
                    }

                    registerForContextMenu(waypointView);
                    waypointView.setOnClickListener(new WaypointInfoClickListener());

                    waypoints.addView(waypointView);
                }
            }

            Button addWaypoint = (Button) view.findViewById(R.id.add_waypoint);
            addWaypoint.setClickable(true);
            addWaypoint.setOnClickListener(new AddWaypointClickListener());

            return view;
        }

        private class AddWaypointClickListener implements View.OnClickListener {

            public void onClick(View view) {
                Intent addWptIntent = new Intent(CacheDetailActivity.this, cgeowaypointadd.class);

                addWptIntent.putExtra("geocode", cache.getGeocode());
                int wpCount = 0;
                if (cache.hasWaypoints()) {
                    wpCount = cache.getWaypoints().size();
                }
                addWptIntent.putExtra("count", wpCount);

                startActivity(addWptIntent);
                refreshOnResume = true;
            }
        }

        private class WaypointInfoClickListener implements View.OnClickListener {
            public void onClick(View view) {
                openContextMenu(view);
            }
        }
    }

    private class InventoryViewCreator implements PageViewCreator {

        ListView view;

        @Override
        public void notifyDataSetChanged() {
            view = null;
        }

        @Override
        public View getView() {
            if (view == null) {
                view = (ListView) getDispatchedView();
            }

            return view;
        }

        @Override
        public View getDispatchedView() {
            if (cache == null) {
                // something is really wrong
                return null;
            }

            view = (ListView) getLayoutInflater().inflate(R.layout.cacheview_inventory, null);

            // TODO: Switch back to Android-resource and delete copied one
            view.setAdapter(new ArrayAdapter<cgTrackable>(CacheDetailActivity.this, R.layout.simple_list_item_1, cache.getInventory()));
            view.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Object selection = arg0.getItemAtPosition(arg2);
                    if (selection instanceof cgTrackable) {
                        cgTrackable trackable = (cgTrackable) selection;
                        cgeotrackable.startActivity(CacheDetailActivity.this, trackable.getGuid(), trackable.getGeocode(), trackable.getName());
                    }
                }
            });

            return view;
        }
    }
}

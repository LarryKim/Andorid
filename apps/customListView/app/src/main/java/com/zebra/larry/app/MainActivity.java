package com.zebra.larry.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private static final int POSITION_WEB_VIEW = 0;
    private static final int POSITION_LIST_VIEW = 1;
    private static final String WEB_VIWE_URL = "http://www.google.com";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ActionBar mActionBar;
    private Button mBtnWebView;
    private Button mBtnListView;
    private TextView mTvTitleActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remove actionBar icon
        mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);

        // Align actionBar title center
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_layout);
        mTvTitleActionBar = (TextView) findViewById(R.id.tvTitleActionBar);
        mTvTitleActionBar.setText(R.string.title_webview);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Bottom WebView tab
        mBtnWebView = (Button) findViewById(R.id.btnWebView);
        mBtnWebView.setPressed(true);
        mBtnWebView.setOnTouchListener(new View.OnTouchListener() {
            // Selected effect - when the button is pushed
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBtnWebView.setPressed(true);
                mViewPager.setCurrentItem(POSITION_WEB_VIEW, true);
                return true;
            }
        });

        // Bottom ListView tab
        mBtnListView = (Button) findViewById(R.id.btnListView);
        mBtnListView.setOnTouchListener(new View.OnTouchListener() {
            // Selected effect - when the button is pushed
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBtnListView.setPressed(true);
                mViewPager.setCurrentItem(POSITION_LIST_VIEW, true);
                return true;
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            public void onPageSelected(int position) {
                // Selected effect - when the page is swiped
                boolean bWebViewPressed = false;
                boolean bListViewPressed = false;
                switch (position) {
                    case POSITION_WEB_VIEW:
                        bWebViewPressed = true;
                        break;
                    case POSITION_LIST_VIEW:
                        bListViewPressed = true;
                        break;
                }
                //Log.d(TAG, "wbp:" + bWebViewPressed + ", blp:" + bListViewPressed);
                mBtnWebView.setPressed(bWebViewPressed);
                mBtnListView.setPressed(bListViewPressed);

                // Change ActionBar title
                mTvTitleActionBar.setText(mSectionsPagerAdapter.getPageTitle(position));
            }
        });
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case POSITION_WEB_VIEW:
                    return getString(R.string.title_webview);
                case POSITION_LIST_VIEW:
                    return getString(R.string.title_listview);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            int position = getArguments().getInt(ARG_SECTION_NUMBER) - 1;
            WebView webView = (WebView) rootView.findViewById(R.id.webView);
            ListView listView = (ListView) rootView.findViewById(R.id.listView);

            //Log.d(TAG, "position: " + position);
            switch (position) {
                case POSITION_WEB_VIEW:
                    webView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                    webView.loadUrl(WEB_VIWE_URL);

                    // For opening a web-page on the webview
                    webView.setWebViewClient(new WebViewClient());
                    setRetainInstance(true);
                    break;
                case POSITION_LIST_VIEW:
                    webView.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    String[] arrItems = getResources().getStringArray(R.array.items_array);
                    Integer[] images = { R.drawable.ic_launcher,
                            R.drawable.ic_launcher };
                    NumberFormat nf = NumberFormat.getInstance();
                    Random oRandom = new Random();
                    List<RowItem> rowItems = new ArrayList<RowItem>();
                    for (int i = 0; i < arrItems.length; i++) {
                        int imgIdx = i%2;
                        // Random price
                        String priceInfo = getResources().getString(R.string.currency)
                                +  nf.format((oRandom.nextInt(10)+1)*10000);
                        RowItem item = new RowItem(images[imgIdx], arrItems[i], priceInfo);
                        rowItems.add(item);
                    }

                    // Set custom ListView Adapter
                    CustomListViewAdapter adapter = new CustomListViewAdapter(getActivity(),
                            R.layout.list_item, rowItems);
                    listView.setAdapter(adapter);
                    break;
            }

            return rootView;
        }
    }
}

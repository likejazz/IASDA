package net.daum.search.IASDA;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // if request is from intent://(deep link)
        Uri data = getIntent().getData();
        if (data != null) {
            // extract drawer `position` from URI
            // it does *NOT* implement exception handling because is very simple example.
            String pos = data.toString();
            pos = pos.substring(pos.length() - 1);

            // Close the Navigation drawer
            ((DrawerLayout) findViewById(R.id.drawer_layout))
                    .closeDrawer(mNavigationDrawerFragment.getActivity().
                            findViewById(R.id.navigation_drawer));
            // Do something like nav drawer has been selected
            onNavigationDrawerItemSelected(Integer.parseInt(pos) - 1);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();

        Toast.makeText(this, R.string.navigation_selected_msg, Toast.LENGTH_SHORT).show();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
            default:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

            // Get section number argument
            int pos = this.getArguments().getInt(ARG_SECTION_NUMBER);

            String title = ""; // StringUtils.EMPTY
            String src = "";
            switch (pos) {
                case 1:
                    title = "Alberto Contador!";
                    src = "http://www.neilbrowne.com/wp-content/uploads/2009/11/pictureb_3025_alberto_contador_hacerse_etapa1.jpg";
                    break;
                case 2:
                    title = "Mark Cavendish!";
                    src = "http://i.telegraph.co.uk/multimedia/archive/01658/Mark_Cavendish_1658574c.jpg";
                    break;
                case 3:
                    title = "Fabian Cancellara!";
                    src = "http://cfile293.uf.daum.net/image/276789395158FDA91160CB";
                    break;
                case 4:
                default:
                    title = "Sang-Kil Park!";
                    src = "http://s30.postimg.org/f24zc6mpt/1012750_10152018837955837_772542991_n.jpg";
                    break;
            }

            // TextView
            ((TextView) rootView.findViewById(R.id.textView)).setText(title);

            /**
             * ImageView
             *
             * UrlImageViewHelper will automatically download and manage
             * all the web images and ImageViews.
             * https://github.com/koush/UrlImageViewHelper
             */
            UrlImageViewHelper.setUrlDrawable(
                    (ImageView) rootView.findViewById(R.id.imageView), src);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
package com.github.jjobes.slidedatetimepicker;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>The {@code DialogFragment} that contains the {@link SlidingTabLayout}
 * and {@link CustomViewPager}.</p>
 * <p/>
 * <p>The {@code CustomViewPager} contains the {@link DateFragment} and {@link TimeFragment}.</p>
 * <p/>
 * <p>This {@code DialogFragment} is managed by {@link SlideDateTimePicker}.</p>
 *
 * @author jjobes
 */
public class SlideDateTimeDialogFragment extends DialogFragment implements DateFragment.DateChangedListener {

    public static final String TAG_SLIDE_DATE_TIME_DIALOG_FRAGMENT = "tagSlideDateTimeDialogFragment";

    public static final String FROM_TAB_TAG = "from:tab";
    public static final String TO_TAB_TAG = "to:tab";

    private static SlideDateTimeListener mListener;

    private Context mContext;
    private CustomViewPager mViewPager;
    private TextView mTitleTextView;
    private NumberPicker mQuantityPicker;
    private ViewPagerAdapter mViewPagerAdapter;
    private SlidingTabLayout mSlidingTabLayout;
    private View mButtonHorizontalDivider;
    private View mButtonVerticalDivider;
    private Button mOkButton;
    private Button mCancelButton;
    private Date mInitialDate;
    private int mTheme;
    private int mIndicatorColor;
    private String mTitle;
    private int mMaxQuantity;
    private Date mMinDate;
    private Date mMaxDate;
    private boolean mIsClientSpecified24HourTime;
    private boolean mIs24HourTime;

    private Calendar mFromCalendar;
    private Calendar mToCalendar;

    private int mDateFlags =
            DateUtils.FORMAT_SHOW_WEEKDAY |
                    DateUtils.FORMAT_SHOW_DATE |
                    DateUtils.FORMAT_ABBREV_ALL;

    public SlideDateTimeDialogFragment() {
        // Required empty public constructor
    }

    /**
     * <p>Return a new instance of {@code SlideDateTimeDialogFragment} with its bundle
     * filled with the incoming arguments.</p>
     * <p/>
     * <p>Called by {@link SlideDateTimePicker#show()}.</p>
     *
     * @param listener
     * @param initialDate
     * @param minDate
     * @param maxDate
     * @param isClientSpecified24HourTime
     * @param is24HourTime
     * @param theme
     * @param indicatorColor
     * @return
     */
    public static SlideDateTimeDialogFragment newInstance(String title,
                                                          SlideDateTimeListener listener,
                                                          Date initialDate, Date minDate, Date maxDate,
                                                          boolean isClientSpecified24HourTime,
                                                          boolean is24HourTime, int theme, int indicatorColor,
                                                          int maxQuantity) {
        mListener = listener;

        // Create a new instance of SlideDateTimeDialogFragment
        SlideDateTimeDialogFragment dialogFragment = new SlideDateTimeDialogFragment();

        // Store the arguments and attach the bundle to the fragment
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putSerializable("initialDate", initialDate);
        bundle.putSerializable("minDate", minDate);
        bundle.putSerializable("maxDate", maxDate);
        bundle.putInt("maxQty", maxQuantity);
        bundle.putBoolean("isClientSpecified24HourTime", isClientSpecified24HourTime);
        bundle.putBoolean("is24HourTime", is24HourTime);
        bundle.putInt("theme", theme);
        bundle.putInt("indicatorColor", indicatorColor);
        dialogFragment.setArguments(bundle);

        // Return the fragment with its bundle
        return dialogFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        unpackBundle();

        mFromCalendar = Calendar.getInstance();
        mToCalendar = (Calendar) mFromCalendar.clone();
        mFromCalendar.setTime(mInitialDate);
        mToCalendar.setTime(mInitialDate);

        switch (mTheme) {
            case SlideDateTimePicker.HOLO_DARK:
                setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog_NoActionBar);
                break;
            case SlideDateTimePicker.HOLO_LIGHT:
                setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
                break;
            default:  // if no theme was specified, default to holo light
                setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slide_date_time_picker, container);

        setupViews(view);
        setupTitle();
        setupQtyPicker();
        customizeViews();
        initViewPager();
        initTabs();
        initButtons();

        return view;
    }

    @Override
    public void onDestroyView() {
        // Workaround for a bug in the compatibility library where calling
        // setRetainInstance(true) does not retain the instance across
        // orientation changes.
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }

        super.onDestroyView();
    }

    private void unpackBundle() {
        Bundle args = getArguments();

        mInitialDate = (Date) args.getSerializable("initialDate");
        mTitle = args.getString("title");
        mMaxQuantity = args.getInt("maxQty");
        mMinDate = (Date) args.getSerializable("minDate");
        mMaxDate = (Date) args.getSerializable("maxDate");
        mIsClientSpecified24HourTime = args.getBoolean("isClientSpecified24HourTime");
        mIs24HourTime = args.getBoolean("is24HourTime");
        mTheme = args.getInt("theme");
        mIndicatorColor = args.getInt("indicatorColor");
    }

    private void setupViews(View v) {
        mViewPager = (CustomViewPager) v.findViewById(R.id.viewPager);
        mTitleTextView = (TextView) v.findViewById(android.R.id.title);
        mQuantityPicker = (NumberPicker) v.findViewById(R.id.quantityPicker);
        mSlidingTabLayout = (SlidingTabLayout) v.findViewById(R.id.slidingTabLayout);
        mButtonHorizontalDivider = v.findViewById(R.id.buttonHorizontalDivider);
        mButtonVerticalDivider = v.findViewById(R.id.buttonVerticalDivider);
        mOkButton = (Button) v.findViewById(R.id.okButton);
        mCancelButton = (Button) v.findViewById(R.id.cancelButton);
    }

    private void setupTitle() {
        mTitleTextView.setText(mTitle);
    }

    private void setupQtyPicker() {
        mQuantityPicker.setMaxValue(1);
        mQuantityPicker.setMaxValue(mMaxQuantity);
    }

    private void customizeViews() {
        int lineColor = mTheme == SlideDateTimePicker.HOLO_DARK ?
                getResources().getColor(R.color.gray_holo_dark) :
                getResources().getColor(R.color.gray_holo_light);

        // Set the colors of the horizontal and vertical lines for the
        // bottom buttons depending on the theme.
        switch (mTheme) {
            case SlideDateTimePicker.HOLO_LIGHT:
            case SlideDateTimePicker.HOLO_DARK:
                mButtonHorizontalDivider.setBackgroundColor(lineColor);
                mButtonVerticalDivider.setBackgroundColor(lineColor);
                break;

            default:  // if no theme was specified, default to holo light
                mButtonHorizontalDivider.setBackgroundColor(getResources().getColor(R.color.gray_holo_light));
                mButtonVerticalDivider.setBackgroundColor(getResources().getColor(R.color.gray_holo_light));
        }

        // Set the color of the selected tab underline if one was specified.
        if (mIndicatorColor != 0)
            mSlidingTabLayout.setSelectedIndicatorColors(mIndicatorColor);
    }

    private void initViewPager() {
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        // Setting this custom layout for each tab ensures that the tabs will
        // fill all available horizontal space.
        mSlidingTabLayout.setCustomTabView(R.layout.custom_tab, R.id.tabText);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    private void initTabs() {
        // Set intial date on from date tab
        updateFromDateTab();

        // Set intial date on from date tab
        updateToDateTab();
    }

    private void initButtons() {
        mOkButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener == null) {
                    throw new NullPointerException(
                            "Listener no longer exists for mOkButton");
                }


                mListener.onDateTimeSet(new Date(mFromCalendar.getTimeInMillis()),
                        new Date(mToCalendar.getTimeInMillis()),
                        mQuantityPicker.getValue());

                dismiss();
            }
        });

        mCancelButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener == null) {
                    throw new NullPointerException(
                            "Listener no longer exists for mCancelButton");
                }

                mListener.onDateTimeCancel();

                dismiss();
            }
        });
    }

    /**
     * <p>The callback used by the DatePicker to update {@code mFromCalendar} as
     * the user changes the date. Each time this is called, we also update
     * the text on the date tab to reflect the date the user has currenly
     * selected.</p>
     * <p/>
     * <p>Implements the {@link DateFragment.DateChangedListener}
     * interface.</p>
     */
    @Override
    public void onDateChanged(String tabTag, int year, int month, int day) {
        if (tabTag.equals(FROM_TAB_TAG)) {
            mFromCalendar.set(year, month, day);
            updateFromDateTab();
        } else if (tabTag.equals(TO_TAB_TAG)) {
            mToCalendar.set(year, month, day);
            updateToDateTab();
        }
    }


    private void updateFromDateTab() {
        mSlidingTabLayout.setTabText(0, String.format("FROM: \n %s", DateUtils.formatDateTime(
                mContext, mFromCalendar.getTimeInMillis(), mDateFlags)));
    }

    private void updateToDateTab() {
        mSlidingTabLayout.setTabText(1, String.format("TO: \n %s", DateUtils.formatDateTime(
                mContext, mToCalendar.getTimeInMillis(), mDateFlags)));
    }

    /**
     * <p>Called when the user clicks outside the dialog or presses the <b>Back</b>
     * button.</p>
     * <p/>
     * <p><b>Note:</b> Actual <b>Cancel</b> button clicks are handled by {@code mCancelButton}'s
     * event handler.</p>
     */
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        if (mListener == null) {
            throw new NullPointerException(
                    "Listener no longer exists in onCancel()");
        }

        mListener.onDateTimeCancel();
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    DateFragment dateFromFragment = DateFragment.newInstance(
                            FROM_TAB_TAG,
                            mTheme,
                            mFromCalendar.get(Calendar.YEAR),
                            mFromCalendar.get(Calendar.MONTH),
                            mFromCalendar.get(Calendar.DAY_OF_MONTH),
                            mMinDate,
                            mMaxDate);
                    dateFromFragment.setTargetFragment(SlideDateTimeDialogFragment.this, 100);
                    return dateFromFragment;
                case 1:
                    DateFragment dateToFragment = DateFragment.newInstance(
                            TO_TAB_TAG,
                            mTheme,
                            mToCalendar.get(Calendar.YEAR),
                            mToCalendar.get(Calendar.MONTH),
                            mToCalendar.get(Calendar.DAY_OF_MONTH),
                            mMinDate,
                            mMaxDate);
                    dateToFragment.setTargetFragment(SlideDateTimeDialogFragment.this, 200);
                    return dateToFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}

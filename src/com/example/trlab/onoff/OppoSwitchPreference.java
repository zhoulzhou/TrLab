/********************************************************************************
 ** Copyright (C), 2008-2012, OPPO Mobile Comm Corp., Ltd
 ** All rights reserved.
 ** VENDOR_EDIT
 ** File: - OppoSwitchPreference.java
 ** Description: 
 **     A preference that provides a two-state toggleable option of oppo style with
 **     scroll effect.
 **     
 ** Version: 1.0
 ** Date: 2013/01/23
 ** Author: Jianhui.Yu@Plf.MidWare.SDK
 ** 
 ** ------------------------------- Revision History: ----------------------------
 ** <author>						<data>		<version>	<desc>
 ** ------------------------------------------------------------------------------
 ** Jianhui.Yu@Plf.MidWare.SDK	2013/01/23	1.0			build this moudle
 ********************************************************************************/

package com.example.trlab.onoff;

import com.example.trlab.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.preference.SwitchPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * A {@link android.preference.Preference} that provides a two-state toggleable
 * option.
 * <p>
 * This preference will store a boolean into the SharedPreferences.
 * 
 * @attr ref android.R.styleable#SwitchPreference_summaryOff
 * @attr ref android.R.styleable#SwitchPreference_summaryOn
 * @attr ref android.R.styleable#SwitchPreference_switchTextOff
 * @attr ref android.R.styleable#SwitchPreference_switchTextOn
 * @attr ref android.R.styleable#SwitchPreference_disableDependentsState attr
 *       ref oppo.R.styleable#OppoPreferenceTextAppearance_titleColor attr ref
 *       oppo.R.styleable#OppoPreferenceTextAppearance_summaryColor
 */
public class OppoSwitchPreference extends SwitchPreference implements
		OnClickListener {
	private boolean mSendClickAccessibilityEvent;
	private ColorStateList mTextColorTitle;
	private ColorStateList mTextColorSummary;
	private final Listener mListener = new Listener();
	private View mView = null;
	private CompoundButton mSwitchView = null;
    private boolean mClickToggle = true;

	private class Listener implements CompoundButton.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (!callChangeListener(isChecked)) {
				// Listener didn't like it, change it back.
				// CompoundButton will make sure we don't recurse.
				buttonView.setChecked(!isChecked);
				return;
			}

			OppoSwitchPreference.this.setChecked(isChecked);
		}
	}

	/**
	 * Construct a new OppoSwitchPreference with the given style options.
	 * 
	 * @param context
	 *            The Context that will style this preference
	 * @param attrs
	 *            Style attributes that differ from the default
	 * @param defStyle
	 *            Theme attribute defining the default style options
	 */
	public OppoSwitchPreference(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		TypedArray b = context.obtainStyledAttributes(attrs,
				R.styleable.OppoPreferenceTextAppearance, defStyle, 0);
		setTitleTextColor(b
				.getColorStateList(R.styleable.OppoPreferenceTextAppearance_titleColor));
		setSummaryTextColor(b
				.getColorStateList(R.styleable.OppoPreferenceTextAppearance_summaryColor));
		b.recycle();
	}

	/**
	 * Construct a new OppoSwitchPreference with the given style options.
	 * 
	 * @param context
	 *            The Context that will style this preference
	 * @param attrs
	 *            Style attributes that differ from the default
	 */
	public OppoSwitchPreference(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.oppoSwitchPreferenceStyle);
	}

	/**
	 * Construct a new OppoSwitchPreference with default style options.
	 * 
	 * @param context
	 *            The Context that will style this preference
	 */
	public OppoSwitchPreference(Context context) {
		this(context, null);
	}

	@Override
	protected void onClick() {
		mSendClickAccessibilityEvent = true;

        if (mClickToggle) {
            if (mSwitchView instanceof OppoSwitch) {
                OppoSwitch switchView = (OppoSwitch) mSwitchView;
                switchView.changeChecked();
            } else {
                boolean newValue = !isChecked();;
                if (!callChangeListener(newValue)) {
                    return;
                }
                setChecked(newValue);
            }
        }
	}

	@Override
	protected void onBindView(View view) {
		View checkableView = view.findViewById(android.R.id.checkbox);
		if (checkableView != null && checkableView instanceof Checkable) {
			if (checkableView instanceof CompoundButton) {
				mSwitchView = (CompoundButton) checkableView;
			}

			if (mSwitchView != null) {
				mSwitchView.setOnCheckedChangeListener(null);
			}
			((Checkable) checkableView).setChecked(isChecked());
			sendAccessibilityEvent(checkableView);
			if (mSwitchView != null) {
				mSwitchView.setOnCheckedChangeListener(mListener);
			}
		}

		super.onBindView(view);
	}

	@Override
	protected View onCreateView(ViewGroup parent) {
		if (mView == null) {
			mView = super.onCreateView(parent);

            if (!mClickToggle) {
                mView.setOnClickListener(this);

        		TextView titleView = (TextView) mView
        		        .findViewById(android.R.id.title);
        		if (titleView != null) {
        			titleView.setTextColor(mTextColorTitle);
        		}

        		TextView summaryView = (TextView) mView
        				.findViewById(android.R.id.summary);
        		if (summaryView != null) {
        			summaryView.setTextColor(mTextColorSummary);
        		}
    		}
        }
		return mView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	/**
	 * Set the title text displayed.
	 * 
	 * @param textColor
	 *            Color of title text
	 */
	public void setTitleTextColor(ColorStateList textColor) {
		mTextColorTitle = textColor;
	}

	/**
	 * Set the summary text displayed.
	 * 
	 * @param textColor
	 *            Color of summary text
	 */
	public void setSummaryTextColor(ColorStateList textColor) {
		mTextColorSummary = textColor;
	}

	/**
	 * Get the switch view.
	 * 
	 * @return Switch view
	 */
	public CompoundButton getSwitch() {
		return mSwitchView;
	}

	void sendAccessibilityEvent(View view) {
		// Since the view is still not attached we create, populate,
		// and send the event directly since we do not know when it
		// will be attached and posting commands is not as clean.
		AccessibilityManager accessibilityManager = getAccessibilityManager();
		if (mSendClickAccessibilityEvent && accessibilityManager.isEnabled()) {
			AccessibilityEvent event = AccessibilityEvent.obtain();
			event.setEventType(AccessibilityEvent.TYPE_VIEW_CLICKED);
			view.onInitializeAccessibilityEvent(event);
			view.dispatchPopulateAccessibilityEvent(event);
			accessibilityManager.sendAccessibilityEvent(event);
		}
		mSendClickAccessibilityEvent = false;
	}

	/**
	 * 
	 */
	private AccessibilityManager getAccessibilityManager() {
		Class<?>[] paramTypes = new Class<?>[] { Context.class };
		Object[] args = new Object[] { getContext() };
		return (AccessibilityManager) OppoDemosUtil.invokeMethod(
				"android.view.accessibility.AccessibilityManager",
				"getInstance", paramTypes, null, args);
	}
}

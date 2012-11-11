package com.everhack.compassnote.view;

import java.util.List;
import java.util.Set;

import com.everhack.compassnote.R;

import com.everhack.compassnote.activity.ListPlacesActivity;
import com.everhack.compassnote.foursquare.FoursquareServiceDelegate;
import com.everhack.compassnote.foursquare.FoursquareVenue;
import com.everhack.compassnote.foursquare.FoursquareVenueImageService;
import com.everhack.compassnote.foursquare.FoursquareVenueService;
import com.everhack.compassnote.model.PlaceData;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.LruCache;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceItemView extends RelativeLayout {
    private ToggleButton mButtonFavorite;

    private ImageView mScenePicture;
    private TextView mTitleView;
    private TextView mSubtitleView;
    private TextView mCommentView;
    private TextView mLoadingView;
    private LruCache mCache;

    private FoursquareVenue mData;
    private Handler mHandler;

    private OnItemFavorited mListener;

    private OnClickListener mButtonRemoveOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            //TODO
//            Message msg = mHandler.obtainMessage(ListPlacesActivity.EVENT_FAVORITE_ITEM);
//            msg.arg1 = mData.getId();
//            mHandler.dispatchMessage(msg);
        }
    };

    public PlaceItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bindView(FoursquareVenue data, OnItemFavorited listener, LruCache cache) {

        mTitleView.setText(data.getName());
        mSubtitleView.setText(data.getCategory());
        mCommentView.setText(data.getLocation().getAddress());

        mLoadingView.setVisibility(VISIBLE);
        mScenePicture.setImageDrawable(null);

        mData = data;
        mListener = listener;
        mCache = cache;

        //Bitmap bitmap = (Bitmap) cache.get(data);
        Bitmap bitmap = null;
        if (bitmap!= null) {
            mLoadingView.setVisibility(GONE);
            mScenePicture.setImageBitmap(bitmap);
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
            mScenePicture.startAnimation(myFadeInAnimation);

        } else {
            FoursquareVenueImageService.getVenuesImageInCity(data, 200, 200, new FoursquareServiceDelegate<Bitmap>() {

                @Override
                public void receivedResponse(Bitmap response) {
                    
                    mCache.put(mData, response);
                    mLoadingView.setVisibility(GONE);
                    mScenePicture.setImageBitmap(response);
                    Animation myFadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
                    mScenePicture.startAnimation(myFadeInAnimation);
                }

                @Override
                public void requestFailed() {
                    // TODO Auto-generated method stub

                }

        });
        }
        //new LoadImageTask().execute(data.getDrawableResScene());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mButtonFavorite = (ToggleButton) findViewById(R.id.buttonFavorite);
        mButtonFavorite.setToggleResources(R.drawable.ic_rating_favorite_red, R.drawable.ic_rating_favorite);
        mButtonFavorite.setOff();

        mTitleView = (TextView) findViewById(R.id.textTitle);
        mSubtitleView = (TextView) findViewById(R.id.textSubtitle);
        mCommentView = (TextView) findViewById(R.id.textComment);
        mLoadingView = (TextView) findViewById(R.id.textLoading);

        mScenePicture = (ImageView) findViewById(R.id.imageScene);

        mButtonFavorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonFavorite.toggle();
                mListener.onItemFavorited(mData, mButtonFavorite.isOn());
            }
        });
    }

    private class LoadImageTask extends AsyncTask<Integer, Object, Drawable> {

        @Override
        protected Drawable doInBackground(Integer... params) {
            int resId = params[0];
            Drawable image = getContext().getResources().getDrawable(resId);

            // Fake loading item

            return image;
        }

        @Override
        protected void onPostExecute(Drawable image) {
            mLoadingView.setVisibility(GONE);
            mScenePicture.setImageDrawable(image);
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
            mScenePicture.startAnimation(myFadeInAnimation);
        }
    }

    public interface OnItemFavorited {
        public void onItemFavorited(FoursquareVenue venue, boolean isFavorite);
        public Set<FoursquareVenue> getFavoritedItems();
    }

}

package com.example.mustafaguven.parallaxsample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
  @BindView(R.id.mainLayout) FrameLayout mainLayout;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.stickyView) RelativeLayout stickyView;
  @BindView(R.id.scrollView) ScrollView scrollView;
  @BindView(R.id.relativeLayout) RelativeLayout relativeLayout;
  @BindView(R.id.imageView) ImageView imageView;
  @BindView(R.id.rlInsideBox) RelativeLayout rlInsideBox;
  @BindView(R.id.text) TextView text;
  @BindDimen(R.dimen.picture_height) int PICTURE_HEIGHT;
  @BindDimen(R.dimen.sticky_header_height) int HEADER_HEIGHT;
  @BindColor(R.color.toolbar_background) int toolbarBackgroundColor;
  Unbinder unbinder;

  @OnClick(R.id.stickyView) void stickViewClicked(){
    Snackbar.make(mainLayout, "StickyHeader is clicked", Snackbar.LENGTH_LONG).show();
  }

  @OnClick(R.id.imageView) void imageViewClicked(){
    Snackbar.make(mainLayout, "ImageView is clicked", Snackbar.LENGTH_LONG).show();
  }

  @OnClick(R.id.text) void textClicked(){
    Snackbar.make(mainLayout, "text is clicked", Snackbar.LENGTH_LONG).show();
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    unbinder = ButterKnife.bind(this);
    toolbar.setTitle(R.string.toolbar_title);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    setWidgetsLocations();
    relativeLayout.getViewTreeObserver()
        .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
          @Override public void onScrollChanged() {
            setWidgetsLocations();
          }
        });
  }

  private void setWidgetsLocations() {
    int scrollViewTopY = scrollView.getScrollY();
    int stickyViewTopY = Math.max(0, PICTURE_HEIGHT - scrollViewTopY);
    float relativeLayoutRefinedMaxY = (PICTURE_HEIGHT + HEADER_HEIGHT);

    relativeLayout.setY(Math.max(toolbar.getHeight(), relativeLayoutRefinedMaxY));
    stickyView.setY(Math.max(toolbar.getHeight(), stickyViewTopY));
    toolbar.getBackground().setAlpha(getProportion(scrollViewTopY));
    imageView.setY(-(scrollViewTopY * 0.5f));
  }

  private int getProportion(int scrollViewTopY) {
    float totalUsableHeight = PICTURE_HEIGHT - toolbar.getHeight();
    float proportion = (scrollViewTopY / totalUsableHeight) * 255;
    proportion = Math.max(0, proportion);
    return Math.min(255, (int)proportion);
  }

  @Override protected void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }
}
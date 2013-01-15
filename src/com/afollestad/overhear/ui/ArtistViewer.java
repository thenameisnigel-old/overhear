package com.afollestad.overhear.ui;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.afollestad.overhear.MusicBoundActivity;
import com.afollestad.overhear.NowPlayingBar;
import com.afollestad.overhear.R;
import com.afollestad.overhear.TaggedFragmentAdapter;
import com.afollestad.overhear.fragments.AlbumListFragment;
import com.afollestad.overhear.fragments.BioListFragment;
import com.afollestad.overhear.fragments.SongListFragment;
import com.afollestad.overhear.tasks.ArtistImageLoader;
import com.afollestad.overhearapi.Artist;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.ImageView;

public class ArtistViewer extends MusicBoundActivity {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	public Artist artist;
	NowPlayingBar nowPlaying;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_artist_viewer);
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(2);
		mViewPager.setCurrentItem(1);
		try {
			artist = Artist.fromJSON(new JSONObject(getIntent().getStringExtra("artist")));
		} catch (JSONException e) {
			e.printStackTrace();
			//throw new Error(e.getMessage());
		}
		setTitle(artist.getName());
		int dimen = getResources().getDimensionPixelSize(R.dimen.gridview_image);
		new ArtistImageLoader(this, (ImageView)findViewById(R.id.cover), dimen, dimen).execute(artist);
	}
	
	public class SectionsPagerAdapter extends TaggedFragmentAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch(position) {
			case 0: {
				SongListFragment songs = new SongListFragment();
				Bundle args = new Bundle();
				args.putString("artist_name", artist.getName());
				songs.setArguments(args);
				return songs;
			}
			case 1: {
				AlbumListFragment albums = new AlbumListFragment();
				Bundle args = new Bundle();
				args.putString("artist_name", artist.getName());
				albums.setArguments(args);
				return albums;
			}
			case 2: {
				return new BioListFragment();
			}
			}
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.songs_str).toUpperCase(Locale.getDefault());
			case 1:
				return getString(R.string.albums_str).toUpperCase(Locale.getDefault());
			case 2:
				return getString(R.string.about_str).toUpperCase(Locale.getDefault());
			}
			return null;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return false;
	}

	@Override
	public void onBound() { 
		nowPlaying = NowPlayingBar.get(this);
	}
}
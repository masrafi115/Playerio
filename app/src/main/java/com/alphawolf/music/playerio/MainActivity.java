package com.alphawolf.music.playerio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.widget.AdapterView;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.facebook.ads.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class MainActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	
	private String song_duration = "";
	private double song = 0;
	private String filepath = "";
	private boolean onSeek = false;
	private String white = "";
	private String whiteDark = "";
	private String accentColor = "";
	private double currentPosition = 0;
	private boolean isPlaying = false;
	private double oldPosition = 0;
	private boolean isSearch = false;
	private boolean isSheeting = false;
	private String color = "";
	private String colorPrimaryDark = "";
	private double viewIndex = 0;
	private double viewTop = 0;
	private double n = 0;
	private double m = 0;
	private String list_json = "";
	private HashMap<String, Object> map_test = new HashMap<>();
	private String item_json = "";
	private double lastPosition = 0;
	private String favSong = "";
	private HashMap<String, Object> map = new HashMap<>();
	private double num_fav = 0;
	private double num = 0;
	private String favorite = "";
	private boolean isDarkMode = false;
	private boolean isLeftBar = false;
	private String dark = "";
	private String darkColor = "";
	private double length = 0;
	private String ID_Placement_banner = "";
	private  AdView adView;
	private  final String TAG = MainActivity.class.getSimpleName();;
	private  InterstitialAd interstitialAd;
	private String ID_Interstitial = "";
	private double interstitial_fail = 0;
	private String ErrorFBInterstitial = "";
	private double interestial_complete = 0;
	
	private ArrayList<HashMap<String, Object>> All_Song_Data = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> background_list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> FavoriteMap = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout background;
	private LinearLayout left_bar;
	private LinearLayout mask;
	private LinearLayout layout;
	private LinearLayout linear11;
	private LinearLayout action_bar;
	private LinearLayout banner_container;
	private ListView listview1;
	private ImageView menu;
	private TextView title;
	private EditText search;
	private ImageView search_icon;
	private ImageView imageview1;
	private LinearLayout bottom;
	private LinearLayout bottom_child;
	private LinearLayout bottom_b;
	private LinearLayout bottom_t;
	private LinearLayout linear4;
	private LinearLayout linear6;
	private TextView time_current;
	private SeekBar seekbar1;
	private TextView time_duration;
	private ImageView sheeting_icon;
	private ImageView repeat_mode;
	private ImageView back;
	private ImageView play;
	private ImageView next;
	private ImageView focus_music;
	private LinearLayout linear13;
	private LinearLayout viewpager;
	private LinearLayout linear8;
	private LinearLayout linear12;
	private LinearLayout linear15;
	private ImageView album;
	private LinearLayout linear14;
	private LinearLayout linear16;
	private TextView song_title;
	private TextView text_artist;
	private TextView text_dot;
	private TextView text_album;
	private LinearLayout tool_music_bottom;
	private ImageView like;
	private ImageView image_shuffle;
	private ImageView image_share;
	private LinearLayout linear17;
	private LinearLayout linear7;
	private LinearLayout linear9;
	private LinearLayout linear18;
	private ImageView cancel;
	private ImageView image_favorite;
	private ImageView imageview3;
	private ImageView imageview4;
	private ImageView darker;
	
	private AlertDialog.Builder permission;
	private TimerTask timer;
	private Calendar calendar = Calendar.getInstance();
	private AlertDialog.Builder media_error_dialog;
	private SharedPreferences songData;
	private Intent intent = new Intent();
	private Intent intentShareFile = new Intent();
	private TimerTask time;
	private TimerTask ad;
	private TimerTask banner;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		main = (LinearLayout) findViewById(R.id.main);
		background = (LinearLayout) findViewById(R.id.background);
		left_bar = (LinearLayout) findViewById(R.id.left_bar);
		mask = (LinearLayout) findViewById(R.id.mask);
		layout = (LinearLayout) findViewById(R.id.layout);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		action_bar = (LinearLayout) findViewById(R.id.action_bar);
		banner_container = (LinearLayout) findViewById(R.id.banner_container);
		listview1 = (ListView) findViewById(R.id.listview1);
		menu = (ImageView) findViewById(R.id.menu);
		title = (TextView) findViewById(R.id.title);
		search = (EditText) findViewById(R.id.search);
		search_icon = (ImageView) findViewById(R.id.search_icon);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		bottom = (LinearLayout) findViewById(R.id.bottom);
		bottom_child = (LinearLayout) findViewById(R.id.bottom_child);
		bottom_b = (LinearLayout) findViewById(R.id.bottom_b);
		bottom_t = (LinearLayout) findViewById(R.id.bottom_t);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		time_current = (TextView) findViewById(R.id.time_current);
		seekbar1 = (SeekBar) findViewById(R.id.seekbar1);
		time_duration = (TextView) findViewById(R.id.time_duration);
		sheeting_icon = (ImageView) findViewById(R.id.sheeting_icon);
		repeat_mode = (ImageView) findViewById(R.id.repeat_mode);
		back = (ImageView) findViewById(R.id.back);
		play = (ImageView) findViewById(R.id.play);
		next = (ImageView) findViewById(R.id.next);
		focus_music = (ImageView) findViewById(R.id.focus_music);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		viewpager = (LinearLayout) findViewById(R.id.viewpager);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		album = (ImageView) findViewById(R.id.album);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		song_title = (TextView) findViewById(R.id.song_title);
		text_artist = (TextView) findViewById(R.id.text_artist);
		text_dot = (TextView) findViewById(R.id.text_dot);
		text_album = (TextView) findViewById(R.id.text_album);
		tool_music_bottom = (LinearLayout) findViewById(R.id.tool_music_bottom);
		like = (ImageView) findViewById(R.id.like);
		image_shuffle = (ImageView) findViewById(R.id.image_shuffle);
		image_share = (ImageView) findViewById(R.id.image_share);
		linear17 = (LinearLayout) findViewById(R.id.linear17);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear18 = (LinearLayout) findViewById(R.id.linear18);
		cancel = (ImageView) findViewById(R.id.cancel);
		image_favorite = (ImageView) findViewById(R.id.image_favorite);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		darker = (ImageView) findViewById(R.id.darker);
		permission = new AlertDialog.Builder(this);
		media_error_dialog = new AlertDialog.Builder(this);
		songData = getSharedPreferences("songData", Activity.MODE_PRIVATE);
		
		mask.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				isLeftBar = false;
				_OpenCloseLeftBar(isLeftBar);
			}
		});
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				
			}
		});
		
		menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isLeftBar) {
					isLeftBar = false;
					_OpenCloseLeftBar(isLeftBar);
					search.setEnabled(false);
				}
				else {
					isLeftBar = true;
					_OpenCloseLeftBar(isLeftBar);
					search.setEnabled(true);
				}
				android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE); imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
			}
		});
		
		search.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				_searchList(All_Song_Data, _charSeq);
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		search_icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isSearch) {
					search.setVisibility(View.GONE);
					title.setVisibility(View.VISIBLE);
					search.setText("");
					if (songData.getString("theme", "").equals("white")) {
						search_icon.setImageResource(R.drawable.search_black);
					}
					else {
						search_icon.setImageResource(R.drawable.search_white);
					}
					isSearch = false;
					android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE); imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
				}
				else {
					if (songData.getString("theme", "").equals("white")) {
						search_icon.setImageResource(R.drawable.close_black);
					}
					else {
						search_icon.setImageResource(R.drawable.close_white);
					}
					search.setVisibility(View.VISIBLE);
					title.setVisibility(View.GONE);
					isSearch = true;
					search.requestFocus();
					android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); if (inputMethodManager != null) { inputMethodManager.toggleSoftInput(android.view.inputmethod.InputMethodManager.SHOW_FORCED, 0); }
				}
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				num_fav = 1;
				length = All_Song_Data.size();
				num = num_fav - 1;
				for(int _repeat16 = 0; _repeat16 < (int)(All_Song_Data.size()); _repeat16++) {
					try{
						if (FavoriteMap.get((int)num).containsKey("favorite")) {
							favorite = All_Song_Data.get((int)num).get("favorite").toString();
							if (favorite.equals("true")) {
								
							}
							else {
								
								All_Song_Data.remove((int)(num));
							}
						}
						else {
							
							All_Song_Data.remove((int)(num));
						}
						num++;
					}catch (java.lang.Exception e){
					}
				}
			}
		});
		
		seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged (SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				if (M.mp != null){
					if (onSeek) {
						if (M.mp.isPlaying()) {
							M.mp.pause();
						}
						else {
							M.mp.pause();
						}
						M.mp.seekTo(_progressValue);
					}
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				if (M.mp != null){
					onSeek = true;
				}
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				if (M.mp != null){
					onSeek = false;
					if (M.mp.isPlaying()) {
						
					}
					else {
						M.mp.start();
					}
				}
			}
		});
		
		sheeting_icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isSheeting) {
					_CheetAnim(linear11, 330, 130, 300);
					isSheeting = false;
				}
				else {
					_CheetAnim(linear11, 330, 130, 300);
					isSheeting = true;
				}
			}
		});
		
		repeat_mode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (songData.getString("repeat", "").equals("false")) {
					songData.edit().putString("repeat", "true").commit();
					repeat_mode.setImageResource(R.drawable.repeat_blue);
				}
				else {
					if (songData.getString("repeat", "").equals("true")) {
						songData.edit().putString("repeat", "one").commit();
						repeat_mode.setImageResource(R.drawable.repeat_dark);
					}
					else {
						songData.edit().putString("repeat", "false").commit();
						if (isDarkMode) {
							repeat_mode.setImageResource(R.drawable.repeat_white);
						}
						else {
							repeat_mode.setImageResource(R.drawable.repeat_gray);
						}
					}
				}
				//adView.loadAd();
			}
		});
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				//Ads
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				if(M.mp == null){
					M.mp=new MediaPlayer();
				}else{
					M.mp.pause();
					M.mp.reset();
					if (All_Song_Data.size() == 0) {
						SketchwareUtil.showMessage(getApplicationContext(), "List lenght from 0");
					}
					else {
						if (songData.getString("shuffle", "").equals("true")) {
							_playWithPosition(SketchwareUtil.getRandom((int)(0), (int)(All_Song_Data.size() - 1)));
						}
						else {
							if (songData.getString("repeat", "").equals("true")) {
								if (lastPosition == (All_Song_Data.size() + 1)) {
									_playWithPosition(0);
								}
								else {
									_playWithPosition(lastPosition - 1);
								}
							}
							else {
								if (songData.getString("repeat", "").equals("one")) {
									_playWithPosition(lastPosition);
								}
								else {
									song--;
									if (song > -1) {
										filepath = All_Song_Data.get((int)song).get("data").toString();
										try {
											if (M.mp.isPlaying()) {
												M.mp.reset();
												M.mp.prepare();
											}else{
												M.mp.setDataSource(filepath);
												M.mp.prepare();
												M.mp.start();
											}
										} catch (Exception e) {
										}
										songData.edit().putString("lastPath", All_Song_Data.get((int)song).get("data").toString()).commit();
										songData.edit().putString("lastPosition", String.valueOf((long)(song))).commit();
										_mp_move();
									}
									else {
										song = 0;
										SketchwareUtil.showMessage(getApplicationContext(), "Top of list");
									}
								}
							}
						}
					}
				}
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
		});
		
		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				if(M.mp != null){
					if (M.mp.isPlaying()) {
						play.setImageResource(R.drawable.play_white);
						M.mp.pause();
					}
					else {
						play.setImageResource(R.drawable.pouse_white);
						M.mp.start();
					}
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				}else {
					if (songData.getString("lastPath", "").equals("")) {
						
					}
					else {
						_playWithPosition(Double.parseDouble(songData.getString("lastPosition", "")));
					}
				}
			}
		});
		
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				//adView.loadAd();
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				if(M.mp == null){
					M.mp=new MediaPlayer();
				}else{
					M.mp.pause();
					M.mp.reset();
					if (All_Song_Data.size() == 0) {
						SketchwareUtil.showMessage(getApplicationContext(), "List lenght from 0");
					}
					else {
						if (songData.getString("shuffle", "").equals("true")) {
							_playWithPosition(SketchwareUtil.getRandom((int)(0), (int)(All_Song_Data.size() - 1)));
						}
						else {
							if (songData.getString("repeat", "").equals("true")) {
								if (lastPosition == (All_Song_Data.size() - 1)) {
									_playWithPosition(0);
								}
								else {
									_playWithPosition(lastPosition + 1);
								}
							}
							else {
								if (songData.getString("repeat", "").equals("one")) {
									_playWithPosition(lastPosition);
								}
								else {
									song++;
									if (song < All_Song_Data.size()) {
										filepath = All_Song_Data.get((int)song).get("data").toString();
										try {
											if (M.mp.isPlaying()) {
												M.mp.reset();
												M.mp.prepare();
											}else{
												M.mp.setDataSource(filepath);
												M.mp.prepare();
												M.mp.start();
											}
										} catch (Exception e) {
										}
										songData.edit().putString("lastPath", All_Song_Data.get((int)song).get("data").toString()).commit();
										songData.edit().putString("lastPosition", String.valueOf((long)(song))).commit();
										_mp_move();
									}
									else {
										song = All_Song_Data.size();
										SketchwareUtil.showMessage(getApplicationContext(), "end of list");
									}
								}
							}
						}
					}
				}
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
		});
		
		focus_music.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				listview1.smoothScrollToPosition((int)(song));
				//adView.loadAd();
			}
		});
		
		like.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				//rewarded video ad show
			}
		});
		
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				isLeftBar = false;
				_OpenCloseLeftBar(isLeftBar);
			}
		});
		
		image_favorite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (All_Song_Data.get((int)song).containsKey("favourite")) {
					if (All_Song_Data.get((int)song).get("favourite").toString().equals("true")) {
						All_Song_Data.get((int)song).put("favourite", "false");
					}
					else {
						All_Song_Data.get((int)song).put("favourite", "true");
					}
				}
				else {
					All_Song_Data.get((int)song).put("favourite", "true");
				}
				listview1.setSelectionFromTop((int)viewIndex,(int) viewTop);
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				_SongFavourite(song);
				_Favorite(song);
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (songData.getString("shuffle", "").equals("false")) {
					songData.edit().putString("shuffle", "true").commit();
					imageview3.setImageResource(R.drawable.shuffle_blue);
				}
				else {
					songData.edit().putString("shuffle", "false").commit();
					if (isDarkMode) {
						imageview3.setImageResource(R.drawable.shuffle_white);
					}
					else {
						imageview3.setImageResource(R.drawable.shuffle);
					}
					songData.edit().putString("repeat", "false").commit();
					if (isDarkMode) {
						repeat_mode.setImageResource(R.drawable.repeat_white);
					}
					else {
						repeat_mode.setImageResource(R.drawable.repeat_gray);
					}
				}
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intentShareFile.setType("application/music");
				    intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+filepath));
				
				    intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
				                        "Sharing File...");
				    intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
				
				    startActivity(Intent.createChooser(intentShareFile, "Share File"));
			}
		});
		
		darker.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isDarkMode) {
					isDarkMode = false;
					songData.edit().putString("theme", "white").commit();
					_UI_DarkMode(isDarkMode, songData.getString("accent", ""));
				}
				else {
					isDarkMode = true;
					songData.edit().putString("theme", "dark").commit();
					_UI_DarkMode(isDarkMode, songData.getString("accent", ""));
				}
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
		});
	}
	
	private void initializeLogic() {
		_startApp();
		
		AudienceNetworkAds.initialize(this);
		banner = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_FB_AdView_Banner();
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(banner, (int)(100), (int)(60000));
		ad = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_AdView_Interstitial();
					}
				});
			}
		};
		_timer.schedule(ad, (int)(1000));
		/*
FileUtil.writeFile(FileUtil.getExternalStorageDir(), FileUtil.getExternalStorageDir());
*/
		listview1.setAdapter(new Listview1Adapter(All_Song_Data));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		_CheckSelf();
		if (songData.getString("accent", "").equals("")) {
			songData.edit().putString("accent", "#2196F3").commit();
		}
		else {
			
		}
		accentColor = songData.getString("accent", "");
		if (songData.getString("theme", "").equals("")) {
			songData.edit().putString("theme", "white").commit();
			_UI_DarkMode(false, songData.getString("accent", ""));
			isDarkMode = false;
		}
		else {
			if (songData.getString("theme", "").equals("dark")) {
				_UI_DarkMode(true, songData.getString("accent", ""));
				isDarkMode = true;
			}
			else {
				_UI_DarkMode(false, songData.getString("accent", ""));
				isDarkMode = false;
			}
		}
		if (M.mp != null) {
			if (M.mp.isPlaying()) {
				if (isDarkMode) {
					play.setImageResource(R.drawable.pouse_black);
				}
				else {
					play.setImageResource(R.drawable.pouse_white);
				}
				calendar.setTimeInMillis((long)(M.mp.getDuration()));
				time_duration.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
				seekbar1.setMax((int)M.mp.getDuration());
				timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								calendar.setTimeInMillis((long)(M.mp.getCurrentPosition()));
								time_current.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
								seekbar1.setProgress((int)M.mp.getCurrentPosition());
							}
						});
					}
				};
				_timer.scheduleAtFixedRate(timer, (int)(0), (int)(400));
			}
			else {
				calendar.setTimeInMillis((long)(M.mp.getDuration()));
				time_duration.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
				seekbar1.setMax((int)M.mp.getDuration());
				calendar.setTimeInMillis((long)(M.mp.getCurrentPosition()));
				time_current.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
				seekbar1.setProgress((int)M.mp.getCurrentPosition());
			}
		}
		song_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		song_title.setMarqueeRepeatLimit(-1);
		song_title.setSingleLine(true);
		song_title.setSelected(true);
		list_json = new Gson().toJson(All_Song_Data);
		background_list = new Gson().fromJson(list_json, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		if (songData.getString("repeat", "").equals("")) {
			songData.edit().putString("repeat", "false").commit();
		}
		else {
			if (songData.getString("repeat", "").equals("false")) {
				if (isDarkMode) {
					repeat_mode.setImageResource(R.drawable.repeat_white);
				}
				else {
					repeat_mode.setImageResource(R.drawable.repeat_gray);
				}
			}
			else {
				if (songData.getString("repeat", "").equals("true")) {
					repeat_mode.setImageResource(R.drawable.repeat_blue);
				}
				else {
					repeat_mode.setImageResource(R.drawable.repeat_dark);
				}
			}
		}
		if (songData.getString("shuffle", "").equals("")) {
			songData.edit().putString("shuffle", "false").commit();
		}
		else {
			if (songData.getString("shuffle", "").equals("false")) {
				if (isDarkMode) {
					imageview3.setImageResource(R.drawable.shuffle_white);
				}
				else {
					imageview3.setImageResource(R.drawable.shuffle);
				}
			}
			else {
				imageview3.setImageResource(R.drawable.shuffle_blue);
			}
		}
		if (!songData.getString("favorite", "").equals("")) {
			FavoriteMap = new Gson().fromJson(songData.getString("favorite", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		}
		mask.setTranslationX((float)((0 - SketchwareUtil.getDisplayWidthPixels(getApplicationContext())) - SketchwareUtil.getDip(getApplicationContext(), (int)(61))));
		_Animator(mask, "alpha", 0.0d, 200);
		left_bar.setTranslationZ(5);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (!songData.getString("viewIndex", "").equals("")) {
			viewIndex = Double.parseDouble(songData.getString("viewIndex", ""));
			viewTop = Double.parseDouble(songData.getString("viewTop", ""));
			listview1.setSelectionFromTop((int)viewIndex,(int) viewTop);
		}
	}
	
	@Override
	public void onBackPressed() {
		if (isLeftBar) {
			isLeftBar = false;
			_OpenCloseLeftBar(isLeftBar);
		}
		else {
			if (isSheeting) {
				_CheetAnim(linear11, 300, 116, 200);
				isSheeting = false;
			}
			else {
				finish();
			}
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		songData.edit().putString("viewIndex", String.valueOf((long)(listview1.getFirstVisiblePosition()))).commit();
		View v = listview1.getChildAt(0);
		songData.edit().putString("viewTop", String.valueOf((long)((v == null) ? 0 : (v.getTop() - listview1.getPaddingTop())))).commit();
	}
	public void _CheckSelf () {
		if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED) {
			permission.setTitle("Note Permissions");
			permission.setMessage("Read permission required, do you allow it?");
			permission.setPositiveButton("Yeah", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					requestPermissions(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
				}
			});
			permission.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					SketchwareUtil.showMessage(getApplicationContext(), "Obs files could not be retrieved not allowed");
				}
			});
			permission.setCancelable(false);
			permission.create().show();
		}else {
			getAllSongData();
		}
	}
	
	
	public void _AllData () {
	}
	public void getAllSongData() { 
		
		String[] projection = {
			
			android.provider.MediaStore.Audio.Media._ID, 
						android.provider.MediaStore.Audio.Media.ALBUM,
						android.provider.MediaStore.Audio.Media.ALBUM_KEY, 
						android.provider.MediaStore.Audio.Media.ARTIST,
						android.provider.MediaStore.Audio.Media.DATA,
						android.provider.MediaStore.Audio.Media.TITLE,
						android.provider.MediaStore.Audio.Media.DURATION,
			
			android.provider.MediaStore.Audio.Media.ALBUM_ID,
			
			//android.provider.MediaStore.Audio.Albums.ALBUM_ART
		};
		
		String orderBy = " " + android.provider.MediaStore.MediaColumns.DISPLAY_NAME;
		
		android.net.Uri uri = android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI; 
		
		cursor = getApplicationContext().getContentResolver().query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, orderBy);
		getAlbumColumnData(cursor);
	} 
	{
	}
	public static android.database.Cursor cursor;
	public static int music_column_index;
	
	private void getAlbumColumnData(android.database.Cursor cur) {
				try {
						if (cur.moveToFirst()) {
								int _id;
								String name;
								String data;
								String artist;
								String album;
								String songs_duration;
				
				do {
					
					name = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.TITLE));
										data = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.DATA));
										
										artist = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.ARTIST));
										
										album = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.ALBUM));
					
						{
												HashMap<String, Object> _item = new HashMap<>();
												_item.put("album", album);
												_item.put("name", name);
												_item.put("data", data);
												_item.put("artist", artist);
												All_Song_Data.add( _item);
										}
								} while (cur.moveToNext());}
				} catch (Exception e) {
						e.printStackTrace();
		}
	}
	{
	}
	private String setCorrectDuration(String songs_duration) {
						// TODO Auto-generated method stub
				
						if(Integer.valueOf(songs_duration) != null) {
									int time = Integer.valueOf(songs_duration);
						
									int seconds = time/1000;
									int minutes = seconds/60;
									seconds = seconds % 60;
						
									if(seconds<10) {
												songs_duration = String.valueOf(minutes) + ":0" + String.valueOf(seconds);
								song_duration = songs_duration;
									} else {
												songs_duration = String.valueOf(minutes) + ":" + String.valueOf(seconds);
								song_duration = songs_duration;
									}
									return songs_duration;
						}
						return null;
			}
	{
	}
	
	
	public void _mp_move () {
		System.runFinalization();
		Runtime.getRuntime().gc();
		System.gc();
		if (M.mp != null){
			MediaMetadataRetriever mr = new MediaMetadataRetriever();
			try{
				mr.setDataSource(filepath);
				byte[] art = mr.getEmbeddedPicture();
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inSampleSize = 2;
				Bitmap songImage = BitmapFactory.decodeByteArray(art,0,art.length,opt);
				
				album.setImageBitmap(songImage);
				
			}catch (java.lang.Exception e){
				album.setImageResource(R.drawable.icon_large_white);
			}
			song_title.setText(All_Song_Data.get((int)song).get("name").toString());
			if (All_Song_Data.get((int)song).containsKey("artist")) {
				text_artist.setText(All_Song_Data.get((int)song).get("artist").toString());
			}
			else {
				text_artist.setText("Unknown");
			}
			if (All_Song_Data.get((int)song).containsKey("album")) {
				text_album.setText(All_Song_Data.get((int)song).get("album").toString());
			}
			else {
				text_album.setText("Unknown");
			}
			calendar.setTimeInMillis((long)(M.mp.getDuration()));
			time_duration.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
			seekbar1.setMax((int)M.mp.getDuration());
			timer = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							calendar.setTimeInMillis((long)(M.mp.getCurrentPosition()));
							time_current.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
							seekbar1.setProgress((int)M.mp.getCurrentPosition());
						}
					});
				}
			};
			_timer.scheduleAtFixedRate(timer, (int)(400), (int)(400));
		}else {
			SketchwareUtil.showMessage(getApplicationContext(), "Not: Media is not found expention");
		}
		M.mp.setOnCompletionListener (new MediaPlayer.OnCompletionListener (
		) {
			public void
			 onCompletion (MediaPlayer theMediaPlayer) {
				if(M.mp == null){
					M.mp=new MediaPlayer();
				}else{
					M.mp.pause(); M.mp.reset();
					if (!(All_Song_Data.size() == 0)) {
						if (songData.getString("shuffle", "").equals("true")) {
							_playWithPosition(SketchwareUtil.getRandom((int)(lastPosition), (int)(All_Song_Data.size() - 1)));
						}
						else {
							if (songData.getString("repeat", "").equals("true")) {
								if (lastPosition == (All_Song_Data.size() - 1)) {
									_playWithPosition(0);
								}
								else {
									_playWithPosition(lastPosition + 1);
								}
							}
							else {
								if (songData.getString("repeat", "").equals("one")) {
									_playWithPosition(lastPosition);
								}
								else {
									song++;
									if (song < All_Song_Data.size()) {
										filepath = All_Song_Data.get((int)song).get("data").toString();
										try {
											if (M.mp.isPlaying()) {
												M.mp.reset();
												M.mp.prepare();
											}else{
												M.mp.setDataSource(filepath);
												M.mp.prepare();
												M.mp.start();
											}
										} catch (Exception e) {
										}
										((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
										_mp_move();
									}
									else {
										song = All_Song_Data.size();
									}
								}
							}
						}
					}
				}
			}});
		M.mp.setOnErrorListener(new MediaPlayer.OnErrorListener(){
			
							@Override
							public boolean onError(MediaPlayer p1, int p2, int p3)
							{
				if (!(All_Song_Data.size() == 0)) {
					if (!((song == All_Song_Data.size()) || (song == 0))) {
						media_error_dialog.setTitle("Media Error");
						media_error_dialog.setMessage("Unfortunately this file is corrupt, media cannot be played");
						media_error_dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						media_error_dialog.create().show();
					}
					else {
						
					}
				}
				M.mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
					@Override
					public void onPrepared(MediaPlayer mp){
					}
				});
				return true;
			}
		});
	}
	
	
	public void _UI_DarkMode (final boolean _darkMode, final String _color) {
		if (_darkMode) {
			color = "#252525";
			colorPrimaryDark = "#212121";
			if (isLeftBar) {
				View decor = getWindow().getDecorView();
				decor.setSystemUiVisibility(0);
				
				getWindow().setStatusBarColor(Color.parseColor(colorPrimaryDark));
				
				getWindow().setNavigationBarColor(Color.parseColor(colorPrimaryDark));
			}
			else {
				View decor = getWindow().getDecorView();
				decor.setSystemUiVisibility(0);
				getWindow().setStatusBarColor(Color.parseColor(color));
				getWindow().setNavigationBarColor(Color.parseColor(color));
			}
			seekbar1.getProgressDrawable().setColorFilter(Color.parseColor("#2196F3"), PorterDuff.Mode.SRC_IN);
			seekbar1.getThumb().setColorFilter(Color.parseColor("#1976D2"), PorterDuff.Mode.SRC_IN);
			_setBackground(background, 0, 0, color, false);
			_setBackground(main, 0, 0, color, false);
			_SX_CornerRadius_4(linear11, color, color, 2, 50, 50, 0, 0);
			_SX_CornerRadius_4(left_bar, color, color, 0, 0, 30, 30, 0);
			_GradientDrawable(action_bar, 0, 0, 5, colorPrimaryDark, colorPrimaryDark, false, false, 0);
			_GradientDrawable(play, 50, 0, 0, _color, _color, false, true, 100);
			_GradientDrawable(back, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(next, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(tool_music_bottom, 50, 0, 0, color, color, false, false, 0);
			_GradientDrawable(like, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(repeat_mode, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(image_shuffle, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(image_share, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(focus_music, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(sheeting_icon, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(menu, 50, 0, 0, colorPrimaryDark, colorPrimaryDark, true, true, 100);
			_GradientDrawable(search_icon, 50, 0, 0, colorPrimaryDark, colorPrimaryDark, true, true, 100);
			_GradientDrawable(cancel, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(image_favorite, 50, 0, 0, colorPrimaryDark, colorPrimaryDark, true, true, 100);
			_GradientDrawable(darker, 50, 0, 0, colorPrimaryDark, colorPrimaryDark, true, true, 100);
			_GradientDrawable(imageview3, 50, 0, 0, colorPrimaryDark, colorPrimaryDark, true, true, 100);
			_GradientDrawable(imageview4, 50, 0, 0, colorPrimaryDark, colorPrimaryDark, true, true, 100);
			menu.setImageResource(R.drawable.menu_white);
			search_icon.setImageResource(R.drawable.search_white);
			sheeting_icon.setImageResource(R.drawable.swipe_top_white);
			back.setImageResource(R.drawable.backward_white);
			play.setImageResource(R.drawable.play_black);
			next.setImageResource(R.drawable.forward_white);
			focus_music.setImageResource(R.drawable.focus_white);
			darker.setImageResource(R.drawable.light);
			cancel.setImageResource(R.drawable.swipe_left_white);
			title.setTextColor(0xFFF5F5F5);
			time_current.setTextColor(0xFFF5F5F5);
			time_duration.setTextColor(0xFFF5F5F5);
			song_title.setTextColor(0xFFF5F5F5);
			text_artist.setTextColor(0xFFF5F5F5);
			text_album.setTextColor(0xFFF5F5F5);
			search.setTextColor(0xFFF5F5F5);
			search.setHintTextColor(Color.parseColor("#30ffffff"));
		}
		else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); }
			color = "#f5f5f5";
			colorPrimaryDark = "#eeeeee";
			if (isLeftBar) {
				getWindow().setStatusBarColor(Color.parseColor("#cccccc"));
				
				getWindow().setNavigationBarColor(Color.parseColor("#cccccc"));
			}
			else {
				getWindow().setStatusBarColor(Color.parseColor(color));
				
				getWindow().setNavigationBarColor(Color.parseColor(color));
			}
			seekbar1.getProgressDrawable().setColorFilter(Color.parseColor("#2196F3"), PorterDuff.Mode.SRC_IN);
			seekbar1.getThumb().setColorFilter(Color.parseColor("#1976D2"), PorterDuff.Mode.SRC_IN);
			_setBackground(main, 0, 0, "#FFFFFF", false);
			_setBackground(background, 0, 0, "#FFFFFF", false);
			_SX_CornerRadius_4(linear11, "#ffffff", "#ffffff", 2, 50, 50, 0, 0);
			_SX_CornerRadius_4(left_bar, color, color, 0, 0, 30, 30, 0);
			_GradientDrawable(action_bar, 0, 0, 5, "#ffffff", "#ffffff", false, false, 0);
			_GradientDrawable(play, 50, 0, 0, _color, _color, false, true, 100);
			_GradientDrawable(back, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(next, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(tool_music_bottom, 50, 0, 0, color, color, false, false, 0);
			_GradientDrawable(like, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(repeat_mode, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(image_shuffle, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(image_share, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(focus_music, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(sheeting_icon, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(menu, 50, 0, 0, "#ffffff", "#ffffff", true, true, 100);
			_GradientDrawable(search_icon, 50, 0, 0, "#ffffff", "#ffffff", true, true, 100);
			_GradientDrawable(darker, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(image_favorite, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(cancel, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(imageview3, 50, 0, 0, color, color, true, true, 100);
			_GradientDrawable(imageview4, 50, 0, 0, color, color, true, true, 100);
			menu.setImageResource(R.drawable.menu_black);
			search_icon.setImageResource(R.drawable.search_black);
			sheeting_icon.setImageResource(R.drawable.swipe_top_black);
			back.setImageResource(R.drawable.backward_black);
			play.setImageResource(R.drawable.play_white);
			next.setImageResource(R.drawable.forward_black);
			focus_music.setImageResource(R.drawable.focus_black);
			darker.setImageResource(R.drawable.night);
			cancel.setImageResource(R.drawable.swipe_left);
			title.setTextColor(0xFF212121);
			time_current.setTextColor(0xFF212121);
			time_duration.setTextColor(0xFF212121);
			song_title.setTextColor(0xFF212121);
			text_artist.setTextColor(0xFF212121);
			text_album.setTextColor(0xFF212121);
			search.setTextColor(0xFF424242);
			search.setHintTextColor(Color.parseColor("#30000000"));
		}
	}
	
	
	public void _GradientDrawable (final View _view, final double _radius, final double _stroke, final double _shadow, final String _color, final String _borderColor, final boolean _ripple, final boolean _clickAnim, final double _animDuration) {
		if (_ripple) {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			gd.setStroke((int)_stroke,Color.parseColor(_borderColor));
			if (Build.VERSION.SDK_INT >= 21){
				_view.setElevation((int)_shadow);}
			android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#9e9e9e")});
			android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb , gd, null);
			_view.setClickable(true);
			_view.setBackground(ripdrb);
		}
		else {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			gd.setStroke((int)_stroke,Color.parseColor(_borderColor));
			_view.setBackground(gd);
			if (Build.VERSION.SDK_INT >= 21){
				_view.setElevation((int)_shadow);}
		}
		if (_clickAnim) {
			_view.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()){
						case MotionEvent.ACTION_DOWN:{
							ObjectAnimator scaleX = new ObjectAnimator();
							scaleX.setTarget(_view);
							scaleX.setPropertyName("scaleX");
							scaleX.setFloatValues(0.9f);
							scaleX.setDuration((int)_animDuration);
							scaleX.start();
							
							ObjectAnimator scaleY = new ObjectAnimator();
							scaleY.setTarget(_view);
							scaleY.setPropertyName("scaleY");
							scaleY.setFloatValues(0.9f);
							scaleY.setDuration((int)_animDuration);
							scaleY.start();
							break;
						}
						case MotionEvent.ACTION_UP:{
							
							ObjectAnimator scaleX = new ObjectAnimator();
							scaleX.setTarget(_view);
							scaleX.setPropertyName("scaleX");
							scaleX.setFloatValues((float)1);
							scaleX.setDuration((int)_animDuration);
							scaleX.start();
							
							ObjectAnimator scaleY = new ObjectAnimator();
							scaleY.setTarget(_view);
							scaleY.setPropertyName("scaleY");
							scaleY.setFloatValues((float)1);
							scaleY.setDuration((int)_animDuration);
							scaleY.start();
							
							break;
						}
					}
					return false;
				}
			});
		}
	}
	
	
	public void _SX_CornerRadius_4 (final View _view, final String _color1, final String _color2, final double _str, final double _n1, final double _n2, final double _n3, final double _n4) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor(_color1));
		
		gd.setStroke((int)_str, Color.parseColor(_color2));
		
		gd.setCornerRadii(new float[]{(int)_n1,(int)_n1,(int)_n2,(int)_n2,(int)_n3,(int)_n3,(int)_n4,(int)_n4});
		
		_view.setBackground(gd);
		
		_view.setElevation(8);
		_view.setTranslationZ(10);
	}
	
	
	public void _startApp () {
		oldPosition = -1;
		search.setVisibility(View.GONE);
		left_bar.setTranslationX((float)((0 - SketchwareUtil.getDisplayWidthPixels(getApplicationContext())) - SketchwareUtil.getDip(getApplicationContext(), (int)(61))));
		_LayoutParams(mask, SketchwareUtil.getDisplayWidthPixels(getApplicationContext()), SketchwareUtil.getDisplayHeightPixels(getApplicationContext()));
		_LayoutParams(layout, SketchwareUtil.getDisplayWidthPixels(getApplicationContext()), SketchwareUtil.getDisplayHeightPixels(getApplicationContext()) - SketchwareUtil.getDip(getApplicationContext(), (int)(130)));
		linear11.setTranslationZ(5);
		mask.setTranslationX((float)((0 - SketchwareUtil.getDisplayWidthPixels(getApplicationContext())) - SketchwareUtil.getDip(getApplicationContext(), (int)(61))));
		mask.setVisibility(View.GONE);
		mask.setAlpha((float)(0));
		if (songData.getString("lastPosition", "").equals("")) {
			song = -1;
		}
		else {
			song = Double.parseDouble(songData.getString("lastPosition", ""));
		}
	}
	
	
	public void _playWithPosition (final double _position) {
		song = _position;
		songData.edit().putString("lastPath", All_Song_Data.get((int)_position).get("data").toString()).commit();
		songData.edit().putString("lastPosition", String.valueOf((long)(_position))).commit();
		lastPosition = Double.parseDouble(songData.getString("lastPosition", ""));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		if(M.mp == null){
			M.mp=new MediaPlayer();
		}else{
			M.mp.pause();
			M.mp.reset();
		}
		filepath = All_Song_Data.get((int)_position).get("data").toString();
		try {
			if (M.mp.isPlaying()) {
				M.mp.pause();
			}else{
				play.setImageResource(R.drawable.pouse_white);
				M.mp.setDataSource(filepath);
				M.mp.prepare();
				M.mp.start();
				_mp_move();
			}
		} catch (java.io.IOException e) { }
	}
	
	
	public void _LayoutParams (final View _view, final double _width, final double _height) {
		_view.setLayoutParams(new LinearLayout.LayoutParams((int)_width,(int) _height));
	}
	
	
	public void _CheetAnim (final View _view, final double _size, final double _childSize, final double _duration) {
		if (isSheeting) {
			_Animator(sheeting_icon, "rotation", 0, 300);
			_Animator(_view, "translationY", SketchwareUtil.getDip(getApplicationContext(), (int)(10)), _duration);
			timer = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							_Animator(_view, "translationY", 0, _duration);
						}
					});
				}
			};
			_timer.schedule(timer, (int)(_duration));
		}
		else {
			_Animator(sheeting_icon, "rotation", 180, 300);
			_Animator(_view, "translationY", SketchwareUtil.getDip(getApplicationContext(), (int)(_childSize - (_size + 10))), _duration);
			timer = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							_Animator(_view, "translationY", SketchwareUtil.getDip(getApplicationContext(), (int)(_childSize - _size)), _duration);
						}
					});
				}
			};
			_timer.schedule(timer, (int)(_duration));
		}
	}
	
	
	public void _Animator (final View _view, final String _propertyName, final double _value, final double _duration) {
		ObjectAnimator anim = new ObjectAnimator();
		anim.setTarget(_view);
		anim.setPropertyName(_propertyName);
		anim.setFloatValues((float)_value);
		anim.setDuration((long)_duration);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.start();
	}
	
	
	public void _setText (final View _view, final String _text) {
		TextView view = (TextView)_view;
		
		view.setText(_text);
	}
	
	
	public void _searchList (final ArrayList<HashMap<String, Object>> _listmap, final String _searchString) {
		All_Song_Data.clear();
		n = 0;
		for(int _repeat12 = 0; _repeat12 < (int)(background_list.size()); _repeat12++) {
			map_test = background_list.get((int)n);
			if (_searchString.equals("")) {
				All_Song_Data = new Gson().fromJson(list_json, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			}
			else {
				item_json = new Gson().toJson(map_test);
				if (item_json.toLowerCase().contains(_searchString.toLowerCase())) {
					All_Song_Data.add(map_test);
				}
			}
			n++;
		}
		listview1.setAdapter(new Listview1Adapter(All_Song_Data));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _setClass () {
	}
	static class M{
		static Context context;
		static MediaPlayer mp;
	}
	
	private void shareFile(java.io.File file) { Intent intentShareFile = new Intent(Intent.ACTION_SEND); intentShareFile.setType(java.net.URLConnection.guessContentTypeFromName(file.getName())); intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+file.getAbsolutePath())); //if you need //intentShareFile.putExtra(Intent.EXTRA_SUBJECT,"Sharing File Subject); //intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File Description"); startActivity(Intent.createChooser(intentShareFile, "Share File")); 
	}
	
	
	public void _SongFavourite (final double _position) {
		if (All_Song_Data.get((int)_position).containsKey("favorite")) {
			if (All_Song_Data.get((int)_position).get("favorite").toString().equals("true")) {
				
			}
			else {
				
			}
		}
		else {
			
		}
	}
	
	
	public void _Favorite (final double _position) {
		m = 0;
		if (!(FavoriteMap.size() == 0)) {
			for(int _repeat15 = 0; _repeat15 < (int)(FavoriteMap.size()); _repeat15++) {
				try{
					if (FavoriteMap.get((int)m).get("id").toString().equals(All_Song_Data.get((int)_position).get("id").toString())) {
						favSong = "true";
						break;
					}
					else {
						favSong = "false";
					}
					m++;
				}catch (java.lang.Exception e){
				}
			}
		}
		else {
			favSong = "false";
		}
		if (favSong.equals("false")) {
			map = All_Song_Data.get((int)_position);
			FavoriteMap.add(map);
		}
		else {
			FavoriteMap.remove((int)(m));
		}
		if (!(FavoriteMap.size() == 0)) {
			try{
				songData.edit().putString("favorite", new Gson().toJson(FavoriteMap)).commit();
			}catch (java.lang.Exception e){
			}
		}
	}
	
	
	public void _OpenCloseLeftBar (final boolean _open) {
		if (_open) {
			_Animator(left_bar, "translationX", 0 - SketchwareUtil.getDisplayWidthPixels(getApplicationContext()), 200);
			mask.setEnabled(true);
			_Animator(mask, "alpha", 0.2d, 200);
			mask.setVisibility(View.VISIBLE);
			if (isDarkMode) {
				 @android.annotation.SuppressLint("RestrictedApi")
				        android.animation.ValueAnimator colorAnimation = android.animation.ValueAnimator.ofObject(
				                new ArgbEvaluator(), Color.parseColor("#252525"), Color.parseColor("#212121"));
				        colorAnimation.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
					            @Override
					            public void onAnimationUpdate(android.animation.ValueAnimator animator) {
						                int color = (int) animator.getAnimatedValue();
						                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							                    getWindow().setStatusBarColor(color);
							getWindow().setNavigationBarColor(color);
							                }
						            }
					
					        });
				        colorAnimation.setDuration(200);
				        colorAnimation.start();
				    
				
				
			}
			else {
				 @android.annotation.SuppressLint("RestrictedApi")
				        android.animation.ValueAnimator colorAnimation = android.animation.ValueAnimator.ofObject(
				                new ArgbEvaluator(), Color.parseColor("#f5f5f5"), Color.parseColor("#eeeeee"));
				        colorAnimation.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
					            @Override
					            public void onAnimationUpdate(android.animation.ValueAnimator animator) {
						                int color = (int) animator.getAnimatedValue();
						                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							                    getWindow().setStatusBarColor(color);
							getWindow().setNavigationBarColor(color);
							                }
						            }
					
					        });
				        colorAnimation.setDuration(200);
				        colorAnimation.start();
				    
				
				
			}
		}
		else {
			_Animator(left_bar, "translationX", (0 - SketchwareUtil.getDisplayWidthPixels(getApplicationContext())) - SketchwareUtil.getDip(getApplicationContext(), (int)(61)), 200);
			mask.setEnabled(false);
			_Animator(mask, "alpha", 0, 200);
			time = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							mask.setVisibility(View.GONE);
						}
					});
				}
			};
			_timer.schedule(time, (int)(200));
			if (isDarkMode) {
				 @android.annotation.SuppressLint("RestrictedApi")
				        android.animation.ValueAnimator colorAnimation = android.animation.ValueAnimator.ofObject(
				                new ArgbEvaluator(), Color.parseColor("#252525"), Color.parseColor("#212121"));
				        colorAnimation.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
					            @Override
					            public void onAnimationUpdate(android.animation.ValueAnimator animator) {
						                int color = (int) animator.getAnimatedValue();
						                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							                    getWindow().setStatusBarColor(color);
							getWindow().setNavigationBarColor(color);
							                }
						            }
					
					        });
				        colorAnimation.setDuration(200);
				        colorAnimation.start();
				    
				
				
			}
			else {
				 @android.annotation.SuppressLint("RestrictedApi")
				        android.animation.ValueAnimator colorAnimation = android.animation.ValueAnimator.ofObject(
				                new ArgbEvaluator(), Color.parseColor("#f5f5f5"), Color.parseColor("#eeeeee"));
				        colorAnimation.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
					            @Override
					            public void onAnimationUpdate(android.animation.ValueAnimator animator) {
						                int color = (int) animator.getAnimatedValue();
						                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							                    getWindow().setStatusBarColor(color);
							getWindow().setNavigationBarColor(color);
							                }
						            }
					
					        });
				        colorAnimation.setDuration(200);
				        colorAnimation.start();
				    
				
				
			}
		}
	}
	
	
	public void _setBackground (final View _view, final double _radius, final double _shadow, final String _color, final boolean _ripple) {
		if (_ripple) {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			_view.setElevation((int)_shadow);
			android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#9e9e9e")});
			android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb , gd, null);
			_view.setClickable(true);
			_view.setBackground(ripdrb);
		}
		else {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			_view.setBackground(gd);
			_view.setElevation((int)_shadow);
		}
	}
	
	
	public void _statusbarColorAnim (final String _color1, final String _color2, final double _duration) {
		 @android.annotation.SuppressLint("RestrictedApi")
		        android.animation.ValueAnimator colorAnimation = android.animation.ValueAnimator.ofObject(
		                new ArgbEvaluator(), Color.parseColor(_color1), Color.parseColor(_color2));
		        colorAnimation.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
			            @Override
			            public void onAnimationUpdate(android.animation.ValueAnimator animator) {
				                int color = (int) animator.getAnimatedValue();
				                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					                    getWindow().setStatusBarColor(color);
					                }
				            }
			
			        });
		        colorAnimation.setDuration((int)_duration);
		        colorAnimation.start();
	}
	
	
	public void _FB_AdView_Banner () {
		ID_Placement_banner = "370815064186018_371642247436633";
		AdView adView = new AdView(this, ID_Placement_banner, AdSize.BANNER_HEIGHT_50);
		        // Find the Ad Container
		        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
		        // Add the ad view to your activity layout
		        adContainer.addView(adView);
		        // Request an ad
		
		        AdListener adListener = new AdListener() {
			            @Override
			            public void onError(Ad ad, AdError adError) {
			}
			            @Override
			            public void onAdLoaded(Ad ad) {
			}                @Override
			            public void onAdClicked(Ad ad) {
				                // Ad clicked callback
				//What To Do
			}
			
			            @Override
			            public void onLoggingImpression(Ad ad) {
				                // Ad impression logged callback
				            }
			        };
		        // Request an ad
		        AdView.AdViewLoadConfig loadAdConfig = adView.buildLoadAdConfig()
					.withAdListener(adListener)
					.build();
		
				adView.loadAd(loadAdConfig);
	}
	
	
	public void _AdView_Interstitial () {
		ID_Interstitial = "370815064186018_371642754103249";
		 // Instantiate an InterstitialAd object.
		        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
		        // now, while you are testing and replace it later when you have signed up.
		        // While you are using this temporary code you will only get test ads and if you release
		        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
		        interstitialAd = new InterstitialAd(this, ID_Interstitial);
		// Set listeners for the Interstitial Ad
		        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
			            @Override
			            public void onInterstitialDisplayed(Ad ad) {
				                // Interstitial ad displayed callback
				                Log.e(TAG, "Interstitial ad displayed.");
				            }
			
			            @Override
			            public void onInterstitialDismissed(Ad ad) {
				                // Interstitial dismissed callback
				                Log.e(TAG, "Interstitial ad dismissed.");
				                interstitialAd.loadAd();
				            }
			
			            @Override
			            public void onError(Ad ad, AdError adError) {
				                // Ad error callback
				                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
				           
				 }
			
			            @Override
			            public void onAdLoaded(Ad ad) {
				                // Interstitial ad is loaded and ready to be displayed
				                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
				
				            
			}
			
			            @Override
			            public void onAdClicked(Ad ad) {
				                // Ad clicked callback
				                Log.d(TAG, "Interstitial ad clicked!");
				            }
			
			            @Override
			            public void onLoggingImpression(Ad ad) {
				                // Ad impression logged callback
				                Log.d(TAG, "Interstitial ad impression logged!");
				            }
			        };
		
		        // For auto play video ads, it's recommended to load the ad
		        // at least 30 seconds before it is shown
		        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig()
				.withAdListener(interstitialAdListener) 
				
				.build());
	}
	
	
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.custom, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final TextView song_name = (TextView) _view.findViewById(R.id.song_name);
			final TextView song_artist = (TextView) _view.findViewById(R.id.song_artist);
			final TextView time = (TextView) _view.findViewById(R.id.time);
			final TextView album = (TextView) _view.findViewById(R.id.album);
			
			if (_data.size() > 0) {
				if (_data.get((int)_position).containsKey("name")) {
					song_name.setText(_data.get((int)_position).get("name").toString());
				}
				if (_data.get((int)_position).containsKey("artist")) {
					song_artist.setText(_data.get((int)_position).get("artist").toString());
				}
				if (_data.get((int)_position).containsKey("album")) {
					album.setText(_data.get((int)_position).get("album").toString());
				}
				if (M.mp!=null) {
					if (M.mp.isPlaying()) {
						if (songData.getString("lastPath", "").equals(_data.get((int)_position).get("data").toString())) {
							song_name.setTextColor(0xFFB71C1C);
							imageview1.setImageResource(R.drawable.pouse_white);
						}
						else {
							song_name.setTextColor(0xFF424242);
							imageview1.setImageResource(R.drawable.icon_large_white);
						}
					}
					else {
						song_name.setTextColor(0xFF424242);
						imageview1.setImageResource(R.drawable.icon_large_white);
					}
				}
				else {
					song_name.setTextColor(0xFF424242);
					imageview1.setImageResource(R.drawable.icon_large_white);
				}
				try{
					
					music_column_index = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.DURATION);
					
					cursor.moveToPosition(_position);
					
					if (cursor != null) {
						while (cursor.moveToNext()) {
							
							song_duration = cursor.getString(music_column_index);
							setCorrectDuration(song_duration);
							time.setText(song_duration);
						}
						cursor.close();
					}
				}catch (java.lang.Exception e){
				}
				if (isDarkMode) {
					_GradientDrawable(imageview1, 15, 3, 0, accentColor, accentColor, false, false, 0);
					_GradientDrawable(linear1, 15, 0, 0, "#252525", "#252525", true, false, 0);
					song_name.setTextColor(0xFFF5F5F5);
				}
				else {
					_GradientDrawable(imageview1, 15, 3, 0, accentColor, accentColor, false, false, 0);
					_GradientDrawable(linear1, 15, 0, 0, "#ffffff", "#ffffff", true, false, 0);
					song_name.setTextColor(0xFF424242);
				}
				linear1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						_playWithPosition(_position);
						//adView.loadAd();
					}
				});
			}
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
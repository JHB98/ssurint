package listview.androidtown.org.ssuprinter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class choice extends AppCompatActivity {

    Toolbar myToolbar;
    GridLayout showfiles;
    Uri uri;
    ArrayList<File> fileArrayList;
    ArrayList<Uri> fileUri;
    TextView tv;
    TextView ref;
    Point size;
    Uri clickFileUri;
    int count=0;
    int choiceFileId;
    boolean multiclick=false;
    boolean click=false;
    int num;
    Button settingButton;
    ViewGroup.LayoutParams params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);

        Display display=getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);

        settingButton=(Button)findViewById(R.id.settingButton);
        settingButton.setEnabled(false);

       String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download";
       File f=new File(path);
       fileArrayList=new ArrayList<File>();
       fileUri=new ArrayList<Uri>();
       File[] files=f.listFiles();
        for(File file : files)
            {
                if((file.getName().endsWith(".jpg"))||(file.getName().endsWith(".pdf"))||(file.getName().endsWith(".docx"))||(file.getName().endsWith(".pptx"))||(file.getName().endsWith(".txt"))||(file.getName().endsWith(".png")))
                {
                    fileArrayList.add(file);
                }

            }
        showfiles=(GridLayout)findViewById(R.id.showfile);
        for(int i=0;i<fileArrayList.size();i++)
        {
            tv=new TextView(this);
            tv.setId(i);
            tv.setWidth((size.x/2));
            tv.setHeight((size.y/4));
            uri=Uri.parse(fileArrayList.get(i).toString());
            fileUri.add(uri);
            tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionImage(uri),null,null);
            tv.setText(fileArrayList.get(i).getName());
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.BLACK);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(multiclick)
                    {
                        tv=(TextView)findViewById(choiceFileId);
                        tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionImage(clickFileUri),null,null);
                        ref=(TextView)v;
                        for(num=0;num<fileUri.size();num++) {
                            if (ref.getText().equals(fileArrayList.get(num).getName())) {
                                clickFileUri = fileUri.get(num);
                                break;
                            }
                        }
                        ref.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionCheckedImage(clickFileUri),null,null);
                        if(tv.equals(v))
                        {
                            ref=(TextView)v;
                            ref.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionImage(clickFileUri),null,null);
                            click=false;
                            multiclick=false;
                            settingButton.setEnabled(false);
                        }
                        choiceFileId=v.getId();
                    }
                    else {
                        choiceFileId=v.getId();
                        tv=(TextView)findViewById(choiceFileId);
                        for(num=0;num<fileUri.size();num++) {
                            if (tv.getText().equals(fileArrayList.get(num).getName())) {
                                clickFileUri = fileUri.get(num);
                                break;
                            }
                        }
                        tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionCheckedImage(clickFileUri),null,null);
                        if(click==false)
                        {
                            click = true;
                            multiclick=true;
                            settingButton.setEnabled(true);
                        }
                    }
                }
            });
            showfiles.addView(tv);
        }
        myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(Color.GRAY);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        android.support.v7.widget.SearchView searchView=(android.support.v7.widget.SearchView)menu.findItem(R.id.find).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("파일명으로 검색합니다.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               count=0;
               click=false;
               multiclick=false;
               settingButton.setEnabled(false);
               Context context=getApplicationContext();
               showfiles.removeAllViews();
               for(int i=0;i<fileArrayList.size();i++)
               {
                   if (fileArrayList.get(i).getName().contains(query))
                   {
                       ref = new TextView(context);
                       ref.setId(i);
                       ref.setWidth((size.x/2));
                       ref.setHeight((size.y/4));
                       ref.setText(fileArrayList.get(i).getName());
                       uri=Uri.parse(fileArrayList.get(i).toString());
                       ref.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionImage(uri),null,null);
                       ref.setTextColor(Color.BLACK);
                       ref.setGravity(Gravity.CENTER);
                       ref.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v)
                           {
                               if(multiclick)
                               {
                                   ref=(TextView)findViewById(choiceFileId);
                                   ref.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionImage(clickFileUri),null,null);
                                   tv=(TextView)v;
                                   for(num=0;num<fileUri.size();num++) {
                                       if (tv.getText().equals(fileArrayList.get(num).getName())) {
                                           clickFileUri = fileUri.get(num);
                                           break;
                                       }
                                   }
                                   tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionCheckedImage(clickFileUri),null,null);
                                   if(ref.equals(v))
                                   {
                                       tv=(TextView)v;
                                       tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionImage(clickFileUri),null,null);
                                       click=false;
                                       multiclick=false;
                                       settingButton.setEnabled(false);
                                   }
                                   choiceFileId=v.getId();
                               }
                               else {
                                   choiceFileId=v.getId();
                                   ref=(TextView)findViewById(choiceFileId);
                                   for(num=0;num<fileUri.size();num++) {
                                       if (ref.getText().equals(fileArrayList.get(num).getName())) {
                                           clickFileUri = fileUri.get(num);
                                           break;
                                       }
                                   }
                                   ref.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionCheckedImage(clickFileUri),null,null);
                                   if(click==false)
                                   {
                                       click = true;
                                       multiclick=true;
                                       settingButton.setEnabled(true);
                                   }
                               }
                           }
                       });
                       showfiles.addView(ref);
                   }
               }
               showfiles.postInvalidate();
               return true;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               count=0;
               click=false;
               multiclick=false;
               settingButton.setEnabled(false);
               Context context=getApplicationContext();
               showfiles.removeAllViews();
               for(int i=0;i<fileArrayList.size();i++)
               {
                   if (fileArrayList.get(i).getName().contains(newText))
                   {
                       ref = new TextView(context);
                       ref.setWidth((size.x/2));
                       ref.setId(i);
                       ref.setHeight((size.y/4));
                       ref.setText(fileArrayList.get(i).getName());
                       ref.setTextColor(Color.BLACK);
                       ref.setGravity(Gravity.CENTER);
                       uri=Uri.parse(fileArrayList.get(i).toString());
                       ref.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionImage(uri),null,null);
                       ref.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v)
                           {
                               if(multiclick)
                               {
                                   ref=(TextView)findViewById(choiceFileId);
                                   ref.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionImage(clickFileUri),null,null);
                                   tv=(TextView)v;
                                   for(num=0;num<fileUri.size();num++) {
                                       if (tv.getText().equals(fileArrayList.get(num).getName())) {
                                           clickFileUri = fileUri.get(num);
                                           break;
                                       }
                                   }
                                   tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionCheckedImage(clickFileUri),null,null);
                                   if(ref.equals(v))
                                   {
                                       tv=(TextView)v;
                                       tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionImage(clickFileUri),null,null);
                                       click=false;
                                       multiclick=false;
                                       settingButton.setEnabled(false);
                                   }
                                   choiceFileId=v.getId();
                               }
                               else {
                                   choiceFileId=v.getId();
                                   ref=(TextView)findViewById(choiceFileId);
                                   for(num=0;num<fileUri.size();num++) {
                                       if (ref.getText().equals(fileArrayList.get(num).getName())) {
                                           clickFileUri = fileUri.get(num);
                                           break;
                                       }
                                   }
                                   ref.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionCheckedImage(clickFileUri),null,null);
                                   if(click==false)
                                   {
                                       click = true;
                                       multiclick=true;
                                       settingButton.setEnabled(true);
                                   }
                               }
                           }
                       });
                       showfiles.addView(ref);
                   }
               }
               showfiles.postInvalidate();
               return true;
           }
       });
        return true;
    }
    public void cancelClick(View v)
    {
        count=0;
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void settingClick(View v)
    {
        count=0;
        Intent intent=new Intent(this,setting.class);
        intent.putExtra("choicedFileUri",clickFileUri);
        startActivity(intent);
    }

    public void listdraw(Context context,String extenstion)
    {
        settingButton.setEnabled(false);
        click=false;
        multiclick=false;
        for(int i=0;i<fileArrayList.size();i++)
        {
            if (fileArrayList.get(i).getName().contains(extenstion))
            {
                ref = new TextView(context);
                ref.setWidth((size.x / 2));
                ref.setId(i);
                ref.setHeight((size.y/4));
                ref.setText(fileArrayList.get(i).getName());
                ref.setGravity(Gravity.CENTER);
                uri=Uri.parse(fileArrayList.get(i).toString());
                ref.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionImage(uri),null,null);
                ref.setTextColor(Color.BLACK);
                ref.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if(multiclick)
                        {
                            ref=(TextView)findViewById(choiceFileId);
                            ref.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionImage(clickFileUri),null,null);
                            tv=(TextView)v;
                            for(num=0;num<fileUri.size();num++) {
                                if (tv.getText().equals(fileArrayList.get(num).getName())) {
                                    clickFileUri = fileUri.get(num);
                                    break;
                                }
                            }
                            tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionCheckedImage(clickFileUri),null,null);
                            if(ref.equals(v))
                            {
                                tv=(TextView)v;
                                tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionImage(clickFileUri),null,null);
                                click=false;
                                multiclick=false;
                                settingButton.setEnabled(false);
                            }
                            choiceFileId=v.getId();
                        }
                        else {
                            choiceFileId=v.getId();
                            ref=(TextView)findViewById(choiceFileId);
                            for(num=0;num<fileUri.size();num++) {
                                if (ref.getText().equals(fileArrayList.get(num).getName())) {
                                    clickFileUri = fileUri.get(num);
                                    break;
                                }
                            }
                            ref.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getExtenstionCheckedImage(clickFileUri),null,null);
                            if(click==false)
                            {
                                click = true;
                                multiclick=true;
                                settingButton.setEnabled(true);
                            }
                        }
                    }
                });
                showfiles.addView(ref);
            }
        }
        showfiles.postInvalidate();
        count++;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.sort:
                Context context=getApplicationContext();
                showfiles.removeAllViews();

                switch(count)
                {
                    case 0:
                        listdraw(context,".");
                        Toast.makeText(context,"모든 파일",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        listdraw(context,".jpg");
                        Toast.makeText(context,"jpg 파일",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        listdraw(context,".pdf");
                        Toast.makeText(context,"pdf 파일",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        listdraw(context,".docx");
                        Toast.makeText(context,"docx 파일",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        listdraw(context,".pptx");
                        Toast.makeText(context,"pptx 파일",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        listdraw(context,".txt");
                        Toast.makeText(context,"txt 파일",Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        listdraw(context,".png");
                        Toast.makeText(context,"png 파일",Toast.LENGTH_SHORT).show();
                        count=0;
                        break;
                }
                break;
        }

        return true;
    }
    public Drawable getExtenstionImage(Uri uri) {
        Drawable icon;
        if(uri.toString().contains(".jpg"))
        {
            icon=getResources().getDrawable(R.drawable.jpg_icon);
        }
        else if(uri.toString().contains(".pdf"))
        {
            icon=getResources().getDrawable(R.drawable.pdf_icon);
        }
        else if(uri.toString().contains(".docx"))
        {
            icon=getResources().getDrawable(R.drawable.docx_icon);
        }
        else if(uri.toString().contains(".pptx"))
        {
            icon=getResources().getDrawable(R.drawable.ppt_icon);
        }
        else if(uri.toString().contains(".txt"))
        {
            icon=getResources().getDrawable(R.drawable.txt_icon);
        }
        else
        {
            icon=getResources().getDrawable(R.drawable.png_icon);
        }

        return icon;
    }
    public Drawable getExtenstionCheckedImage(Uri uri) {
        Drawable icon;
        if(uri.toString().contains(".jpg"))
        {
            icon=getResources().getDrawable(R.drawable.checked_jpg_icon);
        }
        else if(uri.toString().contains(".pdf"))
        {
            icon=getResources().getDrawable(R.drawable.checked_pdf_icon);
        }
        else if(uri.toString().contains(".docx"))
        {
            icon=getResources().getDrawable(R.drawable.checked_docx_icon);
        }
        else if(uri.toString().contains(".pptx"))
        {
            icon=getResources().getDrawable(R.drawable.checked_ppt_icon);
        }
        else if(uri.toString().contains(".txt"))
        {
            icon=getResources().getDrawable(R.drawable.checked_txt_icon);
        }
        else
        {
            icon=getResources().getDrawable(R.drawable.checked_png_icon);
        }

        return icon;
    }
}

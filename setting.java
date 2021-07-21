package listview.androidtown.org.ssuprinter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class setting extends AppCompatActivity {
    Toolbar myToolbar;
    LinearLayout linearLayout;
    ConstraintLayout constraintLayout;
    boolean status=false;
    boolean user=false;
    boolean setting=false;
    TextView choicedFile;
    Uri choicedFileUri;
    String qNum;
    EditText nameAndNumber;
    EditText x;
    EditText y;
    EditText times;
    TextView queNum;
    FileEvent fileEvent=new FileEvent();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        myToolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(Color.GRAY);
        new Thread() {
            public void run() {
                try {
                    Socket socket = new Socket("203.253.21.119", 8001);
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    qNum = input.readLine();
                    socket.close();
                    queupdate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        choicedFile=(TextView)findViewById(R.id.choicedFile);
        choicedFileUri=getIntent().getParcelableExtra("choicedFileUri");
        choicedFile.setText("선택된 파일 : "+choicedFileUri.toString());
    }
    public void queupdate() {
        queNum=(TextView)findViewById(R.id.queNum);
        queNum.setText("프린트 큐 : "+qNum+"개의 프린트가 현재 작업 대기 중입니다.");
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu2, menu);
        return true;
    }
    public void printClick(View v)
    {
        nameAndNumber=(EditText)findViewById(R.id.nameAndNumber);
        if(nameAndNumber.getText().toString().equals(""))
        {
            fileEvent.setUserInfo("Users");
        }
        else
        {
            fileEvent.setUserInfo(nameAndNumber.getText().toString());
        }
        x=(EditText)findViewById(R.id.x);
        try
        {
            fileEvent.setLower(Integer.parseInt(x.getText().toString()));
        }
        catch(Exception e) {
            fileEvent.setLower(-1);
        }
        y=(EditText)findViewById(R.id.y);
        try
        {
            fileEvent.setUpper(Integer.parseInt(y.getText().toString()));
        }
        catch(Exception e) {
            fileEvent.setUpper(-1);
        }

        times=(EditText)findViewById(R.id.times);
        try
        {
            fileEvent.setTimes(Integer.parseInt(times.getText().toString()));
        }
        catch(Exception e) {
            fileEvent.setTimes(1);
        }
        sending(fileEvent);
    }
    public void backClick()
    {
        Intent intent=new Intent(this,choice.class);
        startActivity(intent);
    }
    public void statusClick(View v)
    {
        linearLayout = (LinearLayout) findViewById(R.id.statusLayout);
        if(status)
        {
            linearLayout.setVisibility(View.GONE);
            status=false;
        }
        else
        {
            linearLayout.setVisibility(View.VISIBLE);
            status=true;
        }
    }
    public void settingClick2(View v)
    {
        constraintLayout=(ConstraintLayout) findViewById(R.id.settingLayout);
        if(setting)
        {
            constraintLayout.setVisibility(View.GONE);
            setting=false;
        }
        else
        {
            constraintLayout.setVisibility(View.VISIBLE);
            setting=true;
        }
    }
    public void userClick(View v)
    {
        constraintLayout = (ConstraintLayout) findViewById(R.id.userLayout);
        if (user)
        {
            constraintLayout.setVisibility(View.GONE);
            user=false;
        }
        else
        {
            constraintLayout.setVisibility(View.VISIBLE);
            user=true;
        }
    }
    public void previewClick(View v) {
        Intent intent=new Intent();

        if (checkExtenstion(".jpg"))
        {
            getAppWithExtenstion(".jpg",intent);
        }
        else if (checkExtenstion(".pdf"))
        {
            getAppWithExtenstion(".pdf",intent);
        }
        else if (checkExtenstion(".docx"))
        {
            getAppWithExtenstion(".docx",intent);
        }
        else if (checkExtenstion(".pptx"))
        {
            getAppWithExtenstion(".pptx",intent);
        }
        else if (checkExtenstion(".txt"))
        {
            getAppWithExtenstion(".txt",intent);
        }
        else
        {
            getAppWithExtenstion(".png",intent);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.back:
                backClick();
                break;
        }

        return true;
    }

    public boolean checkExtenstion(String extenstion)
    {
        if(choicedFileUri.toString().contains(extenstion))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void getAppWithExtenstion(String extenstion,Intent intent) {
        File f = new File(choicedFileUri.getPath());
        f.setReadable(true);
        choicedFileUri = Uri.fromFile(f);
        if(extenstion.equals(".jpg"))
        {
            intent.setDataAndType(choicedFileUri, "image/jpg");
        }
        else if(extenstion.equals(".pdf"))
        {
            intent.setDataAndType(choicedFileUri, "application/pdf");
        }
        else if(extenstion.equals(".docx"))
        {
            intent.setDataAndType(choicedFileUri, "application/msword");
        }
        else if(extenstion.equals(".pptx"))
        {
           intent.setDataAndType(choicedFileUri, "application/vnd.ms-powerpoint");
        }
        else if(extenstion.equals(".txt"))
        {
            intent.setDataAndType(choicedFileUri, "text/plain");
        }
        else
        {
            intent.setDataAndType(choicedFileUri, "image/png");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(Intent.createChooser(intent, extenstion));
    }

    public void sending(final FileEvent fileEvent1){
            new Thread() {
                public void run() {
                try {
                    TcpClient client = new TcpClient("203.253.21.119", 8000, choicedFileUri.toString(), "C:/prac/test/");
                    client.sendFile(fileEvent1);
                    client.receive();
                    client.close();
                }
                catch(Exception e)
                {
                    Intent intent=new Intent(getApplicationContext(),serverfail.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                }
            }.start();
            Intent intent=new Intent(this,serversuccess.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
    }
}


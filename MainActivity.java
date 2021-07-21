package listview.androidtown.org.ssuprinter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    TextView queText;
    String qNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    public void queupdate() {
        queText=(TextView)findViewById(R.id.que);
        queText.setText("프린트 큐 : "+qNum+"개의 프린트가 현재 작업 대기 중입니다.");
    }
    public void choiceClick(View v)
    {
        Intent intent=new Intent(this,choice.class);
        startActivity(intent);
    }

}

package com.example.mysocketclient;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    EditText editText2;

    TextView textView;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);

        textView = (TextView) findViewById(R.id.textView);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestThread thread = new RequestThread();
                thread.start();
            }
        });

    }



    class RequestThread extends Thread {
        public void run() {
            request();
        }

        public void request() {
            try {
                String host = editText.getText().toString().trim();
                String portStr = editText2.getText().toString().trim();
                int port = Integer.parseInt(portStr);

                Socket socket = new Socket(host, port);
                println("소켓이 만들어졌습니다. : " + "localhost" + ":" + "11001");

                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                outstream.writeObject("Hello.");
                outstream.flush();
                println("서버로 보낸 데이터 : Hello.");

                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                Object input = instream.readObject();
                println("서버로부터 받은 데이터 : " + input);

                socket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void println(final String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });

    }



}

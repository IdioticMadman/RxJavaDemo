package com.robert.rxjavademo;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    private OptionsPickerView<IPickerViewData> mPickerView;
    private List<IPickerViewData> mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv.setText("测试下划线");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPickerView.show();
            }
        });







        mPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                Toast.makeText(MainActivity.this, mItem.get(options1).getPickerViewText(), Toast.LENGTH_SHORT).show();
            }
        })
                .build();
        mItem = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mItem.add(new Data());
        }
        mPickerView.setPicker(mItem);
        Observable.OnSubscribe<Integer> onSubscribe = new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onStart();
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onCompleted();
            }
        };

        Observable<Integer> observable = Observable.create(onSubscribe);
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Integer integer) {
            }
        };
        observable.subscribe(subscriber);
    }

    static class Data implements IPickerViewData {

        @Override
        public String getPickerViewText() {
            return this.hashCode() + "";
        }
    }
}

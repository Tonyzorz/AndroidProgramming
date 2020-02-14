package com.example.calculatorfeb03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    //기준은 애플 계산기
    //에러 보기 기능
    //top messages
    private TextView introduction;
    private int randomNum = 0;
    String[] randomIntroduction = {"neat", "could try better..", "just seeing cool nums", "way to go!", "pretty smart"};

    //버튼들
    private Button calculation_result, button_c, button_plusMinus, button_percent, button_divide, button_7, button_8, button_9, button_multiply,
            button_4, button_5, button_6, button_minus, button_1, button_2, button_3, button_plus, button_0, button_dot, button_equals;

    //숫자 에딧텍스트
    private EditText edit;
    //숫자 값 저장 변수
    private Double a = 0.00;

    //음수(negative number), 정수(positive number)시 비교해야하는 .charAt()이 달라서 사용하는 변수
    private int plusMinusCharNumber = 0;

    //애플 계산기 처럼 연산을 눌렀을 경우 숫자를 입력하기 전까지 기존에 있던 숫자가 보이게하는 조건
    //checks if operation( *, %, -, +, =) was used
    //true= used
    //false= hasnt been used
    private boolean testing = false;

    //연산 번호
    //( 2 = divide, 3 = multiply, 4 = minus, 5 = plus)
    private int where = 0;

    //음수 또는 정수 역할 정하는 불리언
    //number starts as positive so default value must be false.
    //true = need to remove '-'
    //false = need to add '-'
    boolean plusMinus = false;

    //랜덤으로 배열에 담긴 메세지 출력하는 재미로해본 기능
    //For TextView introduction, random index generator from 0 ~ 4
    private void randomGenerator(){
        randomNum = (int)(Math.random() * 5);
        introduction.setText(randomIntroduction[randomNum]);
        //introduction.setText();
    }

    //연산을 클릭하면 저장
    //Saves number to a and resets edit
    private void saveNumber(){
        introduction.setText("Arrived at saveNumber()");
        a = Double.valueOf(edit.getText().toString().trim());
        testing = true;
        settingText();
    }

    //소수점이 필요한지 확인하는 기능
    //Checs if decimal point is needed or not
    private void settingText(){
        //만약 1로 나눴을 시 나머지가 0이면 정수
        if(a % 1 == 0){
            edit.setText(String.valueOf(Math.round(a)));
        } else {
            edit.setText(Double.toString(a));
        }

        //계산 후 음수이면 바꿔주기
        if(a < 0) plusMinus = true;
    }

    //숫자입력시 확인하는 메소드
    //for number pad
    private void typingNumber(int number){
        //0이 시작일 경우 숫자입력
        if(edit.getText().toString().charAt(0) == '0') {
            introduction.setText("arrived");
            edit.setText(String.valueOf(number));
            testing = false;
        //0이 시작이지만 앞에 - 사인이 있을때
        } else if(plusMinus && edit.getText().toString().charAt(plusMinusCharNumber) == '0'){
            edit.setText('-' + String.valueOf(number));
            plusMinus = true;
            plusMinusCharNumber = 0;
        //연산을 눌렀을 경우 첫 숫자 입력하기
        } else if(testing){
            edit.setText(String.valueOf(number));
            plusMinus = false;
            testing = false;
        //연산 후, 숫자 더 추가
        }else {
            plusMinus = false;
            testing = false;
            edit.setText(edit.getText().toString() + String.valueOf(number));
        }
    }

    //퍼센트 버튼 클릭시 연산하는 메소드
    private void percentNumber(){
        a = Double.valueOf(edit.getText().toString());
        a *= 0.01;
        edit.setText(String.valueOf(a));
    }

    //소수점 추가하기
    private void addDecimalPoint(){
        String checking = edit.getText().toString();
        //'.' 없을시 추가하기
        if(!checking.contains(".")){
            edit.setText(edit.getText().toString() + ".");
        }
    }

    //+ or - 추가하는 메소드
    private void addPlusMinus(){
        //plusMinus가 false시 - 추가하고, plusMinus true바꿔주고, 숫자가 -0일수도 있으니 plusMinusCharNumber 1로 변경
        if(!plusMinus) {
            edit.setText("-" + edit.getText().toString());
            plusMinus = !plusMinus;
            plusMinusCharNumber = 1;
        }
        //plusMinus true시 '-' 없이 저장, plusMinus false바꿔주고, CharNumber 0로 변경
        else if(plusMinus /*|| edit.getText().toString().charAt(0) == '-'*/){
            edit.setText(edit.getText().toString().substring(1, edit.getText().toString().length()));
            plusMinus = !plusMinus;
            plusMinusCharNumber = 0;
        }
    }

    //연산해주는 메소드
    private void checkEquals(){
        //2 = /, 3 = *, 4 = -, 5 = +
        if(where == 2){
            a /= Double.valueOf(edit.getText().toString().trim());
            settingText();
            randomGenerator();
        } else if(where == 3){
            a *= Double.valueOf(edit.getText().toString().trim());
            settingText();
            randomGenerator();
        } else if(where == 4){
            a -= Double.valueOf(edit.getText().toString().trim());
            settingText();
            randomGenerator();
        } else if(where == 5){
            a += Double.valueOf(edit.getText().toString().trim());
            settingText();
            randomGenerator();
        }
    }

    //계산기 리셋해주는 메소드
    public void checkC(){
        edit.setText("0");
        a = 0.00;
        plusMinus = false;
        testing = false;
    }
    //시작시 만들자
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "Calculator", Toast.LENGTH_SHORT).show();

        //Button ids
        introduction = findViewById(R.id.calculation_introduction);

        button_c = findViewById(R.id.button_c);
        button_plusMinus = findViewById(R.id.button_plusMinus);
        button_percent = findViewById(R.id.button_percent);
        button_divide = findViewById(R.id.button_divide);

        button_7 = findViewById(R.id.button_7);
        button_8 = findViewById(R.id.button_8);
        button_9 = findViewById(R.id.button_9);
        button_multiply = findViewById(R.id.button_multiply);

        button_4 = findViewById(R.id.button_4);
        button_5 = findViewById(R.id.button_5);
        button_6 = findViewById(R.id.button_6);
        button_minus = findViewById(R.id.button_minus);

        button_1 = findViewById(R.id.button_1);
        button_2 = findViewById(R.id.button_2);
        button_3 = findViewById(R.id.button_3);
        button_plus = (Button)findViewById(R.id.button_plus);

        button_0 = findViewById(R.id.button_0);
        button_dot = findViewById(R.id.button_dot);
        button_equals = findViewById(R.id.button_equals);

        edit = findViewById(R.id.calculation_result);

        //Listener functions
        View.OnClickListener cl = new View.OnClickListener(){

            @Override
                public void onClick(View v) {
                    if(v == button_1) typingNumber(1);
                    else if(v == button_2) typingNumber(2);
                    else if(v == button_3) typingNumber(3);
                    else if(v == button_4) typingNumber(4);
                    else if(v == button_5) typingNumber(5);
                    else if(v == button_6) typingNumber(6);
                    else if(v == button_7) typingNumber(7);
                    else if(v == button_8) typingNumber(8);
                    else if(v == button_9) typingNumber(9);
                    else if(v == button_0) typingNumber(0);
                    else if(v == button_percent) percentNumber();
                    else if(v == button_dot) addDecimalPoint();
                    else if(v == button_c) checkC();
                    else if(v == button_plusMinus) addPlusMinus();
                    else if(v == button_divide){
                        saveNumber();
                        where = 2;
                    } else if(v == button_multiply){
                        saveNumber();
                        where = 3;
                    } else if(v == button_minus){
                        saveNumber();
                        where = 4;
                    } else if(v == button_plus){
                        saveNumber();
                        where = 5;
                    } else if(v == button_equals) checkEquals();
            }
        };

        //Sets the listeners per button
        button_c.setOnClickListener(cl);
        button_plusMinus.setOnClickListener(cl);
        button_percent.setOnClickListener(cl);
        button_divide.setOnClickListener(cl);

        button_7.setOnClickListener(cl);
        button_8.setOnClickListener(cl);
        button_9.setOnClickListener(cl);
        button_multiply.setOnClickListener(cl);

        button_4.setOnClickListener(cl);
        button_5.setOnClickListener(cl);
        button_6.setOnClickListener(cl);
        button_minus.setOnClickListener(cl);

        button_1.setOnClickListener(cl);
        button_2.setOnClickListener(cl);
        button_3.setOnClickListener(cl);
        button_plusMinus.setOnClickListener(cl);

        button_0.setOnClickListener(cl);
        button_dot.setOnClickListener(cl);
        button_equals.setOnClickListener(cl);




    }
}

package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText input;
    private TextView display;
    private double operand1 ;
    private double operand2 ;

    private String pendingOperation="=";
    private static  final String operationSave="pendingOperation";

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_layout);

        result =(EditText) findViewById(R.id.result);
        input  =(EditText) findViewById(R.id.input);
        display =(TextView) findViewById(R.id.display);


       Button button0=(Button) findViewById(R.id.button0);
       Button button1=(Button) findViewById(R.id.button1);
       Button button2=(Button) findViewById(R.id.button2);
       Button button3=(Button) findViewById(R.id.button3);
       Button button4=(Button) findViewById(R.id.button4);
       Button button5=(Button) findViewById(R.id.button5);
       Button button6=(Button) findViewById(R.id.button6);
       Button button7=(Button) findViewById(R.id.button7);
       Button button8=(Button) findViewById(R.id.button8);
       Button button9=(Button) findViewById(R.id.button9);
       Button button_dot=(Button) findViewById(R.id.button_dot);


       Button calculate=(Button) findViewById(R.id.calculate);
       Button button_modulus=(Button) findViewById(R.id.button_modulus);
       Button button_devide=(Button) findViewById(R.id.button_devide);
       Button button_subtract=(Button) findViewById(R.id.button_subtract);
       Button button_add=(Button) findViewById(R.id.button_add);
       Button button_multiply=(Button) findViewById(R.id.button_multiply);
       Button button_power=(Button) findViewById(R.id.button_power);
       Button button_clear=(Button) findViewById(R.id.clear_button);
       Button button_neg=(Button) findViewById(R.id.button_Neg);

//       Button button_fact=(Button) findViewById(R.id.button_Fact);




        View.OnClickListener negate= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("-"+input.getText());
            }
        };
        button_neg.setOnClickListener(negate);

      View.OnClickListener listener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "onClick: "+v);
                Button temp=(Button) v;

                String num=temp.getText().toString();
                if (input.getText().toString().length()==0 && num.equals("."))
                    input.append("0.");
                else
                    input.append(num);

            }
        };


        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        button_dot.setOnClickListener(listener);

        View.OnClickListener listener1= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "onClick: "+v);
                Button temp=(Button) v;

                String operation=temp.getText().toString();
                String num=input.getText().toString();
                String op2=result.getText().toString();


//                Log.d(TAG, "onClick: here us  =="+operation+"/");
                if(operation.equals("!") && num.length()>0) {

                    double ans=Double.parseDouble(num);
                    if(ans<1) {
                        result.setText("Not a number");
                        input.setText("");
                    }
                    else if(ans<=10) {
                        ans = factorial(ans);
                        num = Double.toString(ans);
                        result.setText(num);
                    }
                    else {
                        result.setText("Ans is too large");
                    }
//                    return;
                    input.setText("");
                }
                else if(num.length()>0 && op2.length()>=0)
                {

                    result.setText(num);
                    input.setText("");
                    pendingOperation=operation;
                    display.setText(pendingOperation);
                }
                else if(num.length()==0 && op2.length()>0)
                {
                    pendingOperation=operation;
                    display.setText(pendingOperation);
                }

            }
        };

        View.OnClickListener clear= new View.OnClickListener() {
            @Override
            public void onClick(View clear) {
                result.setText("");
                input.setText("");
                display.setText("");
                pendingOperation="=";
            }
        };
        button_clear.setOnClickListener(clear);


        button_add.setOnClickListener(listener1);
        button_multiply.setOnClickListener(listener1);
        button_devide.setOnClickListener(listener1);
        button_subtract.setOnClickListener(listener1);
        button_modulus.setOnClickListener(listener1);
        button_power.setOnClickListener(listener1);



//        button_fact.setOnClickListener(listener1);//this button is for factorial of a no.



        View.OnClickListener equal =new View.OnClickListener() {
            @Override
            public void onClick(View equal) {
                String op1=result.getText().toString();
                String op2=input.getText().toString();

                if(op1.length()!=0 && op2.length()!=0 && display.getText().toString().length()!=0)
                {
                    double ans=performOperation(Double.parseDouble(op1),Double.parseDouble(op2));
                    result.setText(Double.toString(ans));
                    input.setText("");
                    display.setText("");
                }
            }
        };
        calculate.setOnClickListener(equal);
    }
    private double factorial(double num)
    {
        if(num<=1)  return 1;

        return  factorial(num-1)*num;
    }
    private double performOperation(double op1,double op2)
    {
        switch (pendingOperation)
        {
            case "/":
                return op1/op2;
            case "*":
                return op1*op2;
            case "+":
                return op1+op2;
            case "-":
                return op1-op2;
            case "%":
                return op1%op2;
            case "^":
                double ans=1.0;
                for(int i=0;i<op2;i++)
                    ans*=op1;
                return ans;
        }
        return 0.0;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation =savedInstanceState.getString(operationSave);
        display.setText(pendingOperation);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(operationSave,pendingOperation);
        super.onSaveInstanceState(outState);
    }

}
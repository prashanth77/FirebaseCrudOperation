package sample.firebaseexpense.com;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {
    Spinner spin_category, spin_paymetn;
    Button btn_save, btn_cancel, btn_delete, btn_update;
    EditText et_date, et_ItemName, et_Itemdesc, et_Itemprice;
    List<String> cat_list, pay_list;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    String current_date, item_name, item_details, category_name, payment_name;
    int item_price;

    //    String current_date1, item_name1, item_details1, category_name1, payment_name1;
    int item_price1;
    boolean isUpdate = false;
    //public static    String id;
    public static String id;
    DatabaseReference databaseExpense;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseExpense = FirebaseDatabase.getInstance().getReference("ExpenseManager");
//        id = databaseExpense.push().getKey();

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_can);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_update = (Button) findViewById(R.id.btn_update);
        spin_category = (Spinner) findViewById(R.id.spinner_category);
        spin_paymetn = (Spinner) findViewById(R.id.spiner_payment);
        et_date = (EditText) findViewById(R.id.edit_date);
        et_ItemName = (EditText) findViewById(R.id.edit_itemName);
        et_Itemdesc = (EditText) findViewById(R.id.edit_itemdetails);
        et_Itemprice = (EditText) findViewById(R.id.edit_price);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Expense Manager");



        try {
            isUpdate = getIntent().getExtras().getBoolean("isUpdate");

        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        if (isUpdate == true) {

            current_date = getIntent().getExtras().getString("_date");
            item_name = getIntent().getExtras().getString("_name");
            item_details = getIntent().getExtras().getString("_deta");
            category_name = getIntent().getExtras().getString("_cate");
            payment_name = getIntent().getExtras().getString("_paym");
            item_price = getIntent().getExtras().getInt("_pric");
            id = getIntent().getExtras().getString("_id");
            update_values();

            btn_delete.setVisibility(View.VISIBLE);
            btn_update.setVisibility(View.VISIBLE);
        }

        cat_list = new ArrayList<String>();
        cat_list.add("Books");
        cat_list.add("By Cheque");
        cat_list.add("Debit Card");
        cat_list.add("Credit Card");
        cat_list.add("EFT");
        cat_list.add("Vochers");

        pay_list = new ArrayList<String>();
       pay_list.add("By Cash");
        pay_list.add("Entertainment");
        pay_list.add("food");
        pay_list.add("Groceries");
        pay_list.add("Medical");
        pay_list.add("Travel");

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        final ArrayAdapter<String> category_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_gallery_item, cat_list);
        category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_category.setAdapter(category_Adapter);
        int spinnerPosition2 = category_Adapter.getPosition(category_name);
        spin_category.setSelection(spinnerPosition2);
        spin_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> pay_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pay_list);

        pay_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_paymetn.setAdapter(pay_Adapter);
        int spinnerPosition = pay_Adapter.getPosition(payment_name);
        spin_paymetn.setSelection(spinnerPosition);
        spin_paymetn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                payment_name = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                et_date.getText().clear();
                et_ItemName.getText().clear();
                et_Itemdesc.getText().clear();
                et_Itemprice.getText().clear();
                String id = getIntent().getExtras().getString("_id");
                DatabaseReference databaseExpense = FirebaseDatabase.getInstance().getReference("ExpenseManager").child(id);
                databaseExpense.removeValue();

                Toast.makeText(getApplicationContext(),"successfully deleted", Toast.LENGTH_LONG).show();

            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpenses();

                //                   Toast.makeText(getApplicationContext(),"hello", Toast.LENGTH_LONG).show();

            }


        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

      /*          Intent intent = new Intent(MainActivity.this, ExpensesView.class);
                startActivity(intent);
      */
                startActivity(new Intent(MainActivity.this, ExpensesView.class));
            }
        });

        et_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String aid = getIntent().getExtras().getString("_id");


                String update_date, update_name, update_details;
                int update_price;

                update_date = et_date.getText().toString();
                update_name = et_ItemName.getText().toString();
                update_details = et_Itemdesc.getText().toString();
                update_price = Integer.parseInt(et_Itemprice.getText().toString());
                //String id, String Update_cate, String Update_date,String Update_name, String Update_details, int Update_price, String Update_payment
                Update_values(aid, category_name, update_date, update_name, update_details, update_price, payment_name);



            }
        });

    }

    private void update_values() {
        et_Itemprice.setText("" + item_price);
        et_Itemdesc.setText(item_details);
        et_ItemName.setText(item_name);
        et_date.setText(current_date);


        Log.e("TAG", "" + item_price + "" + item_details + "" + item_price1 + "" + current_date);
    }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_date.setText(sdf.format(myCalendar.getTime()));
        current_date = sdf.format(myCalendar.getTime());
    }

    private void addExpenses() {
        item_name = et_ItemName.getText().toString();
        item_details = et_Itemdesc.getText().toString();
//        String price = et_Itemprice.getText().toString();
        item_price = Integer.parseInt(et_Itemprice.getText().toString());
        current_date = et_date.getText().toString();
        if(TextUtils.isEmpty(item_name)) {
            Toast.makeText(this, "enter item name ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(item_details)) {
            Toast.makeText(this, "enter item details ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(current_date)) {
            Toast.makeText(this, "enter current_date", Toast.LENGTH_SHORT).show();
            return;
        }

/*
        if (item_details.length() < 0 && item_name.length() < 0 && current_date.length() < 0) {
            Toast.makeText(getApplicationContext(), "adfdsgh", Toast.LENGTH_LONG).show();

        }
*/

        else {

            databaseExpense = FirebaseDatabase.getInstance().getReference("ExpenseManager");
         String id=databaseExpense.push().getKey();
            DataModel productModel = new DataModel(id, category_name, current_date, item_name, item_details, item_price, payment_name);
            databaseExpense.child(id).setValue(productModel);


            et_date.getText().clear();
            et_Itemdesc.getText().clear();
            et_Itemprice.getText().clear();
            et_ItemName.getText().clear();

            Toast.makeText(this, "success fully added", Toast.LENGTH_SHORT).show();

        }
    }



    public void Update_values(String id, String Update_cate, String Update_date, String Update_name, String Update_details, int Update_price, String Update_payment) {
        databaseExpense = FirebaseDatabase.getInstance().getReference("ExpenseManager").child(id);
        DataModel productModel = new DataModel(id, Update_cate, Update_date, Update_name, Update_details, Update_price, Update_payment);
        databaseExpense.setValue(productModel);
        Log.e("_aaId", productModel.getId());
        Log.e("_aaaN", productModel.getName());
        Log.e("_aaaC", productModel.getCategory());
        Log.e("_aaade", productModel.getDetails());
    }

}

package com.giraffe.foodplannerapplication.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.PlannedMeal;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class BottomSheet {
    private static Date date;

    private static ChipGroup cgType;

    private static TextView tvWeekDay;

    private static Button btnConfirm;

    private static int mealType = -1, day = -1;

    private static final String TAG = "BottomSheet";

    private static void showDatePickerDialog(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();//fri => 6
        int tomorrowDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Log.i(TAG, "tomorrowDayOfWeek: " + tomorrowDayOfWeek);
        int daysUntilNextFriday = Calendar.FRIDAY - tomorrowDayOfWeek;//6-7
        Log.i(TAG, "daysUntilNextFriday: " + daysUntilNextFriday);
        if (daysUntilNextFriday < 0) {
            daysUntilNextFriday += 7;
        }
        calendar.add(Calendar.DAY_OF_WEEK, daysUntilNextFriday);
        Date maxDate = calendar.getTime();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            /*date = new Date(year, month, dayOfMonth);
            Date d = new Date(date.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String readableDate = sdf.format(d);
            tvWeekDay.setText(readableDate);*/
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.set(Calendar.HOUR, 0);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            Log.i(TAG, mCalendar.getTime().getTime() + "");


            date = new Date(mCalendar.getTime().getTime());


            String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
            day = getDayNum(selectedDate.split(",")[0]);
            tvWeekDay.setText(selectedDate);
        }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(startDate.getTime());
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime());
        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.orange));
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.orange));
    }

    public static void showBottomSheetDialog(Context context, Meal meal, OnBottomConfirmed onBottomConfirmed) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_layout);


        cgType = bottomSheetDialog.findViewById(R.id.cg_type);
        tvWeekDay = bottomSheetDialog.findViewById(R.id.tv_week_day);
        btnConfirm = bottomSheetDialog.findViewById(R.id.btn_confirm);

        cgType.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = group.findViewById(checkedId);
            if (chip != null) {
                switch (chip.getText().toString().trim()) {
                    case "Breakfast":
                        mealType = Constants.MealTypes.BREAKFAST;
                        break;
                    case "Lunch":
                        mealType = Constants.MealTypes.LUNCH;
                        break;
                    case "Dinner":
                        mealType = Constants.MealTypes.DINNER;
                        break;
                    default:
                        mealType = -1;
                }
            } else {
                mealType = -1;
            }
        });

        tvWeekDay.setOnClickListener(v -> showDatePickerDialog(context));

        btnConfirm.setOnClickListener(v -> {
            if (meal != null) {
                if (isValid(context)) {
                    PlannedMeal plannedMeal = new PlannedMeal(date.getTime(), day, mealType, meal);
                    onBottomConfirmed.onClick(plannedMeal,bottomSheetDialog);
                }
            }
        });
        bottomSheetDialog.show();
    }

    private static boolean isValid(Context context) {
        if (mealType == -1) {
            Toast.makeText(context, "Choose your meal type.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (day == -1) {
            Toast.makeText(context, "Choose a date to plan the meal.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public interface OnBottomConfirmed {
        void onClick(PlannedMeal plannedMeal,BottomSheetDialog dialog);
    }

    private static int getDayNum(String dayStr) {
        switch (dayStr) {
            case "Saturday":
                return Constants.DAYS.SATURDAY;
            case "Sunday":
                return Constants.DAYS.SUNDAY;
            case "Monday":
                return Constants.DAYS.MONDAY;
            case "Tuesday":
                return Constants.DAYS.TUESDAY;
            case "Wednesday":
                return Constants.DAYS.WEDNESDAY;
            case "Thursday":
                return Constants.DAYS.THURSDAY;
            case "Friday":
                return Constants.DAYS.FRIDAY;
            default:
                return -1;
        }
    }
}

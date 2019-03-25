package peaceinfotech.malegaonbazar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

   final public static String DATABASE_NAME="Offers.db";
   final public static String TABLE_NAME="offers_table";
   final public static String COL_1="ID";
   final public static String COL_2="TITLE";
   final public static String COL_3="DESCRIPTION";
   final public static String COL_4="MIN";
   final public static String COL_5="MAX";
   final public static String COL_6="DETAILS";
   final public static String COL_7="START_DATE";
   final public static String COL_8="EXPIRY_DATE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLE_NAME+"("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_2+" TEXT,"+COL_3+" TEXT,"+COL_4+" TEXT,"+COL_5+" TEXT,"+COL_6+" TEXT,"+COL_7+" TEXT,"+COL_8+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean InsertData (String title,String desc,String min,String max,String details,String startDate,String exDate){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,title);
        contentValues.put(COL_3,desc);
        contentValues.put(COL_4,min);
        contentValues.put(COL_5,max);
        contentValues.put(COL_6,details);
        contentValues.put(COL_7,startDate);
        contentValues.put(COL_8,exDate);
        Long result= db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }
        else {
            return true;
        }
    }


    public Cursor GetAllOffers(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Cursor GetSpecificOffer(String id){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where "+COL_1+"="+id,null);
        if (res != null) {
            res.moveToFirst();
        }
        return res;
    }

    public int DeleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[]{id});
    }

    public boolean UpdateData (String id,String title,String desc,String min,String max,String details,String startDate,String exDate){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,title);
        contentValues.put(COL_3,desc);
        contentValues.put(COL_4,min);
        contentValues.put(COL_5,max);
        contentValues.put(COL_6,details);
        contentValues.put(COL_7,startDate);
        contentValues.put(COL_8,exDate);
        int result= db.update(TABLE_NAME,contentValues,COL_1+"="+id,null);
        if(result==0){
            return false;
        }
        else {
            return true;
        }
    }

}

package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.ContentValues;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.Tasks;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.TableConfigurationDatabase;

public class TBTasksDatabase extends TableConfigurationDatabase<Tasks> {

    public enum Fields {
        ID_TASK,
        TITLE,
        ID_CLIENT,
        ALL_DAY,
        DATE,
        HOUR,
        OBSERVATION
    }

    public TBTasksDatabase(Context context) {
        super(context);
        super.Table = "TB_TASKS";
    }

    public static TBTasksDatabase newInstance(Context context){
        return new TBTasksDatabase(context);
    }

    @Override
    public List<Tasks> Select(long id) {
        super.openDatabaseInstance();
        try{
            List<Tasks> list = new ArrayList<Tasks>();

            if (id > 0){
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Where " + Fields.ID_TASK + " = " + id
                        + " Order by " + Fields.ID_TASK.name();
            }else{
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Order by " + Fields.ID_TASK.name();
            }

            this.cursor = this.database.rawQuery(this.SQL,null);

            if (this.cursor.getCount()>0){
                this.cursor.moveToFirst();
                Tasks obj;
                do{
                    obj = new Tasks();

                    obj.setTasksId(this.cursor.getInt(0));
                    obj.setTitle(this.cursor.getString(1));
                    obj.setClienteId(this.cursor.getInt(2));
                    obj.setAllDay(this.cursor.getInt(3));
                    obj.setDate(this.cursor.getString(4));
                    obj.setHour(this.cursor.getString(   5));
                    obj.setObservation(this.cursor.getString(6));

                    list.add(obj);
                }while (this.cursor.moveToNext());
            }

            this.cursor.close();

            return list;
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    @Override
    public long Insert(Tasks tasks) {
        super.openDatabaseInstance();
        try{
            ContentValues values = new ContentValues();

            values.put(Fields.TITLE.name(),tasks.getTitle());
            values.put(Fields.ID_CLIENT.name(),tasks.getClienteId());
            values.put(Fields.ALL_DAY.name(),tasks.getAllDay());
            values.put(Fields.DATE.name(),tasks.getDate());
            values.put(Fields.HOUR.name(),tasks.getHour());
            values.put(Fields.OBSERVATION.name(),tasks.getObservation());

            return this.database.insert(this.Table,null,values);
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    @Override
    public Boolean Update(Tasks tasks) {
        super.openDatabaseInstance();
        try {
            ContentValues values = new ContentValues();

            values.put(Fields.TITLE.name(), tasks.getTitle());
            values.put(Fields.ID_CLIENT.name(), tasks.getClienteId());
            values.put(Fields.ALL_DAY.name(), tasks.getAllDay());
            values.put(Fields.DATE.name(), tasks.getDate());
            values.put(Fields.HOUR.name(), tasks.getHour());
            values.put(Fields.OBSERVATION.name(), tasks.getObservation());

            this.database.update(this.Table, values,
                    Fields.ID_TASK.name() + " = " + tasks.getTasksId(),
                    null);

            return true;
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    @Override
    public Boolean Delete(Tasks tasks) {
        super.openDatabaseInstance();
        try {
            if (tasks.getTasksId() > 0) {
                this.database.delete(this.Table,
                        Fields.ID_TASK.name() + " = " + tasks.getTasksId(),
                        null);
            }
            return true;
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    @Override
    protected String getFields() {
        String StringFields = "";

        for(Fields Field: Fields.values()){
            StringFields += Field.name() + ",";
        }

        if (StringFields.length() > 0){
            StringFields = StringFields.substring(0,StringFields.length()-1);
        }else{
            StringFields = "*";
        }

        return StringFields;
    }
}

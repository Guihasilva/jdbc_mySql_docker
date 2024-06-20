package App.repository;

import App.Listener.CustomRowSetListener;
import App.conn.ConnectionFactory;
import App.dominio.Producer;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProducerRepositoryRowSet {
    public static List<Producer> findByNameJdbcRowSet(String name){
        String sql = "SELECT * FROM anime_store.producer where name like ?;";
        List<Producer> producers = new ArrayList<>();
        try(JdbcRowSet jrs = ConnectionFactory.getJdbcRowSet()) {
            jrs.addRowSetListener(new CustomRowSetListener());
            jrs.setCommand(sql);
            jrs.setString(1,name);
            jrs.execute();
            while(jrs.next()){
                producers.add(Producer.builder()
                        .id(jrs.getInt("id"))
                        .name(jrs.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producers;
    }

    public static void updateJdbcRowSet(Producer producer){
        String sql = "SELECT * FROM anime_store.producer WHERE id = ?;";
        try(JdbcRowSet jrs = ConnectionFactory.getJdbcRowSet()) {
            jrs.addRowSetListener(new CustomRowSetListener());
            jrs.setCommand(sql);
            jrs.setInt(1, producer.getId());
            jrs.execute();
            if(jrs.next()) {
                jrs.updateString("name", producer.getName());
                jrs.updateRow();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateCachedRowSet(Producer producer){
        String sql = "SELECT * FROM anime_store.producer WHERE id = ?;";
        try(CachedRowSet crs = ConnectionFactory.getCachedRowSet()) {
            //crs.addRowSetListener(new CustomRowSetListener());
            crs.setCommand(sql);
            crs.setInt(1, producer.getId());
            crs.execute();
            if(crs.next()) {
                crs.updateString("name", producer.getName());
                crs.updateRow();
                crs.acceptChanges();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

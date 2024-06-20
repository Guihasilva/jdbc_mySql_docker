package App.repository;

import App.conn.ConnectionFactory;
import App.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2

public class ProducerRepository {
    public static void save(Producer producer){
        String sql = "INSERT INTO `anime_store`.`producer` (`name`) VALUES ('%s');".formatted(producer.getName());
        try(Connection conn = ConnectionFactory.getConnection()) {
            Statement stmt = conn.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            log.info("Inserted producer '{}' database rows affected '{}'", producer.getName(), rowsAffected);

        } catch (SQLException e) {
            log.error("Error while trying to insert producer '{}'", producer.getName(), e);
        }

    };

    public static void delete(int id){
        String sql = "DELETE FROM `anime_store`.`producer` WHERE (`id` = '%d')".formatted(id);

        try(Connection conn = ConnectionFactory.getConnection()) {
            Statement stmt = conn.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            log.info("Deleted producer '{}' database rows affected '{}'", id, rowsAffected);

        } catch (SQLException e) {
            log.error("Error while trying to insert producer '{}'", id, e);
        }

    };

    public static void update(Producer producer){
        String sql = "UPDATE `anime_store`.`producer` SET `name` = ? WHERE (`id` = ?);";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, producer.getName());
            ps.setInt(2, producer.getId());
            int rowsAffected = ps.executeUpdate();
            log.info("Updated producer '{}' database rows affected '{}'", producer.getId(), rowsAffected);

        } catch (SQLException e) {
            log.error("Error while trying update producer '{}'", producer.getId(), e);
        }

    };


    public static void saveTransaction (List<Producer> producer) {
        String sql = "INSERT INTO `anime_store`.`producer` (`name`) VALUES (?);";
        try(Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                for(Producer p : producer){
                    ps.setString(1,p.getName());
                log.info("Adicionando producer '{}'' ", p.getName());
                if(p.getName().equals("WhiteFox"))throw new SQLException(p.getName() + " não pode ser inserido, esta proibido");
                ps.execute();
                }
                conn.commit();
            }catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            log.error("Error while trying to insert producer ", e);

            throw new RuntimeException(e);
        }
    }


    public static List<Producer> findAll() {
        log.info("Finding all");
      return findByName("");
    }



    public static List<Producer> findByName(String names) {
        List<Producer> producers = new ArrayList<>();
        log.info("Finding by name");
        String sql = "SELECT * FROM anime_store.producer where name like '%%%s%%';"
                .formatted(names);

        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                var id =  rs.getInt("id");
                var name =  rs.getString("name");

                Producer producer = Producer.builder().id(id).name(name).build();
                producers.add(Producer.builder().id(id).name(name).build());



            }
        } catch (SQLException e) {
            log.error("Error at Trying finding all producers ", e);
        }
        return producers;
    }


    public static void showTypeScrollWorking() {
        String sql = "SELECT * FROM anime_store.producer ;";
        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(sql);
            log.info("Last row '{}'", rs.last());
            log.info("Row Number '{}' ", rs.getRow());
            log.info(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")));

            log.info("\n");
            log.info("First row '{}'", rs.first());
            log.info("Row Number '{}' ", rs.getRow());
            log.info(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")));

            log.info("\n");
            log.info("Absolute row '{}'", rs.absolute(2));
            log.info("Row Number '{}' ", rs.getRow());

            log.info(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")));

            log.info("\n");
            log.info("Relative row '{}'", rs.relative(-1));
            log.info("Row Number '{}' ", rs.getRow());
            log.info(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")));

            log.info("\n");
            log.info("is last ?  row '{}'", rs.isLast());
            log.info("Row Number '{}' ", rs.getRow());

            log.info("\n");
            log.info("is first ?  row '{}'", rs.isFirst());
            log.info("Row Number '{}' ", rs.getRow());

        } catch (SQLException e) {
            log.error("Error at Trying finding all producers ", e);
        }
    }



    public static void showProducerMetadata() {
        log.info("Finding by MetaData");
        String sql = "SELECT * FROM anime_store.producer;";
        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            rs.next();
            int columnCount = metaData.getColumnCount();
            log.info("Columns count '{}'" , columnCount);
            for (int i = 1; i <= columnCount; i++) {
                 log.info("Table name '{}'", metaData.getTableName(i));
                 log.info("Column name '{}'", metaData.getColumnName(i));
                 log.info("Column size '{}'", metaData.getColumnDisplaySize(i));
                 log.info("Column type '{}'", metaData.getColumnType(i));
            }

        } catch (SQLException e) {
            log.error("Error at show ProductMetaData ", e);
        }

    }



    public static void showDriverMetaData() {
        log.info("Finding by DriverMetaData");
        String sql = "SELECT * FROM anime_store.producer;";
        try (Connection conn = ConnectionFactory.getConnection()) {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            if(dbMetaData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)){
                log.info("Supports ResultSet.TYPE_FORWARD_ONLY");
                if(dbMetaData.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)){
                    log.info("Supports ResultSet.TYPE_FORWARD_ONLY and ResultSet.CONCUR_UPDATABLE");
                }
            }
            if(dbMetaData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)){
                log.info("Supports ResultSet.TYPE_SCROLL_INSENSITIVE");
                if(dbMetaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                    log.info("Supports ResultSet.TYPE_INSENSEITIVE and ResultSet.CONCUR_UPDATABLE");
                }
            }
            if(dbMetaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)){
                log.info("Supports ResultSet.SCROLL_SENSITIVE");
                if(dbMetaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                    log.info("Supports ResultSet.TYPE_SCROLL SENSITIVE and ResultSet.CONCUR_UPDATABLE");
                }
            }
        } catch (SQLException e) {
            log.error(" ", e);
        }

    }

    public static List<Producer> findByNameAndUpdateToUpperCase(String names) {
        List<Producer> producers = new ArrayList<>();
        String sql = "SELECT * FROM anime_store.producer where name like '%%%s%%';"
                .formatted(names);

        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
               rs.updateString("name", rs.getString("name").toUpperCase());
                rs.updateRow();

                Producer producer = Producer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name")).build();
                producers.add(producer);



            }
        } catch (SQLException e) {
            log.error("Error at Trying finding all producers ", e);
        }
        return producers;
    }

    public static List<Producer> findByNameAndInsertWhenNotFound(String name) {
        List<Producer> producers = new ArrayList<>();
        String sql = "SELECT * FROM anime_store.producer where name like ?;";

        try (Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                rs.moveToInsertRow();
                rs.updateString("name",name);
                rs.insertRow();
                rs.beforeFirst();
                rs.next();
                Producer producer = Producer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build();
                producers.add(producer);

            }
        } catch (SQLException e) {
            log.error("Error at Trying finding all producers ", e);
        }
        return producers;
    }


    public static List<Producer> findByNameCallableStatement(String name){
        String sql = "CALL `anime_store`.`sp_get_producer_by_name`(?);";
        List<Producer> producers = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1,name);
            ResultSet rs = cs.executeQuery();
            while(rs.next()) {
                producers.add(Producer.builder()
                                .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return producers;
    }

}

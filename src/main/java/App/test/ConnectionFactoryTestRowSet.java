package App.test;

import App.dominio.Producer;
import App.repository.ProducerRepositoryRowSet;
import lombok.extern.log4j.Log4j2;
import java.util.List;

@Log4j2
public class ConnectionFactoryTestRowSet {
    public static void main(String[] args) {
//       List<Producer> producerList =   ProducerRepositoryRowSet.findByNameJdbcRowSet("NHK");
//     log.info(producerList);

        Producer producer = Producer.builder()
                .id(27)
                .name("MadHouse")
                .build();


    }
}

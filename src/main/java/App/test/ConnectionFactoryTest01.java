package App.test;

import App.Service.ProducerService;
import App.dominio.Producer;
import App.repository.ProducerRepository;
import lombok.extern.log4j.Log4j2;

import java.util.List;


import java.util.List;
@Log4j2
public class ConnectionFactoryTest01 {
    public static void main(String[] args) {
        Producer producer = Producer.builder()
                .name("MadHouse")
                .build();


        Producer producerToUpdate = Producer.builder()
                .id(26)
               .name("MADHOUSE")
                .build();

        //ProducerRepository.update(producerToUpdate);
       //ProducerService.save(producer);
        //ProducerRepository.delete(1);
      // List<Producer> producers =  ProducerService.findAll();
      // List<Producer> producersByName =  ProducerService.findByName("Mad");
        //ProducerService.showMetadata();
        //ProducerService.showDriverMetaData();
       // log.info(producers);
       // ProducerService.showTypeScrollWorking();
      // ProducerService.findByNameAndUpdateToUpperCase("deen");
        //ProducerService.findByNameAndInsertWhenNotFound("Bones");

       // List<Producer> producersList = ProducerService.findByNameCallableStatement("NHK");





    }
}

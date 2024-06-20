package App.Service;

import App.dominio.Producer;
import App.repository.ProducerRepository;

import java.util.List;

public class ProducerService {

    public static void save(Producer producer) {
        ProducerRepository.save(producer);
    }
    public static void update(Producer producer) {
        requiredValidId(producer.getId());
        ProducerRepository.update(producer);
    }

    public static List<Producer> findAll() {
        return ProducerRepository.findAll();
    }

    public static void delete(int id) {
        requiredValidId(id);
        ProducerRepository.delete(id);
    }

    public static void findByNameAndUpdateToUpperCase(String name){
        ProducerRepository.findByNameAndUpdateToUpperCase(name);
    }

    public static void showMetadata() {
        ProducerRepository.showProducerMetadata();
    }
    public static void showDriverMetaData() {
        ProducerRepository.showDriverMetaData();
    }

    public static void showTypeScrollWorking() {
        ProducerRepository.showTypeScrollWorking();
    }


    public static List<Producer> findByName(String name){return ProducerRepository.findByName(name);}

    public static List<Producer> findByNameAndInsertWhenNotFound(String name){
        return ProducerRepository.findByNameAndInsertWhenNotFound(name);
    }

    public static List<Producer> findByNameCallableStatement(String name){
        return ProducerRepository.findByNameCallableStatement(name);
    }
    public static void saveTransaction(List<Producer> producer){
        ProducerRepository.saveTransaction(producer);
    }

    private static void requiredValidId(Integer id) {
        if(id == null && id <= 0 ){
            throw new IllegalArgumentException("Id must be greater than 0");
        }
    }
}

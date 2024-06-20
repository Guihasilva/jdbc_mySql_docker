package App.test;

import App.dominio.Producer;
import App.repository.ProducerRepository;

import java.util.List;

public class ConnectionFactoryTestCommit {
    public static void main(String[] args) {
        Producer producer1 = Producer.builder().name("Toei Animation").build();
        Producer producer2 = Producer.builder().name("WhiteFox").build();
        Producer producer3 = Producer.builder().name("Studio Ghibli").build();

        ProducerRepository.saveTransaction(List.of(producer1,producer2,producer3));
    }
}

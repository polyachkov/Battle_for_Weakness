package nsu.ru.BattleForWeakness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class BattleForWeaknessApplication {

	public BattleForWeaknessApplication() throws FileNotFoundException {
	}

	public static void main(String[] args) {
		SpringApplication.run(BattleForWeaknessApplication.class, args);
	}
}
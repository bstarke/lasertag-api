package net.starkenberg.training.lasertagapi.game;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfig {

    @Bean(name = "theGame")
    public Game singletonGame() {
        return new Game();
    }
}

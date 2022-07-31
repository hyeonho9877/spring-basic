package spring.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService s1 = ac.getBean(StatefulService.class);
        StatefulService s2 = ac.getBean(StatefulService.class);

        // ThreadA : A 사용자가 1 만원 주문
        int userAPrice = s1.order("userA", 10000);
        // ThreadB : B 사용자가 2 만원 주문
        int userBPrice = s2.order("userB", 20000);

        // ThreadA : 사용자 A가 주문 금액 조회
        System.out.println("userAPrice = " + userAPrice);
        Assertions.assertThat(userAPrice).isNotSameAs(userBPrice);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}
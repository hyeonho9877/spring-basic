package spring.core.autowired;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.core.AutoAppConfig;
import spring.core.discount.DiscountPolicy;
import spring.core.member.Grade;
import spring.core.member.Member;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int fixDiscount = discountService.discount(member, 10000, "fixDiscountPolicy");
        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(fixDiscount).isEqualTo(1000);

        int rateDiscount = discountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(rateDiscount).isEqualTo(2000);
    }


    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> discountPolicies;

        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> discountPolicies) {
            this.policyMap = policyMap;
            this.discountPolicies = discountPolicies;

            System.out.println("policyMap = " + policyMap);
            System.out.println("discountPolicies = " + discountPolicies);
        }

        public int discount(Member member, int price, String discountPolicy) {
            DiscountPolicy policy = policyMap.get(discountPolicy);
            return policy.discount(member, price);
        }
    }
}

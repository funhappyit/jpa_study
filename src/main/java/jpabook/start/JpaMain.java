package jpabook.start;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // [엔티티 메니저 팩토리]- 생성 JPA
        // 엔티티 메니저 팩토리는 애플리케이션 전체에서 딱 한 번만 생성하고 공유해서 사용해야 한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        //[엔티티 메니저] - 생성
        //엔티티 메니저를 사용해서 엔티티를 데이터베이스에 등록/수정/삭제/조회할 수 있다.
        EntityManager em = emf.createEntityManager();
        //[트랜잭션] - 획득
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin(); //[트랜잭션] -시작
            criteriaQuery(em); //비즈니스 로직 실행
            tx.commit(); //[트랜잭션] - 커밋
        }catch (Exception e){
            tx.rollback(); //[트랜잭션] - 롤백
        }finally {
            em.close(); //[엔티티 메니저] - 종료
        }
        emf.close(); //[엔티티 매니저 팩토리] - 종료
    }

    //비즈닌스 로직
    private static void logic(EntityManager em){
        Delivery delivery = new Delivery();
        OrderItem orderItem1 = new OrderItem();
        OrderItem orderItem2 = new OrderItem();

        Order order = new Order();
        order.setDelivery(delivery);
        order.addOrderItem(orderItem1);
        order.addOrderItem(orderItem2);

        em.persist(order);
    }

    private static void multiQueryEntity(EntityManager em){
        List resultList = em.createQuery("select i from Item i where treat (i as Book ).author = 'kim'").getResultList();
    }
    private static void qlString(EntityManager em){
        String qlString = "select m from Member m where m.id = :memberId";
        List resultList = em.createQuery(qlString)
            .setParameter("memberId",4L)
            .getResultList();
    }
    private static void nameQuery(EntityManager em){
        List<Member> resultList = em.createNamedQuery("Member.findByUsername",Member.class)
            .setParameter("name","회원1")
            .getResultList();
    }

    private static void criteriaQuery(EntityManager em){
        CriteriaBuilder cb = em.getCriteriaBuilder();//Criteria 쿼리 빌더
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> m = cq.from(Member.class);
        cq.select(m);
        TypedQuery<Member> query = em.createQuery(cq);
        List<Member> members = query.getResultList();
    }



}

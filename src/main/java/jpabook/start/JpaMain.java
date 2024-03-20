package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
            logic(em); //비즈니스 로직 실행
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
        //실행로직
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        //등록
        em.persist(member);

        //수정
        member.setAge(20);

        //한 건 조회
        Member findMember = em.find(Member.class,id);
        System.out.println("findMember="+findMember.getUsername()+" ,"+findMember.getAge());

        //목록 조회
        List<Member> members = em.createQuery("select m from Member m",Member.class).getResultList();
        System.out.println("members.size = "+members.size());

        //삭제
        em.remove(member);
    }
}

package jpabook.start;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(
    name="Member.findByUsername",
    query ="select m from Member m where m.name=:name"
)
public class Member{

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // @OneToMany(mappedBy = "member")
    // private List<Order> orders = new ArrayList<>();

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public List<Order> getOrders() {
    //     return orders;
    // }
    //
    // public void setOrders(List<Order> orders) {
    //     this.orders = orders;
    // }
}

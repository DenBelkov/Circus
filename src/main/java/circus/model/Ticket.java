package circus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Выступление, на которое куплен билет.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    /**
     * ФИО покупателя.
     */
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    /**
     * Количество зрителей в заказе.
     */
    @Column(name = "viewers_count", nullable = false)
    private Integer viewersCount;

    /**
     * Суммарная стоимость билетов в заказе.
     */
    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    public Ticket() {
    }

    // getters/setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    ;

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getViewersCount() {
        return viewersCount;
    }

    public void setViewersCount(Integer viewersCount) {
        this.viewersCount = viewersCount;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
}

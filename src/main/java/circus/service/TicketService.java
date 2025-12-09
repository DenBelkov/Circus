package circus.service;

import circus.model.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> findAll();

    List<Ticket> findByPerformanceId(Long performanceId);

    Ticket findById(Long id);

    Ticket save(Ticket ticket);

    void deleteById(Long id);
}

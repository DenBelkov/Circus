package circus.controller;

import circus.model.Performance;
import circus.model.Ticket;
import circus.service.PerformanceService;
import circus.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketWebController {

    private final TicketService ticketService;
    private final PerformanceService performanceService;

    public TicketWebController(TicketService ticketService,
                               PerformanceService performanceService) {
        this.ticketService = ticketService;
        this.performanceService = performanceService;
    }

    @GetMapping
    public String listTickets(Model model) {
        model.addAttribute("tickets", ticketService.findAll());
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("performances", performanceService.findAll());
        return "tickets";
    }

    @PostMapping("/save")
    public String saveTicket(@ModelAttribute("ticket") Ticket ticket,
                             @RequestParam(required = false, defaultValue = "tickets") String returnTo) {
        // загружаем performance по id
        Long perfId = ticket.getPerformance() != null
                ? ticket.getPerformance().getId()
                : null;

        if (perfId != null) {
            Performance performance = performanceService.findById(perfId);
            ticket.setPerformance(performance);
        }

        ticketService.save(ticket);

        // Редирект в зависимости от параметра
        if ("performances".equals(returnTo)) {
            return "redirect:/performances";
        } else {
            return "redirect:/tickets";
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.deleteById(id);
        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String editTicket(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.findById(id);
        model.addAttribute("tickets", ticketService.findAll());
        model.addAttribute("ticket", ticket);
        model.addAttribute("editingId", id);
        model.addAttribute("performances", performanceService.findAll());
        return "tickets";
    }
}

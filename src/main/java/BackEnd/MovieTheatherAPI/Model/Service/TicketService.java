package BackEnd.MovieTheatherAPI.Model.Service;

import BackEnd.MovieTheatherAPI.Model.Entity.ClientEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.SeatEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.TicketEntity;
import BackEnd.MovieTheatherAPI.Model.Repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ClientService clientService;
    private final RoomService roomService;

    public TicketEntity compraDeIngresso(ClientEntity client, SeatEntity seat){
        var ticket = new TicketEntity();
        ticket.setClient(client);
        return ticket;
    }

}

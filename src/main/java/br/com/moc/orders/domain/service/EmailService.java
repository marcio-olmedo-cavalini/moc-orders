package br.com.moc.orders.domain.service;

import br.com.moc.orders.domain.model.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {

    public void sendEmail(OrderEntity order) {
        String emailContent = "The order that you requested was COMPLETED!";
        log.debug("==============================================================================");
        log.debug("=                         SEND E-MAIL SERVICE                                ");
        log.debug("= > Order Completed: {}                                                      ", order.getId());
        log.debug("= > e-mail to: {}                                                            ", order.getUser().getEmail());
        log.debug("= > e-mail subject: Order {} Completed                                       ", order.getId());
        log.debug("= > e-mail content: {}                                                       ", emailContent);
        log.debug("==============================================================================");
    }
}

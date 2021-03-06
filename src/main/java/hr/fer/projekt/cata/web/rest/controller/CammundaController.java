package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.service.CamundaService;
import hr.fer.projekt.cata.web.rest.dto.CamundaDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(("/api/camunda"))
public class CammundaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CammundaController.class);
    private final CamundaService camundaService;

    @PostMapping("/notify/user/reminder")
    public void remindToPay(@RequestBody CamundaDto camundaDto) {
        LOGGER.info("remindToPay:" + camundaDto.toString());
        camundaService.remindToPay(camundaDto.getUserId(), camundaDto.getTripId());
    }

    @PostMapping("/notify/organizer/user-quit")
    public void userQuit(@RequestBody CamundaDto camundaDto) {
        LOGGER.info("userQuit:" + camundaDto.toString());
        camundaService.notifyOrganizers(camundaDto.getUserId(), camundaDto.getTripId(), "Change mind");
    }

    @PostMapping("/notify/organizer/not-paid")
    public void notPaid(@RequestBody CamundaDto camundaDto) {
        LOGGER.info("notPaid:" + camundaDto.toString());
        camundaService.notifyOrganizers(camundaDto.getUserId(), camundaDto.getTripId(), "Not paid");
    }

    @PostMapping("/notify/passengers")
    public void notifyPassengers(@RequestBody CamundaDto camundaDto) {
        LOGGER.info("notifyPassengers:" + camundaDto.toString());
        camundaService.notifyPassengers(camundaDto.getTripId());
    }
}

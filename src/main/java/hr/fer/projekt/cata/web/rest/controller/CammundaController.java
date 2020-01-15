package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.service.CammundaService;
import hr.fer.projekt.cata.web.rest.dto.CamundaDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(("/api/camunda"))
public class CammundaController {

	private CammundaService cammundaService;

	@PostMapping("/notify/user/reminder")
	@CrossOrigin
	private void remindToPay(@RequestBody CamundaDto camundaDto) {
		cammundaService.remindToPay(camundaDto.getUserId(), camundaDto.getTripId());
	}

	@PostMapping("/notify/organizer/user-quit")
	@CrossOrigin
	private void userQuit(@RequestBody CamundaDto camundaDto) {
		cammundaService.notifyOrganizers(camundaDto.getUserId(), camundaDto.getTripId(), "Change mind");
	}

	@PostMapping("/notify/organizer/not-paid")
	@CrossOrigin
	private void notPaiid(@RequestBody CamundaDto camundaDto) {
		cammundaService.notifyOrganizers(camundaDto.getUserId(), camundaDto.getTripId(), "Not paid");
	}

	@PostMapping("/notify/passengers")
	@CrossOrigin
	private void notifyPassengers(@RequestBody CamundaDto camundaDto) {
		cammundaService.notifyPassengers(camundaDto.getTripId());
	}
}

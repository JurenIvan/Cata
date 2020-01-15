package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.service.CammundaService;
import hr.fer.projekt.cata.service.EmailSender;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@AllArgsConstructor
@RestController
@RequestMapping(("/api/camunda"))
public class CammundaController {

	private CammundaService cammundaService;

	@GetMapping("/notify/user/reminder")
	@CrossOrigin
	private void remindToPay(@RequestParam Long userId, @RequestParam Long tripId) {
		cammundaService.remindToPay(userId,tripId);
	}

	@GetMapping("/notify/organizer/reminder")
	@CrossOrigin
	private void notifyOrganizers(@RequestParam Long userId, @RequestParam Long tripId) {
		cammundaService.notifyOrganizers(userId,tripId);
	}

	@GetMapping("/notify/passengers")
	@CrossOrigin
	private void notifyPassengers(@RequestParam Long tripId) {
		cammundaService.notifyPassengers(tripId);

	}


}
